package io.bgroup.topline.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by VSB on 18.04.2016.
 * ToplineWeb.2.5
 */
public class BidDetailTmp {
    private String bid_car_sec_1_oilType_id;
    private String bid_car_sec_1_storageOut_id;
    private String bid_car_sec_2_oilType_id;
    private String bid_car_sec_2_storageOut_id;
    private String bid_car_sec_3_oilType_id;
    private String bid_car_sec_3_storageOut_id;

    private String bid_trailer_sec_1_oilType_id;
    private String bid_trailer_sec_1_storageOut_id;
    private String bid_trailer_sec_2_oilType_id;
    private String bid_trailer_sec_2_storageOut_id;
    private String bid_trailer_sec_3_oilType_id;
    private String bid_trailer_sec_3_storageOut_id;
    private String bid_trailer_sec_4_oilType_id;
    private String bid_trailer_sec_4_storageOut_id;
    private String bid_trailer_sec_5_oilType_id;
    private String bid_trailer_sec_5_storageOut_id;
    private String bid_trailer_sec_6_oilType_id;
    private String bid_trailer_sec_6_storageOut_id;

    private String bid_car_sec_1_p_in;
    private String bid_car_sec_1_t_in;
    private String bid_car_sec_1_p_out;
    private String bid_car_sec_1_t_out;
    private String bid_car_sec_1_date_in;
    private String bid_car_sec_1_date_out;
    private String bid_car_sec_2_p_in;
    private String bid_car_sec_2_t_in;
    private String bid_car_sec_2_p_out;
    private String bid_car_sec_2_t_out;
    private String bid_car_sec_2_date_in;
    private String bid_car_sec_2_date_out;
    private String bid_car_sec_3_p_in;
    private String bid_car_sec_3_t_in;
    private String bid_car_sec_3_p_out;
    private String bid_car_sec_3_t_out;
    private String bid_car_sec_3_date_in;
    private String bid_car_sec_3_date_out;

    private String bid_trailer_sec_1_p_in;
    private String bid_trailer_sec_1_t_in;
    private String bid_trailer_sec_1_p_out;
    private String bid_trailer_sec_1_t_out;
    private String bid_trailer_sec_1_date_in;
    private String bid_trailer_sec_1_date_out;
    private String bid_trailer_sec_2_p_in;
    private String bid_trailer_sec_2_t_in;
    private String bid_trailer_sec_2_p_out;
    private String bid_trailer_sec_2_t_out;
    private String bid_trailer_sec_2_date_in;
    private String bid_trailer_sec_2_date_out;
    private String bid_trailer_sec_3_p_in;
    private String bid_trailer_sec_3_t_in;
    private String bid_trailer_sec_3_p_out;
    private String bid_trailer_sec_3_t_out;
    private String bid_trailer_sec_3_date_in;
    private String bid_trailer_sec_3_date_out;
    private String bid_trailer_sec_4_p_in;
    private String bid_trailer_sec_4_t_in;
    private String bid_trailer_sec_4_p_out;
    private String bid_trailer_sec_4_t_out;
    private String bid_trailer_sec_4_date_in;
    private String bid_trailer_sec_4_date_out;
    private String bid_trailer_sec_5_p_in;
    private String bid_trailer_sec_5_t_in;
    private String bid_trailer_sec_5_p_out;
    private String bid_trailer_sec_5_t_out;
    private String bid_trailer_sec_5_date_in;
    private String bid_trailer_sec_5_date_out;
    private String bid_trailer_sec_6_p_in;
    private String bid_trailer_sec_6_t_in;
    private String bid_trailer_sec_6_p_out;
    private String bid_trailer_sec_6_t_out;
    private String bid_trailer_sec_6_date_in;
    private String bid_trailer_sec_6_date_out;

    public BidDetailTmp() {
    }

    public BidDetailTmp(Map row) {
        Iterator<Map.Entry<String, Object>> iterator = row.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> pair = iterator.next();
            if (pair.getKey().equals("bid_car_sec_1_oilType_id")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_1_oilType_id((String) pair.getValue().toString());
                } else setBid_car_sec_1_oilType_id(null);
            } else if (pair.getKey().equals("bid_car_sec_1_storageOut_id")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_1_storageOut_id((String) pair.getValue().toString());
                } else setBid_car_sec_1_storageOut_id(null);
            } else if (pair.getKey().equals("bid_car_sec_2_oilType_id")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_2_oilType_id((String) pair.getValue().toString());
                } else setBid_car_sec_2_oilType_id(null);
            } else if (pair.getKey().equals("bid_car_sec_2_storageOut_id")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_2_storageOut_id((String) pair.getValue().toString());
                } else setBid_car_sec_2_storageOut_id(null);
            } else if (pair.getKey().equals("bid_car_sec_3_oilType_id")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_3_oilType_id((String) pair.getValue().toString());
                } else setBid_car_sec_3_oilType_id(null);
            } else if (pair.getKey().equals("bid_car_sec_3_storageOut_id")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_3_storageOut_id((String) pair.getValue().toString());
                } else setBid_car_sec_3_storageOut_id(null);
            } else if (pair.getKey().equals("bid_trailer_sec_1_oilType_id")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_1_oilType_id((String) pair.getValue().toString());
                } else setBid_trailer_sec_1_oilType_id(null);
            } else if (pair.getKey().equals("bid_trailer_sec_1_storageOut_id")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_1_storageOut_id((String) pair.getValue().toString());
                } else setBid_trailer_sec_1_storageOut_id(null);
            } else if (pair.getKey().equals("bid_trailer_sec_2_oilType_id")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_2_oilType_id((String) pair.getValue().toString());
                } else setBid_trailer_sec_2_oilType_id(null);
            } else if (pair.getKey().equals("bid_trailer_sec_2_storageOut_id")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_2_storageOut_id((String) pair.getValue().toString());
                } else setBid_trailer_sec_2_storageOut_id(null);
            } else if (pair.getKey().equals("bid_trailer_sec_3_oilType_id")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_3_oilType_id((String) pair.getValue().toString());
                } else setBid_trailer_sec_3_oilType_id(null);
            } else if (pair.getKey().equals("bid_trailer_sec_3_storageOut_id")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_3_storageOut_id((String) pair.getValue().toString());
                } else setBid_trailer_sec_3_storageOut_id(null);
            } else if (pair.getKey().equals("bid_trailer_sec_4_oilType_id")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_4_oilType_id((String) pair.getValue().toString());
                } else setBid_trailer_sec_4_oilType_id(null);
            } else if (pair.getKey().equals("bid_trailer_sec_4_storageOut_id")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_4_storageOut_id((String) pair.getValue().toString());
                } else setBid_trailer_sec_4_storageOut_id(null);
            } else if (pair.getKey().equals("bid_trailer_sec_5_oilType_id")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_5_oilType_id((String) pair.getValue().toString());
                } else setBid_trailer_sec_5_oilType_id(null);
            } else if (pair.getKey().equals("bid_trailer_sec_5_storageOut_id")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_5_storageOut_id((String) pair.getValue().toString());
                } else setBid_trailer_sec_5_storageOut_id(null);
            } else if (pair.getKey().equals("bid_trailer_sec_6_oilType_id")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_6_oilType_id((String) pair.getValue().toString());
                } else setBid_trailer_sec_6_oilType_id(null);
            } else if (pair.getKey().equals("bid_trailer_sec_6_storageOut_id")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_6_storageOut_id((String) pair.getValue().toString());
                } else setBid_trailer_sec_6_storageOut_id(null);
            } else if (pair.getKey().equals("bid_car_sec_1_p_in")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_1_p_in((String) pair.getValue().toString());
                } else setBid_car_sec_1_p_in(null);
            } else if (pair.getKey().equals("bid_car_sec_1_t_in")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_1_t_in((String) pair.getValue().toString());
                } else setBid_car_sec_1_t_in(null);
            } else if (pair.getKey().equals("bid_car_sec_1_p_out")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_1_p_out((String) pair.getValue().toString());
                } else setBid_car_sec_1_p_out(null);
            } else if (pair.getKey().equals("bid_car_sec_1_t_out")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_1_t_out((String) pair.getValue().toString());
                } else setBid_car_sec_1_t_out(null);
            } else if (pair.getKey().equals("bid_car_sec_1_date_in")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_1_date_in((String) pair.getValue().toString());
                } else setBid_car_sec_1_date_in(null);
            } else if (pair.getKey().equals("bid_car_sec_1_date_out")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_1_date_out((String) pair.getValue().toString());
                } else setBid_car_sec_1_date_out(null);
            } else if (pair.getKey().equals("bid_car_sec_2_p_in")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_2_p_in((String) pair.getValue().toString());
                } else setBid_car_sec_2_p_in(null);
            } else if (pair.getKey().equals("bid_car_sec_2_t_in")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_2_t_in((String) pair.getValue().toString());
                } else setBid_car_sec_2_t_in(null);
            } else if (pair.getKey().equals("bid_car_sec_2_p_out")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_2_p_out((String) pair.getValue().toString());
                } else setBid_car_sec_2_p_out(null);
            } else if (pair.getKey().equals("bid_car_sec_2_t_out")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_2_t_out((String) pair.getValue().toString());
                } else setBid_car_sec_2_t_out(null);
            } else if (pair.getKey().equals("bid_car_sec_2_date_in")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_2_date_in((String) pair.getValue().toString());
                } else setBid_car_sec_2_date_in(null);
            } else if (pair.getKey().equals("bid_car_sec_2_date_out")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_2_date_out((String) pair.getValue().toString());
                } else setBid_car_sec_2_date_out(null);
            } else if (pair.getKey().equals("bid_car_sec_3_p_in")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_3_p_in((String) pair.getValue().toString());
                } else setBid_car_sec_3_p_in(null);
            } else if (pair.getKey().equals("bid_car_sec_3_t_in")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_3_t_in((String) pair.getValue().toString());
                } else setBid_car_sec_3_t_in(null);
            } else if (pair.getKey().equals("bid_car_sec_3_p_out")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_3_p_out((String) pair.getValue().toString());
                } else setBid_car_sec_3_p_out(null);
            } else if (pair.getKey().equals("bid_car_sec_3_t_out")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_3_t_out((String) pair.getValue().toString());
                } else setBid_car_sec_3_t_out(null);
            } else if (pair.getKey().equals("bid_car_sec_3_date_in")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_3_date_in((String) pair.getValue().toString());
                } else setBid_car_sec_3_date_in(null);
            } else if (pair.getKey().equals("bid_car_sec_3_date_out")) {
                if (pair.getValue() != null) {
                    setBid_car_sec_3_date_out((String) pair.getValue().toString());
                } else setBid_car_sec_3_date_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_1_p_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_1_p_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_1_p_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_1_t_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_1_t_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_1_t_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_1_p_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_1_p_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_1_p_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_1_t_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_1_t_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_1_t_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_1_date_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_1_date_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_1_date_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_1_date_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_1_date_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_1_date_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_2_p_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_2_p_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_2_p_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_2_t_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_2_t_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_2_t_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_2_p_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_2_p_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_2_p_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_2_t_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_2_t_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_2_t_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_2_date_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_2_date_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_2_date_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_2_date_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_2_date_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_2_date_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_3_p_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_3_p_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_3_p_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_3_t_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_3_t_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_3_t_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_3_p_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_3_p_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_3_p_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_3_t_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_3_t_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_3_t_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_3_date_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_3_date_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_3_date_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_3_date_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_3_date_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_3_date_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_4_p_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_4_p_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_4_p_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_4_t_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_4_t_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_4_t_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_4_p_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_4_p_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_4_p_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_4_t_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_4_t_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_4_t_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_4_date_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_4_date_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_4_date_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_4_date_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_4_date_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_4_date_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_5_p_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_5_p_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_5_p_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_5_t_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_5_t_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_5_t_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_5_p_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_5_p_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_5_p_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_5_t_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_5_t_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_5_t_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_5_date_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_5_date_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_5_date_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_5_date_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_5_date_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_5_date_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_6_p_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_6_p_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_6_p_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_6_t_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_6_t_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_6_t_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_6_p_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_6_p_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_6_p_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_6_t_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_6_t_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_6_t_out(null);
            } else if (pair.getKey().equals("bid_trailer_sec_6_date_in")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_6_date_in((String) pair.getValue().toString());
                } else setBid_trailer_sec_6_date_in(null);
            } else if (pair.getKey().equals("bid_trailer_sec_6_date_out")) {
                if (pair.getValue() != null) {
                    setBid_trailer_sec_6_date_out((String) pair.getValue().toString());
                } else setBid_trailer_sec_6_date_out(null);
            }
        }
    }


    public String getBid_car_sec_1_oilType_id() {
        return bid_car_sec_1_oilType_id;
    }

    public void setBid_car_sec_1_oilType_id(String bid_car_sec_1_oilType_id) {
        this.bid_car_sec_1_oilType_id = bid_car_sec_1_oilType_id;
    }

    public String getBid_car_sec_1_storageOut_id() {
        return bid_car_sec_1_storageOut_id;
    }

    public void setBid_car_sec_1_storageOut_id(String bid_car_sec_1_storageOut_id) {
        this.bid_car_sec_1_storageOut_id = bid_car_sec_1_storageOut_id;
    }

    public String getBid_car_sec_2_oilType_id() {
        return bid_car_sec_2_oilType_id;
    }

    public void setBid_car_sec_2_oilType_id(String bid_car_sec_2_oilType_id) {
        this.bid_car_sec_2_oilType_id = bid_car_sec_2_oilType_id;
    }

    public String getBid_car_sec_2_storageOut_id() {
        return bid_car_sec_2_storageOut_id;
    }

    public void setBid_car_sec_2_storageOut_id(String bid_car_sec_2_storageOut_id) {
        this.bid_car_sec_2_storageOut_id = bid_car_sec_2_storageOut_id;
    }

    public String getBid_car_sec_3_oilType_id() {
        return bid_car_sec_3_oilType_id;
    }

    public void setBid_car_sec_3_oilType_id(String bid_car_sec_3_oilType_id) {
        this.bid_car_sec_3_oilType_id = bid_car_sec_3_oilType_id;
    }

    public String getBid_car_sec_3_storageOut_id() {
        return bid_car_sec_3_storageOut_id;
    }

    public void setBid_car_sec_3_storageOut_id(String bid_car_sec_3_storageOut_id) {
        this.bid_car_sec_3_storageOut_id = bid_car_sec_3_storageOut_id;
    }

    /*
        public String getBid_trailer_id() {
            return bid_trailer_id;
        }

        public void setBid_trailer_id(String bid_trailer_id) {
            this.bid_trailer_id = bid_trailer_id;
        }
    */
    public String getBid_trailer_sec_1_oilType_id() {
        return bid_trailer_sec_1_oilType_id;
    }

    public void setBid_trailer_sec_1_oilType_id(String bid_trailer_sec_1_oilType_id) {
        this.bid_trailer_sec_1_oilType_id = bid_trailer_sec_1_oilType_id;
    }

    public String getBid_trailer_sec_1_storageOut_id() {
        return bid_trailer_sec_1_storageOut_id;
    }

    public void setBid_trailer_sec_1_storageOut_id(String bid_trailer_sec_1_storageOut_id) {
        this.bid_trailer_sec_1_storageOut_id = bid_trailer_sec_1_storageOut_id;
    }

    public String getBid_trailer_sec_2_oilType_id() {
        return bid_trailer_sec_2_oilType_id;
    }

    public void setBid_trailer_sec_2_oilType_id(String bid_trailer_sec_2_oilType_id) {
        this.bid_trailer_sec_2_oilType_id = bid_trailer_sec_2_oilType_id;
    }

    public String getBid_trailer_sec_2_storageOut_id() {
        return bid_trailer_sec_2_storageOut_id;
    }

    public void setBid_trailer_sec_2_storageOut_id(String bid_trailer_sec_2_storageOut_id) {
        this.bid_trailer_sec_2_storageOut_id = bid_trailer_sec_2_storageOut_id;
    }

    public String getBid_trailer_sec_3_oilType_id() {
        return bid_trailer_sec_3_oilType_id;
    }

    public void setBid_trailer_sec_3_oilType_id(String bid_trailer_sec_3_oilType_id) {
        this.bid_trailer_sec_3_oilType_id = bid_trailer_sec_3_oilType_id;
    }

    public String getBid_trailer_sec_3_storageOut_id() {
        return bid_trailer_sec_3_storageOut_id;
    }

    public void setBid_trailer_sec_3_storageOut_id(String bid_trailer_sec_3_storageOut_id) {
        this.bid_trailer_sec_3_storageOut_id = bid_trailer_sec_3_storageOut_id;
    }

    public String getBid_trailer_sec_4_oilType_id() {
        return bid_trailer_sec_4_oilType_id;
    }

    public void setBid_trailer_sec_4_oilType_id(String bid_trailer_sec_4_oilType_id) {
        this.bid_trailer_sec_4_oilType_id = bid_trailer_sec_4_oilType_id;
    }

    public String getBid_trailer_sec_4_storageOut_id() {
        return bid_trailer_sec_4_storageOut_id;
    }

    public void setBid_trailer_sec_4_storageOut_id(String bid_trailer_sec_4_storageOut_id) {
        this.bid_trailer_sec_4_storageOut_id = bid_trailer_sec_4_storageOut_id;
    }

    public String getBid_trailer_sec_5_oilType_id() {
        return bid_trailer_sec_5_oilType_id;
    }

    public void setBid_trailer_sec_5_oilType_id(String bid_trailer_sec_5_oilType_id) {
        this.bid_trailer_sec_5_oilType_id = bid_trailer_sec_5_oilType_id;
    }

    public String getBid_trailer_sec_5_storageOut_id() {
        return bid_trailer_sec_5_storageOut_id;
    }

    public void setBid_trailer_sec_5_storageOut_id(String bid_trailer_sec_5_storageOut_id) {
        this.bid_trailer_sec_5_storageOut_id = bid_trailer_sec_5_storageOut_id;
    }

    public String getBid_trailer_sec_6_oilType_id() {
        return bid_trailer_sec_6_oilType_id;
    }

    public void setBid_trailer_sec_6_oilType_id(String bid_trailer_sec_6_oilType_id) {
        this.bid_trailer_sec_6_oilType_id = bid_trailer_sec_6_oilType_id;
    }

    public String getBid_trailer_sec_6_storageOut_id() {
        return bid_trailer_sec_6_storageOut_id;
    }

    public void setBid_trailer_sec_6_storageOut_id(String bid_trailer_sec_6_storageOut_id) {
        this.bid_trailer_sec_6_storageOut_id = bid_trailer_sec_6_storageOut_id;
    }

    public String getBid_car_sec_1_p_in() {
        return bid_car_sec_1_p_in;
    }

    public void setBid_car_sec_1_p_in(String bid_car_sec_1_p_in) {
        this.bid_car_sec_1_p_in = bid_car_sec_1_p_in;
    }

    public String getBid_car_sec_1_t_in() {
        return bid_car_sec_1_t_in;
    }

    public void setBid_car_sec_1_t_in(String bid_car_sec_1_t_in) {
        this.bid_car_sec_1_t_in = bid_car_sec_1_t_in;
    }

    public String getBid_car_sec_1_p_out() {
        return bid_car_sec_1_p_out;
    }

    public void setBid_car_sec_1_p_out(String bid_car_sec_1_p_out) {
        this.bid_car_sec_1_p_out = bid_car_sec_1_p_out;
    }

    public String getBid_car_sec_1_t_out() {
        return bid_car_sec_1_t_out;
    }

    public void setBid_car_sec_1_t_out(String bid_car_sec_1_t_out) {
        this.bid_car_sec_1_t_out = bid_car_sec_1_t_out;
    }

    public String getBid_car_sec_1_date_in() {
        return bid_car_sec_1_date_in;
    }

    public void setBid_car_sec_1_date_in(String bid_car_sec_1_date_in) {
        this.bid_car_sec_1_date_in = bid_car_sec_1_date_in;
    }

    public String getBid_car_sec_1_date_out() {
        return bid_car_sec_1_date_out;
    }

    public void setBid_car_sec_1_date_out(String bid_car_sec_1_date_out) {
        this.bid_car_sec_1_date_out = bid_car_sec_1_date_out;
    }

    public String getBid_car_sec_2_p_in() {
        return bid_car_sec_2_p_in;
    }

    public void setBid_car_sec_2_p_in(String bid_car_sec_2_p_in) {
        this.bid_car_sec_2_p_in = bid_car_sec_2_p_in;
    }

    public String getBid_car_sec_2_t_in() {
        return bid_car_sec_2_t_in;
    }

    public void setBid_car_sec_2_t_in(String bid_car_sec_2_t_in) {
        this.bid_car_sec_2_t_in = bid_car_sec_2_t_in;
    }

    public String getBid_car_sec_2_p_out() {
        return bid_car_sec_2_p_out;
    }

    public void setBid_car_sec_2_p_out(String bid_car_sec_2_p_out) {
        this.bid_car_sec_2_p_out = bid_car_sec_2_p_out;
    }

    public String getBid_car_sec_2_t_out() {
        return bid_car_sec_2_t_out;
    }

    public void setBid_car_sec_2_t_out(String bid_car_sec_2_t_out) {
        this.bid_car_sec_2_t_out = bid_car_sec_2_t_out;
    }

    public String getBid_car_sec_2_date_in() {
        return bid_car_sec_2_date_in;
    }

    public void setBid_car_sec_2_date_in(String bid_car_sec_2_date_in) {
        this.bid_car_sec_2_date_in = bid_car_sec_2_date_in;
    }

    public String getBid_car_sec_2_date_out() {
        return bid_car_sec_2_date_out;
    }

    public void setBid_car_sec_2_date_out(String bid_car_sec_2_date_out) {
        this.bid_car_sec_2_date_out = bid_car_sec_2_date_out;
    }

    public String getBid_car_sec_3_p_in() {
        return bid_car_sec_3_p_in;
    }

    public void setBid_car_sec_3_p_in(String bid_car_sec_3_p_in) {
        this.bid_car_sec_3_p_in = bid_car_sec_3_p_in;
    }

    public String getBid_car_sec_3_t_in() {
        return bid_car_sec_3_t_in;
    }

    public void setBid_car_sec_3_t_in(String bid_car_sec_3_t_in) {
        this.bid_car_sec_3_t_in = bid_car_sec_3_t_in;
    }

    public String getBid_car_sec_3_p_out() {
        return bid_car_sec_3_p_out;
    }

    public void setBid_car_sec_3_p_out(String bid_car_sec_3_p_out) {
        this.bid_car_sec_3_p_out = bid_car_sec_3_p_out;
    }

    public String getBid_car_sec_3_t_out() {
        return bid_car_sec_3_t_out;
    }

    public void setBid_car_sec_3_t_out(String bid_car_sec_3_t_out) {
        this.bid_car_sec_3_t_out = bid_car_sec_3_t_out;
    }

    public String getBid_car_sec_3_date_in() {
        return bid_car_sec_3_date_in;
    }

    public void setBid_car_sec_3_date_in(String bid_car_sec_3_date_in) {
        this.bid_car_sec_3_date_in = bid_car_sec_3_date_in;
    }

    public String getBid_car_sec_3_date_out() {
        return bid_car_sec_3_date_out;
    }

    public void setBid_car_sec_3_date_out(String bid_car_sec_3_date_out) {
        this.bid_car_sec_3_date_out = bid_car_sec_3_date_out;
    }

    public String getBid_trailer_sec_1_p_in() {
        return bid_trailer_sec_1_p_in;
    }

    public void setBid_trailer_sec_1_p_in(String bid_trailer_sec_1_p_in) {
        this.bid_trailer_sec_1_p_in = bid_trailer_sec_1_p_in;
    }

    public String getBid_trailer_sec_1_t_in() {
        return bid_trailer_sec_1_t_in;
    }

    public void setBid_trailer_sec_1_t_in(String bid_trailer_sec_1_t_in) {
        this.bid_trailer_sec_1_t_in = bid_trailer_sec_1_t_in;
    }

    public String getBid_trailer_sec_1_p_out() {
        return bid_trailer_sec_1_p_out;
    }

    public void setBid_trailer_sec_1_p_out(String bid_trailer_sec_1_p_out) {
        this.bid_trailer_sec_1_p_out = bid_trailer_sec_1_p_out;
    }

    public String getBid_trailer_sec_1_t_out() {
        return bid_trailer_sec_1_t_out;
    }

    public void setBid_trailer_sec_1_t_out(String bid_trailer_sec_1_t_out) {
        this.bid_trailer_sec_1_t_out = bid_trailer_sec_1_t_out;
    }

    public String getBid_trailer_sec_1_date_in() {
        return bid_trailer_sec_1_date_in;
    }

    public void setBid_trailer_sec_1_date_in(String bid_trailer_sec_1_date_in) {
        this.bid_trailer_sec_1_date_in = bid_trailer_sec_1_date_in;
    }

    public String getBid_trailer_sec_1_date_out() {
        return bid_trailer_sec_1_date_out;
    }

    public void setBid_trailer_sec_1_date_out(String bid_trailer_sec_1_date_out) {
        this.bid_trailer_sec_1_date_out = bid_trailer_sec_1_date_out;
    }

    public String getBid_trailer_sec_2_p_in() {
        return bid_trailer_sec_2_p_in;
    }

    public void setBid_trailer_sec_2_p_in(String bid_trailer_sec_2_p_in) {
        this.bid_trailer_sec_2_p_in = bid_trailer_sec_2_p_in;
    }

    public String getBid_trailer_sec_2_t_in() {
        return bid_trailer_sec_2_t_in;
    }

    public void setBid_trailer_sec_2_t_in(String bid_trailer_sec_2_t_in) {
        this.bid_trailer_sec_2_t_in = bid_trailer_sec_2_t_in;
    }

    public String getBid_trailer_sec_2_p_out() {
        return bid_trailer_sec_2_p_out;
    }

    public void setBid_trailer_sec_2_p_out(String bid_trailer_sec_2_p_out) {
        this.bid_trailer_sec_2_p_out = bid_trailer_sec_2_p_out;
    }

    public String getBid_trailer_sec_2_t_out() {
        return bid_trailer_sec_2_t_out;
    }

    public void setBid_trailer_sec_2_t_out(String bid_trailer_sec_2_t_out) {
        this.bid_trailer_sec_2_t_out = bid_trailer_sec_2_t_out;
    }

    public String getBid_trailer_sec_2_date_in() {
        return bid_trailer_sec_2_date_in;
    }

    public void setBid_trailer_sec_2_date_in(String bid_trailer_sec_2_date_in) {
        this.bid_trailer_sec_2_date_in = bid_trailer_sec_2_date_in;
    }

    public String getBid_trailer_sec_2_date_out() {
        return bid_trailer_sec_2_date_out;
    }

    public void setBid_trailer_sec_2_date_out(String bid_trailer_sec_2_date_out) {
        this.bid_trailer_sec_2_date_out = bid_trailer_sec_2_date_out;
    }

    public String getBid_trailer_sec_3_p_in() {
        return bid_trailer_sec_3_p_in;
    }

    public void setBid_trailer_sec_3_p_in(String bid_trailer_sec_3_p_in) {
        this.bid_trailer_sec_3_p_in = bid_trailer_sec_3_p_in;
    }

    public String getBid_trailer_sec_3_t_in() {
        return bid_trailer_sec_3_t_in;
    }

    public void setBid_trailer_sec_3_t_in(String bid_trailer_sec_3_t_in) {
        this.bid_trailer_sec_3_t_in = bid_trailer_sec_3_t_in;
    }

    public String getBid_trailer_sec_3_p_out() {
        return bid_trailer_sec_3_p_out;
    }

    public void setBid_trailer_sec_3_p_out(String bid_trailer_sec_3_p_out) {
        this.bid_trailer_sec_3_p_out = bid_trailer_sec_3_p_out;
    }

    public String getBid_trailer_sec_3_t_out() {
        return bid_trailer_sec_3_t_out;
    }

    public void setBid_trailer_sec_3_t_out(String bid_trailer_sec_3_t_out) {
        this.bid_trailer_sec_3_t_out = bid_trailer_sec_3_t_out;
    }

    public String getBid_trailer_sec_3_date_in() {
        return bid_trailer_sec_3_date_in;
    }

    public void setBid_trailer_sec_3_date_in(String bid_trailer_sec_3_date_in) {
        this.bid_trailer_sec_3_date_in = bid_trailer_sec_3_date_in;
    }

    public String getBid_trailer_sec_3_date_out() {
        return bid_trailer_sec_3_date_out;
    }

    public void setBid_trailer_sec_3_date_out(String bid_trailer_sec_3_date_out) {
        this.bid_trailer_sec_3_date_out = bid_trailer_sec_3_date_out;
    }

    public String getBid_trailer_sec_4_p_in() {
        return bid_trailer_sec_4_p_in;
    }

    public void setBid_trailer_sec_4_p_in(String bid_trailer_sec_4_p_in) {
        this.bid_trailer_sec_4_p_in = bid_trailer_sec_4_p_in;
    }

    public String getBid_trailer_sec_4_t_in() {
        return bid_trailer_sec_4_t_in;
    }

    public void setBid_trailer_sec_4_t_in(String bid_trailer_sec_4_t_in) {
        this.bid_trailer_sec_4_t_in = bid_trailer_sec_4_t_in;
    }

    public String getBid_trailer_sec_4_p_out() {
        return bid_trailer_sec_4_p_out;
    }

    public void setBid_trailer_sec_4_p_out(String bid_trailer_sec_4_p_out) {
        this.bid_trailer_sec_4_p_out = bid_trailer_sec_4_p_out;
    }

    public String getBid_trailer_sec_4_t_out() {
        return bid_trailer_sec_4_t_out;
    }

    public void setBid_trailer_sec_4_t_out(String bid_trailer_sec_4_t_out) {
        this.bid_trailer_sec_4_t_out = bid_trailer_sec_4_t_out;
    }

    public String getBid_trailer_sec_4_date_in() {
        return bid_trailer_sec_4_date_in;
    }

    public void setBid_trailer_sec_4_date_in(String bid_trailer_sec_4_date_in) {
        this.bid_trailer_sec_4_date_in = bid_trailer_sec_4_date_in;
    }

    public String getBid_trailer_sec_4_date_out() {
        return bid_trailer_sec_4_date_out;
    }

    public void setBid_trailer_sec_4_date_out(String bid_trailer_sec_4_date_out) {
        this.bid_trailer_sec_4_date_out = bid_trailer_sec_4_date_out;
    }

    public String getBid_trailer_sec_5_p_in() {
        return bid_trailer_sec_5_p_in;
    }

    public void setBid_trailer_sec_5_p_in(String bid_trailer_sec_5_p_in) {
        this.bid_trailer_sec_5_p_in = bid_trailer_sec_5_p_in;
    }

    public String getBid_trailer_sec_5_t_in() {
        return bid_trailer_sec_5_t_in;
    }

    public void setBid_trailer_sec_5_t_in(String bid_trailer_sec_5_t_in) {
        this.bid_trailer_sec_5_t_in = bid_trailer_sec_5_t_in;
    }

    public String getBid_trailer_sec_5_p_out() {
        return bid_trailer_sec_5_p_out;
    }

    public void setBid_trailer_sec_5_p_out(String bid_trailer_sec_5_p_out) {
        this.bid_trailer_sec_5_p_out = bid_trailer_sec_5_p_out;
    }

    public String getBid_trailer_sec_5_t_out() {
        return bid_trailer_sec_5_t_out;
    }

    public void setBid_trailer_sec_5_t_out(String bid_trailer_sec_5_t_out) {
        this.bid_trailer_sec_5_t_out = bid_trailer_sec_5_t_out;
    }

    public String getBid_trailer_sec_5_date_in() {
        return bid_trailer_sec_5_date_in;
    }

    public void setBid_trailer_sec_5_date_in(String bid_trailer_sec_5_date_in) {
        this.bid_trailer_sec_5_date_in = bid_trailer_sec_5_date_in;
    }

    public String getBid_trailer_sec_5_date_out() {
        return bid_trailer_sec_5_date_out;
    }

    public void setBid_trailer_sec_5_date_out(String bid_trailer_sec_5_date_out) {
        this.bid_trailer_sec_5_date_out = bid_trailer_sec_5_date_out;
    }

    public String getBid_trailer_sec_6_p_in() {
        return bid_trailer_sec_6_p_in;
    }

    public void setBid_trailer_sec_6_p_in(String bid_trailer_sec_6_p_in) {
        this.bid_trailer_sec_6_p_in = bid_trailer_sec_6_p_in;
    }

    public String getBid_trailer_sec_6_t_in() {
        return bid_trailer_sec_6_t_in;
    }

    public void setBid_trailer_sec_6_t_in(String bid_trailer_sec_6_t_in) {
        this.bid_trailer_sec_6_t_in = bid_trailer_sec_6_t_in;
    }

    public String getBid_trailer_sec_6_p_out() {
        return bid_trailer_sec_6_p_out;
    }

    public void setBid_trailer_sec_6_p_out(String bid_trailer_sec_6_p_out) {
        this.bid_trailer_sec_6_p_out = bid_trailer_sec_6_p_out;
    }

    public String getBid_trailer_sec_6_t_out() {
        return bid_trailer_sec_6_t_out;
    }

    public void setBid_trailer_sec_6_t_out(String bid_trailer_sec_6_t_out) {
        this.bid_trailer_sec_6_t_out = bid_trailer_sec_6_t_out;
    }

    public String getBid_trailer_sec_6_date_in() {
        return bid_trailer_sec_6_date_in;
    }

    public void setBid_trailer_sec_6_date_in(String bid_trailer_sec_6_date_in) {
        this.bid_trailer_sec_6_date_in = bid_trailer_sec_6_date_in;
    }

    public String getBid_trailer_sec_6_date_out() {
        return bid_trailer_sec_6_date_out;
    }

    public void setBid_trailer_sec_6_date_out(String bid_trailer_sec_6_date_out) {
        this.bid_trailer_sec_6_date_out = bid_trailer_sec_6_date_out;
    }
}
