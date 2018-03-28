package bookstore.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.model.Book;
import bookstore.model.Comment;
import bookstore.model.User;
import bookstore.repository.CommentRepository;


@Service
public class CommentServiceImpl implements CommentService{
	


    @Autowired
    private CommentRepository commentRepository;

    
	
	@Override
    public void save(Comment comment, User user, Book book) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        comment.setContent(comment.getContent()); 
        comment.setPublishTime(timestamp);
		comment.setUser(user);
		comment.setBook(book);
        commentRepository.save(comment);
    }
	
}
