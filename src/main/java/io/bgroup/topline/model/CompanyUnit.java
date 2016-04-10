package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private Company companyMvc;

    private String idCompanyUnit;
    private String companyUnitName;
    private Company company;

    public CompanyUnit() {
    }

    public Company getCompany() {
        return company;
    }

    private void setCompany(Company company) {
        this.company = company;
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

    public ArrayList<CompanyUnit> getCompanyUnitList(String companyId) {
        ArrayList<CompanyUnit> companyUnitList = null;
        String sql = "select * from company_unit where company_id = '" + companyId + "'";
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
            companyUnit.setIdCompanyUnit(row.get("id_company_unit").toString());
            companyUnit.setCompanyUnitName(row.get("company_unit_name").toString());
            companyUnit.setCompany(companyMvc.getCompany(row.get("company_id").toString()));
            if (companyUnitArrayList == null) companyUnitArrayList = new ArrayList<CompanyUnit>();
            companyUnitArrayList.add(companyUnit);
        }
        return companyUnitArrayList;
    }

    public String getCompanyUnitsForAjax(HttpServletRequest request) {
        String response = "";
        String companyId = request.getParameter("companyId");
        if (companyId == null) return "Ошибка: в передача companyId";
        ArrayList<CompanyUnit> companyUnitsList = getCompanyUnitList(companyId);

        if (companyUnitsList == null) return "Ошибка поиска подразделений";
        response += "<li ripple>" +
                "       <span class=\"item-text\">" +
                "                    <select class=\"dropdown-menu\" id=\"companyUnitId\" name=\"companyUnitId\">" +
                "                        <option value=\"-1\">Выбрете подразделение</option>";
        for (CompanyUnit companyUnit : companyUnitsList) {
            response += "<option value=\"" + companyUnit.getIdCompanyUnit() + "\">" + companyUnit.getCompanyUnitName() + "</option>";
        }
        response += "</select><br><span class=\"secondary-text\">" +
                "                        <label for=\"companyUnitId\" class=\"label\">Подразделение</label>" +
                "                    </span></span>" +
                "                </li>";
        return response;
    }
}
