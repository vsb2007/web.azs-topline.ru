package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

/**
 * Created by VSB on 04.06.2016.
 * ToplineWeb.2.5
 */
public class OilTypeStorage {
    private int IdOilTypeStorage;
    private OilType oilType;
    private double volumeV;
    private double volumeM;

    @Autowired
    private JdbcTemplate jdbcTemplateMvc;

    public OilTypeStorage() {
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
        return null;
    }


}
