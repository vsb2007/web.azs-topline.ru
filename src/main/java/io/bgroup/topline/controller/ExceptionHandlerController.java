package io.bgroup.topline.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        //return "login";// view name for 404 error
        System.out.println("404 - 1"); // not login to this point
        return "redirect:/index?logout";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle(Exception ex) {
        System.out.println("404 - 2"); // not login to this point
        return "redirect:/index?logout";
    }
    /*
    @RequestMapping(value = {"/404"})
    public String NotFoundPage() {
        return "login";

    }*/

}
