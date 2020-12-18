package uni.fmi.moviePortal;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import uni.fmi.moviePortal.bean.RoleBean;
import uni.fmi.moviePortal.bean.MovieMakerBean;
import uni.fmi.moviePortal.repo.MovieMakerRepo;

@Service
public class ApplicationAdminDetailService implements UserDetailsService{
	
	private MovieMakerRepo movieMakerRepo;
	
	public ApplicationAdminDetailService(MovieMakerRepo movieMakerRepo) {
		this.movieMakerRepo = movieMakerRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		
		MovieMakerBean movieMaker = movieMakerRepo.findByName(name);
		
		if(movieMaker == null)
			throw new UsernameNotFoundException(name);
		
		Set<RoleBean> roles = movieMaker.getRoles();
		
		return new AdminPrincipal(movieMaker, roles);		
	}

}
