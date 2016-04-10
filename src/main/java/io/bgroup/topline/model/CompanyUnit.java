package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    /*public Company getCompany() {
        return company;
    }
*/
    public void setCompany(Company company) {
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

    public ArrayList<CompanyUnit> getComapnyUnitList() {
        ArrayList<CompanyUnit> companyUnitList = null;
        String sql = "select * from company_unit";
        companyUnitList = getCompanyUnitFromDbSelect(sql);
        return companyUnitList;
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
        ResultSet resultSet = null;

        try {
            resultSet = dbMvc.getSelectResult(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resultSet == null) return null;
        ArrayList<CompanyUnit> companyUnitArrayList = new ArrayList<CompanyUnit>();
        try {
            while (resultSet.next()) {
                CompanyUnit companyUnit = new CompanyUnit();
                setCompanyUnitFromResulset(companyUnit, resultSet);
                if (companyUnitArrayList == null) companyUnitArrayList = new ArrayList<CompanyUnit>();
                companyUnitArrayList.add(companyUnit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companyUnitArrayList;
    }

    private void setCompanyUnitFromResulset(CompanyUnit companyUnit, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                String companyUnitId = resultSet.getString(DbModel.tableCompanyUnit.id_company_unit.toString());
                String companyUnitName = resultSet.getString(DbModel.tableCompanyUnit.company_unit_name.toString());
                String companyId = resultSet.getString(DbModel.tableCompanyUnit.company_id.toString());

                companyUnit.setIdCompanyUnit(companyUnitId);
                companyUnit.setCompanyUnitName(companyUnitName);
                companyUnit.setCompany(companyMvc.getCompany(companyId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCompanyUnitsForAjax(HttpServletRequest request) {
        String response = "";
        String companyId = request.getParameter("companyId");
        if (companyId == null) return "Ошибка: в передача companyId";

        //Company company = companyMvc.getCompany(companyId);
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
