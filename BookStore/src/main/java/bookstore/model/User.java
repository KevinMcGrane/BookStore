package bookstore.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import bookstore.model.Role;

@Entity
@Table(name = "user")
public class User {
	private Long id;
	private boolean admin = false;
	private String username;
	private String password;
	private String passwordConfirm;
	private Set<Role> roles;
	private List<Comment> comments;
	private String address;
	private String creditNum;
	private String name;
	private List<Book> booksInCart;
	private List<Book> purchased;
	private List<Purchase> purchases;
	


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreditNum() {
		return creditNum;
	}

	public void setCreditNum(String creditNum) {
		this.creditNum = creditNum;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_cart", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
	public List<Book> getBooksInCart() {
		return booksInCart;
	}

	public void setBooksInCart(List<Book> booksInCart) {
		this.booksInCart = booksInCart;
	}

	@OneToMany(mappedBy = "purchasedBy", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	public List<Book> getPurchased() {
		return purchased;
	}

	public void setPurchased(List<Book> purchased) {
		this.purchased = purchased;
	}

	public String toString() {
		return "User [id=" + id + ", name = " + username + ", password= " + password + ", password confirmed "
				+ passwordConfirm + "]";
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	public List<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}
	
	

}
