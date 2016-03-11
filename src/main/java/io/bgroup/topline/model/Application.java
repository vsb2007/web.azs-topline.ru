package io.bgroup.topline.model;

/**
 * Created by VSB on 11.03.2016.
 * ToplineWeb.2.5
 */
public class Application {
    private SiteUser siteUser;
    private OilFarm oilFarm;
    private Driver driver;
    private Car car;
    private String dateOfCreation;
    private String dateOfClose;
    private String error;

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
    }

    public void setOilFarm(OilFarm oilFarm) {
        this.oilFarm = oilFarm;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setDateOfClose(String dateOfClose) {
        this.dateOfClose = dateOfClose;
    }

    public Application() {
    }

    public SiteUser getSiteUser() {
        return siteUser;
    }

    public OilFarm getOilFarm() {
        return oilFarm;
    }

    public Driver getDriver() {
        return driver;
    }

    public Car getCar() {
        return car;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public String getDateOfClose() {
        return dateOfClose;
    }
}
