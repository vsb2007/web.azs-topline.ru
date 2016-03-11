package io.bgroup.topline.controller;

import io.bgroup.topline.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
@EnableWebMvc
public class MvcAppController {

    @Autowired
    private DriverManagerDataSource dataSource;

    @Autowired
    private SiteUser siteUser;

    @Autowired
    private Car car;

    @Autowired
    private Driver driver;

    @Autowired
    private OilFarm oilFarm;

    @Autowired
    private OilType oilType;

    @Autowired
    private DbToplineWeb dbToplineWeb;

    @RequestMapping(value = "/appcreate")
    public ModelAndView AppList(UsernamePasswordAuthenticationToken principal) {
        ModelAndView model = new ModelAndView();
        SiteUser appUser = siteUser.findSiteUser(principal);
        ArrayList<Car> carsList = car.getCarsList();
        ArrayList<Driver> driversList = driver.getDriversList();
        ArrayList<OilFarm> oilFarmsList = oilFarm.getOilFarmsList();
        ArrayList<OilType> oilTypesList = oilType.getOilTypesList();

        model.addObject("appUser", appUser);
        model.addObject("carsList", carsList);
        model.addObject("driversList", driversList);
        model.addObject("oilFarmsList", oilFarmsList);
        model.addObject("oilTypesList", oilTypesList);
        model.setViewName("appCreate");
        return model;
    }

}