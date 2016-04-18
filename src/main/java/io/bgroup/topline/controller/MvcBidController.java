package io.bgroup.topline.controller;

import io.bgroup.topline.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@EnableWebMvc
public class MvcBidController {

    @Autowired
    private DriverManagerDataSource dataSource;

    @Autowired
    private SiteUser siteUserMvc;

    @Autowired
    private Car carMvc;

    @Autowired
    private Driver driverMvc;

    @Autowired
    private OilStorage oilStorageMvc;

    @Autowired
    private OilType oilTypeMvc;

    @Autowired
    private Bid bidMvc;

    @RequestMapping(value = "bidcreate")
    public ModelAndView bidcreate(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        SiteUser bidUser = siteUserMvc.findSiteUser(principal);
        ArrayList<Car> carsList = carMvc.getCarsList();
        ArrayList<Driver> driversList = driverMvc.getDriverList();
        ArrayList<OilStorage> oilStorageList = oilStorageMvc.getOilStorageList();
        model.addObject("appUser", bidUser);
        model.addObject("carsList", carsList);
        model.addObject("driversList", driversList);
        model.addObject("oilStorageList", oilStorageList);
        model.setViewName("bidCreate");
        return model;
    }

    @RequestMapping(value = "bidcreateform")
    public ModelAndView bidcreatedo(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        String message = bidMvc.createBid(principal, request);
        if (request != null) {
            model.addObject("message", message);
        }
        ArrayList<Bid> bidsArrayList = bidMvc.getBidsList(principal);
        model.addObject("bidsList", bidsArrayList);
        model.setViewName("bidListOpen");
        return model;

    }

    @RequestMapping(value = "bidlistopen")
    public ModelAndView bidlistopen(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        ArrayList<Bid> bidsArrayList = bidMvc.getBidsList(principal);
        model.addObject("bidsList", bidsArrayList);
        model.setViewName("bidListOpen");
        return model;
    }

    @RequestMapping(value = "bidView")
    public ModelAndView bidView(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Bid bid = bidMvc.getBidForView(principal,request);
        SiteUser bidUser = siteUserMvc.findSiteUser(principal);
        ArrayList<Car> carsList = carMvc.getCarsList();
        ArrayList<Driver> driversList = driverMvc.getDriverList();
        ArrayList<OilStorage> oilStorageList = oilStorageMvc.getOilStorageList();
        model.addObject("appUser", bidUser);
        model.addObject("carsList", carsList);
        model.addObject("driversList", driversList);
        model.addObject("oilStorageList", oilStorageList);
        model.addObject("bid", bid);
        model.setViewName("bidView");
        return model;
    }
}