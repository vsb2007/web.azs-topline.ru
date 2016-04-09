package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Company {
    @Autowired
    private DbModel dbMvc;
    @Autowired
    private CompanyUnit companyUnitMvc;

    private String idCompany;
    private String companyName;
    //private ArrayList<CompanyUnit> companyUnitsList;

    public ArrayList<CompanyUnit> getCompanyUnitsList(String idCompany) {
        return companyUnitMvc.getComapnyUnitList(getCompany(idCompany));
    }

    public String getIdCompany() {
        return idCompany;
    }

    private void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getCompanyName() {
        return companyName;
    }

    private void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public ArrayList<Company> getCompanyList() {
        ArrayList<Company> companyList = null;
        String sql = "select * from company";
        companyList = getCompanyFromDbSelect(sql);
        return companyList;
    }

    public Company getCompany(String idCompany) {
        ArrayList<Company> companyArrayList = null;
        String sql = "select * from company where id_company='" + idCompany + "'";
        companyArrayList = getCompanyFromDbSelect(sql);
        if (companyArrayList == null || companyArrayList.size() == 0) return null;
        return companyArrayList.get(0);
    }

    private ArrayList<Company> getCompanyFromDbSelect(String sql) {
        List<Map<String, Object>> companyListFromDb = null;
        companyListFromDb = dbMvc.getSelectResult(sql);
        if (companyListFromDb == null) return null;
        ArrayList<Company> companyArrayList = null;
        for (Map row : companyListFromDb) {
            Company company = new Company();
            company.setIdCompany((String) row.get("id_company").toString());
            company.setCompanyName((String) row.get("company_name").toString());
            if (companyArrayList == null) companyArrayList = new ArrayList<Company>();
            companyArrayList.add(company);
        }
        return companyArrayList;
    }

    public String getCompanyForAjax(HttpServletRequest request) {
        String response = "";
        String postId = request.getParameter("postId");
        if (postId == null) return "Error: ошибка в передаче запроса postId";
        if (postId.equals("-1")) return "";
        else if (postId.equals("1")) return "";
        else if (postId.equals("2")) return "";
        else if (postId.equals("3")) return getCompanyListAsStringForSelectForAjax();
        return response;
    }

    private String getCompanyListAsStringForSelectForAjax() {
        String response = "";
        ArrayList<Company> companyList = getCompanyList();
        if (companyList == null) return "Ошибка поиска компаний";
        response += "<li ripple>" +
                "       <span class=\"item-text\">" +
                "                    <select class=\"dropdown-menu\" id=\"companyId\" name=\"companyId\" onchange=\"getCompanyUnits(this)\">" +
                "                        <option value=\"-1\">Выбрете организацию</option>";
        for (Company company : companyList) {
            response += "<option value=\"" + company.getIdCompany() + "\">" + company.getCompanyName() + "</option>";
        }
        response += "</select><br><span class=\"secondary-text\">" +
                "                        <label for=\"companyId\" class=\"label\">Организация</label>" +
                "                    </span></span>" +
                "                </li>";
        return response;
    }
}
