package bookstore.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bookstore.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);


    	
}
