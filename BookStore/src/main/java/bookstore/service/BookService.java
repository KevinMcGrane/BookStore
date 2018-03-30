package bookstore.service;

import java.util.List;


import bookstore.model.Book;
import bookstore.model.User;

public interface BookService {
	List<Book> findAll();
	void save(Book book);
	Book findById(Long id);
	void addBookToCart(Book book, User user);

}
