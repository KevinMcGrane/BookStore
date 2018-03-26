package bookstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import bookstore.model.Book;

public interface BookService {
	List<Book> findAll();
	void save(Book book);

}
