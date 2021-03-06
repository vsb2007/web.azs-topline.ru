package io.bgroup.topline.controller;

import io.bgroup.topline.model.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

@Controller
@EnableWebMvc
public class MvcBidController {

    @Autowired
    private DriverManagerDataSource dataSource;
    @Autowired
    private SiteUser siteUserMvc;
    @Autowired
    private Car carMvc;
    @Autowired
    private Trailer trailerMvc;
    @Autowired
    private Driver driverMvc;
    @Autowired
    private OilStorage oilStorageMvc;
    @Autowired
    private OilType oilTypeMvc;
    @Autowired
    private Bid bidMvc;
    @Autowired
    private BidDetail bidDetailMvc;
    @Autowired
    private Organization organizationMvc;

    @RequestMapping(value = "bidcreate")
    public ModelAndView bidcreate(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        //long time = System.currentTimeMillis();
        ModelAndView model = new ModelAndView();
        SiteUser bidUser = siteUserMvc.findSiteUser(principal);
        ArrayList<Car> carsList = carMvc.getCarsList();
        ArrayList<Driver> driversList = driverMvc.getDriverList();
        ArrayList<OilStorage> oilStorageList = oilStorageMvc.getOilStorageList();
        //System.out.println(System.currentTimeMillis() - time);
        model.addObject("title", "Перемещение между складами");
        boolean isEntrance = false;
        model.addObject("isEntrance", isEntrance);
        model.addObject("appUser", bidUser);
        model.addObject("carsList", carsList);
        model.addObject("driversList", driversList);
        model.addObject("oilStorageList", oilStorageList);
        model.setViewName("bidCreate");
        return model;
    }

    @RequestMapping(value = "bidEntrance")
    public ModelAndView bidEntrance(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        long time = System.currentTimeMillis();
        ModelAndView model = new ModelAndView();
        SiteUser bidUser = siteUserMvc.findSiteUser(principal);
        ArrayList<Car> carsList = carMvc.getCarsList();
        ArrayList<Driver> driversList = driverMvc.getDriverList();
        ArrayList<OilStorage> oilStorageList = oilStorageMvc.getOilStorageShipperList();
        System.out.println(System.currentTimeMillis() - time);
        model.addObject("title", "Поступление бензовозами");
        model.addObject("appUser", bidUser);
        model.addObject("carsList", carsList);
        model.addObject("driversList", driversList);
        model.addObject("oilStorageList", oilStorageList);
        boolean isEntrance = true;
        model.addObject("isEntrance", isEntrance);
        model.setViewName("bidCreate");
        return model;
    }

    @RequestMapping(value = "bidcreateform")
    public ModelAndView bidcreatedo(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        String message = bidMvc.createBid(principal, request);
        if (request != null) {
            model.addObject("message", message);
        }
        ArrayList<Bid> bidsArrayList = bidMvc.getBidsList(principal);
        model.addObject("bidsList", bidsArrayList);
        model.setViewName("bidListOpen");
        return model;
    }

    @RequestMapping(value = "bidlistopen")
    public ModelAndView bidlistopen(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        //long i = System.currentTimeMillis();
        ArrayList<Bid> bidsArrayList = bidMvc.getBidsList(principal);
        //System.out.println(System.currentTimeMillis()-i);
        model.addObject("bidsList", bidsArrayList);
        model.setViewName("bidListOpen");
        return model;
    }

    @RequestMapping(value = "bidView")
    public ModelAndView bidView(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Bid bid = bidMvc.getBidForView(principal, request);
        /*
        if (bid.getBid_is_freeze()) {
            if (bid.isPdfFileExist()) {
                model.addObject("pdfFile", 1);
            } else {
                model.addObject("pdfFile", 0);
            }
        } else {
            model.addObject("pdfFile", 0);
        }
        */
        model.addObject("pdfFile", 1);
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        ArrayList<BidDetail> bidDetailsCar = bidDetailMvc.getBidDetailList(bid.getId_bid(), bid.getCar());
        ArrayList<BidDetail> bidDetailsTrailer = bidDetailMvc.getBidDetailList(bid.getId_bid(), bid.getTrailer());
        boolean isCarSectionBidUp = bidDetailMvc.isSectionBidUp(bidDetailsCar, bid, siteUser);
        boolean isTrailerSectionBidUp = bidDetailMvc.isSectionBidUp(bidDetailsTrailer, bid, siteUser);
        model.addObject("siteUser", siteUser);
        model.addObject("bid", bid);
        model.addObject("isCarSectionBidUp", isCarSectionBidUp);
        model.addObject("isTrailerSectionBidUp", isTrailerSectionBidUp);
        model.addObject("bidDetailsCar", bidDetailsCar);
        model.addObject("bidDetailsTrailer", bidDetailsTrailer);
        setViewNameForModel(model, siteUser, bid);
        return model;
    }

    @RequestMapping(value = "bidRed")
    public ModelAndView bidRed(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Bid bid = bidMvc.getBidForView(principal, request);
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        ArrayList<BidDetail> bidDetailsCar = bidDetailMvc.getBidDetailList(bid.getId_bid(), bid.getCar());
        ArrayList<BidDetail> bidDetailsTrailer = bidDetailMvc.getBidDetailList(bid.getId_bid(), bid.getTrailer());
        boolean isCarSectionBidUp = bidDetailMvc.isSectionBidUp(bidDetailsCar, bid, siteUser);
        boolean isTrailerSectionBidUp = bidDetailMvc.isSectionBidUp(bidDetailsTrailer, bid, siteUser);
        ArrayList<Car> carsList = carMvc.getCarsList();
        ArrayList<Driver> driversList = driverMvc.getDriverList();
        ArrayList<OilStorage> oilStorageList = oilStorageMvc.getOilStorageList();
        ArrayList<Organization> organizationList = organizationMvc.getOrganizationListWithSaleBids();
        ArrayList<OilType> oilTypeList = oilTypeMvc.getOilTypesList();
        ArrayList<Trailer> trailersList = trailerMvc.getTrailersList();
        model.addObject("siteUser", siteUser);
        model.addObject("carsList", carsList);
        model.addObject("trailersList", trailersList);
        model.addObject("driversList", driversList);
        model.addObject("oilStorageList", oilStorageList);
        model.addObject("organizationList", organizationList);
        model.addObject("oilTypeList", oilTypeList);
        model.addObject("bid", bid);
        model.addObject("isCarSectionBidUp", isCarSectionBidUp);
        model.addObject("isTrailerSectionBidUp", isTrailerSectionBidUp);
        model.addObject("bidDetailsCar", bidDetailsCar);
        model.addObject("bidDetailsTrailer", bidDetailsTrailer);
        model.setViewName("bidRedCreator");
        return model;
    }

    @RequestMapping(value = "bidClose")
    public ModelAndView bidClose(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        bidMvc.closeBid(principal, request);
        ArrayList<Bid> bidsArrayList = bidMvc.getBidsList(principal);
        model.addObject("bidsList", bidsArrayList);
        model.setViewName("bidListOpen");
        return model;
    }

    @RequestMapping(value = "bidUpdate")
    public ModelAndView bidUpdate(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Bid bid = bidMvc.updateBid(principal, request);
        SiteUser siteUser = siteUserMvc.findSiteUser(principal);
        ArrayList<BidDetail> bidDetailsCar = bidDetailMvc.getBidDetailList(bid.getId_bid(), bid.getCar());
        ArrayList<BidDetail> bidDetailsTrailer = bidDetailMvc.getBidDetailList(bid.getId_bid(), bid.getTrailer());
        boolean isCarSectionBidUp = bidDetailMvc.isSectionBidUp(bidDetailsCar, bid, siteUser);
        boolean isTrailerSectionBidUp = bidDetailMvc.isSectionBidUp(bidDetailsTrailer, bid, siteUser);
        model.addObject("siteUser", siteUser);
        model.addObject("bid", bid);
        model.addObject("isCarSectionBidUp", isCarSectionBidUp);
        model.addObject("isTrailerSectionBidUp", isTrailerSectionBidUp);
        model.addObject("bidDetailsCar", bidDetailsCar);
        model.addObject("bidDetailsTrailer", bidDetailsTrailer);
        setViewNameForModel(model, siteUser, bid);
        return model;
    }

    private void setViewNameForModel(ModelAndView model, SiteUser siteUser, Bid bid) {
        if (siteUser.getPost() == null || siteUser.getPost().getIdPost() == 1) {
            model.setViewName("bidViewCreator");
        } else if (siteUser.getPost() != null && siteUser.getPost().getIdPost() == 2) {
            if (!bid.getBid_is_freeze()) {
                if (bid.isDriverCanUpdateIn()) {
                    model.setViewName("bidViewOperatorIn");
                } else {
                    model.setViewName("bidViewDriver");
                }
            } else if (bid.getBid_is_freeze()) {
                if (bid.isDriverCanUpdateOut()) {
                    model.setViewName("bidViewOperatorOut");
                } else {
                    model.setViewName("bidViewDriver");
                }
            } else
                model.setViewName("bidViewDriver");
        } else if (siteUser.getPost() != null && siteUser.getPost().getIdPost() == 3) {
            if (siteUser.getCompanyUnit() != null && siteUser.getCompanyUnit().getIdCompanyUnit() == bid.getOilStorageIn().getIdOilStorage())
                model.setViewName("bidViewOperatorIn");
            if (siteUser.getCompanyUnit() != null && siteUser.getCompanyUnit().getIdCompanyUnit() != bid.getOilStorageIn().getIdOilStorage()) {
                model.setViewName("bidViewOperatorOut");
            }
        } else if (siteUser.getPost() != null && siteUser.getPost().getIdPost() == 4) {
            model.setViewName("bidViewWatcher");
        }
    }

    // for 403 access denied page
    @RequestMapping(value = "/403")
    public String accessDenied() {
        return "redirect:index?logout";
    }

    @RequestMapping(value = "/*")
    public String pageNotFound() {
        return "redirect:/index?logout";
    }

    @Autowired
    private MyConstant myConstantMvc;

    @RequestMapping(value = "downloadPdfFile")
    public void getLogFile(HttpServletResponse response, UsernamePasswordAuthenticationToken principal, HttpServletRequest request) throws Exception {
        if (request == null) return;
        String bidId = request.getParameter("bidId");
        if (bidId != null && !bidId.equals("")) ;
        Bid bid = bidMvc.getBid(bidId);
        if (bid == null) return;
        String fileNamePath = "";
        String fileName = "";
        fileName = myConstantMvc.getFilePrefix() + "_" + bid.getId_bid()
                + "_" + bid.getBid_date_create().replace("-", "") + ".pdf";
        PdfCreator pdfCreator = new PdfCreator();
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".pdf");
            ArrayList<BidDetail> bidDetailListCar = bidDetailMvc.getBidDetailList(bid.getId_bid(), bid.getCar());
            ArrayList<BidDetail> bidDetailListTrailer = bidDetailMvc.getBidDetailList(bid.getId_bid(), bid.getTrailer());
            pdfCreator.getDocument(response.getOutputStream(),bid.getHtmlTTN(bidDetailListCar,bidDetailListTrailer));
            response.flushBuffer();
        } catch (Exception e) {
            //LOGGER.debug("Request could not be completed at this moment. Please try again.");
            e.printStackTrace();
        }
    }
}