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
    private String volume;

    @Autowired
    DbModel dbMvc;
    @Autowired
    OilType oilTypeMvc;
    @Autowired
    CompanyUnit companyUnitMvc;

    public BidDetail() {

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

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public ArrayList<BidDetail> getBidDetailList(String bidId, Object object) {
        ArrayList<OilSections> oilSectionsList = null;
        String prefix = null;
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
        String volumeTmp = null;
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
                } else if (pair.getKey().equals("bid_" + oilSection.getId_section() + "_volume")) {
                    if (pair.getValue() != null) {
                        volumeTmp = pair.getValue().toString();
                    } else volumeTmp = null;
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
            bidDetail.setVolume(volumeTmp);
            if (bidDetailArrayList == null) bidDetailArrayList = new ArrayList<BidDetail>();
            bidDetailArrayList.add(bidDetail);
        }
        return bidDetailArrayList;
    }
}
