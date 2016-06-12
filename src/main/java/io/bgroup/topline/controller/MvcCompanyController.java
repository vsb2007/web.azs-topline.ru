package io.bgroup.topline.controller;

import io.bgroup.topline.model.*;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MvcCompanyController {

    @Autowired
    private SiteUser siteUserMvc;
    @Autowired
    private Post postMvc;
    @Autowired
    private Company companyMvc;
    @Autowired
    private CompanyUnit companyUnitMvc;
    @Autowired
    private OilType oilTypeMvc;
    @Autowired
    private OilTypeStorage oilTypeStorageMvc;


    @RequestMapping(value = "company")
    public ModelAndView company(UsernamePasswordAuthenticationToken principal) {
        ModelAndView model = new ModelAndView();
        ArrayList<Company> companyList = companyMvc.getCompanyList();
        model.addObject("companyList", companyList);
        model.setViewName("company");
        return model;
    }

    @RequestMapping(value = "companyAdd")
    public ModelAndView companyAdd(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        companyMvc.addCompany(principal, request);
        model.addObject("errorCompanyAdd", companyMvc.getError());
        ArrayList<Company> companyList = companyMvc.getCompanyList();
        model.addObject("companyList", companyList);
        model.setViewName("company");
        return model;
    }

    @RequestMapping(value = "companyRed")
    public ModelAndView companyRed(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        companyMvc.redCompany(principal, request);
        model.addObject("errorCompanyRed", companyMvc.getError());
        Company company = companyMvc.getCompany(request);
        ArrayList<CompanyUnit> companyUnitList = companyUnitMvc.getCompanyUnitList(company.getIdCompany());

        model.addObject("company", company);
        model.addObject("companyUnitList", companyUnitList);
        model.setViewName("companyView");
        return model;
    }

    @RequestMapping(value = "companyView")
    public ModelAndView companyView(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Company company = companyMvc.getCompany(request);
        ArrayList<CompanyUnit> companyUnitList = companyUnitMvc.getCompanyUnitList(company.getIdCompany());
        model.addObject("company", company);
        model.addObject("companyUnitList", companyUnitList);
        model.setViewName("companyView");
        return model;
    }

    @RequestMapping(value = "companyUnitView")
    public ModelAndView companyUnitView(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        CompanyUnit companyUnit = companyUnitMvc.getCompanyUnit(request);
        ArrayList<OilType> oilTypeArrayList = oilTypeMvc.getOilTypesList();
        model.addObject("companyUnit", companyUnit);
        model.addObject("oilTypeList", oilTypeArrayList);
        model.setViewName("companyUnitView");
        return model;
    }

    @RequestMapping(value = "companyUnitRed")
    public ModelAndView companyUnitRed(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        companyUnitMvc.redCompanyUnit(principal, request);
        CompanyUnit companyUnit = companyUnitMvc.getCompanyUnit(request);
        model.addObject("errorCompanyUnitRed", companyUnitMvc.getError());
        model.addObject("companyUnit", companyUnit);
        model.setViewName("companyUnitView");
        return model;
    }

    @RequestMapping(value = "companyUnitAdd")
    public ModelAndView companyUnitAdd(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Company company = companyMvc.getCompany(request);
        companyUnitMvc.addCompanyUnit(request);
        ArrayList<CompanyUnit> companyUnitList = companyUnitMvc.getCompanyUnitList(company.getIdCompany());
        companyUnitMvc.redCompanyUnit(principal, request);
        model.addObject("errorCompanyUnitRed", companyUnitMvc.getError());
        model.addObject("company", company);
        model.addObject("companyUnitList", companyUnitList);
        model.setViewName("companyView");
        return model;
    }

    @RequestMapping(value = "companyUnitAddOilStorage")
    public ModelAndView companyUnitAddOilStorage(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();

        String error = companyUnitMvc.addCompanyUnitOilStorage(principal, request);

        CompanyUnit companyUnit = companyUnitMvc.getCompanyUnit(request);
        ArrayList<OilType> oilTypeArrayList = oilTypeMvc.getOilTypesList();
        model.addObject("companyUnit", companyUnit);
        model.addObject("addOilStorageMessage", error);
        model.addObject("oilTypeList", oilTypeArrayList);

        model.setViewName("companyUnitView");
        return model;
    }

    @RequestMapping(value = "updateOilTypeStorage")
    public ModelAndView updateOilTypeStorage(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        String message;
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        if (siteUser != null && !siteUser.isUserHasRole(principal, "ROLE_COMPANY_RED")) message = "нет прав: 02";
        else {
            int companyUnitId = -1;
            try {
                companyUnitId = Integer.parseInt(request.getParameter("companyUnitId"));
            } catch (Exception e) {
                companyUnitId = -1;
            }
            CompanyUnit companyUnit = companyUnitMvc.getCompanyUnit(companyUnitId);
            message = oilTypeStorageMvc.updateOilTypeStorage(siteUser, request, companyUnit);
        }
        CompanyUnit companyUnit = companyUnitMvc.getCompanyUnit(request);
        ArrayList<OilType> oilTypeArrayList = oilTypeMvc.getOilTypesList();
        model.addObject("companyUnit", companyUnit);
        model.addObject("updateOilStorageMessage", message);
        model.addObject("oilTypeList", oilTypeArrayList);

        model.setViewName("companyUnitView");
        return model;
    }

}