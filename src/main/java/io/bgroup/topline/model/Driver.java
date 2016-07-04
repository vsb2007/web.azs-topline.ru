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
    private boolean driverBlock;
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

    public boolean getDriverBlock() {
        return driverBlock;
    }

    private void setDriverBlock(boolean driverBlock) {
        this.driverBlock = driverBlock;
    }

    public ArrayList<Driver> getDriverList() {
        ArrayList<Driver> driverList = null;
        String sql = "select * from users where enabled='1' and user_post_id='2'";
        driverList = getDriverFromDbSelect(sql, null);
        return driverList;
    }

    public Driver getDriver(String idDriver) {
        if (idDriver == null) return null;
        int id = -1;
        try {
            id = Integer.parseInt(idDriver);
        } catch (Exception e) {
            return null;
        }
        return getDriver(id);
    }

    public Driver getDriver(int idDriver) {
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
        ArrayList<Driver> driverList = new ArrayList<Driver>();
        ArrayList<Thread> threadArrayList = new ArrayList<Thread>();
        for (Map row : driverListFromDb) {
            Driver driver = new Driver();
            driverList.add(driver);
            Thread thread = new Thread(new GetDriverThread(driver, row));
            thread.start();
            threadArrayList.add(thread);
        }
        for (Thread thread : threadArrayList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return driverList;
    }

    private class GetDriverThread implements Runnable {
        private Driver driver;
        Map row;

        public GetDriverThread(Driver driver, Map row) {
            this.driver = driver;
            this.row = row;
        }

        @Override
        public void run() {
            setDriverFromMapRow(driver,row);
        }

        public void setDriverFromMapRow(Driver driver, Map row) {
            Object userIdObject = row.get("id_user");
            Integer userId;
            Long userIdLong;
            if (userIdObject instanceof Long) {
                userIdLong = (Long) userIdObject;
                userId = userIdLong.intValue();
            } else
                userId = (Integer) userIdObject;
            driver.setIdDriver(userId);
            driver.setDriver(siteUserMvc.findSiteUser(userId));
            if (driver.getDriver() != null) {
                driver.setDriverBlock(driver.getDriver().getIsEnable());
                driver.setDriverEmail(driver.getDriver().getEmail());
                driver.setDriverPhone(driver.getDriver().getPhone());
                driver.setDriverFio(driver.getDriver().getFio());
            }
        }
    }
}
