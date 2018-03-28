package bookstore.model;

import java.sql.Time;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {

	
	private Long commentId;



	private String content;
	
	private Date publishTime;
	
	private Customer customer;
	
	private Book book;

	
	public Comment(){
		
	}
	
	public Comment(String content, Date publishTime, Customer customer) {
		super();
		this.content = content;
		this.publishTime = publishTime;
		this.customer = customer;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getCommentId() {
		return commentId;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="customer_id")
	public Customer getCustomer() {
		return customer;
	}
	
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="book_id")
	public Book getBook() {
		return book;
	}
	
	
	public void setBook(Book book) {
		this.book = book;
	}
	
	
	 
	


	
}
