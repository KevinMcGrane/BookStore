package bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.model.Role;
import bookstore.model.User;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByName(String name);
}
