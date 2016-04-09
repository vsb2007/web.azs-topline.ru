package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by VSB on 08.04.2016.
 * ToplineWeb.2.5
 */
public class CompanyUnit {
    @Autowired
    private DbModel dbMvc;

    private String idCompanyUnit;
    private String companyUnitName;
    private String company_id;

    public String getCompany_id() {
        return company_id;
    }

    private void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getIdCompanyUnit() {
        return idCompanyUnit;
    }

    private void setIdCompanyUnit(String idCompanyUnit) {
        this.idCompanyUnit = idCompanyUnit;
    }

    public String getCompanyUnitName() {
        return companyUnitName;
    }

    private void setCompanyUnitName(String companyUnitName) {
        this.companyUnitName = companyUnitName;
    }

    public ArrayList<CompanyUnit> getComapnyUnitList() {
        ArrayList<CompanyUnit> companyUnitList = null;
        String sql = "select * from company_unit";
        companyUnitList = getCompanyUnitFromDbSelect(sql);
        return companyUnitList;
    }

    public CompanyUnit getCompanyUnit(String idCompanyUnit) {
        ArrayList<CompanyUnit> companyUnitArrayList = null;
        String sql = "select * from company_unit where id_company_unit='" + idCompanyUnit + "'";
        companyUnitArrayList = getCompanyUnitFromDbSelect(sql);
        if (companyUnitArrayList == null || companyUnitArrayList.size() == 0) return null;
        return companyUnitArrayList.get(0);
    }

    private ArrayList<CompanyUnit> getCompanyUnitFromDbSelect(String sql) {
        List<Map<String, Object>> companyUnitListFromDb = null;
        companyUnitListFromDb = dbMvc.getSelectResult(sql);
        if (companyUnitListFromDb == null) return null;
        ArrayList<CompanyUnit> companyUnitArrayList = null;
        for (Map row : companyUnitListFromDb) {
            CompanyUnit companyUnit = new CompanyUnit();
            companyUnit.setIdCompanyUnit((String) row.get("id_company_unit").toString());
            companyUnit.setCompanyUnitName((String) row.get("company_unit_name").toString());
            companyUnit.setCompany_id((String) row.get("company_id"));
            if (companyUnitArrayList == null) companyUnitArrayList = new ArrayList<CompanyUnit>();
            companyUnitArrayList.add(companyUnit);
        }
        return companyUnitArrayList;
    }
}
