package uni.fmi.moviePortal.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.servlet.ModelAndView;

import uni.fmi.moviePortal.bean.GenreBean;
import uni.fmi.moviePortal.bean.MovieBean;
import uni.fmi.moviePortal.bean.MovieMakerBean;
import uni.fmi.moviePortal.repo.GenreRepo;
import uni.fmi.moviePortal.repo.MovieRepo;

@RestController
public class MovieController {

	
	MovieRepo movieRepo;
	 
	GenreRepo genreRepo;
	public MovieController(MovieRepo movieRepo, GenreRepo genreRepo) {
		this.movieRepo = movieRepo;
		this.genreRepo = genreRepo;
	}
	
	@RequestMapping(value = "/movie/save", method = RequestMethod.POST)	 
	public ResponseEntity<MovieBean> saveMovie(
			@RequestParam(value = "id", required = false)int id,
			@RequestParam(value = "title")String title,
			@RequestParam(value = "releaseDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date releaseDate,			
			@RequestParam(value = "image", required = false)String image,	
			@RequestParam(value = "overview", required = false)String overview,	
			@RequestParam(value = "popularity", defaultValue = "0")double popularity,
			@RequestParam(value = "genre_id")int genre_id,
			@RequestParam(value = "vote_average", defaultValue = "0")double vote_average,
			HttpSession session 
			) {
		MovieMakerBean mm = (MovieMakerBean)session.getAttribute("movieMaker");
		Optional<GenreBean> genre = genre_id > 0? genreRepo.findById(genre_id) : null;
		//Date date1;
		
		try {
			//date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		 		
			if(mm != null) {			
				MovieBean movieBean;
				if(id > 0) {
					Optional<MovieBean >movieBean_ = movieRepo.findById(id);
					if(movieBean_.isPresent()) movieBean = movieBean_.get();
					else {					
						return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
					}
				} else {
					movieBean = new MovieBean();
				}
				
				movieBean.setMovie_maker(mm);
				movieBean.setTitle(title);
				movieBean.setPoster_path(image);
				movieBean.setOverview(overview);
				movieBean.setOverview(overview);
				movieBean.setPopularity(popularity);
				movieBean.setVote_average(vote_average);
				if(releaseDate != null)movieBean.setReleasedate(releaseDate.getTime());
				if(genre.isPresent())movieBean.setGenre(genre.get());
				
				movieBean.setPopularity(popularity);				
				movieBean = movieRepo.saveAndFlush(movieBean);
				return new ResponseEntity<>(movieBean, HttpStatus.OK);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	
		
	}
	
	@GetMapping(path = "/movie/all")
	public List<MovieBean> getAllMovies(){
		return movieRepo.findAll();
	}
	
	@GetMapping(path = "/movie/get")
	public ResponseEntity<MovieBean> getMovieById(
		@RequestParam(value = "id")int id,
		HttpSession session
		){		
	 
		MovieMakerBean mm = (MovieMakerBean)session.getAttribute("movieMaker");
		
		if(mm == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		Optional<MovieBean> optionalMovie = movieRepo.findById(id);
	
		if(optionalMovie.isPresent()) {
			return new ResponseEntity<>(optionalMovie.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	/*
	@GetMapping(path = "/movie/filter")
	public List<MovieBean> getAllMovies(
			@RequestParam(value = "year", defaultValue = "0")int year,
			@RequestParam(value = "genreId", defaultValue = "0")int genreId){
		Date date1;
		
		try {
			date1 = new SimpleDateFormat("yyyy/dd/MM").parse(year + "/01/01" );
		} catch (ParseException e) {
			return null;
		}  
		
		if(genreId > 0) {
		return movieRepo.findByGenreIdAndReleasedateGreaterThanOrderByReleasedateDesc(genreId,
				  date1.getTime());
		}
		return movieRepo.findByReleasedateGreaterThanOrderByReleasedateDesc(date1.getTime());
	} */
	
	@GetMapping(path = "/movie/filter")
	public List<MovieBean> getAllMovies(
			@RequestParam(value = "startReleaseDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startReleaseDate,			
			@RequestParam(value = "endReleaseDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endReleaseDate, 
			@RequestParam(value = "genreId", defaultValue = "0")int genreId){
		 
		if(startReleaseDate == null && endReleaseDate == null && genreId == 0) {
			return movieRepo.findAll();
		} else if(genreId > 0) {		
			
			if(startReleaseDate == null && endReleaseDate == null) {
				return movieRepo.findByGenreId(genreId);
			} else if(startReleaseDate == null) {
				return movieRepo.findByGenreIdAndReleasedateLessThanOrderByReleasedate(genreId,endReleaseDate.getTime());
			} else if(endReleaseDate == null) {
				return movieRepo.findByGenreIdAndReleasedateGreaterThanOrderByReleasedateDesc(genreId, startReleaseDate.getTime());
			} 
			
			return	movieRepo.findByGenreAndReleasedateBetween(genreId, startReleaseDate.getTime(), endReleaseDate.getTime());
		} else if(startReleaseDate == null) {
			return movieRepo.findByReleasedateLessThan(endReleaseDate.getTime());
		} else if(endReleaseDate == null) {
			return movieRepo.findByReleasedateGreatrThan(startReleaseDate.getTime());
		}
		
		return movieRepo.findByReleasedateBetween(startReleaseDate.getTime(), endReleaseDate.getTime());
	}
	
	@DeleteMapping(path = "/movie/delete")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Boolean> deleteMovie(
			@RequestParam(value = "id")int id,
			HttpSession session
			){		
		 
		MovieMakerBean mm = (MovieMakerBean)session.getAttribute("movieMaker");
		
		if(mm == null) {
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
		}
		
		Optional<MovieBean> optionalMovie = movieRepo.findById(id);
		
		if(optionalMovie.isPresent()) {
			MovieBean movie = optionalMovie.get();
			
			if(movie.getMovie_maker().getId() == mm.getId())	{
				movieRepo.delete(movie);				
				return new ResponseEntity<>(true, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
			}			
			
		}else {
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}
		
	}
}
