package bookstore.service;



import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import bookstore.model.Book;
import bookstore.model.Purchase;
import bookstore.model.Role;
import bookstore.model.User;
import bookstore.repository.RoleRepository;
import bookstore.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private BookService bookService;
    @Autowired
    private PurchaseService purchaseService;

    
    @Override
    public void save(User user) {
    	if(user.isAdmin()==true) {
    		Role admin = roleRepository.findByName("ROLE_ADMIN");
        	Set<Role> adminRoles = new HashSet<>();
        	adminRoles.add(admin);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(adminRoles);
            userRepository.save(user);
    	}else {
    		Role cust = roleRepository.findByName("ROLE_CUSTOMER");
        	Set<Role> custRoles = new HashSet<>();
        	custRoles.add(cust);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(custRoles);
            user.setComments(user.getComments());
            user.setCreditNum(user.getCreditNum());
            user.setAddress(user.getAddress());
            userRepository.save(user);
    	}
    	
    }
    
    @Override
    public void update(User userForm, User user) {
        if(user.isAdmin()==true) {
        	user.setPassword(user.getPassword());
        	Role admin = roleRepository.findByName("ROLE_ADMIN");
        	Set<Role> adminRoles = new HashSet<>();
        	adminRoles.add(admin);
        	user.setRoles(adminRoles);
            userRepository.save(user);
        }else {
        	user.setPassword(user.getPassword());
        	Role cust = roleRepository.findByName("ROLE_CUSTOMER");
        	Set<Role> custRoles = new HashSet<>();
        	custRoles.add(cust);
            user.setRoles(custRoles);
            user.setComments(user.getComments());
            user.setCreditNum(userForm.getCreditNum());
            user.setAddress(userForm.getAddress());
            user.setName(userForm.getName());
            user.setBooksInCart(user.getBooksInCart());
            user.setPurchased(user.getPurchased());
            userRepository.save(user);
    	}
        
        
    }

    
    @Override
    public User findUser(long id) {
        return userRepository.getOne(id);
    }
    
    @Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	
	@Override
	public void checkout(User user) {
		user.getPurchased().addAll(user.getBooksInCart());
		List<Book> purchased = user.getBooksInCart();
		Purchase purchase = new Purchase();
		purchase.setBook(purchased);
		purchase.setUser(user);
		purchaseService.save(purchase);
		List<Book> books2 = purchase.getBook();
		for(Book book:books2) {
			System.out.println("111111" + book.getAuthor());
		}
		user.getBooksInCart().clear();
		List<Book> books3 = purchase.getBook();
		for(Book book2:books3) {
			System.out.println("22222222" + book2.getAuthor());
		}
		List<Book> books = user.getPurchased();
		for(Book book : books) {
			int stock=book.getStockLevel() - 1;
			book.setStockLevel(stock);
			bookService.save(book);
			
			
		}
		save(user);
	}


    

  

}
