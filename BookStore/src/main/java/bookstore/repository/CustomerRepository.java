package bookstore.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);


    	
}
