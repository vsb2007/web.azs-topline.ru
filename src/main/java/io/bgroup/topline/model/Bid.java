package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

public class Bid {
    private int id_bid;
    private SiteUser createUser;
    private String name;
    private OilStorage oilStorageIn;
    private Driver driver;
    private Car car;
    private Trailer trailer;
    private String fileLink;

    private boolean driverCanUpdateIn;
    private boolean driverCanUpdateOut;
    private String bid_date_freeze;
    private boolean bid_is_freeze;
    private String bid_date_close;
    private boolean bid_is_close;
    private boolean bid_is_done;
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
    private DbJdbcModel dbMvc;
    @Autowired
    private BidDetail bidDetailMvc;
    @Autowired
    private MyConstant myConstantMvc;
    @Autowired
    private CompanyUnit companyUnitMvc;

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

    public boolean isDriverCanUpdateOut() {
        return driverCanUpdateOut;
    }

    public void setDriverCanUpdateOut(boolean driverCanUpdateOut) {
        this.driverCanUpdateOut = driverCanUpdateOut;
    }

    public boolean isDriverCanUpdateIn() {
        return driverCanUpdateIn;
    }

    public void setDriverCanUpdateIn(boolean driverCanUpdateIn) {
        this.driverCanUpdateIn = driverCanUpdateIn;
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

    public boolean getBid_is_freeze() {
        return bid_is_freeze;
    }

    public void setBid_is_freeze(boolean bid_is_freeze) {
        this.bid_is_freeze = bid_is_freeze;
    }

    public String getBid_date_close() {
        return bid_date_close;
    }

    public void setBid_date_close(String bid_date_close) {
        this.bid_date_close = bid_date_close;
    }

    public boolean getBid_is_close() {
        return bid_is_close;
    }

    public void setBid_is_close(boolean bid_is_close) {
        this.bid_is_close = bid_is_close;
    }

    public boolean getBid_is_done() {
        return bid_is_done;
    }

    public void setBid_is_done(boolean bid_is_done) {
        this.bid_is_done = bid_is_done;
    }

    public String getBid_date_done() {
        return bid_date_done;
    }

    public void setBid_date_done(String bid_date_done) {
        this.bid_date_done = bid_date_done;
    }

    public int getId_bid() {
        return id_bid;
    }

    private void setId_bid(int id_bid) {
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
        String oilStorageId = request.getParameter("oilStorage");
        String driverId = request.getParameter("driver");
        String driverCanUpdateIn = request.getParameter("driverCanUpdateIn");
        String driverCanUpdateOut = request.getParameter("driverCanUpdateOut");
        if (driverCanUpdateIn == null) {
            driverCanUpdateIn = "0";
        }
        if (driverCanUpdateOut == null) {
            driverCanUpdateOut = "0";
        }
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
        ArrayList<Object> args = new ArrayList<Object>();
        String columns = "";
        String values = "";

        if (siteUser != null) {
            columns = strPlusCommaPlusValue(columns, "bid_create_user_id");
            values = strPlusCommaPlusValue(values, "?");
            args.add(siteUser.getId());
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
        /*
        водила может принимать и сливать топливо
         */
        if (driverCanUpdateIn != null && driverCanUpdateOut != null) {
            columns = strPlusCommaPlusValue(columns, "bid_driverCanUpdateIn");
            values = strPlusCommaPlusValue(values, "?");
            args.add(driverCanUpdateIn);
            columns = strPlusCommaPlusValue(columns, "bid_driverCanUpdateOut");
            values = strPlusCommaPlusValue(values, "?");
            args.add(driverCanUpdateOut);
        }
        if (oilStorage.getIdOilStorage() > 0) {
            columns = strPlusCommaPlusValue(columns, "bid_storage_in_id");
            values = strPlusCommaPlusValue(values, "?");
            args.add(oilStorage.getIdOilStorage());
        }
        if (driver != null && driver.getIdDriver() >= 0) {
            columns = strPlusCommaPlusValue(columns, "bid_driver_id");
            values = strPlusCommaPlusValue(values, "?");
            args.add(driver.getIdDriver());
        }
        if (car.getId_car() != null) {
            columns = strPlusCommaPlusValue(columns, "bid_car_id");
            values = strPlusCommaPlusValue(values, "?");
            args.add(car.getId_car());
        }
        if (carOilSections != null && carOilSections.size() != 0) {
            String[] columnAndValue = new String[2];
            columnAndValue[0] = columns;
            columnAndValue[1] = values;
            addColumnsAndValuesForSections(columnAndValue, carOilSections, request, args);
            columns = columnAndValue[0];
            values = columnAndValue[1];
        }

        if (trailerOilSections != null && trailerOilSections.size() != 0) {
            if (trailer != null) {
                columns = strPlusCommaPlusValue(columns, "bid_trailer_id");
                values = strPlusCommaPlusValue(values, "?");
                args.add(trailer.getId_trailer());
            }
            String[] columnAndValue = new String[2];
            columnAndValue[0] = columns;
            columnAndValue[1] = values;
            addColumnsAndValuesForSections(columnAndValue, trailerOilSections, request, args);
            columns = columnAndValue[0];
            values = columnAndValue[1];
        }
        if (emptySectionFlag) return "Error: секции пусты";
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

    private void addColumnsAndValuesForSections(String[] columnAndValue, ArrayList<OilSections> tmpOilSections, HttpServletRequest request, ArrayList<Object> args) {
        String columns = columnAndValue[0];
        String values = columnAndValue[1];
        for (OilSections oilSections : tmpOilSections) {
            String oilTypeIdTmp = request.getParameter(oilSections.getId_section() + "_oilTypeId");
            if (oilTypeIdTmp.equals("-1")) continue;
            String storageOutIdTmp = request.getParameter(oilSections.getId_section() + "_storageOutId");
            if (storageOutIdTmp.equals("-1")) continue;
            emptySectionFlag = false;
            columns = strPlusCommaPlusValue(columns, "bid_" + oilSections.getId_section() + "_oilType_id");
            values = strPlusCommaPlusValue(values, "?");
            args.add(oilTypeIdTmp);
            columns = strPlusCommaPlusValue(columns, "bid_" + oilSections.getId_section() + "_storageOut_id");
            values = strPlusCommaPlusValue(values, "?");
            args.add(storageOutIdTmp);
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
        ArrayList<Object> args = new ArrayList<Object>();
        if (siteUserTmp.getName().equals("admin")) { //  admin
            sql = "select * from bids where bid_is_close=?";
            args.add("0");
        } else if (siteUserTmp.getPost().getIdPost() == 1) { //  руководитель
            sql = "select * from bids where bid_is_close=? and bid_create_user_id = ?";
            args.add("0");
            args.add(siteUserTmp.getId());
        } else if (siteUserTmp.getPost().getIdPost() == 4) { //  наблюдатель
            sql = "select * from bids where bid_is_close=?";
            args.add("0");
        } else if (siteUserTmp.getPost().getIdPost() == 2) { //Водитель
            sql = "select * from bids where bid_is_close = ? and bid_driver_id = ?";
            args.add("0");
            args.add(siteUserTmp.getId());
        } else if (siteUserTmp.getPost().getIdPost() == 3) { //Оператор
            int companyUnitId = siteUserTmp.getCompanyUnit().getIdCompanyUnit();
            if (companyUnitId <= 0) return null;
            sql = "select * from bids where bid_is_close=? and (" +
                    "bid_storage_in_id = ? " +
                    "or bid_car_sec_1_storageOut_id = ? " +
                    "or bid_car_sec_2_storageOut_id = ? " +
                    "or bid_car_sec_3_storageOut_id = ? " +
                    "or bid_trailer_sec_1_storageOut_id = ? " +
                    "or bid_trailer_sec_2_storageOut_id = ? " +
                    "or bid_trailer_sec_3_storageOut_id = ? " +
                    "or bid_trailer_sec_4_storageOut_id = ? " +
                    "or bid_trailer_sec_5_storageOut_id = ? " +
                    "or bid_trailer_sec_6_storageOut_id = ? )";
            args.add("0");
            args.add(companyUnitId);
            args.add(companyUnitId);
            args.add(companyUnitId);
            args.add(companyUnitId);
            args.add(companyUnitId);
            args.add(companyUnitId);
            args.add(companyUnitId);
            args.add(companyUnitId);
            args.add(companyUnitId);
            args.add(companyUnitId);
        }
        if (sql == null) return null;
        ArrayList<Bid> bidsList = null;
        sql += " order by id_bids";
        bidsList = getBidsFromDbSelect(sql, args);
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

    private ArrayList<Bid> getBidsFromDbSelect(String sql, ArrayList<Object> args) {
        List<Map<String, Object>> bidsListFromDb = null;
        bidsListFromDb = dbMvc.getSelectResult(sql, args);
        if (bidsListFromDb == null) return null;
        ArrayList<Bid> bidsList = new ArrayList<Bid>();
        ArrayList<Thread> threadArrayList = new ArrayList<Thread>();
        for (Map row : bidsListFromDb) {
            Bid bid = new Bid();
            //setBidFromMapRow(bid, row);
            bidsList.add(bid);
            Thread thread = new Thread(new Bid.SetRowThread(bid,row));
            thread.start();
            threadArrayList.add(thread);
        }
        for (Thread thread: threadArrayList){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return bidsList;
    }

    private class SetRowThread implements Runnable {
        private Bid bid;
        Map row;

        public SetRowThread(Bid bid, Map row) {
            this.bid = bid;
            this.row = row;
        }

        @Override
        public void run() {
            setBidFromMapRow();
        }

        private void setBidFromMapRow() {
            if (bid == null || row == null) return;
            bid.setId_bid((Integer) row.get("id_bids"));
            bid.setCreateUser(siteUserMvc.findSiteUser((Integer) row.get("bid_create_user_id")));
            bid.setName((String) row.get("bid_number"));
            bid.setOilStorageIn(oilStorageMvc.getOilStorage((Integer) row.get("bid_storage_in_id")));
            bid.setDriver(driverMvc.getDriver((Integer) row.get("bid_driver_id")));
            bid.setCar(carMvc.getCar((Integer) row.get("bid_car_id")));
            bid.setTrailer(trailerMvc.getTrailer((Integer) row.get("bid_trailer_id")));
            Object dateFreeze = null;
            dateFreeze = row.get("bid_date_freeze");
            if (dateFreeze != null)
                bid.setBid_date_freeze((String) dateFreeze.toString());
            else bid.setBid_date_freeze(null);
            int isFreezeField = (Integer) row.get("bid_is_freeze");
            if (isFreezeField == 1)
                bid.setBid_is_freeze(true);
            else bid.setBid_is_freeze(false);
            Object dateClose = null;
            dateClose = row.get("bid_date_close");
            if (dateClose != null)
                bid.setBid_date_close((String) dateClose.toString());
            else bid.setBid_date_close(null);
            int isCloseField = (Integer) row.get("bid_is_close");
            if (isCloseField == 1)
                bid.setBid_is_close(true);
            else bid.setBid_is_close(false);
            int isDoneField = (Integer) row.get("bid_is_done");
            if (isDoneField == 1)
                bid.setBid_is_done(true);
            else bid.setBid_is_done(false);
            Object dateDone = null;
            dateDone = row.get("bid_date_done");
            if (dateDone != null)
                bid.setBid_date_done((String) dateDone.toString());
            else bid.setBid_date_done(null);
            Object dateCreate = null;
            dateCreate = row.get("bid_date_create");
            if (dateCreate != null)
                bid.setBid_date_create(((String) dateCreate.toString()).split(" ")[0]);
            else bid.setBid_date_create(null);
            Object dateLastUpdate = null;
            dateLastUpdate = row.get("bid_date_last_update");
            if (dateLastUpdate != null)
                bid.setBid_date_last_update((String) dateLastUpdate.toString());
            else bid.setBid_date_last_update(null);
            int driverCanUpdateInField = (Integer) row.get("bid_driverCanUpdateIn");
            if (driverCanUpdateInField == 1)
                bid.setDriverCanUpdateIn(true);
            else bid.setDriverCanUpdateIn(false);
            int driverCanUpdateOutField = (Integer) row.get("bid_driverCanUpdateOut");
            if (driverCanUpdateOutField == 1)
                bid.setDriverCanUpdateOut(true);
            else bid.setDriverCanUpdateOut(false);

        /*
        Iterator<Map.Entry<String, Object>> iterator = row.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> pair = iterator.next();
            if (pair.getKey().equals("id_bids")) {
                if (pair.getValue() != null) {
                    bid.setId_bid((Integer) pair.getValue());
                } else bid.setId_bid(-1);
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
                    int isFreezeField = (Integer) pair.getValue();
                    if (isFreezeField == 1)
                        bid.setBid_is_freeze(true);
                    else bid.setBid_is_freeze(false);
                } else bid.setBid_is_freeze(false);
            } else if (pair.getKey().equals("bid_date_close")) {
                if (pair.getValue() != null) {
                    bid.setBid_date_close(pair.getValue().toString());
                } else bid.setBid_date_close(null);
            } else if (pair.getKey().equals("bid_is_close")) {
                if (pair.getValue() != null) {
                    int isCloseField = (Integer) pair.getValue();
                    if (isCloseField == 1)
                        bid.setBid_is_close(true);
                    else bid.setBid_is_close(false);
                } else bid.setBid_is_close(false);
            } else if (pair.getKey().equals("bid_is_done")) {
                if (pair.getValue() != null) {
                    int isDoneField = (Integer) pair.getValue();
                    if (isDoneField == 1)
                        bid.setBid_is_done(true);
                    else bid.setBid_is_done(false);
                } else bid.setBid_is_done(false);
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
            } else if (pair.getKey().equals("bid_driverCanUpdate")) {
                if (pair.getValue() != null) {
                    int driverCanUpdateField = (Integer) pair.getValue();
                    if (driverCanUpdateField == 1)
                        bid.setDriverCanUpdate(true);
                    else bid.setDriverCanUpdate(false);
                } else bid.setDriverCanUpdate(false);
            }
        }*/
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
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "select * from bids where id_bids = ?";
        args.add(bidId);
        if (sql == null) return null;
        ArrayList<Bid> bidsList = null;
        bidsList = getBidsFromDbSelect(sql, args);
        if (bidsList == null || bidsList.size() != 1) return null;
        Bid bid = bidsList.get(0);
        return bid;
    }

    public Bid updateBid(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        if (siteUser == null || !siteUser.isUserHasRole(principal, "ROLE_BID_UPDATE")) return null;
        Bid bid = null;
        String bidId = request.getParameter("bidId").toString();
        if (bidId == null) return null;
        bid = getBid(bidId);
        if (bid == null) return null;
        if (!updateBidDbFields(bid, request, siteUser)) return null;
        return getBid(bidId);
    }

    private boolean updateBidDbFields(Bid bid, HttpServletRequest request, SiteUser siteUser) {
        ArrayList<BidDetail> bidDetailsCar = bidDetailMvc.getBidDetailList(bid.getId_bid(), bid.getCar());
        ArrayList<BidDetail> bidDetailsTrailer = bidDetailMvc.getBidDetailList(bid.getId_bid(), bid.getTrailer());
        String sql = "update bids set bid_date_last_update=now(),";
        String suffix = "";
        //if (bid.getBid_is_freeze().equals("0")) {
        ArrayList<Object> args = new ArrayList<Object>();
        if (!bid.getBid_is_freeze()) {
            suffix = "in";
            sql += "bid_date_freeze=now(), bid_is_freeze='1',";
        } else {
            suffix = "out";
        }
        String sqlCar = addSqlForUpdateString(bidDetailsCar, suffix, request, args, siteUser);
        String sqlTrailer = addSqlForUpdateString(bidDetailsTrailer, suffix, request, args, siteUser);
        if (!sqlCar.equals("")) sql += sqlCar;
        if (!sqlCar.equals("") && !sqlTrailer.equals("")) sql += "," + sqlTrailer;
        if (sqlCar.equals("") && !sqlTrailer.equals("")) sql += sqlTrailer;
        if (sqlCar.equals("") && sqlTrailer.equals("")) return false;
        sql += " where id_bids=?";

        args.add(bid.getId_bid());
        /*
        изменения в контроль остатков
         */
        boolean flag = false;
        if (!bid.getBid_is_freeze()) {
            /* загрузка*/
            OilStorage oilStorageIn = bid.getOilStorageIn();
            if (oilStorageIn == null) return false;
            CompanyUnit companyUnit = oilStorageIn.getCompanyUnit();

            if (companyUnit.getOilTypeStorageArrayList() != null) {
                flag = updateOilTypeStorageControl(1, companyUnit, bidDetailsCar, bidDetailsTrailer, request);
            } else flag = true;
        } else {
            /*
            слив
             */
            CompanyUnit companyUnit = findCompanyUnitFromRequestOnOilOut(request, bidDetailsCar, bidDetailsTrailer);
            if (companyUnit == null) {
                return false;
            }
            if (companyUnit.getOilTypeStorageArrayList() != null) {
                flag = updateOilTypeStorageControl(0, companyUnit, bidDetailsCar, bidDetailsTrailer, request);
            } else {
                flag = true;
            }
        }
        /*
        закончили изменения в контроль остатков
         */
        //System.out.println(sql);
        if (flag) {
            if (!dbMvc.getUpdateResult(sql, args)) return false;
        } else return false;
        return true;
    }

    private CompanyUnit findCompanyUnitFromRequestOnOilOut(HttpServletRequest request, ArrayList<BidDetail> bidDetailsCar,
                                                           ArrayList<BidDetail> bidDetailsTrailer) {
        if (request == null) return null;
        if (bidDetailsCar == null && bidDetailsTrailer == null) return null;
        String destinationId = null;
        if (bidDetailsCar != null) {
            for (BidDetail bidDetail : bidDetailsCar) {
                destinationId = request.getParameter(bidDetail.getSection().getId_section() + "_destination");
                if (destinationId != null) break;
            }
        }
        if (bidDetailsTrailer != null && destinationId == null) {
            for (BidDetail bidDetail : bidDetailsTrailer) {
                destinationId = request.getParameter(bidDetail.getSection().getId_section() + "_destination");
                if (destinationId != null) break;
            }
        }
        if (destinationId == null) return null;
        int id = -1;
        try {
            id = Integer.parseInt(destinationId);
        } catch (Exception e) {
            id = -1;
        }
        CompanyUnit companyUnit = companyUnitMvc.getCompanyUnit(id);
        return companyUnit;
    }

    private boolean updateOilTypeStorageControl(int operation, CompanyUnit companyUnit, ArrayList<BidDetail> bidDetailsCar,
                                                ArrayList<BidDetail> bidDetailsTrailer, HttpServletRequest request) {
        ArrayList<OilTypeStorage> oilTypeStorageArrayList = companyUnit.getOilTypeStorageArrayList();
        HashMap<String, Double> arrayV = new HashMap<String, Double>();
        HashMap<String, Double> arrayM = new HashMap<String, Double>();
        for (OilTypeStorage oilTypeStorage : oilTypeStorageArrayList) {
            int idOilType = oilTypeStorage.getOilType().getId_oilType();
            arrayM.put(idOilType + "", oilTypeStorage.getVolumeM());
            arrayV.put(idOilType + "", oilTypeStorage.getVolumeV());
        }
        String message = getCheckForbidDetail(operation, bidDetailsCar, arrayV, arrayM, request);
        if (message.equals("1"))
            message = getCheckForbidDetail(operation, bidDetailsTrailer, arrayV, arrayM, request);
        if (!message.equals("1")) return false;
        String sql = "insert into oilstorage (id_oilStorage,volumeV,volumeM) " +
                "values ";
        ArrayList<Object> args = new ArrayList<Object>();
        boolean commaFlag = false;
        for (OilTypeStorage oilTypeStorage : oilTypeStorageArrayList) {
            double volumeVNew;
            double volumeMNew;
            if (!arrayV.containsKey(oilTypeStorage.getOilType().getId_oilType() + "")
                    || !arrayM.containsKey(oilTypeStorage.getOilType().getId_oilType() + "")) {
                continue;
            }
            volumeVNew = arrayV.get(oilTypeStorage.getOilType().getId_oilType() + "");
            volumeMNew = arrayM.get(oilTypeStorage.getOilType().getId_oilType() + "");
            /* округляем */
            volumeVNew = (double) ((int) Math.round(volumeVNew * 100)) / 100;
            volumeMNew = (double) ((int) Math.round(volumeMNew * 1000)) / 1000;
            if (commaFlag) {
                sql += ",";
            }
            sql += "(?,?,?)";
            if (!commaFlag) commaFlag = true;
            args.add(oilTypeStorage.getIdOilTypeStorage());
            args.add(volumeVNew);
            args.add(volumeMNew);
        }
        sql += " ON DUPLICATE KEY UPDATE " +
                "oilstorage.volumeV = VALUES(volumeV),oilstorage.volumeM = VALUES(volumeM)";
        boolean flag = dbMvc.getUpdateResult(sql, args);
        return flag;
    }

    private String addSqlForUpdateString(ArrayList<BidDetail> bidDetails, String suffix, HttpServletRequest request,
                                         ArrayList<Object> args, SiteUser siteUser) {
        String sql = "";
        if (bidDetails == null) return sql;
        boolean flagIsSuffixOut = false;
        if (suffix.equals("in")) {
            sql += "bid_user_in_id=?";
            args.add(siteUser.getId());
        } else {
            flagIsSuffixOut = true;
        }

        for (BidDetail bidDetail : bidDetails) {
            String strP = request.getParameter(bidDetail.getSection().getId_section() + "_p");
            String strT = request.getParameter(bidDetail.getSection().getId_section() + "_t");
            String volume = request.getParameter(bidDetail.getSection().getId_section() + "_volume");
            String strM = request.getParameter(bidDetail.getSection().getId_section() + "_mass");
            if (volume != null && strM != null && strP != null && strT != null) {
                if (!sql.equals("")) sql += ",";
                if (flagIsSuffixOut) {
                    sql += "bid_" + bidDetail.getSection().getId_section() + "_user_out_id=?,";
                    args.add(siteUser.getId());
                }
                sql += "bid_" + bidDetail.getSection().getId_section() + "_date_" + suffix + "=now(),";
                sql += "bid_" + bidDetail.getSection().getId_section() + "_volume_" + suffix + "=?,";
                args.add(volume);
                sql += "bid_" + bidDetail.getSection().getId_section() + "_p_" + suffix + "=?,";
                args.add(strP);
                sql += "bid_" + bidDetail.getSection().getId_section() + "_t_" + suffix + "=?,";
                args.add(strT);
                sql += "bid_" + bidDetail.getSection().getId_section() + "_mass_" + suffix + "=?";
                args.add(strM);
            }
        }
        return sql;
    }

    public void closeBid(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        if (!siteUser.isUserHasRole(principal, "ROLE_BID_RED")) return;
        String bidId = request.getParameter("bidIdButton");
        ArrayList<Object> args = new ArrayList<Object>();
        if (bidId == null) return;
        String sql = "update bids set bid_date_close=now(), bid_is_close='1' where id_bids=?";
        args.add(bidId);
        dbMvc.getUpdateResult(sql, args);
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
        String driverCanUpdateInStr = request.getParameter("driverCanUpdateIn");
        String driverCanUpdateOutStr = request.getParameter("driverCanUpdateOut");
        if (driverCanUpdateInStr == null) driverCanUpdateInStr = "0";
        if (driverCanUpdateOutStr == null) driverCanUpdateOutStr = "0";
        int driverCanUpdateIn = -1;
        int driverCanUpdateOut = -1;
        try {
            driverCanUpdateIn = Integer.parseInt(driverCanUpdateInStr);
            driverCanUpdateOut = Integer.parseInt(driverCanUpdateOutStr);
        } catch (Exception e) {
            return "параметры водителя не верны";
        }

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
        ArrayList<Object> args = new ArrayList<Object>();
        sql += "update bids set bid_date_last_update=now()";
        sql += ", bid_storage_in_id=?";
        args.add(oilStorageIn.getIdOilStorage());
        sql += ", bid_driver_id=?";
        args.add(driver.getIdDriver());
        sql += ", bid_driverCanUpdateIn=?";
        args.add(driverCanUpdateIn);
        sql += ", bid_driverCanUpdateOut=?";
        args.add(driverCanUpdateOut);
        sql += ", bid_car_id=?";
        args.add(car.getId_car());
        ArrayList<OilSections> carOilSections = car.getOilSections();
        sql += addSqlForUpdateStringForBidRed(carOilSections, request, args);
        if (trailer != null) {
            sql += ", bid_trailer_id=?";
            args.add(trailer.getId_trailer());
            ArrayList<OilSections> trailerOilSections = trailer.getOilSections();
            sql += addSqlForUpdateStringForBidRed(trailerOilSections, request, args);
        }
        sql += " where id_bids=" + bid.getId_bid();
        if (dbMvc.getUpdateResult(sql, args))
            return "Данные сохранены";
        return "неизвестная ошибка редактирования заявки";
    }

    private String addSqlForUpdateStringForBidRed(ArrayList<OilSections> tmpOilSections, HttpServletRequest request, ArrayList<Object> args) {
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
                sql += "=?";
                args.add(oilTypeIdTmp);
                sql += ", bid_" + oilSections.getId_section() + "_storageOut_id";
                sql += "=?";
                args.add(storageOutIdTmp);
            }
        }
        return sql;
    }

    public boolean isPdfFileExist() {
        String filename = "";
        filename = myConstantMvc.getFileFolder() + myConstantMvc.getFilePrefix() + "_" + this.getId_bid()
                + "_" + this.getBid_date_create().replace("-", "") + ".pdf";
        File f = new File(filename);
        if (f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }

    public String checkUpdate(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        if (!siteUser.isUserHasRole(principal, "ROLE_BID_UPDATE")) return "-1";
        Bid bid = null;
        String bidId = (String) request.getParameter("bidId").toString();
        if (bidId == null) return "мало данных";
        bid = getBid(bidId);
        if (bid == null) return "заявка не найдена";
        if (bid.getOilStorageIn() == null) return "не найден отправитель";
        if (bid.getOilStorageIn().getCompanyUnit() == null) return "не найден отправитель";
        if (bid.getOilStorageIn().getCompanyUnit().getOilTypeStorageArrayList() == null) return "1";

        OilStorage oilStorageIn = bid.getOilStorageIn();
        Car car = bid.getCar();
        if (car == null) return "машина не найдена";
        Trailer trailer = bid.getTrailer();
        ArrayList<BidDetail> bidDetailsCar = bidDetailMvc.getBidDetailList(bid.getId_bid(), car);
        ArrayList<BidDetail> bidDetailsTrailer = bidDetailMvc.getBidDetailList(bid.getId_bid(), trailer);
        String checkFlag = null;
        checkFlag = checkForOilTypeStorage(bidDetailsCar, bidDetailsTrailer, request, oilStorageIn);
        if (checkFlag == null)
            return "неизвестная ошибка проверки остатков";
        else return checkFlag;
    }

    private String checkForOilTypeStorage(ArrayList<BidDetail> bidDetailsCar, ArrayList<BidDetail> bidDetailsTrailer,
                                          HttpServletRequest request, OilStorage oilStorageIn) {
        if (bidDetailsCar == null && bidDetailsTrailer == null) return "не найдены секции";
        if (request == null) return "мало данных";
        ArrayList<OilTypeStorage> oilTypeStorageArrayList = oilStorageIn.getCompanyUnit().getOilTypeStorageArrayList();
        //ArrayList<Object> arrayV = new ArrayList<Object>();
        HashMap<String, Double> arrayV = new HashMap<String, Double>();
        HashMap<String, Double> arrayM = new HashMap<String, Double>();
        for (OilTypeStorage oilTypeStorage : oilTypeStorageArrayList) {
            int idOilType = oilTypeStorage.getOilType().getId_oilType();
            arrayM.put(idOilType + "", oilTypeStorage.getVolumeM());
            arrayV.put(idOilType + "", oilTypeStorage.getVolumeV());
        }
        String message = getCheckForbidDetail(1, bidDetailsCar, arrayV, arrayM, request);
        if (message.equals("1"))
            message = getCheckForbidDetail(1, bidDetailsTrailer, arrayV, arrayM, request);
        return message;
    }

    private String getCheckForbidDetail(int operation, ArrayList<BidDetail> bidDetails,
                                        HashMap<String, Double> arrayV, HashMap<String, Double> arrayM, HttpServletRequest request) {
        if (bidDetails == null) return "1";
        for (BidDetail bidDetail : bidDetails) {
            String idOilTypeString = bidDetail.getOilType().getId_oilType() + "";
            if (!arrayV.containsKey(idOilTypeString)) continue;
            double volV = 0;
            double volM = 0;
            try {
                String volume = request.getParameter(bidDetail.getSection().getId_section() + "_volume");
                String strM = request.getParameter(bidDetail.getSection().getId_section() + "_mass");
                if (volume == null || strM == null) {
                    continue;
                }
                volV = Double.parseDouble(volume);
                volM = Double.parseDouble(strM);
            } catch (Exception e) {
                return "мало данных";
            }
            if (operation == 1) {
                arrayV.put(idOilTypeString, arrayV.get(idOilTypeString) - volV);
                arrayM.put(idOilTypeString, arrayM.get(idOilTypeString) - volM);
            }
            if (operation == 0) {
                arrayV.put(idOilTypeString, arrayV.get(idOilTypeString) + volV);
                arrayM.put(idOilTypeString, arrayM.get(idOilTypeString) + volM);
            }
            if (arrayV.get(idOilTypeString) < 0 || arrayM.get(idOilTypeString) < 0)
                return "Не достаточно топлива: " + bidDetail.getOilType().getOilTypeName();
        }
        return "1";
    }
}