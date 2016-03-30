package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

public class Bid {
    private String dateOfCreation;
    private String dateOfClose;
    private String error;
    /*
      private SiteUser siteUser;
      private OilStorage oilStorage;
      private Driver driver;
      private Car car;
      private Trailer trailer;
   */
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
        String sql = "insert into bids";
        String columns = "";
        String values = "";
        if (siteUser.getId() != null) {
            columns += strPlusCommaPlusValue(columns, "bid_create_user_id");
            values += strPlusCommaPlusValue(values, "'" + siteUser.getId() + "'");
        }
        if (bidNumber != null) {
            columns += strPlusCommaPlusValue(columns, "bid_number");
            values += strPlusCommaPlusValue(values, "'" + bidNumber + "'");
        }
        {
            //дата создания, она же дата последнего апдейта
            columns += strPlusCommaPlusValue(columns, "bid_date_create");
            values += strPlusCommaPlusValue(values, "now()");
            columns += strPlusCommaPlusValue(columns, "bid_date_last_update");
            values += strPlusCommaPlusValue(values, "now()");
        }
        if (oilStorage.getIdOilStorage() != null) {
            columns += strPlusCommaPlusValue(columns, "bid_storage_in_id");
            values += strPlusCommaPlusValue(values, "'" + oilStorage.getIdOilStorage() + "'");
        }
        if (driver.getIdDriver() != null) {
            columns += strPlusCommaPlusValue(columns, "bid_driver_id");
            values += strPlusCommaPlusValue(values, "'" + driver.getIdDriver() + "'");
        }
        if (car.getId_cars() != null) {
            columns += strPlusCommaPlusValue(columns, "bid_car_id");
            values += strPlusCommaPlusValue(values, "'" + car.getId_cars() + "'");
        }
        if (carOilSections != null && carOilSections.size() != 0) {
            for (OilSections oilSections: carOilSections){
                
            }
        }


        Map<String, String[]> param = request.getParameterMap();
        for (Map.Entry<String, String[]> pair : param.entrySet()) {
            System.out.println(pair.getKey() + " - " + pair.getValue());
        }

        String str = request.getParameterNames().toString();
        return str;
    }

    private String strPlusCommaPlusValue(String str, String value) {
        if (!str.equals("")) str += ",";
        str += value;
        return str;
    }
}
