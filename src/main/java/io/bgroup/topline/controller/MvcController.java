package io.bgroup.topline.controller;

import io.bgroup.topline.model.SiteUser;
import io.bgroup.topline.servlets.Login;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@EnableWebMvc
public class MvcController {

    @RequestMapping(value = {"/", "/index**","/index"})
    public String welcomePage(Model model,UsernamePasswordAuthenticationToken principal) {
    //public ModelAndView welcomePage() {
       // ModelAndView model = new ModelAndView();
        //SiteUser dbUserName = new SiteUser().findSiteUser(principal.getName());
        //model.addObject("dbUserName", dbUserName);
        //model.setViewName("index");
        try {
            model.addAttribute("dbUserName", principal.getName());
        }
        catch (Exception e){

        }
        return "index";
    }
/*
    @RequestMapping("/index")
    public String index(Model model) {
        //public ModelAndView welcomePage() {
        // ModelAndView model = new ModelAndView();
        //SiteUser dbUserName = new SiteUser().findSiteUser(principal.getName());
        //model.addObject("dbUserName", dbUserName);
        //model.setViewName("index");
        //model.addAttribute("dbUserName", dbUserName);
        return "index";
    }
*/
    @RequestMapping(value = "/protected**", method = RequestMethod.GET)
    public ModelAndView protectedPage() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Welcome to secure page!");
        model.addObject("message", "This is protected page - only for admin users!");
        model.setViewName("protected");
        return model;
    }

    @RequestMapping(value = "/confidential**", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Welcome to super puper secure page");
        model.addObject("message", "This is confidential page - need super admin role!");
        model.setViewName("protected");
        return model;
    }


    @RequestMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        System.out.println("Exit auth!!!");
        return "redirect:/index?logout";
    }

}