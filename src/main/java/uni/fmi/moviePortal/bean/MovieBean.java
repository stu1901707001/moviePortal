package uni.fmi.moviePortal.bean;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn; 
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movie")
public class MovieBean implements Serializable {	
	 
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "title", length = 250, nullable = false)
	private String title;
	
	@Column(name = "overview", length = 1000, nullable = true)
	private String overview;
	
	@Column(name = "backdrop_path" , length = 256, nullable = true)
	private String backdrop_path;
	
	@Column(name = "poster_path" , length = 256, nullable = true)
	private String poster_path;
	
	@Column(name = "releasedate", nullable = true)
	private Long releasedate;	
	
	@Column(name = "original_language", length = 1000, nullable = true)
	private String original_language;
	
	@Column(name = "popularity", length = 5, precision = 2, nullable = true)
	private double popularity;
	
	@Column(name = "vote_average", length = 5, precision = 2, nullable = true)
	private double vote_average;	
	
	@Column(name = "vote_count", nullable = true)
	private int vote_count;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "genreid")
	private GenreBean genre;

	/*@ManyToMany
	@JoinTable(name = "genre",
	joinColumns=@JoinColumn(name="genre_id"),
	inverseJoinColumns=@JoinColumn(name="id"))
	private Set<GenreBean> genres;
	
	public Set<GenreBean> getGenres() {
		return genres;
	}

	public void setGenres(Set<GenreBean> genres) {
		this.genres = genres;
	}
	*/
	 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "movie_maker_id")
	private MovieMakerBean movie_maker;

	public int getId() {
		return id;
	}  
	
	public double getVoteAverage() {
		return vote_average;
	}

	public void setTemp(double vote) {
		this.vote_average = vote;
	}

	public String getBackdrop_path() {
		return backdrop_path;
	}

	public void setBackdrop_path(String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}

	public Long getReleasedate() {
		return releasedate;
	}

	public void setReleasedate(Long releasedate) {
		this.releasedate = releasedate;
	}

	public String getOriginal_language() {
		return original_language;
	}

	public void setOriginal_language(String original_language) {
		this.original_language = original_language;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public double getVote_average() {
		return vote_average;
	}

	public void setVote_average(double vote_average) {
		this.vote_average = vote_average;
	}

	public int getVote_count() {
		return vote_count;
	}

	public void setVote_count(int vote_count) {
		this.vote_count = vote_count;
	}	 

	public MovieMakerBean getMovie_maker() {
		return movie_maker;
	}

	public void setMovie_maker(MovieMakerBean movie_maker) {
		this.movie_maker = movie_maker;
	} 
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOverview() {
		return overview;
	}

	public GenreBean getGenre() {
		return genre;
	}

	public void setGenre(GenreBean genre) {
		this.genre = genre;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getPosterPath() {
		return poster_path;
	}

	public void setPosterPath(String path) {
		this.poster_path = path;
	}
}
