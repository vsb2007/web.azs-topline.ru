package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        ResultSet resultSet = null;
        try {
            resultSet = dbMvc.getSelectResult(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resultSet == null) return null;

        ArrayList<OilType> oilTypeArrayList = null;
        try {
            while (resultSet.next()) {
                OilType oilType = new OilType();
                setOilTypeFromResultSet(oilType,resultSet);
                if (oilTypeArrayList == null) oilTypeArrayList = new ArrayList<OilType>();
                oilTypeArrayList.add(oilType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oilTypeArrayList;
    }
    private void setOilTypeFromResultSet(OilType oilType, ResultSet resultSet){
        try {
            if (resultSet != null) {
                String oilTypeId = resultSet.getString(DbModel.tableNomenclature.id_Nomenclature.toString());
                String oilTypeBlock = resultSet.getString(DbModel.tableNomenclature.block.toString());
                String oilTypeName =  resultSet.getString(DbModel.tableNomenclature.Name.toString());

                oilType.setOilTypeBlock(oilTypeBlock);
                oilType.setId_oilType(oilTypeId);
                oilType.setOilTypeName(oilTypeName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
