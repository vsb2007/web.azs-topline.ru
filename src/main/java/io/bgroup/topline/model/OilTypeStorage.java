package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OilTypeStorage {
    private int IdOilTypeStorage;
    private OilType oilType;
    private double volumeV;
    private double volumeM;

    @Autowired
    private DbJdbcModel dbMvc;
    @Autowired
    private OilType oilTypeMvc;

    public OilTypeStorage() {
    }

    public enum oilStorageFields {
        id_oilStorage , companyUnitId, oilTypeId, volumeV, volumeM
    }

    public int getIdOilTypeStorage() {
        return IdOilTypeStorage;
    }

    public void setIdOilTypeStorage(int idOilTypeStorage) {
        IdOilTypeStorage = idOilTypeStorage;
    }

    public OilType getOilType() {
        return oilType;
    }

    public void setOilType(OilType oilType) {
        this.oilType = oilType;
    }

    public double getVolumeV() {
        return volumeV;
    }

    public void setVolumeV(double volumeV) {
        this.volumeV = volumeV;
    }

    public double getVolumeM() {
        return volumeM;
    }

    public void setVolumeM(double volumeM) {
        this.volumeM = volumeM;
    }

    public ArrayList<OilTypeStorage> getOilTypeStorageList(int idCompanyUnit) {
        String sql = "select * from oilstorage where companyUnitId = ?";
        Object[] args = new Object[1];
        args[0] = idCompanyUnit;
        List<Map<String, Object>> resultSql = dbMvc.getSelectResult(sql, args);
        if (resultSql == null) return null;
        ArrayList<OilTypeStorage> oilTypeStorageArrayList = null;
        for (Map row : resultSql) {
            OilTypeStorage oilTypeStorage = new OilTypeStorage();
            setOilTypeStorageFromMapRow(oilTypeStorage, row);
            if (oilTypeStorageArrayList == null) oilTypeStorageArrayList = new ArrayList<OilTypeStorage>();
            oilTypeStorageArrayList.add(oilTypeStorage);
        }

        return oilTypeStorageArrayList;
    }

    private void setOilTypeStorageFromMapRow(OilTypeStorage oilTypeStorage, Map row) {
        if (oilTypeStorage == null || row == null) return;
        Object idOilStorageObject = row.get("id_oilStorage");

        Long oilTypeStorageIdLong;
        Integer oilTypeStorageId;
        if (idOilStorageObject instanceof Long) {
            oilTypeStorageIdLong = (Long) idOilStorageObject;
            oilTypeStorageId = oilTypeStorageIdLong.intValue();
        }
        else
            oilTypeStorageId  = (Integer) idOilStorageObject;
        Integer companyUnitId = (Integer) row.get("companyUnitId");
        Integer oilTypeId = (Integer) row.get("oilTypeId");
        Double volumeV = (Double) row.get("volumeV");
        Double volumeM = (Double) row.get("volumeM");

        OilType oilType = oilTypeMvc.getOilType(oilTypeId);
        oilTypeStorage.setOilType(oilType);
        oilTypeStorage.setIdOilTypeStorage(oilTypeStorageId);
        oilTypeStorage.setVolumeM(volumeM);
        oilTypeStorage.setVolumeV(volumeV);
    }

    public boolean addOilTypeStorage(int idCompanyUnit, int idOilType) {
        String sql = "insert into oilstorage (companyUnitId,oilTypeId,volumeV,volumeM)" +
                "values (?,?,?,?)";
        Object[] args = new Object[4];
        args[0] = idCompanyUnit;
        args[1] = idOilType;
        args[2] = 0;
        args[3] = 0;
        boolean flag = dbMvc.getUpdateResult(sql, args);
        return flag;
    }
}
