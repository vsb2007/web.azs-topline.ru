package io.bgroup.topline.model;

import java.util.ArrayList;

public class OilSections {
    private String id_section;
    private String oilSectionName;
    private String vol;

    public OilSections() {
    }

    public OilSections(String id_section, String vol) {
        this.id_section = id_section;
        this.vol = vol;
        this.oilSectionName = id_section.replace("car_sec_", "");
        this.oilSectionName = this.oilSectionName.replace("trailer_sec_", "");
    }

    public String getVol() {
        return vol;
    }

    private void setVol(String vol) {
        this.vol = vol;
    }

    public String getId_section() {
        return id_section;
    }

    public void setId_section(String id_section) {
        this.id_section = id_section;
    }

    public String getOilSectionName() {
        return oilSectionName;
    }

    private void setOilSectionName(String oilSectionName) {
        this.oilSectionName = oilSectionName;
    }

    public String getOilSectionsForAjaxSelect(ArrayList<OilSections> sections, ArrayList<OilType> oilTypesList, ArrayList<OilStorage> oilStorageList, ArrayList<Organization> organizationList) {
        String response = "";
        for (OilSections section : sections) {
            response += "<li>" +
                    "Секция " + section.getOilSectionName() + " (" + section.getVol() + "л.)"
                    + "&nbsp;"
                    + "<select class=\"dropdown-menu\""
                    + "id=\"" + section.getId_section() + "_oilTypeId\" "
                    + "name=\"" + section.getId_section() + "_oilTypeId\">"
                    + "<option value=\"-1\">Пустая секция</option>"
            ;
            for (OilType oilTypeTmp : oilTypesList) {
                response += "<option value=\"" + oilTypeTmp.getId_oilType() + "\">" +
                        oilTypeTmp.getOilTypeName() + "</option>";
            }
            response += "</select>"
                    + "&nbsp;"
                    + "<select class=\"dropdown-menu\""
                    + "id=\"" + section.getId_section() + "_storageOutId\" "
                    + "name=\"" + section.getId_section() + "_storageOutId\">"
                    + "<option value=\"-1\">Пункт отгрузки</option>";
            for (OilStorage oilStorageTmp : oilStorageList) {
                response += "<option value=\"" + oilStorageTmp.getIdOilStorage() + "\">" +
                        oilStorageTmp.getOilStorageName() + "</option>";
            }
            response += "</select>"
                    + "&nbsp;";
            if (organizationList != null && organizationList.size() > 0) {
                response += "<select class=\"dropdown-menu\""
                        + "id=\"" + section.getId_section() + "_OrgId\" "
                        + "name=\"" + section.getId_section() + "_OrgId\">"
                        + "<option value=\"-1\">Покупатель</option>";
                for (Organization organization : organizationList) {
                    response += "<option value=\"" + organization.getIdOrganization() + "\">" +
                            organization.getOrganizationName() + "</option>";
                }
                response += "</select>";
            }
            response += "</li>";
        }
        return response;
    }
}

