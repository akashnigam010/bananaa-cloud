package in.socyal.sc.helper.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Configuration
    @Order(2)
    public static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().withUser("user").password("password").roles("ADMIN");
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.antMatchers("/", "/index.html","/login/skipLogin", "/login/fbLogin").permitAll()
				.antMatchers("/manage/login").permitAll()
				.antMatchers("/manage/**").hasAnyRole("ADMIN")
				.and()
					.formLogin()
						.loginPage("/manage/login").permitAll()
				.and()
					.csrf().disable();
		};
    }
	
	@Configuration
    @Order(1)
    public static class ApiConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Autowired JwtAuthenticationProvider jwtAuthenticationProvider;
		@Autowired JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
		@Override
		protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().withUser("user").password("password").roles("ADMIN");
			auth.authenticationProvider(jwtAuthenticationProvider);
		}

        @Override
        protected void configure(HttpSecurity http) throws Exception {
        	RestAuthenticationEntryPoint entryPoint = new RestAuthenticationEntryPoint();
            http
            	.authorizeRequests().antMatchers("/", "/index.html", "/socyal/login/fbLogin", "/socyal/location/**", "/socyal/merchant/**").permitAll()
            	.and().authorizeRequests().antMatchers("/manage").hasAnyAuthority("ROLE_ADMIN")
            	.and()
            	.authorizeRequests()
            		.antMatchers("/socyal/checkin/**").hasAuthority("USER").and()
            			.addFilterBefore(customJwtAuthenticationFilter("/socyal/user/**"), AbstractPreAuthenticatedProcessingFilter.class)
            	.authorizeRequests()
                	.antMatchers("/socyal/user/**").authenticated().and()
                			.addFilterBefore(customJwtAuthenticationFilter("/socyal/user/**"), AbstractPreAuthenticatedProcessingFilter.class)
				.formLogin()
					.loginPage("/manage/login").permitAll().and()
    			.csrf().disable();
        }
        
        public JwtAuthenticationFilter customJwtAuthenticationFilter(String defaultProcessingUrl) throws Exception {
    		JwtAuthenticationFilter customUsernamePasswordAuthenticationFilter = new JwtAuthenticationFilter(defaultProcessingUrl);
    		customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
    		customUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
    		return customUsernamePasswordAuthenticationFilter;
    	}
    }
}