package io.bgroup.topline.controller;

import io.bgroup.topline.model.Company;
import io.bgroup.topline.model.CompanyUnit;
import io.bgroup.topline.model.Post;
import io.bgroup.topline.model.SiteUser;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

@Controller
@EnableWebMvc
public class MvcUserController {

    @Autowired
    private SiteUser siteUserMvc;
    @Autowired
    private Post postMvc;
    @Autowired
    private Company companyMvc;
    @Autowired
    private CompanyUnit companyUnitMvc;

    @RequestMapping(value = {"/", "/index**", "/index"})
    public String welcomePage(Model model, UsernamePasswordAuthenticationToken principal) {
        try {
            model.addAttribute("dbUserName", principal.getName());
        } catch (Exception e) {
            //some actions
        }
        return "index";
    }

    @RequestMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/index?logout";
    }

    @RequestMapping(value = "/users")
    public ModelAndView UsersPage(UsernamePasswordAuthenticationToken principal) {
        ModelAndView model = new ModelAndView();
        ArrayList<SiteUser> list = siteUserMvc.getListSiteUsers(principal);
        model.addObject("listUsers", list);
        model.setViewName("users");
        return model;
    }

    @RequestMapping(value = "/usersadd")
    public ModelAndView UsersAdd(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();

        siteUserMvc.userAdd(principal, request);
        model.addObject("errorAddUser", siteUserMvc.getError());
        ArrayList<SiteUser> list = siteUserMvc.getListSiteUsers(principal);
        model.addObject("listUsers", list);

        model.setViewName("users");
        return model;
    }

    @RequestMapping(value = "/usersred")
    public ModelAndView UsersRed(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();

        //System.out.println("1111");
        SiteUser userRed = siteUserMvc.findRedSiteUser(principal, request);
        /*
        System.out.println(userRed.getName());
        System.out.println("idCompany: " + userRed.getCompanyUnit().getCompany().getIdCompany());
        CompanyUnit companyUnit = userRed.getCompanyUnit();
        System.out.println("unitName: " + companyUnit.getCompanyUnitName());

        ArrayList<CompanyUnit> arrayList = companyUnit.getCompanyUnitList("4");
        System.out.println("взяли лист");
        System.out.println("size: " + arrayList.size());


        */
        ArrayList<Company> companyArrayList = companyMvc.getCompanyList();
        //ArrayList<Company> companyArrayList = companyUnitMvc.getCompany().getCompanyList();
        ArrayList<Post> postArrayList = postMvc.getPostList();
        model.addObject("userRed", userRed);
        model.addObject("postList", postArrayList);
        model.addObject("companyList", companyArrayList);
        model.setViewName("usersred");
        return model;
    }

    @RequestMapping(value = "/roles")
    public ModelAndView Roles(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        model.setViewName("roles");
        return model;
    }

    //test
    @RequestMapping("/profile")
    public String profile(Model model, UsernamePasswordAuthenticationToken principal) {
        model.addAttribute("principal", principal.getPrincipal());
        return "profile";
    }
}