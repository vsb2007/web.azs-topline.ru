package io.bgroup.topline.controller;

import io.bgroup.topline.model.Car;
import io.bgroup.topline.model.Trailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@EnableWebMvc
public class MvcBidAjaxController {
    @Autowired
    private Car car;
    @Autowired
    private Trailer trailer;

    @ResponseBody
    @RequestMapping(value = "/getCarSections", produces = {"text/plain; charset=UTF-8"})
    public String getCarSections(HttpServletRequest request) {
        String responseBody = "Error";
        if (request != null) {
         //   responseBody = car.getCarSectionsForAjax(request.getParameter("idCar"));
        }
        return responseBody;
    }

    @RequestMapping(value = "/getTrailerSections", produces = {"text/plain; charset=UTF-8"})
    @ResponseBody
    public String getTrailerSections(HttpServletRequest request) {
        String responseBody = "Error";
        if (request != null) {
      //      responseBody = trailer.getTrailerSectionsForAjax(request.getParameter("idTrailer"));
        }
        return responseBody;
    }

    @RequestMapping(value = "/getTrailers", produces = {"text/plain; charset=UTF-8"})
    @ResponseBody
    public String getTrailers(HttpServletRequest request) {
        String responseBody = "Error";
        if (request != null) {
            //responseBody = trailer.getTrailerForAjax(request.getParameter("idCar"));
        }
        return responseBody;
    }
}
