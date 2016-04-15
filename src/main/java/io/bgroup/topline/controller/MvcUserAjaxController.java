package io.bgroup.topline.controller;

import io.bgroup.topline.model.Company;
import io.bgroup.topline.model.CompanyUnit;
import io.bgroup.topline.model.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

@Controller
@EnableWebMvc
public class MvcUserAjaxController {
    @Autowired
    private SiteUser siteUserMvc;
    @Autowired
    private Company companyMvc;
    @Autowired
    private CompanyUnit companyUnitMvc;

    @ResponseBody
    @RequestMapping(value = "getCompany", produces = {"text/plain; charset=UTF-8"})
    public String getCompany(HttpServletRequest request) {
        String responseBody = "Error";
        if (request != null) {
            responseBody = companyMvc.getCompanyForAjax(request);
        }
        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "getCompanyAndUnits", produces = {"text/plain; charset=UTF-8"})
    public String getCompanyAndUnits(HttpServletRequest request) {
        String responseBody = "Error";
        if (request != null) {
            responseBody = companyUnitMvc.getCompanyUnitsForAjax(request);
        }
        return responseBody;
    }
    @ResponseBody
    @RequestMapping(value = "saveRoleForUser", produces = {"text/plain; charset=UTF-8"})
    public String saveRoleForUser(HttpServletRequest request, UsernamePasswordAuthenticationToken principal) {
        String responseBody = "Error";
        if (request != null) {
            responseBody = siteUserMvc.saveRoleForUserForAjax(request,principal);
        }
        return responseBody;
    }

}
