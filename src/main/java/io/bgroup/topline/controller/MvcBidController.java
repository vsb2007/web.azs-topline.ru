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
    private SiteUser siteUser;

    @Autowired
    private Car car;

    @Autowired
    private Driver driver;

    @Autowired
    private OilStorage oilStorage;

    @Autowired
    private OilType oilType;

    @Autowired
    private Bid bid;

    @RequestMapping(value = "/bidcreate")
    public ModelAndView AppList(UsernamePasswordAuthenticationToken principal,HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        if (request!=null) bid.createBid(principal,request);

        SiteUser bidUser = siteUser.findSiteUser(principal);
        ArrayList<Car> carsList = car.getCarsList();
        ArrayList<Driver> driversList = driver.getDriversList();
        ArrayList<OilStorage> oilStorageList = oilStorage.getOilStorageList();

        model.addObject("appUser", bidUser);
        model.addObject("carsList", carsList);
        model.addObject("driversList", driversList);
        model.addObject("oilStorageList", oilStorageList);
        model.setViewName("bidCreate");
        return model;
    }

}