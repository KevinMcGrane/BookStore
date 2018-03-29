package bookstore.service;

import java.util.List;

import bookstore.model.Book;
import bookstore.model.Comment;
import bookstore.model.User;

public interface CommentService {
	
	 void save(Comment comment, User user, Book book);

	List<Comment> findByBook(Book book);


}
