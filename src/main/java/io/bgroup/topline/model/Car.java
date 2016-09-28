package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Car {
    private final int countOilSection = 3;
    private String id_car;
    private String car_number;
    private String car_name;
    private String car_block;
    private ArrayList<OilSections> oilSections;

    @Autowired
    private DbJdbcModel dbMvc;
    @Autowired
    private OilType oilTypeMvc;
    @Autowired
    private OilStorage oilStorageMvc;
    @Autowired
    private Organization organizationMvc;
    @Autowired
    private OilSections oilSectionsMvc;

    public ArrayList<Car> getCarsList() {
        ArrayList<Car> carsList = null;
        String sql = "select * from cars where car_block='0'";
        carsList = getCarsFromDbSelect(sql, null);
        return carsList;
    }

    public Car getCar(String id_car) {
        if (id_car == null) return null;
        int id = -1;
        try {
            id = Integer.parseInt(id_car);
        } catch (Exception e) {
            return null;
        }
        return getCar(id);
    }

    public Car getCar(int id_car) {
        Car car = null;
        String sql = "select * from cars where id_car = ?";
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(id_car);
        car = getCarFromDbSelect(sql, args);
        return car;
    }

    private Car getCarFromDbSelect(String sql, ArrayList<Object> args) {
        ArrayList<Car> carsList = getCarsFromDbSelect(sql, args);
        if (carsList == null) return null;
        return carsList.get(0);
    }

    private ArrayList<Car> getCarsFromDbSelect(String sql, ArrayList<Object> args) {
        List<Map<String, Object>> carsListFromDb = null;
        carsListFromDb = dbMvc.getSelectResult(sql, args);
        if (carsListFromDb == null) return null;
        ArrayList<Car> carsList = null;
        for (Map row : carsListFromDb) {
            Car car = new Car();
            setCarFromMapRow(car, row);
            if (carsList == null) carsList = new ArrayList<Car>();
            carsList.add(car);
        }
        return carsList;
    }

    private void setCarFromMapRow(Car car, Map row) {
       /* Iterator<Map.Entry<String, Object>> iterator = row.entrySet().iterator();
        while (iterator.hasNext()) {
            break;
        }*/
        car.setCar_block((String) row.get("car_block").toString());
        car.setId_car((String) row.get("id_car").toString());
        car.setCar_number((String) row.get("car_number").toString());
        car.setCar_name((String) row.get("car_name").toString());
        ArrayList<OilSections> oilSections = null;
        for (int i = 1; i <= countOilSection; i++) {
            String car_sec = row.get("car_sec_" + i).toString();
            if (!car_sec.equals("0")) {
                if (oilSections == null) {
                    oilSections = new ArrayList<OilSections>();
                    car.setOilSections(oilSections);
                }
                oilSections.add(new OilSections("car_sec_" + i, car_sec));
            }
        }
    }

    public ArrayList<OilSections> getOilSections() {
        return oilSections;
    }

    private void setOilSections(ArrayList<OilSections> oilSections) {
        this.oilSections = oilSections;
    }

    public String getCar_name() {
        return car_name;
    }

    private void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getId_car() {
        return id_car;
    }

    private void setId_car(String id_car) {
        this.id_car = id_car;
    }

    public String getCar_number() {
        return car_number;
    }

    private void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getCar_block() {
        return car_block;
    }

    private void setCar_block(String car_block) {
        this.car_block = car_block;
    }

    public String getCarSectionsForAjax(String idCar) {
        Car car = getCar(idCar);
        if (car == null) return "Error: нет данных по секциям";
        ArrayList<OilSections> carSections = car.getOilSections();
        if (carSections == null) return ""; // если на машине нет секций
        String response = "";
        response += "<ul>";
        ArrayList<OilType> oilTypesList = oilTypeMvc.getOilTypesList();
        ArrayList<OilStorage> oilStorageList = oilStorageMvc.getOilStorageList();
        //ArrayList<Organization> organizationList = organizationMvc.getOrganizationList();
        ArrayList<Organization> organizationList = organizationMvc.getOrganizationListWithSaleBids();
        if (oilStorageList == null || oilTypesList == null) return "Error: не возможно загрузить данные";
        response += oilSectionsMvc.getOilSectionsForAjaxSelect(carSections, oilTypesList, oilStorageList, organizationList);
        response += "</ul>";
        return response;
    }
}
