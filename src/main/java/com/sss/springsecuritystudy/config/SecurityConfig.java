package com.sss.springsecuritystudy.config;

import com.sss.springsecuritystudy.handle.MyAccessDeniedHandler;
import com.sss.springsecuritystudy.handle.MyAuthenticationFailureHandler;
import com.sss.springsecuritystudy.handle.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author lzb
 * create time: 2022/6/20
 * spring security 配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http.formLogin()
                // 当检测到为/login时则认做登录，必须与表单提交地址一致，以此执行UserDetailsServiceImpl
                .loginProcessingUrl("/login")
                // 自定义登录页面
                .loginPage("/login.html")
                // 自定义用户名参数（前端）
                .usernameParameter("username")
                // 自定义密码参数（前端）
                .passwordParameter("password")
                // 登录成功跳转main页面
                .successForwardUrl("/main")
//                .successHandler(new MyAuthenticationSuccessHandler("https://www.bing.com"))
//                .failureForwardUrl("/err");
                .failureHandler(new MyAuthenticationFailureHandler("/error.html"));

        http.authorizeRequests()
                // login.html不需要认证
                .antMatchers("/login.html", "/error.html").permitAll()
                // 正则匹配
                //.regexMatchers(".+[.]png").permitAll()
                //.regexMatchers(HttpMethod.POST,"/demo").permitAll()
                // 匹配前缀为sss的路径
                //.mvcMatchers("/demo").servletPath("/sss").permitAll()
                // 所有请求都必须被认证，必须登录之后才能访问
                // 权限足够成功跳转
                //.antMatchers("/main1.html").hasAuthority("admin")
                // 权限不足不能跳转
                //.antMatchers("/main1.html").hasAuthority("admins")
                // 批量授权
                //.antMatchers("/main1.html").hasAnyAuthority("admin", "admins")
                // 角色判断,默认有ROLE_开头的
//                .antMatchers("/main1.html").hasRole("abc")
                .anyRequest().authenticated();
//                .anyRequest().access("@myServiceImpl.hasPermission(request , authentication)");

        // 关闭跨域防护
        http.csrf().disable();

        http.exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);

        http.rememberMe()
                // 失效时间，单位秒
                .tokenValiditySeconds(60)
                // 自定义参数名称
                .rememberMeParameter("remember-me")
                // 自定义登录逻辑
                .userDetailsService(userDetailsService)
                // 持久登录
                .tokenRepository(persistentTokenRepository);

        http.logout()
                // 退出登录跳转页面
                .logoutSuccessUrl("/login.html");
    }

    @Bean
    public PasswordEncoder getPw(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository getPersistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 自动建表,第一次启动需要，第二次启动注释
        // jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
}
