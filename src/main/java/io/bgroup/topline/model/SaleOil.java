package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class SaleOil {
    @Autowired
    private SiteUser siteUserMvc;
    @Autowired
    private OilStorage oilStorageMvc;
    @Autowired
    private OilType oilTypeMvc;
    @Autowired
    private Driver driverMvc;
    @Autowired
    private Car carMvc;
    @Autowired
    private Trailer trailerMvc;
    @Autowired
    private DbJdbcModel dbMvc;
    @Autowired
    private BidDetail bidDetailMvc;
    @Autowired
    private MyConstant myConstantMvc;
    @Autowired
    private CompanyUnit companyUnitMvc;
    @Autowired
    private Organization organizationMvc;

    private int id;
    private CompanyUnit companyUnit;
    private Organization organization;
    private String fio;
    private String carNumber;
    private OilType oilType;
    private int lt;
    private double col;
    private double priceOil;
    private double priceShipping;
    private double sum;

    public SaleOil() {
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public String createSaleOil(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        if (siteUser == null) return "Error: Ошибка Авторизации";
        if (!siteUser.isUserHasRole(principal, "ROLE_SALE_CREATE")) return "Error: не достаточно прав";
        //считываем основные параметры

        int idUnit = -1;
        int idOrg = -1;
        String fio = request.getParameter("fio");
        String carNumber = request.getParameter("carNumber");
        int idOilType = -1;
        int lt = -1;
        double colLiters = 0;
        double priceLiters = 0;
        double priceShipping = 0;
        double sum = 0;

        try {
            idUnit = Integer.parseInt(request.getParameter("idUnit"));
            idOrg = Integer.parseInt(request.getParameter("OrgId"));
            idOilType = Integer.parseInt(request.getParameter("oilTypeId"));
            lt = Integer.parseInt(request.getParameter("lt"));
            colLiters = Double.parseDouble(request.getParameter("colLiters"));
            priceLiters = Double.parseDouble(request.getParameter("priceLiters"));
            priceShipping = Double.parseDouble(request.getParameter("priceShipping"));
            sum = Double.parseDouble(request.getParameter("sum"));
        } catch (Exception e) {
            return "не верные данные";
        }
        // на их основе подтягиваем данные
        if (idUnit != -1) {
            CompanyUnit companyUnit = companyUnitMvc.getCompanyUnit(idUnit);
            if (companyUnit == null) return "точка отгрузки не найдена";
        }
        if (idOrg > 0) {
            Organization organization = organizationMvc.getOrganization(idOrg);
            if (organization == null) return "покупатель не найден";
        }
        if (idOilType > 0) {
            OilType oilType = oilTypeMvc.getOilType(idOilType);
            if (oilType == null) return "тип топлива не найден";
        }
        if (lt == -1) return "единицы измерения не заданы";

        String sql = "insert into salebid ";
        ArrayList<Object> args = new ArrayList<Object>();
        String columns = "";
        String values = "";

        columns = strPlusCommaPlusValue(columns, "sale_id_unit");
        values = strPlusCommaPlusValue(values, "?");
        args.add(idUnit);

        columns = strPlusCommaPlusValue(columns, "sale_id_org");
        values = strPlusCommaPlusValue(values, "?");
        args.add(idOrg);

        columns = strPlusCommaPlusValue(columns, "sale_driver_fio");
        values = strPlusCommaPlusValue(values, "?");
        args.add(fio);

        columns = strPlusCommaPlusValue(columns, "sale_car_number");
        values = strPlusCommaPlusValue(values, "?");
        args.add(carNumber);

        columns = strPlusCommaPlusValue(columns, "sale_id_oil");
        values = strPlusCommaPlusValue(values, "?");
        args.add(idOilType);

        columns = strPlusCommaPlusValue(columns, "sale_lt");
        values = strPlusCommaPlusValue(values, "?");
        args.add(lt);

        columns = strPlusCommaPlusValue(columns, "sale_col");
        values = strPlusCommaPlusValue(values, "?");
        args.add(colLiters);

        columns = strPlusCommaPlusValue(columns, "sale_price_oil");
        values = strPlusCommaPlusValue(values, "?");
        args.add(priceLiters);

        columns = strPlusCommaPlusValue(columns, "sale_price_shipping");
        values = strPlusCommaPlusValue(values, "?");
        args.add(priceShipping);

        columns = strPlusCommaPlusValue(columns, "sale_sum");
        values = strPlusCommaPlusValue(values, "?");
        args.add(sum);

        sql += "(" + columns + ") values (" + values + ")";
        if (dbMvc.getUpdateResult(sql, args)) {
            /*
            уведомление по email пока не требуется
             */
         /*   try {

                    new SendEmail().sendMail(driver);
            } catch (Exception e) {
                e.printStackTrace();
            }
            */
            return "Заявка создана";
        } else return "Неизвестная ошибка добавления заявки";
    }

    private String strPlusCommaPlusValue(String str, String value) {
        String strTmp = new String(str);
        if (!str.equals("")) strTmp += ",";
        strTmp += value;
        return strTmp;
    }

}
