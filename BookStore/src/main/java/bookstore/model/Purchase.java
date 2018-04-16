package bookstore.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Purchase {

	private Long id;
	private User user;
	private List<Book> book;
  private Date date;
  
  @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name="user_id")
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
@OneToMany(mappedBy = "purchase", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
public List<Book> getBook() {
	return book;
}
public void setBook(List<Book> book) {
	this.book = book;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
  
  
}
