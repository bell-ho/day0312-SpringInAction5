package tacos.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/design", "/orders").access("hasRole('ROLE_USER')")
				.antMatchers("/", "/**").access("permitAll")
				.and().formLogin().loginPage("/login")
				.and().logout().logoutSuccessUrl("/")
				.and().csrf();
		
	}
	
	@Autowired
	DataSource dataSource;

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}
	
	
	//LDAP 사용
	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth .ldapAuthentication() .userSearchBase("ou=people")
	 * .userSearchFilter("(uid={0})") .groupSearchBase("ou=groups")
	 * .groupSearchFilter("member={0}") .contextSource()
	 * .root("dc=tacocloud,dc=com") .ldif("classpath:users.ldif") .and()
	 * .passwordCompare() .passwordEncoder(new BCryptPasswordEncoder())
	 * .passwordAttribute("userPasscode"); }
	 */
	
	//JDBC 기반의 사용자 스토어
	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.jdbcAuthentication().dataSource(dataSource)
	 * .usersByUsernameQuery("select username , password , enabled from users where username=?"
	 * )
	 * .authoritiesByUsernameQuery("select username , authority from authorities where username=?"
	 * ) .passwordEncoder(new NoEncodingPasswordEncoder()); }
	 * 
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * http.authorizeRequests() .antMatchers("/design",
	 * "/orders").access("hasRole('ROLE_USER')") .antMatchers("/",
	 * "/**").access("permitAll") .and().httpBasic(); }
	 */
	//####인메모리 사용자 스토어####
	//변경이 필요없는 사용자만 미리 정해 놓고 애플리케이션을 사용할때
	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * auth.inMemoryAuthentication().withUser("u1").password("{noop}p1").authorities
	 * ("ROLE_USER") .and()
	 * .withUser("u2").password("{noop}p2").authorities("ROLE_USER"); }
	 * 
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * http.authorizeRequests()
	 * .antMatchers("/design","/orders").access("hasRole('ROLE_USER')")
	 * .antMatchers("/","/**").access("permitAll") .and().httpBasic(); }
	 */
	
}
