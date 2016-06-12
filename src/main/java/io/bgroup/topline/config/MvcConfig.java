package io.bgroup.topline.config;

import io.bgroup.topline.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import java.nio.charset.Charset;
import java.sql.SQLException;

@Configuration
@EnableWebMvc
@ComponentScan("io.bgroup.topline")
@PropertySource("classpath:jdbc.properties")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/pages/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/img/**").addResourceLocations("/WEB-INF/pages/images/").setCachePeriod(31556926);
        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/pages/images/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/pages/js/").setCachePeriod(31556926);
        registry.addResourceHandler("/gif/**").addResourceLocations("/WEB-INF/pages/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/demo-files/**").addResourceLocations("/WEB-INF/pages/demo-files/").setCachePeriod(31556926);
        registry.addResourceHandler("/fonts/**").addResourceLocations("/WEB-INF/pages/fonts/").setCachePeriod(31556926);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        //этот медот для кодировки не помог
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(this.dataSource());
    }

    @Bean(name = "dbModel")
    public DbModel dbModel() {
        return new DbModel();
    }

    @Bean(name = "DbJdbcModel")
    public DbJdbcModel dbJdbcModel() {
        return new DbJdbcModel();
    }

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        String jdbcHost = this.environment.getProperty("jdbc.host");
        String jdbcPort = this.environment.getProperty("jdbc.port");
        String jdbcUser = this.environment.getProperty("jdbc.user");
        String jdbcPassword = this.environment.getProperty("jdbc.password");
        String jdbcDbName = this.environment.getProperty("jdbc.db_name");

        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://" + jdbcHost + ":" + jdbcPort + "/" + jdbcDbName);
        driverManagerDataSource.setUsername(jdbcUser);
        driverManagerDataSource.setPassword(jdbcPassword);

        return driverManagerDataSource;
    }

    @Bean(name = "myConstant")
    public MyConstant myConstant() {
        String folder = this.environment.getProperty("pdf.folder");
        String prefix = this.environment.getProperty("pdf.prefix");
        MyConstant myConstant = new MyConstant();
        myConstant.setFileFolder(folder);
        myConstant.setFilePrefix(prefix);
        return myConstant;
    }

    @Bean(name = "siteUser")
    public SiteUser siteUser() {
        SiteUser siteUser = new SiteUser();
        return siteUser;
    }

    @Bean(name = "car")
    public Car car() {
        Car car = new Car();
        return car;
    }

    @Bean(name = "oilTypeStorage")
    public OilTypeStorage oilTypeStorage() {
        OilTypeStorage oilTypeStorage = new OilTypeStorage();
        return oilTypeStorage;
    }

    @Bean(name = "trailer")
    public Trailer trailer() {
        Trailer trailer = new Trailer();
        return trailer;
    }

    @Bean(name = "oilSections")
    public OilSections carSections() {
        OilSections oilSections = new OilSections();
        return oilSections;
    }

    @Bean(name = "driver")
    public Driver driver() {
        Driver driver = new Driver();
        return driver;
    }

    @Bean(name = "post")
    public Post post() {
        Post post = new Post();
        return post;
    }

    @Bean(name = "company")
    public Company company() {
        Company company = new Company();
        return company;
    }

    @Bean(name = "companyUnit")
    public CompanyUnit companyUnit() {
        CompanyUnit companyUnit = new CompanyUnit();
        return companyUnit;
    }

    @Bean(name = "oilStorage")
    public OilStorage oilStorage() {
        OilStorage oilStorage = new OilStorage();
        return oilStorage;
    }

    @Bean(name = "oilType")
    public OilType oilType() {
        OilType oilType = new OilType();
        return oilType;
    }

    @Bean(name = "bid")
    public Bid bid() {
        Bid bid = new Bid();
        return bid;
    }

    @Bean(name = "bidDetail")
    public BidDetail bidDetail() {
        BidDetail bidDetail = new BidDetail();
        return bidDetail;
    }

    @Bean(name = "role")
    public Role role() {
        Role role = new Role();
        return role;
    }

}