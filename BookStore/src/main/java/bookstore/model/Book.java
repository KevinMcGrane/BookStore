package bookstore.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Book {
private Long id;

private String title;

private String author;

private float price;

private String category;

private String image;

private List<Comment> comments;

private List<User> carts;

private User purchasedBy;

private int stockLevel;

private Purchase purchase;


@Id
@GeneratedValue(strategy = GenerationType.AUTO)
public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getAuthor() {
	return author;
}

public void setAuthor(String author) {
	this.author = author;
}

public float getPrice() {
	return price;
}

public void setPrice(float price) {
	this.price = price;
}

public String getCategory() {
	return category;
}

public void setCategory(String category) {
	this.category = category;
}

public String getImage() {
	return image;
}

public void setImage(String image) {
	this.image = image;
}
@OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
public List<Comment> getComments() {
	return comments;
}

public void setComments(List<Comment> comments) {
	this.comments = comments;
}

public int getStockLevel() {
	return stockLevel;
}

public void setStockLevel(int stockLevel) {
	this.stockLevel = stockLevel;
}

@ManyToMany(mappedBy="booksInCart", fetch = FetchType.EAGER)
public List<User> getCarts() {
	return carts;
}

public void setCarts(List<User> carts) {
	this.carts = carts;
}

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name="user_id")
public User getPurchasedBy() {
	return purchasedBy;
}

public void setPurchasedBy(User purchasedBy) {
	this.purchasedBy = purchasedBy;
}

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name="purchase_id")
public Purchase getPurchase() {
	return purchase;
}

public void setPurchase(Purchase purchase) {
	this.purchase = purchase;
}







}
