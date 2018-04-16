package bookstore.service;

import java.util.List;

import bookstore.model.Book;
import bookstore.model.Purchase;
import bookstore.model.User;

public interface PurchaseService {

	void save(Purchase purchase);

	List<Purchase> findByUser(User user);

	Purchase findById(Long id);

	List<Purchase> findAll();


}
