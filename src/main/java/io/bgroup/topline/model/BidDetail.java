package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by VSB on 18.04.2016.
 * ToplineWeb.2.5
 */
public class BidDetail {
    private OilSections section;
    private OilType oilType;
    private CompanyUnit destination;
    private String plIn;
    private String plOut;
    private String tempIn;
    private String tempOut;
    private String volumeIn;
    private String volumeOut;
    private String massIn;
    private String massOut;
    private String dateIn;
    private String dateOut;
    private boolean isDone;

    @Autowired
    DbModel dbMvc;
    @Autowired
    OilType oilTypeMvc;
    @Autowired
    CompanyUnit companyUnitMvc;

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

    public String getVolumeIn() {
        return volumeIn;
    }

    public void setVolumeIn(String volumeIn) {
        this.volumeIn = volumeIn;
    }

    public String getVolumeOut() {
        return volumeOut;
    }

    public void setVolumeOut(String volumeOut) {
        this.volumeOut = volumeOut;
    }

    public String getMassIn() {
        return massIn;
    }

    public void setMassIn(String massIn) {
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

    public ArrayList<BidDetail> getBidDetailList(String bidId, Object object) {
        ArrayList<OilSections> oilSectionsList = null;
        if (object instanceof Car) {
            oilSectionsList = ((Car) object).getOilSections();
        }
        if (object instanceof Trailer) {
            oilSectionsList = ((Trailer) object).getOilSections();
        }
        if (oilSectionsList == null) return null;
        String sql = "select * from bids where id_bids='" + bidId + "'";
        List<Map<String, Object>> bidFromDb = null;
        bidFromDb = dbMvc.getSelectResult(sql);
        if (bidFromDb == null || bidFromDb.size() != 1) return null;
        Map row = bidFromDb.get(0);
        if (row == null) return null;
        ArrayList<BidDetail> bidDetailArrayList = null;
        String pInTmp = null;
        String pOutTmp = null;
        String tInTmp = null;
        String tOutTmp = null;
        String volumeInTmp = null;
        String volumeOutTmp = null;
        String massInTmp = null;
        String massOutTmp = null;
        String dateInTmp = null;
        String dateOutTmp = null;
        for (OilSections oilSection : oilSectionsList) {
            String oilTypeId = null;
            String destinationId = null;
            Iterator<Map.Entry<String, Object>> iterator = row.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> pair = iterator.next();
                if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_oilType_id")) {
                    if (pair.getValue() != null) {
                        oilTypeId = pair.getValue().toString();
                    } else oilTypeId = null;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_storageOut_id")) {
                    if (pair.getValue() != null) {
                        destinationId = pair.getValue().toString();
                    } else destinationId = null;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_storageOut_id")) {
                    if (pair.getValue() != null) {
                        destinationId = pair.getValue().toString();
                    } else destinationId = null;
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
                        volumeInTmp = pair.getValue().toString();
                    } else volumeInTmp = null;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_volume_out")) {
                    if (pair.getValue() != null) {
                        volumeOutTmp = pair.getValue().toString();
                    } else volumeOutTmp = null;
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_mass_in")) {
                    if (pair.getValue() != null) {
                        massInTmp = pair.getValue().toString();
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
                }
            }

            if (oilTypeId == null) continue;
            if (destinationId == null) continue;
            OilType oilTypeTmp = oilTypeMvc.getOilType(oilTypeId);
            CompanyUnit destinationTmp = companyUnitMvc.getCompanyUnit(destinationId);
            BidDetail bidDetail = new BidDetail();
            bidDetail.setDestination(destinationTmp);
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
        if (bidDetails == null) return false;
        for (BidDetail bidDetail : bidDetails) {
            if ((bid.getCreateUser().getName().equals(siteUser.getName()))
                    || (siteUser.getCompanyUnit() != null && siteUser.getCompanyUnit().getIdCompanyUnit().equals(bid.getOilStorageIn().getIdOilStorage()))
                    || (bid.getBid_is_freeze().equals("0") && siteUser.getCompanyUnit() != null && siteUser.getCompanyUnit().getIdCompanyUnit().equals(bidDetail.getDestination().getIdCompanyUnit()))
                    || siteUser.getPost().getIdPost().equals("2")
                    )
                if (!bidDetail.isDone()) return false;
        }
        return true;
    }
}
