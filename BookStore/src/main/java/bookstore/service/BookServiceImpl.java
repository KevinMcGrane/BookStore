package bookstore.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.model.Book;
import bookstore.model.Role;
import bookstore.model.User;
import bookstore.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	BookRepository bookRepository;
	@Override
    public void save(Book book) {
    	 book.setAuthor(book.getAuthor());
    	 book.setCategory(book.getCategory());
    	 book.setPrice(book.getPrice());
    	 book.setComments(book.getComments());
    	 book.setStockLevel(book.getStockLevel());
        bookRepository.save(book);
    }
	
	@Override
	public List<Book> findAll(){
		return bookRepository.findAll();
	}
	
	@Override
	public Book findById(Long id) {
		return bookRepository.findOne(id);
	}

}
