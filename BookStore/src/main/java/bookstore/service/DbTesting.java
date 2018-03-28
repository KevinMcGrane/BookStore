package bookstore.service;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import bookstore.model.User;
import bookstore.repository.UserRepository;
import bookstore.service.UserService;
import bookstore.model.Role;
import bookstore.repository.RoleRepository;
@Transactional
@Service
public class DbTesting {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostConstruct
	public void Init() {

		Role roleAdmin = new Role();
		roleAdmin.setName("ROLE_ADMIN");
		roleRepository.save(roleAdmin);

		Set<GrantedAuthority> grantedAuthoritiesAdmin = new HashSet<>();
		grantedAuthoritiesAdmin.add(new SimpleGrantedAuthority(roleAdmin.getName()));
		
		Role roleCustomer = new Role();
		roleCustomer.setName("ROLE_CUSTOMER");
		roleRepository.save(roleCustomer);

		Set<GrantedAuthority> grantedAuthoritiesCustomer = new HashSet<>();
		grantedAuthoritiesCustomer.add(new SimpleGrantedAuthority(roleCustomer.getName()));

	User user = new User();
	String password = "test";
	user.setUsername("kevin97");
	user.setPassword(bCryptPasswordEncoder.encode(password));
	
	HashSet<Role> roles = new HashSet<>();
	roles.add(roleAdmin);
	user.setRoles(roles);
	userRepository.save(user);
}
}