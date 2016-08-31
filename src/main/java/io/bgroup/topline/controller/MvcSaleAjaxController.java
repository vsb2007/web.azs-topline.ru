package io.bgroup.topline.controller;

import io.bgroup.topline.model.Bid;
import io.bgroup.topline.model.Organization;
import io.bgroup.topline.model.SaleOil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

@Controller
@EnableWebMvc
public class MvcSaleAjaxController {

    @Autowired
    private SaleOil saleOilMvc;

    @RequestMapping(value = "checkReadSaleForm", produces = {"text/plain; charset=UTF-8"})
    @ResponseBody
    public String checkReadSaleForm(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        String responseBody = "Неизвестная ошибка";
        responseBody = saleOilMvc.checkReadSaleOilBid(principal,request);
        return responseBody;
    }

    @RequestMapping(value = "saleRedUpdate", produces = {"text/plain; charset=UTF-8"})
    @ResponseBody
    public String bidRedUpdate(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        String responseBody = "Error";
        if (request != null) {
            responseBody = saleOilMvc.redSaleOil(principal, request);
        }
        return responseBody;
    }

    @RequestMapping(value = "changeStatusSailOil", produces = {"text/plain; charset=UTF-8"})
    @ResponseBody
    public String changeStatusSailOil(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        String responseBody = "Error";
        if (request != null) {
            responseBody = saleOilMvc.changeStatusSaleOil(principal, request);
        }
        return responseBody;
    }
}
