package bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.model.Purchase;
import bookstore.model.User;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

	List<Purchase> findByUser(User user);
	
	Purchase findById(Long id);
	
}
