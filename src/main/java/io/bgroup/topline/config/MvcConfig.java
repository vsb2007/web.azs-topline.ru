package io.bgroup.topline.config;

import io.bgroup.topline.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

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

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(this.dataSource());
    }

    @Bean(name = "dbToplineWeb")
    public DbModel dbToplineWeb() {
        return new DbModel();
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

    @Bean(name = "carSections")
    public CarSections carSections() {
        CarSections carSections = new CarSections();
        return carSections;
    }

    @Bean(name = "driver")
    public Driver driver() {
        Driver driver = new Driver();
        return driver;
    }

    @Bean(name = "oilFarm")
    public OilFarm oilFarm() {
        OilFarm oilFarm = new OilFarm();
        return oilFarm;
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

}