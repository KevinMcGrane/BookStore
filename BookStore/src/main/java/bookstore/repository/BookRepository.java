package bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookstore.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	
}
