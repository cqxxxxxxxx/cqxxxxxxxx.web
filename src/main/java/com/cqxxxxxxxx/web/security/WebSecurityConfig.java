package com.cqxxxxxxxx.web.security;

import com.cqxxxxxxxx.web.dao.UserRepository;
import com.cqxxxxxxxx.web.model.Authorities;
import com.cqxxxxxxxx.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by BG307435 on 2017/11/15.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    @Autowired
    UserRepository userRepository;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        });
        daoAuthenticationProvider.setUserDetailsService(new UserDetailServiceJPA());
        auth.authenticationProvider(daoAuthenticationProvider);
        //        auth.inMemoryAuthentication() //内存里存储user进行认证
//        auth.ldapAuthentication() //ldap上的认证
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


    /**
     * UserDetail
     */
    private class UserDetailServiceJPA implements UserDetailsService{

        @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            Optional<User> optional = userRepository.findByUsername(s);

            if (!optional.isPresent()) {
                throw new IllegalArgumentException("用户不存在");
            }
            User user = optional.get();

//          权限
            List<String> roles = user.getAuthorities().stream()
                    .map(Authorities::getAuthority)
                    .collect(Collectors.toList());
            Set<GrantedAuthority> grantedAuthoritySet = roles.stream()
                    .map(x -> new SimpleGrantedAuthority(x))
                    .collect(Collectors.toSet());

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .accountExpired(false)
                    .accountLocked(false)
                    .authorities(grantedAuthoritySet)
                    .build();
        }
    }
}
