package io.bgroup.topline.controller;

import io.bgroup.topline.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

@Controller
@EnableWebMvc
public class MvcCompanyAjaxController {

    @Autowired
    private Bid bidMvc;
    @Autowired
    private Organization organizationMvc;
    @Autowired
    private OrganizationPact organizationPactMvc;

    @RequestMapping(value = "orgGetListByFilter", produces = {"text/plain; charset=UTF-8"})
    @ResponseBody
    public String orgGetListByFilter(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        String responseBody = "Error";
        if (request != null) {
            responseBody = organizationMvc.getListByFilter(principal, request);
        }
        return responseBody;
    }

    @RequestMapping(value = "orgGetDog", produces = {"text/plain; charset=UTF-8"})
    @ResponseBody
    public String orgGetDog(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        String responseBody = "Error";
        if (request != null) {
            responseBody = organizationMvc.getOrgDogById(principal, request);
        }
        return responseBody;
    }

    @RequestMapping(value = "getOrgDogOpenSum", produces = {"text/plain; charset=UTF-8"})
    @ResponseBody
    public String getOrgDogOpenSum(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        String responseBody = "Error";
        if (request != null) {
            responseBody = organizationPactMvc.getOrgDogOpenSumById(principal, request);
        }
        return responseBody;
    }

}
