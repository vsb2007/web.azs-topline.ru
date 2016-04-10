package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.*;

public class DbModel {

   // @Autowired
    //private JdbcTemplate jdbcTemplateMvc;
    @Autowired
    DriverManagerDataSource dataSourceMvc;

         /*
    public DbModel() {
        try {
            connection = dataSourceMvc.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    enum tableUsers {
        id_user, username, password, user_fio, user_phone, user_email, enabled, user_is_delete,
        user_post_id, user_company_id, user_company_unit_id
    }

    enum tableBids {
        id_bids, bid_create_user_id, bid_number, bid_date_create, bid_date_last_update, bid_storage_in_id, bid_driver_id, bid_car_id,
        bid_car_sec_1_oilType_id, bid_car_sec_1_storageOut_id,
        bid_car_sec_2_oilType_id, bid_car_sec_2_storageOut_id,
        bid_car_sec_3_oilType_id, bid_car_sec_3_storageOut_id,
        bid_trailer_id,
        bid_trailer_sec_1_oilType_id, bid_trailer_sec_1_storageOut_id,
        bid_trailer_sec_2_oilType_id, bid_trailer_sec_2_storageOut_id,
        bid_trailer_sec_3_oilType_id, bid_trailer_sec_3_storageOut_id,
        bid_trailer_sec_4_oilType_id, bid_trailer_sec_4_storageOut_id,
        bid_trailer_sec_5_oilType_id, bid_trailer_sec_5_storageOut_id,
        bid_trailer_sec_6_oilType_id, bid_trailer_sec_6_storageOut_id,
        bid_date_freeze, bid_is_freeze, bid_date_close, bid_is_close, bid_is_done,
        bid_date_done
    }

    enum tableCars {
        id_car, id_1c_car, car_name, car_number, car_sec_1, car_sec_2, car_sec_3, car_sec_4, car_sec_5, car_sec_6, car_block
    }

    enum tableCompany {
        id_company, company_name
    }

    enum tableCompanyUnit {
        id_company_unit, id_1c_storage, company_unit_name, Block, IsAZS, company_id
    }

    enum tableDrivers {
        id_drivers, id_1c_drivers, drivers_fio, drivers_phone, drivers_email, drivers_block
    }

    enum tableNomenclature {
        id_Nomenclature, id_1c_Nomenclature, Name, edIzm, block
    }

    enum tablePost {
        id_post, post_name
    }

    enum tableTrailer {
        id_trailer, trailer_number, trailer_car_id,
        trailer_sec_1, trailer_sec_2, trailer_sec_3, trailer_sec_4, trailer_sec_5, trailer_sec_6,
        trailer_block
    }

    enum tableUserRoles {
        user_role_id, role, username
    }

    public ResultSet getSelectResult(String query) throws Exception {
        try {
            Connection connection = dataSourceMvc.getConnection();
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean getInsertResult(String query) throws Exception {
         Connection connection = dataSourceMvc.getConnection();
         Statement statement = connection.createStatement();
         PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(query);
        return preparedStatement.execute();
    }

    /*
        public void setJdbcTemplateMvc(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplateMvc = jdbcTemplate;
        }

        public JdbcTemplate getJdbcTemplateMvc() {
            return jdbcTemplateMvc;
        }
    */
    private String error;
    public DbModel() {

    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

/*
    public List<Map<String, Object>> getSelectResult(String sql) {
        List<Map<String, Object>> list = null;
        try {
            list = jdbcTemplateMvc.queryForList(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
*/
    /*
    public boolean getInsertResult(String sql) {
        try {
            //sorry for next line :-)
            return jdbcTemplateMvc.getDataSource().getConnection().createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }*/
}
