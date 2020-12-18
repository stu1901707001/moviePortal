package uni.fmi.moviePortal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uni.fmi.moviePortal.bean.GenreBean;

@Repository
public interface GenreRepo extends JpaRepository<GenreBean, Integer>{

}
