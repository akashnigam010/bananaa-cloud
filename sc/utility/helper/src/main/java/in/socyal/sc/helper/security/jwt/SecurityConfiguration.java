package in.socyal.sc.helper.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider());
		super.configure(auth);
	}

	@Bean
	public JwtAuthenticationFilter customJwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter customUsernamePasswordAuthenticationFilter = new JwtAuthenticationFilter();
		customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
		customUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(customSuccessHandler());
		return customUsernamePasswordAuthenticationFilter;
	}

	@Bean
	public JwtAuthenticationSuccessHandler customSuccessHandler() {
		JwtAuthenticationSuccessHandler customSuccessHandler = new JwtAuthenticationSuccessHandler();
		return customSuccessHandler;
	}

	@Bean
	public JwtAuthenticationProvider customAuthenticationProvider() {
		JwtAuthenticationProvider customAuthenticationProvider = new JwtAuthenticationProvider();
		return customAuthenticationProvider;
	}

	@Bean(name = "authenticationManager123")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		List<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
		authenticationProviderList.add(customAuthenticationProvider());
		AuthenticationManager authenticationManager = new ProviderManager(authenticationProviderList);
		return authenticationManager;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		RestAuthenticationEntryPoint entryPoint = new RestAuthenticationEntryPoint();
		http.authorizeRequests().antMatchers("/socyal/login/**", "/socyal/location/**").permitAll().anyRequest().authenticated().and()
				// .formLogin().loginPage("/login").permitAll().and()
				.exceptionHandling().authenticationEntryPoint(entryPoint).and().httpBasic()
				.authenticationEntryPoint(entryPoint).and().logout().permitAll().and().csrf().disable()
				.addFilterBefore(customJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	};

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/socyal/login/**", "/socyal/location/**"); // #3
	}
}