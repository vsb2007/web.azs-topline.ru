package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OilStorage {
    @Autowired
    private CompanyUnit companyUnitMvc;

    private CompanyUnit companyUnit;

    public CompanyUnit getCompanyUnit() {
        return companyUnit;
    }

    private void setCompanyUnit(CompanyUnit companyUnit) {
        this.companyUnit = companyUnit;
    }

    public int getIdOilStorage() {
        if (companyUnit != null)
            return companyUnit.getIdCompanyUnit();
        else return -1;
    }

    public String getOilStorageName() {
        if (companyUnit != null)
            return companyUnit.getCompanyUnitName();
        else return null;
    }

    public ArrayList<OilStorage> getOilStorageList() {
        ArrayList<CompanyUnit> companyUnitArrayList = companyUnitMvc.getCompanyUnitList();
        ArrayList<OilStorage> oilStorageArrayList = new ArrayList<OilStorage>();
        for (CompanyUnit companyUnit : companyUnitArrayList) {
            OilStorage oilStorage = new OilStorage();
            oilStorage.setCompanyUnit(companyUnit);
            oilStorageArrayList.add(oilStorage);
        }
        return oilStorageArrayList;
    }

    public OilStorage getOilStorage(String idOilStorage) {
        try {
            return getOilStorage(Integer.parseInt(idOilStorage));
        } catch (Exception e) {
            return null;
        }
    }

    public OilStorage getOilStorage(int idOilStorage) {
        OilStorage oilStorage = new OilStorage();
        oilStorage.setCompanyUnit(companyUnitMvc.getCompanyUnit(idOilStorage));
        return oilStorage;
    }
}
