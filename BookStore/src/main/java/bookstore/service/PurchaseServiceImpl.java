package bookstore.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.model.Purchase;
import bookstore.model.User;
import bookstore.repository.PurchaseRepository;

@Service
public class PurchaseServiceImpl implements PurchaseService{

	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Override
	public void save(Purchase purchase) {
		purchase.setUser(purchase.getUser());
		purchase.setBook(purchase.getBook());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		purchase.setDate(timestamp);
		purchaseRepository.save(purchase);
	}
	
	@Override
	public List<Purchase> findByUser(User user){
		return purchaseRepository.findByUser(user);
	}
	
	@Override
	public Purchase findById(Long id) {
		return purchaseRepository.findById(id);
	}

	@Override
	public List<Purchase> findAll(){
		return purchaseRepository.findAll();
	}
}
