package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Driver {
    @Autowired
    private DbModel db;

    private String idDriver;
    private String driverFio;
    private String driverPhone;
    private String driverEmail;
    private String driverBlock;

    public String getIdDriver() {
        return idDriver;
    }

    private void setIdDriver(String idDriver) {
        this.idDriver = idDriver;
    }

    public String getDriverFio() {
        return driverFio;
    }

    private void setDriverFio(String driverFio) {
        this.driverFio = driverFio;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    private void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    private void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getDriverBlock() {
        return driverBlock;
    }

    private void setDriverBlock(String driverBlock) {
        this.driverBlock = driverBlock;
    }

    public ArrayList<Driver> getDriverList() {
        ArrayList<Driver> driverList = null;
        String sql = "select * from drivers where drivers_block='0'";
        driverList = getDriverFromDbSelect(sql);
        return driverList;
    }

    private ArrayList<Driver> getDriverFromDbSelect(String sql) {
        List<Map<String, Object>> driverListFromDb = null;
        driverListFromDb = db.getSelectResult(sql);
        if (driverListFromDb == null) return null;
        ArrayList<Driver> driverList = null;
        for (Map row : driverListFromDb) {
            Driver driver = new Driver();
            driver.setDriverBlock((String) row.get("drivers_block").toString());
            driver.setDriverEmail((String) row.get("drivers_email").toString());
            driver.setDriverPhone((String) row.get("drivers_phone").toString());
            driver.setDriverFio((String) row.get("drivers_fio").toString());
            driver.setIdDriver((String) row.get("id_drivers").toString());
            if (driverList == null) driverList = new ArrayList<Driver>();
            driverList.add(driver);
        }
        return driverList;
    }
}
