package leon.college.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin").hasAuthority("ADMIN")
                .antMatchers("/student").hasAuthority("STUDENT")
                .antMatchers("/teacher").hasAuthority("TEACHER")
                .antMatchers("/init").permitAll()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .successHandler(new AuthSuccessHandlerImpl())
                .and()
                .logout().logoutUrl("/logout").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select username, password, enabled from persons where username=?")
                .authoritiesByUsernameQuery("select username, role from persons where username=?");
    }
}
