package io.bgroup.topline.model;

public class CarSections {
    private String id_section;
    private String carSectionName;
    private String vol;

    public CarSections() {
    }

    public CarSections(String id_section, String vol) {
        this.id_section = id_section;
        this.vol = vol;
        this.carSectionName = id_section.replace("cars_sections_","");
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

    public String getCarSectionName() {
        return carSectionName;
    }

    private void setCarSectionName(String carSectionName) {
        this.carSectionName = carSectionName;
    }
}

