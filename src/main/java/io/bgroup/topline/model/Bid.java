package io.bgroup.topline.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;

public class Bid {
    private SiteUser siteUser;
    private OilStorage oilStorage;
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

    public void setOilStorage(OilStorage oilStorage) {
        this.oilStorage = oilStorage;
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

    public Bid() {
    }

    public SiteUser getSiteUser() {
        return siteUser;
    }

    public OilStorage getOilStorages() {
        return oilStorage;
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

    public boolean createBid(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {

        return false;
    }
}
