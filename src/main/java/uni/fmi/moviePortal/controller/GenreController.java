package uni.fmi.moviePortal.controller;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import uni.fmi.moviePortal.bean.GenreBean;
import uni.fmi.moviePortal.bean.MovieBean;
import uni.fmi.moviePortal.bean.MovieMakerBean;
import uni.fmi.moviePortal.repo.GenreRepo; 

@RestController
public class GenreController {
	
	GenreRepo genreRepo;
	 
	public GenreController(GenreRepo genreRepo) {
		this.genreRepo = genreRepo;		
	}
		 
	@PostMapping(path = "/genre/save")	
	public ResponseEntity<GenreBean> saveGenre(
			@RequestParam(value = "id")int id,
			@RequestParam(value = "name")String name,
			@RequestParam(value = "description", required = false)String description 
			) {
		
		GenreBean genreBean;	
		try {
		if(id > 0) {		
			Optional<GenreBean >genreBean_ = genreRepo.findById(id);			
			if(genreBean_.isPresent()) genreBean = genreBean_.get();			
			else {								
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);		
			}
			
		} else {
			genreBean = new GenreBean();			
		}			
		
		genreBean.setDescription(description);		
		genreBean.setName(name);	
		genreBean = genreRepo.saveAndFlush(genreBean);			
		
		if(genreBean != null) {		
			return new ResponseEntity<>(genreBean, HttpStatus.OK);			
		}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping(path = "/genre/all")
	public List<GenreBean> getAllGenres(){
		return genreRepo.findAll();
	}
	
	@GetMapping(path = "/genre/get")
	public ResponseEntity<GenreBean> getGenreById(
		@RequestParam(value = "id")int id,
		HttpSession session
		){		
		Optional<GenreBean> optionalGenre = genreRepo.findById(id);
	
		if(optionalGenre.isPresent()) {
			return new ResponseEntity<>(optionalGenre.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping(path = "/genre/delete")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Boolean> deleteGenre(
			@RequestParam(value = "id")int id){
		 
		Optional<GenreBean> optionalGenre = genreRepo.findById(id);
		
		if(optionalGenre.isPresent()) {
			GenreBean genre = optionalGenre.get();					
			try { 
				genreRepo.delete(genre);
				return new ResponseEntity<>(true, HttpStatus.OK);	
			} catch (Exception e) {
				e.printStackTrace();				
			}		
			
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}
		
	}
	
}
