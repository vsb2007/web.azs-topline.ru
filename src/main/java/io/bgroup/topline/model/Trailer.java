package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trailer {
    private final int countOilSection = 6;
    private int id_trailer;
    private int trailer_car_id;
    private String trailer_number;
    private String trailer_name;
    private String trailer_block;
    private ArrayList<OilSections> oilSections;

    @Autowired
    private DbJdbcModel dbMvc;
    @Autowired
    private OilType oilTypeMvc;
    @Autowired
    private OilStorage oilStorageMvc;
    @Autowired
    private Organization organizationMvc;
    @Autowired
    private OilSections oilSectionsMvc;

    public int getId_trailer() {
        return id_trailer;
    }

    private void setId_trailer(int id_trailer) {
        this.id_trailer = id_trailer;
    }

    public int getTrailer_car_id() {
        return trailer_car_id;
    }

    private void setTrailer_car_id(int trailer_car_id) {
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
        trailersList = getTrailersFromDbSelect(sql, null);
        return trailersList;
    }

    public Trailer getTrailer(String id_trailer) {
        if (id_trailer == null) return null;
        int id = -1;
        try {
            id = Integer.parseInt(id_trailer);
        } catch (Exception e) {
            return null;
        }
        return getTrailer(id);
    }

    public Trailer getTrailer(int id_trailer) {
        Trailer trailer = null;
        String sql = "select * from trailer where id_trailer=?";
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(id_trailer);
        trailer = getTrailerFromDbSelect(sql, args);
        return trailer;
    }

    private Trailer getTrailerFromDbSelect(String sql, ArrayList<Object> args) {
        ArrayList<Trailer> trailerList = getTrailersFromDbSelect(sql, args);
        if (trailerList == null) return null;
        return trailerList.get(0);
    }

    private ArrayList<Trailer> getTrailersFromDbSelect(String sql, ArrayList<Object> args) {
        List<Map<String, Object>> trailersListFromDb = null;
        trailersListFromDb = dbMvc.getSelectResult(sql, args);
        if (trailersListFromDb == null) return null;
        ArrayList<Trailer> trailerList = null;
        for (Map row : trailersListFromDb) {
            Trailer trailer = new Trailer();
            ArrayList<OilSections> oilSections = null;
            trailer.setTrailer_block((String) row.get("trailer_block").toString());
            trailer.setId_trailer((Integer) row.get("id_trailer"));
            trailer.setTrailer_number((String) row.get("trailer_number").toString());
            trailer.setTrailer_car_id((Integer) row.get("trailer_car_id"));
            for (int i = 1; i <= countOilSection; i++) {
                String trailer_sec = row.get("trailer_sec_" + i).toString();
                if (!trailer_sec.equals("0")) {
                    if (oilSections == null) {
                        oilSections = new ArrayList<OilSections>();
                        trailer.setOilSections(oilSections);
                    }
                    oilSections.add(new OilSections("trailer_sec_" + i, trailer_sec));
                }
            }
            if (trailerList == null) trailerList = new ArrayList<Trailer>();
            trailerList.add(trailer);
        }
        return trailerList;
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
        ArrayList<Organization> organizationList = organizationMvc.getOrganizationList();
        if (oilStorageList == null || oilTypesList == null) return "Error: не возможно загрузить данные";
        response += oilSectionsMvc.getOilSectionsForAjaxSelect(oilSections, oilTypesList, oilStorageList,organizationList);
        response += "</ul>";
        return response;
    }

    public String getTrailerForAjax(String idCarString) {
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
        int idCar = -1;
        try {
            idCar = Integer.parseInt(idCarString);
        } catch (Exception e) {
            idCar = -1;
        }
        for (Trailer trailer : trailersList) {
            if (trailer.getTrailer_car_id() == idCar) {
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
