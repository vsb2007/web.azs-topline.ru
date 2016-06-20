package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Driver {
    @Autowired
    private DbJdbcModel dbMvc;
    @Autowired
    private SiteUser siteUserMvc;

    private int idDriver;
    private String driverFio;
    private String driverPhone;
    private String driverEmail;
    private String driverBlock;
    private SiteUser driver;

    public SiteUser getDriver() {
        return driver;
    }

    private void setDriver(SiteUser driver) {
        this.driver = driver;
    }

    public int getIdDriver() {
        return idDriver;
    }

    private void setIdDriver(int idDriver) {
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
        String sql = "select * from users where enabled='1' and user_post_id='2'";
        driverList = getDriverFromDbSelect(sql, null);
        return driverList;
    }

    public Driver getDriver(String idDriver) {
        ArrayList<Driver> driverList = null;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "select * from users where id_user=?";
        args.add(idDriver);
        driverList = getDriverFromDbSelect(sql, args);
        if (driverList == null || driverList.size() == 0) return null;
        return driverList.get(0);
    }

    private ArrayList<Driver> getDriverFromDbSelect(String sql, ArrayList<Object> args) {
        List<Map<String, Object>> driverListFromDb = null;
        driverListFromDb = dbMvc.getSelectResult(sql, args);
        if (driverListFromDb == null) return null;
        ArrayList<Driver> driverList = null;
        for (Map row : driverListFromDb) {
            Driver driver = new Driver();
            Object userIdObject = row.get("id_user");
            Integer userId;
            Long userIdLong;
            if (userIdObject instanceof Long) {
                userIdLong = (Long) userIdObject;
                userId = userIdLong.intValue();
            } else
                userId = (Integer) userIdObject;
            driver.setIdDriver(userId);
            driver.setDriver(siteUserMvc.findSiteUser(driver.getIdDriver()));
            if (driver.getDriver() != null) {
                driver.setDriverBlock(driver.getDriver().getIsEnable());
                driver.setDriverEmail(driver.getDriver().getEmail());
                driver.setDriverPhone(driver.getDriver().getPhone());
                driver.setDriverFio(driver.getDriver().getFio());
            }
            if (driverList == null) driverList = new ArrayList<Driver>();
            driverList.add(driver);
        }
        return driverList;
    }
}
