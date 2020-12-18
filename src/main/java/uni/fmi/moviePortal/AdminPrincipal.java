package uni.fmi.moviePortal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import uni.fmi.moviePortal.bean.RoleBean;
import uni.fmi.moviePortal.bean.MovieMakerBean;

public class AdminPrincipal implements UserDetails{
	
	private MovieMakerBean movieMaker;
	private Set<GrantedAuthority> authorities;	

	public AdminPrincipal(MovieMakerBean movieMaker, Set<RoleBean> roles) {
		this.movieMaker = movieMaker;
		authorities = new HashSet<GrantedAuthority>();
		insertRoles(roles);
	}

	private void insertRoles(Set<RoleBean> roles) {
	
		for(RoleBean role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getCode()));
		}
		
		if(authorities.isEmpty()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); 
		}		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return movieMaker.getPassword();
	}

	@Override
	public String getUsername() {
		return movieMaker.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
