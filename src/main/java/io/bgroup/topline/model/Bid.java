package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Bid {
    private String id_bid;
    private SiteUser createUser;
    private String name;
    private OilStorage oilStorageIn;
    private Driver driver;
    private Car car;
    private Trailer trailer;
    private String fileLink;
    //private ArrayList<BidDetail> bidDetailList;

    private String bid_date_freeze;
    private int bid_is_freeze;
    private String bid_date_close;
    private String bid_is_close;
    private String bid_is_done;
    private String bid_date_done;
    private String bid_date_create;
    private String bid_date_last_update;
    private boolean isDone;

    //для логики
    private String error;
    private boolean emptySectionFlag = true;


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
    private DbModel dbMvc;
    @Autowired
    private BidDetail bidDetailMvc;
    @Autowired
    private MyConstant myConstantMvc;

    public SiteUser getCreateUser() {
        return createUser;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    private void setCreateUser(SiteUser createUser) {
        this.createUser = createUser;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public Bid() {
    }

    public String getBid_date_create() {
        return bid_date_create;
    }

    public void setBid_date_create(String bid_date_create) {
        this.bid_date_create = bid_date_create;
    }

    public String getBid_date_last_update() {
        return bid_date_last_update;
    }

    public void setBid_date_last_update(String bid_date_last_update) {
        this.bid_date_last_update = bid_date_last_update;
    }

    public String getBid_date_freeze() {
        return bid_date_freeze;
    }

    public void setBid_date_freeze(String bid_date_freeze) {
        this.bid_date_freeze = bid_date_freeze;
    }

    public int getBid_is_freeze() {
        return bid_is_freeze;
    }

    public void setBid_is_freeze(int bid_is_freeze) {
        this.bid_is_freeze = bid_is_freeze;
    }

    public String getBid_date_close() {
        return bid_date_close;
    }

    public void setBid_date_close(String bid_date_close) {
        this.bid_date_close = bid_date_close;
    }

    public String getBid_is_close() {
        return bid_is_close;
    }

    public void setBid_is_close(String bid_is_close) {
        this.bid_is_close = bid_is_close;
    }

    public String getBid_is_done() {
        return bid_is_done;
    }

    public void setBid_is_done(String bid_is_done) {
        this.bid_is_done = bid_is_done;
    }

    public String getBid_date_done() {
        return bid_date_done;
    }

    public void setBid_date_done(String bid_date_done) {
        this.bid_date_done = bid_date_done;
    }

    public String getId_bid() {
        return id_bid;
    }

    private void setId_bid(String id_bid) {
        this.id_bid = id_bid;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public OilStorage getOilStorageIn() {
        return oilStorageIn;
    }

    private void setOilStorageIn(OilStorage oilStorageIn) {
        this.oilStorageIn = oilStorageIn;
    }

    public Driver getDriver() {
        return driver;
    }

    private void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Car getCar() {
        return car;
    }

    private void setCar(Car car) {
        this.car = car;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    private void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public String createBid(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        if (siteUser == null) return "Error: Ошибка Авторизации";
        if (!siteUser.isUserHasRole(principal, "ROLE_BID_CREATE")) return "Error: не достаточно прав";
        //считываем основные параметры
        //String bidNumber = request.getParameter("bidNumber").toString(); // отключил
        String oilStorageId = request.getParameter("oilStorage").toString();
        String driverId = request.getParameter("driver").toString();
        String carId = request.getParameter("car");
        String trailerId = request.getParameter("trailerId");
        // на их основе подтягиваем данные
        Car car = carMvc.getCar(carId);
        Driver driver = driverMvc.getDriver(driverId);

        Trailer trailer = trailerMvc.getTrailer(trailerId);
        OilStorage oilStorage = oilStorageMvc.getOilStorage(oilStorageId);
        if (car == null || driver == null || oilStorage == null)
            return "Error: не заданы машина, водитель или пункт загрузки";
        ArrayList<OilSections> carOilSections = car.getOilSections();
        ArrayList<OilSections> trailerOilSections = null;
        if (carOilSections == null || carOilSections.size() == 0) {
            if (trailer == null) return "Error: не возможно выбрать секцию в машине или прицепе";
            trailerOilSections = trailer.getOilSections();
            if (trailerOilSections == null || trailerOilSections.size() == 0)
                return "Error: не возможно выбрать секцию в прицепе";
        } else {
            if (trailer != null) {
                trailerOilSections = trailer.getOilSections();
            }
        }
        String sql = "insert into bids ";
        String columns = "";
        String values = "";

        if (siteUser != null) {
            columns = strPlusCommaPlusValue(columns, "bid_create_user_id");
            values = strPlusCommaPlusValue(values, "'" + siteUser.getId() + "'");
        }
        /*
        if (bidNumber != null) {
            columns = strPlusCommaPlusValue(columns, "bid_number");
            values = strPlusCommaPlusValue(values, "'" + bidNumber + "'");
        }
        */
        {
            //дата создания, она же дата последнего апдейта
            columns = strPlusCommaPlusValue(columns, "bid_date_create");
            values = strPlusCommaPlusValue(values, "now()");
            columns = strPlusCommaPlusValue(columns, "bid_date_last_update");
            values = strPlusCommaPlusValue(values, "now()");
        }
        if (oilStorage.getIdOilStorage() > 0) {
            columns = strPlusCommaPlusValue(columns, "bid_storage_in_id");
            values = strPlusCommaPlusValue(values, "'" + oilStorage.getIdOilStorage() + "'");
        }
        if (driver.getIdDriver() != null) {
            columns = strPlusCommaPlusValue(columns, "bid_driver_id");
            values = strPlusCommaPlusValue(values, "'" + driver.getIdDriver() + "'");
        }
        if (car.getId_car() != null) {
            columns = strPlusCommaPlusValue(columns, "bid_car_id");
            values = strPlusCommaPlusValue(values, "'" + car.getId_car() + "'");
        }
        if (carOilSections != null && carOilSections.size() != 0) {
            String[] columnAndValue = new String[2];
            columnAndValue[0] = columns;
            columnAndValue[1] = values;
            addColumnsAndValuesForSections(columnAndValue, carOilSections, request);
            columns = columnAndValue[0];
            values = columnAndValue[1];
        }

        if (trailerOilSections != null && trailerOilSections.size() != 0) {
            if (trailer != null) {
                columns = strPlusCommaPlusValue(columns, "bid_trailer_id");
                values = strPlusCommaPlusValue(values, "'" + trailer.getId_trailer() + "'");
            }
            String[] columnAndValue = new String[2];
            columnAndValue[0] = columns;
            columnAndValue[1] = values;
            addColumnsAndValuesForSections(columnAndValue, trailerOilSections, request);
            columns = columnAndValue[0];
            values = columnAndValue[1];
        }
        if (emptySectionFlag) return "Error: секции пусты";
        sql += "(" + columns + ") values (" + values + ")";
        if (!dbMvc.getInsertResult(sql)) {
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

    private void addColumnsAndValuesForSections(String[] columnAndValue, ArrayList<OilSections> tmpOilSections, HttpServletRequest request) {
        String columns = columnAndValue[0];
        String values = columnAndValue[1];
        for (OilSections oilSections : tmpOilSections) {
            String oilTypeIdTmp = request.getParameter(oilSections.getId_section() + "_oilTypeId");
            if (oilTypeIdTmp.equals("-1")) continue;
            String storageOutIdTmp = request.getParameter(oilSections.getId_section() + "_storageOutId");
            if (storageOutIdTmp.equals("-1")) continue;
            emptySectionFlag = false;
            columns = strPlusCommaPlusValue(columns, "bid_" + oilSections.getId_section() + "_oilType_id");
            values = strPlusCommaPlusValue(values, "'" + oilTypeIdTmp + "'");
            columns = strPlusCommaPlusValue(columns, "bid_" + oilSections.getId_section() + "_storageOut_id");
            values = strPlusCommaPlusValue(values, "'" + storageOutIdTmp + "'");
        }
        columnAndValue[0] = columns;
        columnAndValue[1] = values;
    }

    public ArrayList<Bid> getBidsList(UsernamePasswordAuthenticationToken principal) {
        if (!siteUserMvc.isUserHasRole(principal, "ROLE_BID_LIST")) return null;
        SiteUser siteUserTmp = siteUserMvc.findSiteUser(principal);
        if (siteUserTmp == null) return null;
        String sql = null;
        if (siteUserTmp.getPost() == null && !siteUserTmp.getName().equals("admin")) return null;
        if (siteUserTmp.getName().equals("admin")) { //  admin
            sql = "select * from bids where bid_is_close='0'";
        } else if (siteUserTmp.getPost().getIdPost().equals("1")) { //  руководитель
            sql = "select * from bids where bid_is_close='0' and bid_create_user_id = '" + siteUserTmp.getId() + "'";
        } else if (siteUserTmp.getPost().getIdPost().equals("4")) { //  наблюдатель
            sql = "select * from bids where bid_is_close='0'";
        } else if (siteUserTmp.getPost().getIdPost().equals("2")) { //Водитель
            sql = "select * from bids where bid_is_close='0' and bid_driver_id = '" + siteUserTmp.getId() + "'";
        } else if (siteUserTmp.getPost().getIdPost().equals("3")) { //Оператор
            int companyUnitId = siteUserTmp.getCompanyUnit().getIdCompanyUnit();
            if (companyUnitId <= 0) return null;
            sql = "select * from bids where bid_is_close='0' and (" +
                    "bid_storage_in_id = '" + companyUnitId + "' " +
                    "or bid_car_sec_1_storageOut_id = '" + companyUnitId + "' " +
                    "or bid_car_sec_2_storageOut_id = '" + companyUnitId + "' " +
                    "or bid_car_sec_3_storageOut_id = '" + companyUnitId + "' " +
                    "or bid_trailer_sec_1_storageOut_id = '" + companyUnitId + "' " +
                    "or bid_trailer_sec_2_storageOut_id = '" + companyUnitId + "' " +
                    "or bid_trailer_sec_3_storageOut_id = '" + companyUnitId + "' " +
                    "or bid_trailer_sec_4_storageOut_id = '" + companyUnitId + "' " +
                    "or bid_trailer_sec_5_storageOut_id = '" + companyUnitId + "' " +
                    "or bid_trailer_sec_6_storageOut_id = '" + companyUnitId + "' )";
        }
        if (sql == null) return null;
        ArrayList<Bid> bidsList = null;
        sql += " order by id_bids";
        bidsList = getBidsFromDbSelect(sql);
        setDoneForBid(bidsList, siteUserTmp);
        return bidsList;
    }

    private void setDoneForBid(ArrayList<Bid> bidsList, SiteUser siteUser) {
        if (bidsList == null || siteUser == null) return;
        for (Bid bid : bidsList) {
            ArrayList<BidDetail> bidDetailsCar = bidDetailMvc.getBidDetailList(bid.getId_bid(), bid.getCar());
            ArrayList<BidDetail> bidDetailsTrailer = bidDetailMvc.getBidDetailList(bid.getId_bid(), bid.getTrailer());
            boolean isCarSectionBidUp = bidDetailMvc.isSectionBidUp(bidDetailsCar, bid, siteUser);
            boolean isTrailerSectionBidUp = bidDetailMvc.isSectionBidUp(bidDetailsTrailer, bid, siteUser);
            if ((bidDetailsCar == null || isCarSectionBidUp == true)
                    && (bidDetailsTrailer == null || isTrailerSectionBidUp == true)) {
                bid.setDone(true);
            } else bid.setDone(false);
        }
    }

    private ArrayList<Bid> getBidsFromDbSelect(String sql) {
        List<Map<String, Object>> bidsListFromDb = null;
        bidsListFromDb = dbMvc.getSelectResult(sql);
        if (bidsListFromDb == null) return null;
        ArrayList<Bid> bidsList = null;
        for (Map row : bidsListFromDb) {
            Bid bid = new Bid();
            setBidFromMapRow(bid, row);
            if (bidsList == null) bidsList = new ArrayList<Bid>();
            bidsList.add(bid);
        }
        return bidsList;
    }

    private void setBidFromMapRow(Bid bid, Map row) {
        if (bid == null || row == null) return;
        Iterator<Map.Entry<String, Object>> iterator = row.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> pair = iterator.next();
            if (pair.getKey().equals("id_bids")) {
                if (pair.getValue() != null) {
                    bid.setId_bid(pair.getValue().toString());
                } else bid.setId_bid(null);
            } else if (pair.getKey().equals("bid_create_user_id")) {
                if (pair.getValue() != null) {
                    bid.setCreateUser(siteUserMvc.findSiteUser((Integer) pair.getValue()));
                } else bid.setCreateUser(null);
            } else if (pair.getKey().equals("bid_number")) {
                if (pair.getValue() != null) {
                    bid.setName((String) pair.getValue());
                } else bid.setName(null);
            } else if (pair.getKey().equals("bid_storage_in_id")) {
                if (pair.getValue() != null) {
                    bid.setOilStorageIn(oilStorageMvc.getOilStorage(pair.getValue().toString()));
                } else bid.setOilStorageIn(null);
            } else if (pair.getKey().equals("bid_driver_id")) {
                if (pair.getValue() != null) {
                    bid.setDriver(driverMvc.getDriver(pair.getValue().toString()));
                } else bid.setDriver(null);
            } else if (pair.getKey().equals("bid_car_id")) {
                if (pair.getValue() != null) {
                    bid.setCar(carMvc.getCar(pair.getValue().toString()));
                } else bid.setCar(null);
            } else if (pair.getKey().equals("bid_trailer_id")) {
                if (pair.getValue() != null) {
                    bid.setTrailer(trailerMvc.getTrailer(pair.getValue().toString()));
                } else bid.setTrailer(null);
            } else if (pair.getKey().equals("bid_date_freeze")) {
                if (pair.getValue() != null) {
                    bid.setBid_date_freeze(pair.getValue().toString());
                } else bid.setBid_date_freeze(null);
            } else if (pair.getKey().equals("bid_is_freeze")) {
                if (pair.getValue() != null) {
                    bid.setBid_is_freeze((Integer) pair.getValue());
                } else bid.setBid_is_freeze(0);
            } else if (pair.getKey().equals("bid_date_close")) {
                if (pair.getValue() != null) {
                    bid.setBid_date_close(pair.getValue().toString());
                } else bid.setBid_date_close(null);
            } else if (pair.getKey().equals("bid_is_close")) {
                if (pair.getValue() != null) {
                    bid.setBid_is_close(pair.getValue().toString());
                } else bid.setBid_is_close(null);
            } else if (pair.getKey().equals("bid_is_done")) {
                if (pair.getValue() != null) {
                    bid.setBid_is_done(pair.getValue().toString());
                } else bid.setBid_is_done(null);
            } else if (pair.getKey().equals("bid_date_done")) {
                if (pair.getValue() != null) {
                    bid.setBid_date_done(pair.getValue().toString());
                } else bid.setBid_date_done(null);
            } else if (pair.getKey().equals("bid_date_create")) {
                if (pair.getValue() != null) {
                    bid.setBid_date_create((String) pair.getValue().toString().split(" ")[0]);
                } else bid.setBid_date_create(null);
            } else if (pair.getKey().equals("bid_date_last_update")) {
                if (pair.getValue() != null) {
                    bid.setBid_date_last_update((String) pair.getValue().toString());
                } else bid.setBid_date_last_update(null);
            }
        }
    }

    public Bid getBidForView(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        if (!siteUserMvc.findSiteUser(principal).isUserHasRole(principal, "ROLE_BID_VIEW")) return null;
        String bidId = request.getParameter("bidIdButton");
        if (bidId == null) return null;
        Bid bid = getBid(bidId);
        return bid;
    }

    public Bid getBid(String bidId) {
        if (bidId == null) return null;
        String sql = "select * from bids where id_bids='" + bidId + "'";
        if (sql == null) return null;
        ArrayList<Bid> bidsList = null;
        bidsList = getBidsFromDbSelect(sql);
        if (bidsList == null || bidsList.size() != 1) return null;
        Bid bid = bidsList.get(0);
        return bid;
    }

    public Bid updateBid(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        if (siteUser == null || !siteUser.isUserHasRole(principal, "ROLE_BID_UPDATE")) return null;
        Bid bid = null;
        String bidId = (String) request.getParameter("bidId").toString();
        if (bidId == null) return null;
        bid = getBid(bidId);
        if (bid == null) return null;
        if (!updateBidDbFields(bid, request)) return null;
        return getBid(bidId);
    }

    private boolean updateBidDbFields(Bid bid, HttpServletRequest request) {
        ArrayList<BidDetail> bidDetailsCar = bidDetailMvc.getBidDetailList(bid.getId_bid(), bid.getCar());
        ArrayList<BidDetail> bidDetailsTrailer = bidDetailMvc.getBidDetailList(bid.getId_bid(), bid.getTrailer());
        String sql = "update bids set ";
        String suffix = "";
        //if (bid.getBid_is_freeze().equals("0")) {
        if (bid.getBid_is_freeze() == 0) {
            suffix = "in";
            sql += "bid_date_freeze=now(), bid_is_freeze='1',";
        } else suffix = "out";
        String sqlCar = addSqlForUpdateString(bidDetailsCar, suffix, request);
        String sqlTrailer = addSqlForUpdateString(bidDetailsTrailer, suffix, request);
        if (!sqlCar.equals("")) sql += sqlCar;
        if (!sqlCar.equals("") && !sqlTrailer.equals("")) sql += "," + sqlTrailer;
        if (sqlCar.equals("") && !sqlTrailer.equals("")) sql += sqlTrailer;
        if (sqlCar.equals("") && sqlTrailer.equals("")) return false;
        sql += " where id_bids='" + bid.getId_bid() + "'";
        if (dbMvc.getInsertResult(sql)) return false;
        return true;
    }

    private String addSqlForUpdateString(ArrayList<BidDetail> bidDetails, String suffix, HttpServletRequest request) {
        String sql = "";
        if (bidDetails == null) return sql;
        for (BidDetail bidDetail : bidDetails) {
            String strP = request.getParameter(bidDetail.getSection().getId_section() + "_p");
            String strT = request.getParameter(bidDetail.getSection().getId_section() + "_t");
            String volume = request.getParameter(bidDetail.getSection().getId_section() + "_volume");
            String strM = request.getParameter(bidDetail.getSection().getId_section() + "_mass");
            if (volume != null && strM != null && strP != null && strT != null) {
                if (!sql.equals("")) sql += ",";
                sql += "bid_" + bidDetail.getSection().getId_section() + "_date_" + suffix + "=now(),";
                sql += "bid_" + bidDetail.getSection().getId_section() + "_volume_" + suffix + "='" + volume + "',";
                sql += "bid_" + bidDetail.getSection().getId_section() + "_p_" + suffix + "='" + strP + "',";
                sql += "bid_" + bidDetail.getSection().getId_section() + "_t_" + suffix + "='" + strT + "',";
                sql += "bid_" + bidDetail.getSection().getId_section() + "_mass_" + suffix + "='" + strM + "'";
            }
        }
        return sql;
    }

    public void closeBid(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        if (!siteUser.isUserHasRole(principal, "ROLE_BID_RED")) return;
        String bidId = request.getParameter("bidIdButton");
        if (bidId == null) return;
        String sql = "update bids set bid_date_close=now(), bid_is_close='1' where id_bids='" + bidId + "'";
        dbMvc.getInsertResult(sql);
    }

    public String redBid(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        if (!siteUser.isUserHasRole(principal, "ROLE_BID_RED")) return "Нет прав на изменения";
        String bidId = request.getParameter("bidId");
        if (bidId == null) return "Нет данных по заявке";
        Bid bid = getBid(bidId);
        if (bid == null) return "Нет такой заявки";
        String oilStorageInId = request.getParameter("oilStorage");
        if (oilStorageInId == null || oilStorageInId.equals("-1")) return "нет данных о загрузке";
        OilStorage oilStorageIn = oilStorageMvc.getOilStorage(oilStorageInId);
        if (oilStorageIn == null) return "нет данных о таком месте загрузки";
        String driverId = request.getParameter("driver");
        if (driverId == null) return "нет данных о водителе";
        Driver driver = driverMvc.getDriver(driverId);
        if (driver == null) return "Водитель не найден";
        String carId = request.getParameter("car");
        if (carId == null || carId.equals("-1")) return "машина не указана";
        Car car = carMvc.getCar(carId);
        if (car == null) return "машина не найдена";
        String trailerId = request.getParameter("trailerId");
        if (trailerId == null) return "что-то не то с прицепом";
        if (trailerId.equals("")) trailerId = "-1";
        Trailer trailer = trailerMvc.getTrailer(trailerId);
        //if (trailer == null && !trailerId.equals("-1")) return "трейлер не найден";
        String sql = "";
        sql += "update bids set bid_date_last_update=now()";
        sql += ", bid_storage_in_id='" + oilStorageIn.getIdOilStorage() + "'";
        sql += ", bid_driver_id='" + driver.getIdDriver() + "'";
        sql += ", bid_car_id='" + car.getId_car() + "'";
        ArrayList<OilSections> carOilSections = car.getOilSections();
        sql += addSqlForUpdateStringForBidRed(carOilSections, request);
        if (trailer != null) {
            sql += ", bid_trailer_id='" + trailer.getId_trailer() + "'";
            ArrayList<OilSections> trailerOilSections = trailer.getOilSections();
            sql += addSqlForUpdateStringForBidRed(trailerOilSections, request);
        }
        sql += " where id_bids=" + bid.getId_bid();
        if (!dbMvc.getInsertResult(sql))
            return "Данные сохранены";
        return "неизвестная ошибка редактирования заявки";
    }

    private String addSqlForUpdateStringForBidRed(ArrayList<OilSections> tmpOilSections, HttpServletRequest request) {
        String sql = "";
        if (tmpOilSections == null || request == null) return "";
        for (OilSections oilSections : tmpOilSections) {
            String oilTypeIdTmp = request.getParameter(oilSections.getId_section() + "_oilTypeId");
            String storageOutIdTmp = request.getParameter(oilSections.getId_section() + "_storageOutId");
            if (oilTypeIdTmp == null || oilTypeIdTmp.equals("-1") || storageOutIdTmp == null || storageOutIdTmp.equals("-1")) {
                sql += ", bid_" + oilSections.getId_section() + "_oilType_id";
                sql += "=null";
                sql += ", bid_" + oilSections.getId_section() + "_storageOut_id";
                sql += "=null";
            } else {
                //emptySectionFlag = false;
                sql += ", bid_" + oilSections.getId_section() + "_oilType_id";
                sql += "='" + oilTypeIdTmp + "'";
                sql += ", bid_" + oilSections.getId_section() + "_storageOut_id";
                sql += "='" + storageOutIdTmp + "'";
            }
        }
        return sql;
    }

    public boolean isPdfFileExist() {
        String filename = "";
        filename = myConstantMvc.getFileFolder() + myConstantMvc.getFilePrefix() + "_" + this.getId_bid()
                + "_" + this.getBid_date_create().replace("-", "") + ".pdf";
        File f = new File(filename);
        if(f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }
}