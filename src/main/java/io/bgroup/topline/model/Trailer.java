package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trailer {
    private final int countOilSection = 6;
    private String id_trailer;
    private String trailer_car_id;
    private String trailer_number;
    private String trailer_name;
    private String trailer_block;
    private ArrayList<OilSections> oilSections;

    @Autowired
    private DbModel dbMvc;
    @Autowired
    private OilType oilTypeMvc;
    @Autowired
    private OilStorage oilStorageMvc;
    @Autowired
    private OilSections oilSectionsMvc;

    public String getId_trailer() {
        return id_trailer;
    }

    private void setId_trailer(String id_trailer) {
        this.id_trailer = id_trailer;
    }

    public String getTrailer_car_id() {
        return trailer_car_id;
    }

    private void setTrailer_car_id(String trailer_car_id) {
        this.trailer_car_id = trailer_car_id;
    }

    public String getTrailer_number() {
        return trailer_number;
    }

    private void setTrailer_number(String trailer_number) {
        this.trailer_number = trailer_number;
    }

    public String getTrailer_name() {
        return trailer_name;
    }

    private void setTrailer_name(String trailer_name) {
        this.trailer_name = trailer_name;
    }

    public String getTrailer_block() {
        return trailer_block;
    }

    private void setTrailer_block(String trailer_block) {
        this.trailer_block = trailer_block;
    }

    public ArrayList<OilSections> getOilSections() {
        return oilSections;
    }

    private void setOilSections(ArrayList<OilSections> oilSections) {
        this.oilSections = oilSections;
    }

    public ArrayList<Trailer> getTrailersList() {
        ArrayList<Trailer> trailersList = null;
        String sql = "select * from trailer where trailer_block='0'";
        trailersList = getTrailersFromDbSelect(sql);
        return trailersList;
    }

    public Trailer getTrailer(String id_trailer) {
        Trailer trailer = null;
        String sql = "select * from trailer where id_trailer=" + id_trailer;
        trailer = getTrailerFromDbSelect(sql);
        return trailer;
    }

    private Trailer getTrailerFromDbSelect(String sql) {
        ArrayList<Trailer> trailerList = getTrailersFromDbSelect(sql);
        if (trailerList == null) return null;
        return trailerList.get(0);
    }

    private ArrayList<Trailer> getTrailersFromDbSelect(String sql) {
        ResultSet resultSet = null;
        try {
            resultSet = dbMvc.getSelectResult(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resultSet == null) return null;
        ArrayList<Trailer> trailerList = null;
        try {
            while (resultSet.next()) {
                Trailer trailer = new Trailer();
                setTrailerFromResultSet(trailer, resultSet);
                if (trailerList == null) trailerList = new ArrayList<Trailer>();
                trailerList.add(trailer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trailerList;
    }

    private void setTrailerFromResultSet(Trailer trailer, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                String trailerId = resultSet.getString(DbModel.tableTrailer.id_trailer.toString());
                String trailerNumber = resultSet.getString(DbModel.tableTrailer.trailer_number.toString());
                String trailerCarId = resultSet.getString(DbModel.tableTrailer.trailer_car_id.toString());
                String trailerBlock = resultSet.getString(DbModel.tableTrailer.trailer_block.toString());

                trailer.setTrailer_block(trailerBlock);
                trailer.setId_trailer(id_trailer);
                trailer.setTrailer_number(trailerNumber);
                trailer.setTrailer_car_id(trailerCarId);
                ArrayList<OilSections> oilSections = null;
                for (int i = 1; i <= countOilSection; i++) {
                    String trailer_sec = resultSet.getString("trailer_sec_" + i).toString();
                    if (!trailer_sec.equals("0")) {
                        if (oilSections == null) {
                            oilSections = new ArrayList<OilSections>();
                            trailer.setOilSections(oilSections);
                        }
                        oilSections.add(new OilSections("trailer_sec_" + i, trailer_sec));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTrailerSectionsForAjax(String idTrailer) {
        Trailer trailer = getTrailer(idTrailer);
        if (trailer == null) return "Error: нет данных по секциям";
        ArrayList<OilSections> oilSections = trailer.getOilSections();
        if (oilSections == null) return "Error: нет данных по секциям";
        String response = "";
        response += "<ul>";
        ArrayList<OilType> oilTypesList = oilTypeMvc.getOilTypesList();
        ArrayList<OilStorage> oilStorageList = oilStorageMvc.getOilStorageList();
        if (oilStorageList == null || oilTypesList == null) return "Error: не возможно загрузить данные";
        response += oilSectionsMvc.getOilSectionsForAjaxSelect(oilSections, oilTypesList, oilStorageList);
        response += "</ul>";
        return response;
    }

    public String getTrailerForAjax(String idCar) {
        ArrayList<Trailer> trailersList = getTrailersList();
        if (trailersList == null) return "Error: нет данных по прицепам";
        String response = "";
        response += "<select class=\"dropdown-menu\""
                + "id=\"trailerId\" "
                + "name=\"trailerId\" "
                + "onchange=\"onTrailerSelect(this)\"" +
                "> "
                + "<option value=\"-1\">Выбрать прицеп</option>";
        String optionSelected = "";
        boolean optionSelectedFlag = false;
        for (Trailer trailer : trailersList) {
            if (trailer.getTrailer_car_id().equals(idCar)) {
                optionSelected = "selected";
                optionSelectedFlag = true;
            }
            response += "<option value=\"" + trailer.getId_trailer() + "\" " + optionSelected + ">"
                    + "Прицеп: " + trailer.getTrailer_number()
                    + "</option>";
            if (optionSelectedFlag) {
                optionSelected = "";
                optionSelectedFlag = false;
            }
        }
        response += "</select>";
        return response;
    }
}
