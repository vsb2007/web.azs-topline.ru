package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Car {
    private final int countOilSection = 2;
    private String id_cars;
    private String cars_number;
    private String cars_name;
    private String cars_block;
    private ArrayList<OilSections> oilSections;

    @Autowired
    private DbModel db;
    @Autowired
    private OilType oilType;
    @Autowired
    private OilStorage oilStorage;
    @Autowired
    private Trailer trailer;

    public ArrayList<Car> getCarsList() {
        ArrayList<Car> carsList = null;
        String sql = "select * from cars where cars_block='0'";
        carsList = getCarsFromDbSelect(sql);
        return carsList;
    }

    public Car getCar(String id_car) {

        Car car = null;
        String sql = "select * from cars where id_cars=" + id_car;
        car = getCarFromDbSelect(sql);
        return car;
    }

    private Car getCarFromDbSelect(String sql) {
        ArrayList<Car> carsList = getCarsFromDbSelect(sql);
        if (carsList == null) return null;
        return carsList.get(0);
    }

    private ArrayList<Car> getCarsFromDbSelect(String sql) {
        List<Map<String, Object>> carsListFromDb = null;
        carsListFromDb = db.getSelectResult(sql);
        if (carsListFromDb == null) return null;
        ArrayList<Car> carsList = null;
        for (Map row : carsListFromDb) {
            Car car = new Car();
            ArrayList<OilSections> oilSections = null;
            car.setCars_block((String) row.get("cars_block").toString());
            car.setId_cars((String) row.get("id_cars").toString());
            car.setCars_number((String) row.get("cars_number").toString());
            car.setCars_name((String) row.get("cars_name").toString());
            for (int i = 1; i <= countOilSection; i++) {
                String cars_sec = row.get("cars_sections_" + i).toString();
                if (!cars_sec.equals("0")) {
                    if (oilSections == null) {
                        oilSections = new ArrayList<OilSections>();
                        car.setOilSections(oilSections);
                    }
                    oilSections.add(new OilSections("cars_sections_" + i, cars_sec));
                }
            }
            if (carsList == null) carsList = new ArrayList<Car>();
            carsList.add(car);
        }
        return carsList;
    }

    public ArrayList<OilSections> getOilSections() {
        return oilSections;
    }

    private void setOilSections(ArrayList<OilSections> oilSections) {
        this.oilSections = oilSections;
    }

    public String getCars_name() {
        return cars_name;
    }

    private void setCars_name(String cars_name) {
        this.cars_name = cars_name;
    }

    public String getId_cars() {
        return id_cars;
    }

    private void setId_cars(String id_cars) {
        this.id_cars = id_cars;
    }

    public String getCars_number() {
        return cars_number;
    }

    private void setCars_number(String cars_number) {
        this.cars_number = cars_number;
    }

    public String getCars_block() {
        return cars_block;
    }

    private void setCars_block(String cars_block) {
        this.cars_block = cars_block;
    }

    public String getCarSectionsForAjax(String idCar) {
        Car car = getCar(idCar);
        if (car == null) return "Error: нет данных по секциям";
        String response = "";
        response += "<ul>";
        ArrayList<OilType> oilTypesList = oilType.getOilTypesList();
        ArrayList<OilStorage> oilStorageList = oilStorage.getOilStorageList();
        for (OilSections carSection : car.getOilSections()) {
            response += "<li>" +
                    "Секция " + carSection.getOilSectionName() + " (" + carSection.getVol() + "л.)"
                    + "&nbsp;"
                    + "<select class=\"dropdown-menu\""
                    + "id=\"oilType_" + car.getId_cars() + "_" + carSection.getId_section() + "\">"
                    + "<option value=\"-1\">Пустая секция</option>"
            ;
            for (OilType oilTypeTmp : oilTypesList) {
                response += "<option value=\"" + oilTypeTmp.getId_oilType() + "\">" +
                        oilTypeTmp.getOilTypeName() + "</option>";
            }
            response += "</select>"
                    + "&nbsp;"
                    + "<select class=\"dropdown-menu\""
                    + "id=\"oilStorage_" + car.getId_cars() + "_" + carSection.getId_section() + "\">"
                    + "<option value=\"-1\">Пункт отгрузки</option>";
            for (OilStorage oilStorageTmp : oilStorageList) {
                response += "<option value=\"" + oilStorageTmp.getIdOilStorage() + "\">" +
                        oilStorageTmp.getOilStorageName() + "</option>";
            }
            response += "</select>"
                    + "&nbsp;";
            response += "</li>";
        }
        response += "</ul>";
        return response;
    }
}
