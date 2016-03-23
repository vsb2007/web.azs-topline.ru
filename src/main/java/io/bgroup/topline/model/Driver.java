package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Driver {
    @Autowired
    private DbModel db;


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
            Driver oilStorage = new Driver();
            //oilStorage.setOilStorageBlock((String) row.get("Block").toString());
            //oilStorage.setIdOilStorage((String) row.get("id_sklad").toString());
            //oilStorage.setOilStorageName((String) row.get("Name").toString());
            //oilStorage.setOilStorageIsAzs((String) row.get("IsAZS").toString());
            if (driverList == null) driverList = new ArrayList<Driver>();
            driverList.add(oilStorage);
        }
        return driverList;
    }
}
