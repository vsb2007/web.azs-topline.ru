package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

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

    private int id;
    private int unitId;
    private int orgId;
    private String fio;
    private String carNumber;
    private int idOil;
    private int lt;
    private double col;
    private double priceOil;
    private double priceShipping;
    private double sum;

    public SaleOil() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
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

    public int getIdOil() {
        return idOil;
    }

    public void setIdOil(int idOil) {
        this.idOil = idOil;
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


}
