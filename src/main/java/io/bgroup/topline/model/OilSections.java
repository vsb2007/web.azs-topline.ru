package io.bgroup.topline.model;

public class OilSections {
    private String id_section;
    private String oilSectionName;
    private String vol;

    public OilSections() {
    }

    public OilSections(String id_section, String vol) {
        this.id_section = id_section;
        this.vol = vol;
        this.oilSectionName = id_section.replace("cars_sections_","");
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
}

