package io.bgroup.topline.controller;

import io.bgroup.topline.model.Bid;
import io.bgroup.topline.model.Car;
import io.bgroup.topline.model.Organization;
import io.bgroup.topline.model.Trailer;
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

    @RequestMapping(value = "orgGetListByFilter", produces = {"text/plain; charset=UTF-8"})
    @ResponseBody
    public String orgGetListByFilter(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        String responseBody = "Error";
        if (request != null) {
            responseBody = organizationMvc.getListByFilter(principal, request);
        }
        return responseBody;
    }

}
