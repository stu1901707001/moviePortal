package uni.fmi.moviePortal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uni.fmi.moviePortal.bean.MovieMakerBean;

@Repository
public interface MovieMakerRepo extends JpaRepository<MovieMakerBean, Integer>{

	MovieMakerBean findMovieMakerByNameAndPassword(String name, String password);
	
	MovieMakerBean findByName(String name);
}
 