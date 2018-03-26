package bookstore.model;

import java.awt.Image;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.mysql.jdbc.Blob;

@Entity
public class Book {
private long id;

private String title;

private String author;

private float price;

private String category;

private String image;

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
public long getId() {
	return id;
}

public void setId(long id) {
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

}
