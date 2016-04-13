package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OilStorage {
    @Autowired
    private DbModel dbMvc;

    private String idOilStorage;
    private String oilStorageName;
    private String oilStorageBlock;
    private String oilStorageIsAzs;

    public String getIdOilStorage() {
        return idOilStorage;
    }

    private void setIdOilStorage(String idOilStorage) {
        this.idOilStorage = idOilStorage;
    }

    public String getOilStorageName() {
        return oilStorageName;
    }

    private void setOilStorageName(String oilStorageName) {
        this.oilStorageName = oilStorageName;
    }

    public String getOilStorageBlock() {
        return oilStorageBlock;
    }

    private void setOilStorageBlock(String oilStorageBlock) {
        this.oilStorageBlock = oilStorageBlock;
    }

    public String getOilStorageIsAzs() {
        return oilStorageIsAzs;
    }

    private void setOilStorageIsAzs(String oilStorageIsAzs) {
        this.oilStorageIsAzs = oilStorageIsAzs;
    }

    public ArrayList<OilStorage> getOilStorageList() {
        ArrayList<OilStorage> oilStorageList = null;
        String sql = "select * from company_unit where Block='0'";
        oilStorageList = getOilStorageFromDbSelect(sql);
        return oilStorageList;
    }

    public OilStorage getOilStorage(String idOilStorage) {
        ArrayList<OilStorage> oilStorageList = null;
        String sql = "select * from company_unit where id_company_unit = '" + idOilStorage + "'";
        oilStorageList = getOilStorageFromDbSelect(sql);
        if (oilStorageList == null || oilStorageList.size() == 0) return null;
        return oilStorageList.get(0);
    }

    private ArrayList<OilStorage> getOilStorageFromDbSelect(String sql) {
        List<Map<String, Object>> oilStorageListFromDb = null;
        oilStorageListFromDb = dbMvc.getSelectResult(sql);
        if (oilStorageListFromDb == null) return null;

        ArrayList<OilStorage> oilStorageArrayList = null;
        for (Map row : oilStorageListFromDb) {
            OilStorage oilStorage = new OilStorage();
            oilStorage.setOilStorageBlock((String) row.get("Block").toString());
            oilStorage.setIdOilStorage((String) row.get("id_company_unit").toString());
            oilStorage.setOilStorageName((String) row.get("company_unit_name").toString());
            //oilStorage.setOilStorageIsAzs((String) row.get("IsAZS").toString());
            oilStorage.setOilStorageIsAzs(null);
            if (oilStorageArrayList == null) oilStorageArrayList = new ArrayList<OilStorage>();
            oilStorageArrayList.add(oilStorage);
        }
        return oilStorageArrayList;
    }
}
