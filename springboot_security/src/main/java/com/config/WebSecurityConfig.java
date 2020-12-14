package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author gg
 * @create 2020-12-14 下午4:44
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定制请求的授权规则
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        //开启自动配置的登陆功能，默认post形式的 @{/login} 为处理登陆请求
        http.formLogin()
                .loginPage("/userlogin")
                //下面两项分别默认为username和password，需设置在登陆表单中
                .usernameParameter("name")
                .passwordParameter("pwd");

        //开启自动配置的注销功能，默认返回登陆页面
        http.logout()
                .logoutSuccessUrl("/");

        //开启"记住我"功能，默认是14天内免登录（登陆成功后，将cookie发给浏览器），点击注销后删除cookie
        http.rememberMe().rememberMeParameter("remember");
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.withDefaultPasswordEncoder()
                .username("zhangsan")
                .password("123456")
                .roles("VIP1", "VIP2")
                .build();
        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username("lisi")
                .password("123456")
                .roles("VIP1", "VIP2", "VIP3")
                .build();

        return new InMemoryUserDetailsManager(user1,user2);
    }
}
