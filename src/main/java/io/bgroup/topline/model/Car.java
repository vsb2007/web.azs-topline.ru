package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Car {
    private final int countCarSection = 6;
    private String id_cars;
    private String cars_number;
    private String cars_name;
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
            ArrayList<CarSections> carSections = car.getCarSections();
            car.setCars_block((String) row.get("cars_block").toString());
            car.setId_cars((String) row.get("id_cars").toString());
            car.setCars_number((String) row.get("cars_number").toString());
            car.setCars_name((String) row.get("cars_name").toString());
            for (int i = 1; i <= countCarSection; i++) {
                String cars_sec = row.get("cars_sections_" + i).toString();
                if (!cars_sec.equals("0")) {
                    if (carSections == null) {
                        carSections = new ArrayList<CarSections>();
                    }
                    car.setCarSections(carSections);
                    carSections.add(new CarSections("cars_sections_" + i, cars_sec));
                }
            }
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

    public String getCars_block() {
        return cars_block;
    }

    private void setCars_block(String cars_block) {
        this.cars_block = cars_block;
    }
}
