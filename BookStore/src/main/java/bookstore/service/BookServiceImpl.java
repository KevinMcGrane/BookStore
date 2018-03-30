package bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.model.Book;
import bookstore.model.User;
import bookstore.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	BookRepository bookRepository;
	@Autowired
	UserService userService;
	@Override
    public void save(Book book) {
    	 book.setAuthor(book.getAuthor());
    	 book.setCategory(book.getCategory());
    	 book.setPrice(book.getPrice());
    	 book.setComments(book.getComments());
    	 book.setStockLevel(book.getStockLevel());
    	 book.setCarts(book.getCarts());
    	 book.setPurchasedBy(book.getPurchasedBy());
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
	
	@Override
	public void addBookToCart(Book book, User user) {
		book.getCarts().add(user);
		user.getBooksInCart().add(book);
		save(book);
		userService.update(user, user);
		
	}
	

}
