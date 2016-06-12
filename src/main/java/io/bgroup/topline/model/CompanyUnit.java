package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

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
    @Autowired
    private OilTypeStorage oilTypeStorageMvc;
    @Autowired
    private SiteUser siteUserMvc;
    @Autowired
    private OilType oilTypeMvc;

    private int idCompanyUnit;
    private String companyUnitName;
    private Company company;
    private String error;
    private boolean isBlock;
    private ArrayList<OilTypeStorage> oilTypeStorageArrayList;

    public CompanyUnit() {
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public ArrayList<OilTypeStorage> getOilTypeStorageArrayList() {
        return oilTypeStorageArrayList;
    }

    public void setOilTypeStorageArrayList(ArrayList<OilTypeStorage> oilTypeStorageArrayList) {
        this.oilTypeStorageArrayList = oilTypeStorageArrayList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Company getCompany() {
        return company;
    }

    private void setCompany(Company company) {
        this.company = company;
    }

    public int getIdCompanyUnit() {
        return idCompanyUnit;
    }

    private void setIdCompanyUnit(int idCompanyUnit) {
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
        String sql = "select * from company_unit where company_id = '" + companyId + "' order by company_unit_name";
        companyUnitList = getCompanyUnitFromDbSelect(sql);
        return companyUnitList;
    }

    public CompanyUnit getCompanyUnit(int idCompanyUnit) {
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
            companyUnit.setIdCompanyUnit((Integer) row.get("id_company_unit"));
            companyUnit.setCompanyUnitName(row.get("company_unit_name").toString());
            companyUnit.setCompany(companyMvc.getCompany(row.get("company_id").toString()));
            int block = -1;
            try {
                block = (Integer) row.get("company_unit_name");
            } catch (Exception e) {
                block = -1;
            }
            if (block == 1) companyUnit.setBlock(true);
            else companyUnit.setBlock(false);
            if (companyUnit != null && companyUnit.getIdCompanyUnit() > 0) {
                ArrayList<OilTypeStorage> oilTypeStorageList = new ArrayList<OilTypeStorage>();
                try {
                    //Integer idCompanyUnit = Integer.parseInt(companyUnit.getIdCompanyUnit());
                    Integer idCompanyUnit = companyUnit.getIdCompanyUnit();
                    companyUnit.setOilTypeStorageArrayList(oilTypeStorageMvc.getOilTypeStorageList(idCompanyUnit));
                } catch (Exception e) {
                    companyUnit.setOilTypeStorageArrayList(null);
                }
            }
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

    public CompanyUnit getCompanyUnit(HttpServletRequest request) {
        CompanyUnit companyUnit = null;
        if (request == null) return null;
        int companyUnitId = -1;
        try {
            companyUnitId = Integer.parseInt(request.getParameter("companyUnitId").toString());
        } catch (Exception e) {
            companyUnitId = -1;
        }
        if (companyUnitId != -1)
            companyUnit = getCompanyUnit(companyUnitId);
        return companyUnit;
    }

    public void redCompanyUnit(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        String companyUnitName = request.getParameter("companyUnitName");
        String companyUnitId = request.getParameter("companyUnitId");
        String sql = "update company_unit set company_unit_name = '" + companyUnitName +
                "' where id_company_unit = '" + companyUnitId + "'";
        boolean flag = dbMvc.getInsertResult(sql);
        if (flag) {
            this.error = "Ошибка обновления имени";
        }
        this.error = "Изменения сохранены";
    }

    public void addCompanyUnit(HttpServletRequest request) {

        String companyUnitNameFromForm = request.getParameter("companyUnitName");
        String companyId = request.getParameter("companyId");

        if (companyUnitNameFromForm == null) {
            this.error = "ошибка request";
            return;
        }
        String sql;

        sql = "INSERT INTO company_unit (company_unit_name,company_id) VALUES ('" + companyUnitNameFromForm + "','" + companyId + "')";
        boolean flag = dbMvc.getInsertResult(sql);
        if (!flag)
            this.error = "подразделение добавлено";
        else
            this.error = "Подразделение не добавлено, ошибка!!!";
    }

    public String addCompanyUnitOilStorage(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        if (!siteUser.isUserHasRole(principal, "ROLE_COMPANY_RED")) return "нет прав: 01";
        int oilTypeId = -1;
        int companyUnitId = -1;
        try {
            oilTypeId = Integer.parseInt(request.getParameter("oilType"));
            companyUnitId = Integer.parseInt(request.getParameter("companyUnitId"));
        } catch (Exception e) {
            oilTypeId = -1;
            return "не читаемый тип топлива или подразделения";
        }
        OilType oilType = oilTypeMvc.getOilType(oilTypeId);
        CompanyUnit companyUnit = getCompanyUnit(companyUnitId);
        if (oilType == null) return "не определен тип топлива";
        if (companyUnit == null) return "подразделение не найдено";

        if (!oilTypeStorageMvc.addOilTypeStorage(companyUnit.getIdCompanyUnit(), oilType.getId_oilType()))
            return "ошибка создания харнилища";

        return "контроль запущен";
    }

    public ArrayList<CompanyUnit> getCompanyUnitList() {
        ArrayList<CompanyUnit> companyUnitArrayList = null;
        String sql = "select * from company_unit where Block='0' order by company_unit_name desc";
        companyUnitArrayList = getCompanyUnitFromDbSelect(sql);
        return companyUnitArrayList;
    }
}
