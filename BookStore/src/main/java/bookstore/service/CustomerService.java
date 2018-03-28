package bookstore.service;

import bookstore.model.Customer;

public interface CustomerService {

	Customer findByUsername(String username);

}
