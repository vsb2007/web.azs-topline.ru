package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Company {
    @Autowired
    private DbModel dbMvc;

    private String idCompany;
    private String companyName;

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

    public ArrayList<Company> getComapnyList() {
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
}
