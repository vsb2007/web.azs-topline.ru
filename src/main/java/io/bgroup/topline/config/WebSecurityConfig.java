package io.bgroup.topline.config;

import io.bgroup.topline.model.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

/**
 * Created by VSB on 29.02.2016.
 * ToplineWeb.2.1
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    DataSource mDataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder())
                .dataSource(mDataSource).usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username,role FROM user_roles WHERE username=?");
    }

/*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("superadmin").password("superadmin").roles("SUPERADMIN");
    }
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
    return bCryptPasswordEncoder;
}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/index").permitAll()
                .antMatchers("/").permitAll()
                .and().formLogin().defaultSuccessUrl("/",true)
                .and().logout().logoutUrl("/index")
                .and().logout().logoutSuccessUrl("/index")

        ;

        /*http
                .authorizeRequests()
               .antMatchers("/protected/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/confidential/**").access("hasRole('ROLE_SUPERADMIN')")
                //.and().formLogin().defaultSuccessUrl("/", false);
                .and().formLogin().defaultSuccessUrl("/",true);
*/
/*
          .antMatchers("/protected/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/confidential/**").access("hasRole('ROLE_SUPERADMIN')")
                .antMatchers("/links/**").access("hasRole('ROLE_LOGIN')")
                //.and().formLogin().defaultSuccessUrl("/", false);
                .and().formLogin()
                .loginPage("/index")
                //.loginProcessingUrl("/login.do")
                .defaultSuccessUrl("/")
                .failureUrl("/index?err=1")
                .usernameParameter("username")
                .passwordParameter("password")

                .and()
                .logout()
                .logoutRequestMatcher( new AntPathRequestMatcher( "/logout" ) )
                .logoutSuccessUrl( "/index" )
                .deleteCookies( "JSESSIONID" )
                .invalidateHttpSession( true )
                .and()
                .sessionManagement()
                .invalidSessionUrl( "/index" )
                .maximumSessions( 1 )
                //.and()
                //.csrf().disable();
        ;
        */
/*        http
                .authorizeRequests()
                .antMatchers("/index").anonymous()
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin()
                .loginPage("/index")
                .defaultSuccessUrl("/")
                .and()
                .logout();
*/


    }

}