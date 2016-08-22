package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/*
класс для отслеживания остатков на складах
 */
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
        id_oilStorage, companyUnitId, oilTypeId, volumeV, volumeM
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
        ArrayList<Object> args = new ArrayList<Object>(1);
        args.add(idCompanyUnit);
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
        } else
            oilTypeStorageId = (Integer) idOilStorageObject;
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
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(idCompanyUnit);
        args.add(idOilType);
        args.add(0);
        args.add(0);
        boolean flag = dbMvc.getUpdateResult(sql, args);
        return flag;
    }

    public String updateOilTypeStorage(SiteUser siteUser, HttpServletRequest request, CompanyUnit companyUnit) {
        if (siteUser == null || request == null) return "мало данных";
        if (companyUnit == null) return "мало данных";
        ArrayList<OilTypeStorage> oilTypeStorageArrayList = companyUnit.getOilTypeStorageArrayList();
        if (oilTypeStorageArrayList == null) return "мало данных";
        int oilTypeStorageSize = oilTypeStorageArrayList.size();
        if (oilTypeStorageSize == 0) return "мало данных";
        String sql = "insert into oilstorage (id_oilStorage,volumeV,volumeM) values ";
        ArrayList<Object> args = new ArrayList<Object>();
        boolean commaFlag = false;
        for (OilTypeStorage oilTypeStorage : oilTypeStorageArrayList) {
            int idOilTypeStorage = oilTypeStorage.getIdOilTypeStorage();
            double volumeV = oilTypeStorage.getVolumeV();
            double volumeM = oilTypeStorage.getVolumeM();
            double volumeVOld;
            double volumeMOld;
            double volumeVNew;
            double volumeMNew;
            try {
                volumeVOld = Double.parseDouble(request.getParameter("oilStorageVOld_" + idOilTypeStorage));
                volumeMOld = Double.parseDouble(request.getParameter("oilStorageMOld_" + idOilTypeStorage));
                volumeVNew = Double.parseDouble(request.getParameter("oilStorageV_" + idOilTypeStorage));
                volumeMNew = Double.parseDouble(request.getParameter("oilStorageM_" + idOilTypeStorage));
            } catch (Exception e) {
                e.printStackTrace();
                return "ошибка вводных данных";
            }
            if (volumeM != volumeMOld) return "данные успели измениться";
            if (volumeV != volumeVOld) return "данные успели измениться";
            if (commaFlag) {
                sql += ",";
            }
            sql += "(?,?,?)";
            if (!commaFlag) commaFlag = true;
            args.add(oilTypeStorage.getIdOilTypeStorage());
            args.add(volumeVNew);
            args.add(volumeMNew);
        }
        sql += " ON DUPLICATE KEY UPDATE " +
                "oilstorage.volumeV = VALUES(volumeV),oilstorage.volumeM = VALUES(volumeM)";
        boolean flag = dbMvc.getUpdateResult(sql, args);
        if (flag) return "Данные обновлены";
        return "не известная ошибка обновления";
    }
}
