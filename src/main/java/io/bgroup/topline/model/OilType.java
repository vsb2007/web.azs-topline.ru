package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OilType {
    private String id_oilType;
    private String oilTypeName;
    private String oilTypeBlock;

    @Autowired
    private DbModel dbMvc;

    public OilType() {
    }

    public ArrayList<OilType> getOilTypesList() {
        ArrayList<OilType> oilTypesList = null;
        String sql = "select * from nomenclature where block='0'";
        oilTypesList = getOilTypesFromDbSelect(sql);
        return oilTypesList;
    }

    private ArrayList<OilType> getOilTypesFromDbSelect(String sql) {
        List<Map<String, Object>> oilTypesListFromDb = null;
        oilTypesListFromDb = dbMvc.getSelectResult(sql);
        if (oilTypesListFromDb == null) return null;

        ArrayList<OilType> oilTypeArrayList = null;
        for (Map row : oilTypesListFromDb) {
            OilType oilType = new OilType();
            oilType.setOilTypeBlock((String) row.get("block").toString());
            oilType.setId_oilType((String) row.get("id_Nomenclature").toString());
            oilType.setOilTypeName((String) row.get("Name").toString());

            if (oilTypeArrayList == null) oilTypeArrayList = new ArrayList<OilType>();
            oilTypeArrayList.add(oilType);
        }
        return oilTypeArrayList;
    }

    public String getId_oilType() {
        return id_oilType;
    }

    private void setId_oilType(String id_oilType) {
        this.id_oilType = id_oilType;
    }

    public String getOilTypeName() {
        return oilTypeName;
    }

    private void setOilTypeName(String oilTypeName) {
        this.oilTypeName = oilTypeName;
    }

    public String getOilTypeBlock() {
        return oilTypeBlock;
    }

    private void setOilTypeBlock(String oilTypeBlock) {
        this.oilTypeBlock = oilTypeBlock;
    }
}
