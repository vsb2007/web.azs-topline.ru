package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Car {
    private String id_cars;
    private String cars_number;
    private String cars_name;
    private String cars_sec1;
    private String cars_sec2;
    private String cars_sec3;
    private String cars_sec4;
    private String cars_sec5;
    private String cars_sec6;
    private String cars_block;
    private ArrayList<CarSections> carSections;

    @Autowired
    private DbModel db;

    public ArrayList<Car> getCarsList() {
        List<Map<String, Object>> carsListFromDb = null;
        ArrayList<Car> carsList = null;
        String sql = "select * from cars where cars_block='0'";
        carsListFromDb = db.getSelectResult(sql);
        if (carsListFromDb == null) return null;
        carsList = getCarsFromDbSelect(carsListFromDb);
        return carsList;
    }

    private ArrayList<Car> getCarsFromDbSelect(List<Map<String, Object>> carsListFromDb) {
        ArrayList<Car> carsList = null;
        for (Map row : carsListFromDb) {
            Car car = new Car();
            car.setCars_block((String) row.get("cars_block").toString());
            car.setId_cars((String) row.get("id_cars").toString());
            car.setCars_number((String) row.get("cars_number").toString());
            car.setCars_name((String) row.get("cars_name").toString());
            String cars_sec1;
            String cars_sec2;
            String cars_sec3;
            String cars_sec4;
            String cars_sec5;
            String cars_sec6;


            car.setCars_sec1((String) row.get("cars_sections_1").toString());
            car.setCars_sec2((String) row.get("cars_sections_2").toString());
            car.setCars_sec3((String) row.get("cars_sections_3").toString());
            car.setCars_sec4((String) row.get("cars_sections_4").toString());
            car.setCars_sec5((String) row.get("cars_sections_5").toString());
            car.setCars_sec6((String) row.get("cars_sections_6").toString());
            if (carsList == null) carsList = new ArrayList<Car>();
            carsList.add(car);
        }
        return carsList;
    }

    public ArrayList<CarSections> getCarSections() {
        return carSections;
    }

    private void setCarSections(ArrayList<CarSections> carSections) {
        this.carSections = carSections;
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

    public String getCars_sec1() {
        return cars_sec1;
    }

    private void setCars_sec1(String cars_sec1) {
        this.cars_sec1 = cars_sec1;
    }

    public String getCars_sec2() {
        return cars_sec2;
    }

    private void setCars_sec2(String cars_sec2) {
        this.cars_sec2 = cars_sec2;
    }

    public String getCars_sec3() {
        return cars_sec3;
    }

    private void setCars_sec3(String cars_sec3) {
        this.cars_sec3 = cars_sec3;
    }

    public String getCars_sec4() {
        return cars_sec4;
    }

    private void setCars_sec4(String cars_sec4) {
        this.cars_sec4 = cars_sec4;
    }

    public String getCars_sec5() {
        return cars_sec5;
    }

    private void setCars_sec5(String cars_sec5) {
        this.cars_sec5 = cars_sec5;
    }

    public String getCars_sec6() {
        return cars_sec6;
    }

    private void setCars_sec6(String cars_sec6) {
        this.cars_sec6 = cars_sec6;
    }

    public String getCars_block() {
        return cars_block;
    }

    private void setCars_block(String cars_block) {
        this.cars_block = cars_block;
    }
}
