package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Driver {
    @Autowired
    private DbModel dbMvc;
    @Autowired
    private SiteUser siteUserMvc;

    private String idDriver;
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
        String sql = "select * from users where enabled='1' and user_post_id='2'";
        driverList = getDriverFromDbSelect(sql);
        return driverList;
    }

    public Driver getDriver(String idDriver) {
        ArrayList<Driver> driverList = null;
        String sql = "select * from users where id_user='" + idDriver + "'";
        driverList = getDriverFromDbSelect(sql);
        if (driverList == null || driverList.size() == 0) return null;
        return driverList.get(0);
    }

    private ArrayList<Driver> getDriverFromDbSelect(String sql) {
        List<Map<String, Object>> driverListFromDb = null;
        driverListFromDb = dbMvc.getSelectResult(sql);
        if (driverListFromDb == null) return null;
        ArrayList<Driver> driverList = null;
        for (Map row : driverListFromDb) {
            Driver driver = new Driver();
            driver.setIdDriver((String) row.get("id_user").toString());
            if (driver.getIdDriver()!=null) {
                int userId = -1;
                try {
                    userId = Integer.parseInt(driver.getIdDriver());
                }
                catch (Exception e){
                    userId = -1;
                }
                driver.setDriver(siteUserMvc.findSiteUser(userId));
            }
            if (driver.getDriver()!=null){
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
