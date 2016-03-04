package io.bgroup.topline.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by VSB on 01.03.2016.
 * ToplineWeb.2.5
 */
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "404";// view name for 404 error
    }
}
