package uni.fmi.moviePortal.repo;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import uni.fmi.moviePortal.bean.MovieBean;

@Repository
public interface MovieRepo extends JpaRepository<MovieBean, Integer>{
	
	List<MovieBean> findByGenreIdAndReleasedateGreaterThanOrderByReleasedateDesc(int genreid, Long releasedate);
	
	List<MovieBean> findByGenreIdAndReleasedateLessThanOrderByReleasedate(int genreid, Long releasedate);	 
	
	//List<MovieBean> findByReleasedateGreaterThan(Long releasedate);
	
	@Query(value= "select * from Movie m where m.genreid = :genreid", nativeQuery = true)
	List<MovieBean> findByGenreId(@Param("genreid")int genreid);
	
	//@Modifying
    //@Transactional
    @Query(value= "select * from Movie m where m.releasedate >= :from and m.releasedate <= :to ", nativeQuery = true)
    List<MovieBean> findByReleasedateBetween(@Param("from") long from, @Param("to") long to);
    
    @Query(value = "select * from Movie m where m.genreid = :genreid and m.releasedate >= :from and m.releasedate <= :to ", nativeQuery = true)
    List<MovieBean> findByGenreAndReleasedateBetween(@Param("genreid")int genreid, @Param("from") long from, @Param("to") long to);
    
    @Query(value = "select * from Movie m where m.releasedate >= :from", nativeQuery = true)
    List<MovieBean> findByReleasedateGreatrThan(@Param("from") long from);
    
    @Query(value = "select * from Movie m where m.releasedate <= :to ", nativeQuery = true)
    List<MovieBean> findByReleasedateLessThan(@Param("to") long to);
    
}
