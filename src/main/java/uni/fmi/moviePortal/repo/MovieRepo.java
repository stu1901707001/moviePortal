package uni.fmi.moviePortal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uni.fmi.moviePortal.bean.MovieBean;

@Repository
public interface MovieRepo extends JpaRepository<MovieBean, Integer>{
	List<MovieBean> findByGenreIdAndReleasedateGreaterThanOrderByReleasedateDesc(int genreid, Long releasedate);
	List<MovieBean> findByReleasedateGreaterThanOrderByReleasedateDesc(Long releasedate);
}
