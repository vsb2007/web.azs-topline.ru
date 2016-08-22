package io.bgroup.topline.controller;

import io.bgroup.topline.model.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

@Controller
@EnableWebMvc
public class MvcSaleController {

    @Autowired
    private DriverManagerDataSource dataSource;
    @Autowired
    private SiteUser siteUserMvc;
    @Autowired
    private Car carMvc;
    @Autowired
    private Trailer trailerMvc;
    @Autowired
    private Driver driverMvc;
    @Autowired
    private OilStorage oilStorageMvc;
    @Autowired
    private OilType oilTypeMvc;
    @Autowired
    private Bid bidMvc;
    @Autowired
    private BidDetail bidDetailMvc;
    @Autowired
    private Organization organizationMvc;

    @RequestMapping(value = "saleCreate")
    public ModelAndView saleCreate(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        //long time = System.currentTimeMillis();
        ModelAndView model = new ModelAndView();
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        ArrayList<OilStorage> oilStorageList = oilStorageMvc.getOilStorageList();
        ArrayList<OilType> oilTypeList = oilTypeMvc.getOilTypesList();

        model.addObject("siteUser", siteUser);
        model.addObject("oilStorageList", oilStorageList);
        model.addObject("oilTypeList", oilTypeList);
        model.setViewName("saleCreate");
        return model;
    }
}