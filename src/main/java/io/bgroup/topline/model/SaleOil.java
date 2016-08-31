package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private boolean isRead;
    private String dateIsRead;
    private boolean isDone;
    private String dateIsDone;
    private boolean isClose;
    private boolean isBlock;
    private String dateIsClose;
    private String dateCreate;
    private SiteUser userCreate;
    private SiteUser userRead;
    private SiteUser userClose;
    private int orgPactId;

    public SaleOil() {
    }

    public int getOrgPactId() {
        return orgPactId;
    }

    public void setOrgPactId(int orgPactId) {
        this.orgPactId = orgPactId;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public SiteUser getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(SiteUser userCreate) {
        this.userCreate = userCreate;
    }

    public SiteUser getUserRead() {
        return userRead;
    }

    public void setUserRead(SiteUser userRead) {
        this.userRead = userRead;
    }

    public SiteUser getUserClose() {
        return userClose;
    }

    public void setUserClose(SiteUser userClose) {
        this.userClose = userClose;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CompanyUnit getCompanyUnit() {
        return companyUnit;
    }

    public void setCompanyUnit(CompanyUnit companyUnit) {
        this.companyUnit = companyUnit;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public OilType getOilType() {
        return oilType;
    }

    public void setOilType(OilType oilType) {
        this.oilType = oilType;
    }

    public int getLt() {
        return lt;
    }

    public void setLt(int lt) {
        this.lt = lt;
    }

    public double getCol() {
        return col;
    }

    public void setCol(double col) {
        this.col = col;
    }

    public double getPriceOil() {
        return priceOil;
    }

    public void setPriceOil(double priceOil) {
        this.priceOil = priceOil;
    }

    public double getPriceShipping() {
        return priceShipping;
    }

    public void setPriceShipping(double priceShipping) {
        this.priceShipping = priceShipping;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getDateIsRead() {
        return dateIsRead;
    }

    public void setDateIsRead(String dateIsRead) {
        this.dateIsRead = dateIsRead;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    public String getDateIsClose() {
        return dateIsClose;
    }

    public void setDateIsClose(String dateIsClose) {
        this.dateIsClose = dateIsClose;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getDateIsDone() {
        return dateIsDone;
    }

    public void setDateIsDone(String dateIsDone) {
        this.dateIsDone = dateIsDone;
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
        double totalSum = -1;
        int orgDogId = -1;
        try {
            idUnit = Integer.parseInt(request.getParameter("idUnit"));
            idOrg = Integer.parseInt(request.getParameter("OrgId"));
            idOilType = Integer.parseInt(request.getParameter("oilTypeId"));
            lt = Integer.parseInt(request.getParameter("lt"));
            colLiters = Double.parseDouble(request.getParameter("colLiters"));
            priceLiters = Double.parseDouble(request.getParameter("priceLiters"));
            priceShipping = Double.parseDouble(request.getParameter("priceShipping"));
            sum = Double.parseDouble(request.getParameter("sum"));
            totalSum = Double.parseDouble(request.getParameter("totalSum"));
            orgDogId = Integer.parseInt(request.getParameter("orgDogId"));

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

        columns = strPlusCommaPlusValue(columns, "sale_org_dog_id");
        values = strPlusCommaPlusValue(values, "?");
        args.add(orgDogId);

        columns = strPlusCommaPlusValue(columns, "sale_create_date");
        values = strPlusCommaPlusValue(values, "now()");
        //args.add("now()");
        if (totalSum < 0) {
            columns = strPlusCommaPlusValue(columns, "sale_is_block");
            values = strPlusCommaPlusValue(values, "1");
        }

        sql += "(" + columns + ") values (" + values + ")";
        if (dbMvc.getUpdateResult(sql, args)) {
            return "Заявка создана";
        } else {
            return "Неизвестная ошибка добавления заявки";
        }
    }

    private String strPlusCommaPlusValue(String str, String value) {
        String strTmp = str;
        if (!str.equals("")) strTmp += ",";
        strTmp += value;
        return strTmp;
    }

    public ArrayList<SaleOil> getSaleOilList(UsernamePasswordAuthenticationToken principal) {
        ArrayList<SaleOil> saleOilList = null;
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        if (!siteUser.isUserHasRole(principal, "ROLE_SALE_LIST")) return null;
        String sql = "";
        //ArrayList<Object> args = new ArrayList<Object>();
        if (siteUser.getName().equals("admin")) {
            sql = "select * from salebid where sale_is_close = 0";
        } else if (siteUser.getPost() == null) {
            return null;
        }
        if (siteUser.getPost() != null) {
            if (siteUser.getPost().getIdPost() == 1) { //логист
                sql = "select * from salebid where sale_id_unit = -1 and sale_is_close = 0 and sale_is_block = 0";
            } else if (siteUser.getPost().getIdPost() == 2) { // водитель
                return null;
            } else if (siteUser.getPost().getIdPost() == 3) { // оператор
                sql = "select * from salebid where sale_id_unit = " + siteUser.getCompanyUnit().getIdCompanyUnit()
                        + " and sale_is_close = 0 and sale_is_block = 0";
            } else if (siteUser.getPost().getIdPost() == 4) { // наблюдатель
                sql = "select * from salebid where sale_is_close = 0";
            } else if (siteUser.getPost().getIdPost() == 5) { // продавец
                sql = "select * from salebid where sale_is_close = 0";
            }
        }
        saleOilList = getSaleListFromDbSelect(sql, null);
        return saleOilList;
    }

    private ArrayList<SaleOil> getSaleListFromDbSelect(String sql, ArrayList<Object> args) {
        List<Map<String, Object>> saleOilListFromDb;
        saleOilListFromDb = dbMvc.getSelectResult(sql, args);
        if (saleOilListFromDb == null) return null;
        ArrayList<SaleOil> saleOilList = new ArrayList<SaleOil>();
        ArrayList<Thread> threadArrayList = new ArrayList<Thread>();
        for (Map row : saleOilListFromDb) {
            SaleOil saleOil = new SaleOil();
            saleOilList.add(saleOil);
            Thread thread = new Thread(new SaleOil.SetRowThread(saleOil, row));
            thread.start();
            threadArrayList.add(thread);
        }
        for (Thread thread : threadArrayList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return saleOilList;
    }

    /*
    отмечаем, что заявка принята к исполнению/прочитана
     */
    public String checkReadSaleOilBid(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        if (siteUser == null) return "ошибка доступа";
        if (!siteUserMvc.isUserHasRole(principal, "ROLE_SALE_UPDATE")) return "нет прав";
        int idSale = -1;
        try {
            idSale = Integer.parseInt(request.getParameter("idSale"));
        } catch (Exception e) {
            return "какой-то не тот номер заявки";
        }
        SaleOil saleOil = getSale(idSale);
        if (saleOil == null) return "заявка не найдена";
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "update salebid set sale_is_read = 1, sale_is_read_date = now(), "
                + "sale_read_user_id = ? where id_sale = ?";
        args.add(siteUser.getId());
        args.add(idSale);
        if (!dbMvc.getUpdateResult(sql, args)) return "ошибка обновления в БД";
        return "1";
    }

    public boolean closeSaleOil(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        if (!siteUser.isUserHasRole(principal, "ROLE_SALE_UPDATE")) return false;
        String id = request.getParameter("saleIdButton");
        if (id == null) return false;
        SaleOil saleOil = getSale(id);
        if (saleOil == null) return false;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "update salebid set sale_is_close=1, sale_is_close_date = now(), sale_close_user_id = ? where id_sale = ?";
        args.add(siteUser.getId());
        args.add(id);
        return dbMvc.getUpdateResult(sql, args);
    }

    public String redSaleOil(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        if (siteUser == null) return "Error: Ошибка Авторизации";
        if (!siteUser.isUserHasRole(principal, "ROLE_SALE_RED")) return "Error: не достаточно прав";
        //считываем основные параметры
        int saleNumber = -1;
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
        double totalSum = 0;
        int orgDogId = -1;
        try {
            saleNumber = Integer.parseInt(request.getParameter("saleNumber"));
            idUnit = Integer.parseInt(request.getParameter("idUnit"));
            idOrg = Integer.parseInt(request.getParameter("OrgId"));
            idOilType = Integer.parseInt(request.getParameter("oilTypeId"));
            lt = Integer.parseInt(request.getParameter("lt"));
            colLiters = Double.parseDouble(request.getParameter("colLiters"));
            priceLiters = Double.parseDouble(request.getParameter("priceLiters"));
            priceShipping = Double.parseDouble(request.getParameter("priceShipping"));
            sum = Double.parseDouble(request.getParameter("sum"));
            totalSum = Double.parseDouble(request.getParameter("totalSum"));
            orgDogId = Integer.parseInt(request.getParameter("orgDogId"));
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

        String sql = "update salebid set ";
        ArrayList<Object> args = new ArrayList<Object>();

        sql += "sale_id_unit = ?,";
        args.add(idUnit);

        sql += "sale_id_org = ?,";
        args.add(idOrg);

        sql += "sale_driver_fio = ?,";
        args.add(fio);

        sql += "sale_car_number = ?,";
        args.add(carNumber);

        sql += "sale_id_oil = ?,";
        args.add(idOilType);

        sql += "sale_lt = ?,";
        args.add(lt);

        sql += "sale_col = ?,";
        args.add(colLiters);

        sql += "sale_price_oil = ?,";
        args.add(priceLiters);

        sql += "sale_price_shipping = ?,";
        args.add(priceShipping);

        sql += "sale_org_dog_id = ?,";
        args.add(orgDogId);

        if (totalSum < 0) {
            sql += "sale_is_block = 1,";
        } else {
            sql += "sale_is_block = 0,";
        }

        sql += "sale_sum = ?";
        args.add(sum);

        sql += " where id_sale = ?";
        args.add(saleNumber);
        if (!dbMvc.getUpdateResult(sql, args)) {
            return "Неизвестная ошибка обновления заявки";
        }
        return "Заявка обновлена";
    }

    public String changeStatusSaleOil(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        String idSale = request.getParameter("saleNumber");
        SaleOil saleOil = getSale(idSale);
        if (saleOil == null) return "Продажа не найдена";
        String sql = "update salebid set sale_is_block = ? where id_sale = ?";
        ArrayList<Object> args = new ArrayList<Object>();
        String response;
        if (saleOil.isBlock()) {
            args.add(0);
            response = "0";
        } else {
            args.add(1);
            response = "1";
        }
        args.add(idSale);
        if (dbMvc.getUpdateResult(sql, args))
            return response;
        return "Error: ошибка обновления статуса";
    }

    private class SetRowThread implements Runnable {
        private SaleOil saleOil;
        Map row;

        SetRowThread(SaleOil saleOil, Map row) {
            this.saleOil = saleOil;
            this.row = row;
        }

        @Override
        public void run() {
            setBidFromMapRow();
        }

        private void setBidFromMapRow() {
            if (saleOil == null || row == null) return;
            Object id = row.get("id_sale");
            if (id instanceof Long)
                saleOil.setId(((Long) id).intValue());
            else saleOil.setId((Integer) id);
            saleOil.setCompanyUnit(companyUnitMvc.getCompanyUnit((Integer) row.get("sale_id_unit")));
            saleOil.setOrganization(organizationMvc.getOrganization((Integer) row.get("sale_id_org")));
            saleOil.setFio(row.get("sale_driver_fio").toString());
            saleOil.setCarNumber(row.get("sale_car_number").toString());
            saleOil.setOilType(oilTypeMvc.getOilType((Integer) row.get("sale_id_oil")));
            saleOil.setLt((Integer) row.get("sale_lt"));
            saleOil.setCol((Double) row.get("sale_col"));
            saleOil.setPriceOil((Double) row.get("sale_price_oil"));
            saleOil.setPriceShipping((Double) row.get("sale_price_shipping"));
            saleOil.setSum((Double) row.get("sale_sum"));
            saleOil.setOrgPactId((Integer) row.get("sale_org_dog_id"));
            int block = (Integer) row.get("sale_is_block");
            if (block == 1) saleOil.setBlock(true);
            else saleOil.setBlock(false);
            int read = (Integer) row.get("sale_is_read");
            if (read == 1) saleOil.setRead(true);
            else saleOil.setRead(false);
            saleOil.setUserRead(siteUserMvc.findSiteUser((Integer) row.get("sale_read_user_id")));
            Object dateRead;
            dateRead = row.get("sale_is_read_date");
            if (dateRead != null)
                saleOil.setDateIsRead(dateRead.toString().split(" ")[0]);
            int isDone = (Integer) row.get("sale_is_done");
            if (isDone == 1) saleOil.setDone(true);
            Object dateDone = null;
            dateDone = row.get("sale_is_done_date");
            if (dateDone != null)
                saleOil.setDateIsDone(dateDone.toString().split(" ")[0]);
            Object dateCreate = null;
            dateCreate = row.get("sale_create_date");
            if (dateCreate != null)
                saleOil.setDateCreate(dateCreate.toString().split(" ")[0]);
            saleOil.setUserCreate(siteUserMvc.findSiteUser((Integer) row.get("sale_create_user_id")));
            Object dateClose = null;
            dateClose = row.get("sale_is_close_date");
            if (dateClose != null)
                saleOil.setDateIsClose(dateClose.toString().split(" ")[0]);
            saleOil.setUserClose(siteUserMvc.findSiteUser((Integer) row.get("sale_close_user_id")));
        }
    }

    public SaleOil getSaleForView(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        if (!siteUserMvc.findSiteUser(principal).isUserHasRole(principal, "ROLE_SALE_VIEW")) return null;
        String saleId = request.getParameter("saleIdButton");
        if (saleId == null) return null;
        return getSale(saleId);
    }

    public SaleOil saleUpdate(SiteUser siteUser, UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        if (!siteUser.isUserHasRole(principal, "ROLE_SALE_UPDATE")) return null;
        String saleIdString = request.getParameter("saleNumber");
        if (saleIdString == null) return null;
        String colLitersString = request.getParameter("colLiters");
        String priceShippingString = request.getParameter("priceShipping");
        double colLiters = 0;
        double priceShipping = 0;
        int saleId = -1;
        try {
            colLiters = Double.parseDouble(colLitersString);
            if (priceShippingString != null)
                priceShipping = Double.parseDouble(priceShippingString);
            saleId = Integer.parseInt(saleIdString);
        } catch (Exception e) {
            return null;
        }

        if (getSale(saleId) == null) return null;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "update salebid set sale_is_done = 1, sale_is_done_date = now(), sale_done_user_id = ?, " +
                "sale_col_out = ?, sale_price_shipping_out = ? where id_sale = ?";
        args.add(siteUser.getId());
        args.add(colLiters);
        args.add(priceShipping);
        args.add(saleId);
        if (!dbMvc.getUpdateResult(sql, args)) return null;
        return getSale(saleId);
    }

    public SaleOil getSale(int saleId) {
        ArrayList<SaleOil> saleOilList = null;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "select * from salebid where id_sale = ?";
        args.add(saleId);
        saleOilList = getSaleListFromDbSelect(sql, args);
        if (saleOilList != null && saleOilList.size() == 1) return saleOilList.get(0);
        return null;
    }

    public SaleOil getSale(String saleId) {
        int id = -1;
        try {
            id = Integer.parseInt(saleId);
        } catch (Exception e) {
            return null;
        }
        return getSale(id);
    }
}
