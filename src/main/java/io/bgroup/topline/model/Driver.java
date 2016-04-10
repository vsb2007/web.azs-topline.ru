package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Driver {
    @Autowired
    private DbModel dbMvc;

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

    public Driver getDriver(String idDriver) {
        ArrayList<Driver> driverList = null;
        String sql = "select * from drivers where id_drivers='" + idDriver + "'";
        driverList = getDriverFromDbSelect(sql);
        if (driverList == null || driverList.size() == 0) return null;
        return driverList.get(0);
    }

    private ArrayList<Driver> getDriverFromDbSelect(String sql) {
        ResultSet resultSet = null;
        try {
            resultSet = dbMvc.getSelectResult(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resultSet == null) return null;
        ArrayList<Driver> driverList = null;
        try {
            while (resultSet.next()) {
                Driver driver = new Driver();
                setDriverFromResultSet(driver,resultSet);
                if (driverList == null) driverList = new ArrayList<Driver>();
                driverList.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return driverList;
    }
    private void setDriverFromResultSet(Driver driver, ResultSet resultSet){
        try {
            if (resultSet != null) {
                String driverId = resultSet.getString(DbModel.tableDrivers.id_drivers.toString());
                String driverBlock = resultSet.getString(DbModel.tableDrivers.drivers_block.toString());
                String driverEmail = resultSet.getString(DbModel.tableDrivers.drivers_email.toString());
                String driverPhone = resultSet.getString(DbModel.tableDrivers.drivers_phone.toString());
                String driverFio = resultSet.getString(DbModel.tableDrivers.drivers_fio.toString());

                driver.setDriverBlock(driverBlock);
                driver.setDriverEmail(driverEmail);
                driver.setDriverPhone(driverPhone);
                driver.setDriverFio(driverFio);
                driver.setIdDriver(driverId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
