package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
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
    //private List<OilStorage> oilStorageOutList;
    private String dateOfCreation;
    private String dateOfClose;
    private String error;
    private boolean emptySectionFlag = true;

    @Autowired
    private SiteUser siteUserMvc;
    @Autowired
    private OilStorage oilStorageMvc;
    @Autowired
    private Driver driverMvc;
    @Autowired
    private Car carMvc;
    @Autowired
    private Trailer trailerMvc;
    @Autowired
    private DbModel dbMvc;

    public SiteUser getCreateUser() {
        return createUser;
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

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setDateOfClose(String dateOfClose) {
        this.dateOfClose = dateOfClose;
    }

    public Bid() {
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

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public String getDateOfClose() {
        return dateOfClose;
    }

    public String createBid(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        if (siteUser == null) return "Error: Ошибка Авторизации";
        if (!siteUser.isUserHasRole(principal, "ROLE_BID_CREATE")) return "Error: не достаточно прав";
        //считываем основные параметры
        String bidNumber = request.getParameter("bidNumber").toString();
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
        if (bidNumber != null) {
            columns = strPlusCommaPlusValue(columns, "bid_number");
            values = strPlusCommaPlusValue(values, "'" + bidNumber + "'");
        }
        {
            //дата создания, она же дата последнего апдейта
            columns = strPlusCommaPlusValue(columns, "bid_date_create");
            values = strPlusCommaPlusValue(values, "now()");
            columns = strPlusCommaPlusValue(columns, "bid_date_last_update");
            values = strPlusCommaPlusValue(values, "now()");
        }
        if (oilStorage.getIdOilStorage() != null) {
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
            addColumnsAndValuesForSetions(columnAndValue, carOilSections, request);
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
            addColumnsAndValuesForSetions(columnAndValue, trailerOilSections, request);
            columns = columnAndValue[0];
            values = columnAndValue[1];
        }
        if (emptySectionFlag) return "Error: секции пусты";
        sql += "(" + columns + ") values (" + values + ")";
        if (!dbMvc.getInsertResult(sql)) return "Заявка создана";
        else return "Неизвестная ошибка добавления заявки";
    }

    private String strPlusCommaPlusValue(String str, String value) {
        String strTmp = new String(str);
        if (!str.equals("")) strTmp += ",";
        strTmp += value;
        return strTmp;
    }

    private void addColumnsAndValuesForSetions(String[] columnAndValue, ArrayList<OilSections> tmpOilSections, HttpServletRequest request) {
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
        if (siteUserTmp.getPost()==null && !siteUserTmp.getName().equals("admin")) return null;
        if (siteUserTmp.getName().equals("admin") || siteUserTmp.getPost().getIdPost().equals("1")) { //  руководитель
            sql = "select * from bids where bid_is_close='0'";
        } else if (siteUserTmp.getPost().getIdPost().equals("2")) { //Водитель
            sql = "select * from bids where bid_is_close='0' and bid_driver_id = '" + siteUserTmp.getId() + "'";
        } else if (siteUserTmp.getPost().getIdPost().equals("3")) { //Оператор
            String companyUnitId = siteUserTmp.getCompanyUnit().getIdCompanyUnit();
            if (companyUnitId == null) return null;
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
        bidsList = getBidsFromDbSelect(sql);
        return bidsList;
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
            }
        }
    }
}