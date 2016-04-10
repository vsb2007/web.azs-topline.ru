package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        ResultSet resultSet = null;
        try {
            resultSet = dbMvc.getSelectResult(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resultSet == null) return null;

        ArrayList<OilStorage> oilStorageArrayList = null;
        try {
            while (resultSet.next()) {
                OilStorage oilStorage = new OilStorage();
                setOilStorageFromResultSet(oilStorage,resultSet);
                if (oilStorageArrayList == null) oilStorageArrayList = new ArrayList<OilStorage>();
                oilStorageArrayList.add(oilStorage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oilStorageArrayList;
    }
    private void setOilStorageFromResultSet(OilStorage oilStorage, ResultSet resultSet){
        try {
            if (resultSet != null) {
                String oilStorageId = resultSet.getString(DbModel.tableCompanyUnit.id_company_unit.toString());
                String oilStorageBlock = resultSet.getString(DbModel.tableCompanyUnit.Block.toString());
                String oilStorageName = resultSet.getString(DbModel.tableCompanyUnit.company_unit_name.toString());
                String oilStorageIsAzs = resultSet.getString(DbModel.tableCompanyUnit.IsAZS.toString());
                oilStorage.setOilStorageBlock(oilStorageBlock);
                oilStorage.setIdOilStorage(oilStorageId);
                oilStorage.setOilStorageName(oilStorageName);
                oilStorage.setOilStorageIsAzs(oilStorageIsAzs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
