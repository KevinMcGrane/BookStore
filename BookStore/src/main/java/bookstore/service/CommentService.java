package bookstore.service;

import bookstore.model.Book;
import bookstore.model.Comment;
import bookstore.model.Customer;

public interface CommentService {
	
	 void save(Comment comment, Customer customer, Book book);


}
