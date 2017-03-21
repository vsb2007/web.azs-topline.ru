package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BidDetail {
    private OilSections section;
    private OilType oilType;
    private CompanyUnit destination;
    private Organization organizationDestination;
    private String plIn;
    private String plOut;
    private String tempIn;
    private String tempOut;
    private double volumeIn;
    private String volumeOut;
    private Double massIn;
    private String massOut;
    private String dateIn;
    private String dateOut;
    private int saleOilId;
    private boolean isDone;

    @Autowired
    DbJdbcModel dbMvc;
    @Autowired
    OilType oilTypeMvc;
    @Autowired
    CompanyUnit companyUnitMvc;
    @Autowired
    Organization organizationMvc;
    @Autowired
    SaleOil saleOilMvc;

    public BidDetail() {

    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public OilSections getSection() {
        return section;
    }

    private void setSection(OilSections section) {
        this.section = section;
    }

    public OilType getOilType() {
        return oilType;
    }

    private void setOilType(OilType oilType) {
        this.oilType = oilType;
    }

    public CompanyUnit getDestination() {
        return destination;
    }

    private void setDestination(CompanyUnit destination) {
        this.destination = destination;
    }

    public Organization getOrganizationDestination() {
        return organizationDestination;
    }

    public void setOrganizationDestination(Organization organizationDestination) {
        this.organizationDestination = organizationDestination;
    }

    public String getPlIn() {
        return plIn;
    }

    public void setPlIn(String plIn) {
        this.plIn = plIn;
    }

    public String getPlOut() {
        return plOut;
    }

    public void setPlOut(String plOut) {
        this.plOut = plOut;
    }

    public String getTempIn() {
        return tempIn;
    }

    public void setTempIn(String tempIn) {
        this.tempIn = tempIn;
    }

    public String getTempOut() {
        return tempOut;
    }

    public void setTempOut(String tempOut) {
        this.tempOut = tempOut;
    }

    public double getVolumeIn() {
        return volumeIn;
    }

    public void setVolumeIn(double volumeIn) {
        this.volumeIn = volumeIn;
    }

    public String getVolumeOut() {
        return volumeOut;
    }

    public void setVolumeOut(String volumeOut) {
        this.volumeOut = volumeOut;
    }

    public Double getMassIn() {
        return massIn;
    }

    public void setMassIn(Double massIn) {
        this.massIn = massIn;
    }

    public String getMassOut() {
        return massOut;
    }

    public void setMassOut(String massOut) {
        this.massOut = massOut;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public int getSaleOilId() {
        return saleOilId;
    }

    public void setSaleOilId(String saleOilId) {
        int idTmp = -1;
        try {
            idTmp = Integer.parseInt(saleOilId);
        }catch (Exception e){}
        this.saleOilId = idTmp;
    }

    public ArrayList<BidDetail> getBidDetailList(int bidId, Object object) {
        if (object == null) return null;
        ArrayList<OilSections> oilSectionsList = null;
        if (object instanceof Car) {
            oilSectionsList = ((Car) object).getOilSections();
        }
        if (object instanceof Trailer) {
            oilSectionsList = ((Trailer) object).getOilSections();
        }
        if (oilSectionsList == null) return null;
        String sql = "select * from bids where id_bids=?";
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(bidId);
        List<Map<String, Object>> bidFromDb = null;
        bidFromDb = dbMvc.getSelectResult(sql, args);
        if (bidFromDb == null || bidFromDb.size() != 1) return null;
        Map row = bidFromDb.get(0);
        if (row == null) return null;
        ArrayList<BidDetail> bidDetailArrayList = null;
        String pInTmp = null;
        String pOutTmp = null;
        String tInTmp = null;
        String tOutTmp = null;
        double volumeInTmp = 0;
        String volumeOutTmp = null;
        Double massInTmp = null;
        String massOutTmp = null;
        String dateInTmp = null;
        String dateOutTmp = null;
        String saleIdtmp = null;
        for (OilSections oilSection : oilSectionsList) {
            int oilTypeId = -1;
            int destinationId = -1;
            int destinationOrgType = 0;
            Iterator<Map.Entry<String, Object>> iterator = row.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> pair = iterator.next();
                if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_oilType_id")) {
                    if (pair.getValue() != null) {
                        oilTypeId = (Integer) pair.getValue();
                    } else oilTypeId = -1;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_storageOut_id")) {
                    if (pair.getValue() != null) {
                        destinationId = (Integer) pair.getValue();
                    } else destinationId = -1;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_orgType")) {
                    if (pair.getValue() != null) {
                        destinationOrgType = (Integer) pair.getValue();
                    } else destinationOrgType = 0;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_p_in")) {
                    if (pair.getValue() != null) {
                        pInTmp = pair.getValue().toString();
                    } else pInTmp = null;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_t_in")) {
                    if (pair.getValue() != null) {
                        tInTmp = pair.getValue().toString();
                    } else tInTmp = null;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_p_out")) {
                    if (pair.getValue() != null) {
                        pOutTmp = pair.getValue().toString();
                    } else pOutTmp = null;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_t_out")) {
                    if (pair.getValue() != null) {
                        tOutTmp = pair.getValue().toString();
                    } else tOutTmp = null;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_volume_in")) {
                    if (pair.getValue() != null) {
                        volumeInTmp = (Double)pair.getValue();
                    } else volumeInTmp = 0;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_volume_out")) {
                    if (pair.getValue() != null) {
                        volumeOutTmp = pair.getValue().toString();
                    } else volumeOutTmp = null;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_mass_in")) {
                    if (pair.getValue() != null) {
                        massInTmp = (Double) pair.getValue();
                    } else massInTmp = null;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_mass_out")) {
                    if (pair.getValue() != null) {
                        massOutTmp = pair.getValue().toString();
                    } else massOutTmp = null;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_date_in")) {
                    if (pair.getValue() != null) {
                        dateInTmp = pair.getValue().toString();
                    } else dateInTmp = null;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_date_out")) {
                    if (pair.getValue() != null) {
                        dateOutTmp = pair.getValue().toString();
                    } else dateOutTmp = null;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_saleId")) {
                    if (pair.getValue() != null) {
                        saleIdtmp = pair.getValue().toString();
                    } else saleIdtmp = null;
                }
            }
            if (oilTypeId == -1) continue;
            if (destinationId == -1) continue;
            OilType oilTypeTmp = oilTypeMvc.getOilType(oilTypeId);
            CompanyUnit destinationTmp = null;
            Organization organizationTmp = null;
            if (destinationOrgType == 0) {
                destinationTmp = companyUnitMvc.getCompanyUnit(destinationId);
            } else destinationTmp = null;
            if (destinationOrgType == 1) {
                organizationTmp = organizationMvc.getOrganization(destinationId);
            } else {
                organizationTmp = null;
            }
            BidDetail bidDetail = new BidDetail();
            bidDetail.setDestination(destinationTmp);
            bidDetail.setOrganizationDestination(organizationTmp);
            bidDetail.setOilType(oilTypeTmp);
            bidDetail.setSection(oilSection);
            bidDetail.setPlIn(pInTmp);
            bidDetail.setPlOut(pOutTmp);
            bidDetail.setTempIn(tInTmp);
            bidDetail.setTempOut(tOutTmp);
            bidDetail.setVolumeIn(volumeInTmp);
            bidDetail.setVolumeOut(volumeOutTmp);
            bidDetail.setMassIn(massInTmp);
            bidDetail.setMassOut(massOutTmp);
            bidDetail.setDateIn(dateInTmp);
            bidDetail.setDateOut(dateOutTmp);
            bidDetail.setSaleOilId(saleIdtmp);
            if (pOutTmp != null
                    && tOutTmp != null
                    && volumeOutTmp != null
                    && massOutTmp != null
                    && dateOutTmp != null) {
                bidDetail.setDone(true);
            } else {
                bidDetail.setDone(false);
            }
            if (bidDetailArrayList == null) bidDetailArrayList = new ArrayList<BidDetail>();
            bidDetailArrayList.add(bidDetail);
        }
        return bidDetailArrayList;
    }

    public boolean isSectionBidUp(ArrayList<BidDetail> bidDetails, Bid bid, SiteUser siteUser) {
        if (bid == null) return false;
        if (bidDetails == null) return false;
        if (siteUser == null) return false;
        for (BidDetail bidDetail : bidDetails) {
            if ((bid.getCreateUser().getName().equals(siteUser.getName()))
                    || (siteUser.getCompanyUnit() != null && siteUser.getCompanyUnit().getIdCompanyUnit() == bid.getOilStorageIn().getIdOilStorage())
                    || (siteUser.getCompanyUnit() != null && bidDetail.getDestination() != null && siteUser.getCompanyUnit().getIdCompanyUnit() == bidDetail.getDestination().getIdCompanyUnit())
                    || (siteUser.getPost() != null && siteUser.getPost().getIdPost() == 2) //водитель
                    || (siteUser.getPost() != null && siteUser.getPost().getIdPost() == 4) //наблюдатель
                    || (siteUser.getName().equals("admin"))
                    )
                if (!bidDetail.isDone()) {
                    return false;
                }
        }
        return true;
    }
}
