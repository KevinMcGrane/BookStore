package bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.model.Book;
import bookstore.model.Purchase;

public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByPurchase(Purchase purchase);
}
