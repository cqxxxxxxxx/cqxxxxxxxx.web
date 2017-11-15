package com.cqxxxxxxxx.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

/**
 * Created by BG307435 on 2017/11/15.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    public void configureGlobal(
            AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER"));

//        auth.jdbcAuthentication() //配置从数据库获取用户信息认证
//        auth.ldapAuthentication() //配置ldap上的认证
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/css/**", "/index").permitAll()
                    .antMatchers("/user/**").hasRole("USER")
                    .and()
                .formLogin().loginPage("/login").failureUrl("/login-error");
    }


//     **
//          * userDetailService用于获取正确的用户信息用于提供其他模块认证
//          * 这里是内存里设置了2个用户
//          * 可以是通过集成UserDetailService重写loadUserByUsername，返回数据库里的UserDetails
//          * @return
//          * @throws Exception
//          */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        User.UserBuilder builder = User.withDefaultPasswordEncoder();
//        UserDetails user = builder.username("user").password("password").roles("USER").build();
//        UserDetails admin = builder.username("admin").password("password").roles("USER", "ADMIN").build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}
