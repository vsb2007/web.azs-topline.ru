package io.bgroup.topline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan("io.bgroup.topline")
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/pages/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	/*@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//registry.addViewController("/home").setViewName("greeting");
		//registry.addViewController("/").setViewName("greeting");
		//registry.addViewController("/hello").setViewName("hello");
		//registry.addViewController("/login").setViewName("login");
	}*/

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/*
		    <mvc:resources mapping="/css/**" location="/WEB-INF/views/css/"/>
    <mvc:resources mapping="/gif/**" location="/WEB-INF/views/css"/>
    <mvc:resources mapping="/js/**" location="/WEB-INF/views/js/"/>
    <mvc:resources mapping="/demo-files/**" location="/WEB-INF/views/demo-files/"/>
    <mvc:resources mapping="/fonts/**" location="/WEB-INF/views/fonts/"/>
    <mvc:resources mapping="/images/*" location="WEB-INF/views/images/"/>

		 */
		registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/pages/css/").setCachePeriod(31556926);
		registry.addResourceHandler("/img/**").addResourceLocations("/WEB-INF/pages/images/").setCachePeriod(31556926);
		registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/pages/images/").setCachePeriod(31556926);
		registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/pages/js/").setCachePeriod(31556926);
		registry.addResourceHandler("/gif/**").addResourceLocations("/WEB-INF/pages/css/").setCachePeriod(31556926);
		registry.addResourceHandler("/demo-files/**").addResourceLocations("/WEB-INF/pages/demo-files/").setCachePeriod(31556926);
		registry.addResourceHandler("/fonts/**").addResourceLocations("/WEB-INF/pages/fonts/").setCachePeriod(31556926);
	}


    @Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
	    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	    driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    driverManagerDataSource.setUrl("jdbc:mysql://192.168.19.43:3306/toplineweb");
	    driverManagerDataSource.setUsername("toplinewebuser");
	    driverManagerDataSource.setPassword("toplinewebpassword");
	    return driverManagerDataSource;
	}

}