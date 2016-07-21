package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Company {
    @Autowired
    private DbJdbcModel dbMvc;
    @Autowired
    private SiteUser siteUserMvc;

    private int idCompany;
    private String companyName;
    private int shipper;
    private String error;

    private void setError(String error) {
        this.error = error;
    }

    public int getShipper() {
        return shipper;
    }

    private void setShipper(int shipper) {
        this.shipper = shipper;
    }

    public int getIdCompany() {
        return idCompany;
    }

    private void setIdCompany(int idCompany) {
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
        companyList = getCompanyFromDbSelect(sql, null);
        return companyList;
    }

    public Company getCompany(int idCompany) {
        ArrayList<Company> companyArrayList = null;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "select * from company where id_company=?";
        args.add(idCompany);
        companyArrayList = getCompanyFromDbSelect(sql, args);
        if (companyArrayList == null || companyArrayList.size() == 0) return null;
        return companyArrayList.get(0);
    }

    private ArrayList<Company> getCompanyFromDbSelect(String sql, ArrayList<Object> args) {
        List<Map<String, Object>> companyListFromDb = null;
        companyListFromDb = dbMvc.getSelectResult(sql, args);
        if (companyListFromDb == null) return null;
        ArrayList<Company> companyArrayList = null;
        for (Map row : companyListFromDb) {
            Company company = new Company();
            company.setIdCompany((Integer) row.get("id_company"));
            company.setCompanyName((String) row.get("company_name").toString());
            company.setShipper((Integer) row.get("shipper"));
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

    public boolean addCompany(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        SiteUser siteUserTmp = siteUserMvc.findSiteUser(principal);
        if (!siteUserTmp.isUserHasRole(principal, "ROLE_COMPANY_ADD")) return false;
        String companyNameFromForm = request.getParameter("companyName");
        if (companyNameFromForm == null) return false;
        String sql;
        ArrayList<Object> args = new ArrayList<Object>();
        sql = "select * from company where company_name=?";
        args.add(companyNameFromForm);
        List<Map<String, Object>> dbSelectResult = dbMvc.getSelectResult(sql, args);
        if (dbSelectResult != null && dbSelectResult.size() > 0) {
            this.error = "Компания существует";
            return false;
        } else {
            sql = "INSERT INTO company (company_name) VALUES (?)";
            boolean flag = dbMvc.getUpdateResult(sql, args);
            if (flag)
                this.error = "Комания добавлена";
            else
                this.error = "Комания  не добавлена, ошибка!!!";
        }
        return true;
    }

    public Object getError() {
        return error;
    }

    public Company getCompany(HttpServletRequest request) {
        int companyId = -1;
        try {
            companyId = Integer.parseInt(request.getParameter("companyId"));
        } catch (Exception e) {
            companyId = -1;
        }
        return getCompany(companyId);
    }

    public void redCompany(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        String companyName = request.getParameter("companyName");
        String companyId = request.getParameter("companyId");
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "update company set company_name = ? where id_company = ?";
        args.add(companyName);
        args.add(companyId);
        boolean flag = dbMvc.getUpdateResult(sql, args);
        if (!flag) {
            this.error = "Ошибка обновления имени";
        }
        this.error = "Изменения сохранены";
    }
}
