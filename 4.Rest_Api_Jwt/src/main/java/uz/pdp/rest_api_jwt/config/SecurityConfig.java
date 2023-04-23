package uz.pdp.rest_api_jwt.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.pdp.rest_api_jwt.security.JwtFilter;
import uz.pdp.rest_api_jwt.service.MyAuthService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    MyAuthService myAuthService;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder
                                .userDetailsService(myAuthService);  // user b-n ishlovchi Class
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http
     .csrf().disable()
     .authorizeRequests()
     .antMatchers("/api/auth/login").permitAll()
     .anyRequest().authenticated();

    // Spring security dependency ning  UsernamePasswordAuthenticationFilter Class idan oldin jwtFilter ishlasin!!!
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    // SPRING SECURITYGA SESSIONGA USHLAB QOLMASLIGINI BUYURMOQDA(DEFAULT HOLATDA USHLAYDI).CHUNKI TOKEN ISHLATSAK HAR REQUESTDA TEKSHIRILSIN
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER); //YOKI NEVER ÖRNIDA STATELESS QÖLLASA BÖLADI
    }


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
       return  new BCryptPasswordEncoder();
   }


}
