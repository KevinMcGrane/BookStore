package bookstore.service;

import java.util.List;


import bookstore.model.Book;

public interface BookService {
	List<Book> findAll();
	void save(Book book);
	Book findById(Long id);

}
