package uni.fmi.moviePortal.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import uni.fmi.moviePortal.WebSecurityConfig;
import uni.fmi.moviePortal.bean.MovieMakerBean;
import uni.fmi.moviePortal.repo.MovieMakerRepo;

@RestController
public class LoginController {

	private MovieMakerRepo movieMakerRepo;
	private WebSecurityConfig webSecurityConfig;
	
	public LoginController(MovieMakerRepo movieMakerRepo, WebSecurityConfig webSecurityConfig) {
		this.movieMakerRepo = movieMakerRepo;
		this.webSecurityConfig = webSecurityConfig;
	}
	
	
	@PostMapping(path = "/register")
	public ModelAndView register( 
			@RequestParam(value ="name")String movieMakername,
			@RequestParam(value ="password")String password,
			@RequestParam(value ="repeatPassword")String repeatPassword, HttpSession session ) {
		
		if(password.equals(repeatPassword)) {
			
			MovieMakerBean movieMaker = movieMakerRepo.saveAndFlush(new MovieMakerBean(movieMakername, hashPassword(password)));
			//session.setAttribute("movieMaker", movieMaker); 
			if(movieMaker != null)return new ModelAndView("redirect:index.html");	
		} 
		
		return new ModelAndView("redirect:error.html");	
	}
	
	@PostMapping(path = "/login")
	public ModelAndView login(
			@RequestParam(value = "name")
			String name, 
			@RequestParam(value = "password")
			String password, HttpSession session) {
		
		MovieMakerBean movieMaker = movieMakerRepo.findMovieMakerByNameAndPassword(name, hashPassword(password));
		
		if(movieMaker != null) {
			session.setAttribute("movieMaker", movieMaker);
			
			try {
				UserDetails movieMakerDetails = 
						webSecurityConfig.userDetailsServiceBean().
						loadUserByUsername(movieMaker.getName());
				
				if(movieMakerDetails != null) {
					Authentication auth = new UsernamePasswordAuthenticationToken(
							movieMakerDetails.getUsername(),
							movieMakerDetails.getPassword(),
							movieMakerDetails.getAuthorities());
					
					SecurityContextHolder.getContext().setAuthentication(auth);
					
					ServletRequestAttributes attr = (ServletRequestAttributes)
							RequestContextHolder.currentRequestAttributes();
					
					HttpSession http = attr.getRequest().getSession(true);
					http.setAttribute("SPRING_SECURITY_CONTEXT", 
							SecurityContextHolder.getContext());
				}
				
				return new ModelAndView("redirect:home.html");	
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		return new ModelAndView("redirect:error.html");	
				
	}
	
	@GetMapping(path = "/whoAmI")
	public ResponseEntity<Integer> whoAmI(HttpSession session){
		
		MovieMakerBean movieMaker = (MovieMakerBean)session.getAttribute("movieMaker");
		
		if(movieMaker != null) {
			return new ResponseEntity<>(movieMaker.getId(), HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>(0, HttpStatus.UNAUTHORIZED);
		}		
	}
	
	@PostMapping(path = "/logout")
	public ResponseEntity<Boolean> logout(HttpSession session){
		
		MovieMakerBean movieMaker = (MovieMakerBean) session.getAttribute("movieMaker");
		
		if(movieMaker != null ) {
			session.invalidate();
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
	}
	
	
private String hashPassword(String password) {
		
		StringBuilder result = new StringBuilder();
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			md.update(password.getBytes());
			
			byte[] bytes = md.digest();
			
			for(int i = 0; i < bytes.length; i++) {
				result.append((char)bytes[i]);
			}			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}		
	
		return result.toString();
	}
}
