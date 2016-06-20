package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OilType {
    private int id_oilType;
    private String oilTypeName;
    private int oilTypeBlock;

    @Autowired
    private DbJdbcModel dbMvc;

    public OilType() {
    }

    public ArrayList<OilType> getOilTypesList() {
        ArrayList<OilType> oilTypesList = null;
        String sql = "select * from nomenclature where block=0";
        oilTypesList = getOilTypesFromDbSelect(sql,null);
        return oilTypesList;
    }
/*
    public OilType getOilType(String id_oilType) {
        ArrayList<OilType> oilTypesList = null;
        try {
            return getOilType(Integer.parseInt(id_oilType));
        }
        catch (Exception e){
            return null;
        }
    }
*/
    public OilType getOilType(int id_oilType) {
        ArrayList<OilType> oilTypesList = null;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "select * from nomenclature where id_Nomenclature=?";
        args.add(id_oilType);
        oilTypesList = getOilTypesFromDbSelect(sql,args);
        if (oilTypesList == null || oilTypesList.size() != 1) return null;
        return oilTypesList.get(0);
    }

    private ArrayList<OilType> getOilTypesFromDbSelect(String sql,ArrayList<Object> args) {
        List<Map<String, Object>> oilTypesListFromDb = null;
        oilTypesListFromDb = dbMvc.getSelectResult(sql,args);
        if (oilTypesListFromDb == null) return null;

        ArrayList<OilType> oilTypeArrayList = null;
        for (Map row : oilTypesListFromDb) {
            OilType oilType = new OilType();
            oilType.setOilTypeBlock((Integer) row.get("block"));
            oilType.setId_oilType((Integer) row.get("id_Nomenclature"));
            oilType.setOilTypeName((String) row.get("Name").toString());

            if (oilTypeArrayList == null) oilTypeArrayList = new ArrayList<OilType>();
            oilTypeArrayList.add(oilType);
        }
        return oilTypeArrayList;
    }

    public int getId_oilType() {
        return id_oilType;
    }

    private void setId_oilType(int id_oilType) {
        this.id_oilType = id_oilType;
    }

    public String getOilTypeName() {
        return oilTypeName;
    }

    private void setOilTypeName(String oilTypeName) {
        this.oilTypeName = oilTypeName;
    }

    public int getOilTypeBlock() {
        return oilTypeBlock;
    }

    private void setOilTypeBlock(int oilTypeBlock) {
        this.oilTypeBlock = oilTypeBlock;
    }
}
