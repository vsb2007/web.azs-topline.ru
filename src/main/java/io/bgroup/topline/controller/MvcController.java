package io.bgroup.topline.controller;

import io.bgroup.topline.model.DbToplineWeb;
import io.bgroup.topline.model.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
@EnableWebMvc
public class MvcController {

    @Autowired
    private DriverManagerDataSource dataSource;

    @Autowired
    private SiteUser siteUser;

    @Autowired
    private DbToplineWeb dbToplineWeb;

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

    @RequestMapping(value = "/users**", method = RequestMethod.GET)
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
        return "redirect:/index?logout";
    }

    @RequestMapping(value="/users")
    public ModelAndView UsersPage (UsernamePasswordAuthenticationToken principal) {
        ModelAndView model = new ModelAndView();
        ArrayList<SiteUser> list = siteUser.getListSiteUsers(principal);
        model.addObject("listUsers",list);
        model.setViewName("users");
        return model;
    }

    @RequestMapping(value="/usersadd")
    public ModelAndView UsersAdd (UsernamePasswordAuthenticationToken principal,HttpServletRequest request) {
        ModelAndView model = new ModelAndView();


        model.setViewName("users");
        return model;
    }

    @RequestMapping("/profile")
    public String profile(Model model, UsernamePasswordAuthenticationToken principal) {
        model.addAttribute("principal", principal.getPrincipal());
        return "profile";
    }


}