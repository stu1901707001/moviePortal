

package uni.fmi.moviePortal.bean;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "movie_maker")
@JsonIgnoreProperties({"movies"})
public class MovieMakerBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="name", nullable = false, unique = true, length = 40)
	private String name;
	
	@Column(name="password", nullable = false, length = 32)
	private String password;
		 
	@Column(name="image")
	private String imagePath;
	
	@OneToMany(mappedBy = "movie_maker", fetch = FetchType.EAGER)
	private List<MovieBean> movies;
	
	@ManyToMany
	@JoinTable(name = "account_role",
	joinColumns=@JoinColumn(name="account_id"),
	inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<RoleBean> roles;
	
	public Set<RoleBean> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleBean> roles) {
		this.roles = roles;
	}

	public MovieMakerBean() {}
	
	public MovieMakerBean(String name) {
		this.name = name; 
	}
	
	public MovieMakerBean(String name, String password) {
		this.name = name;
		this.password = password;
		}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<MovieBean> getMovies() {
		return movies;
	}

	public void setMovies(List<MovieBean> movies) {
		this.movies = movies;
	}	

}

