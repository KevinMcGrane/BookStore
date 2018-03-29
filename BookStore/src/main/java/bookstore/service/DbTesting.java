package bookstore.service;

import java.sql.Timestamp;
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
import bookstore.model.Book;
import bookstore.model.Comment;
import bookstore.model.Role;
import bookstore.repository.CommentRepository;
import bookstore.repository.RoleRepository;
@Transactional
@Service
public class DbTesting {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private BookService bookService;
	@Autowired
	private CommentService commentService;
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
	roles.add(roleCustomer);
	user.setRoles(roles);
	userRepository.save(user);
	
	Book book = new Book();
	book.setTitle("jjjjjj");
	book.setAuthor("a");
	book.setCategory("ww");
	book.setPrice(0);
	bookService.save(book);
	
	Comment comment = new Comment();
	comment.setContent("Hi");
//	comment.setBook(book);
//	comment.setUser(user);
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	comment.setPublishTime(timestamp);
	commentService.save(comment, user, book);
	
	Comment comment2 = new Comment();
	comment2.setContent("Hii");
//	comment2.setBook(book);
//	comment2.setUser(user);
	comment2.setPublishTime(timestamp);
	commentService.save(comment2, user, book);
	
}
}