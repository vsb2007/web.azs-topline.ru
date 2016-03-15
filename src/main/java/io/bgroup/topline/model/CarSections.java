package io.bgroup.topline.model;

public class CarSections {
    private String id_section;
    private String vol;

    public CarSections(String id_section, String vol) {
        this.id_section = id_section;
        this.vol = vol;
    }

    public String getVol() {
        return vol;
    }

    private void setVol(String vol) {
        this.vol = vol;
    }
}
