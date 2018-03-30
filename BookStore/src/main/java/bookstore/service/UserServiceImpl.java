package bookstore.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import bookstore.model.Role;
import bookstore.model.User;
import bookstore.repository.RoleRepository;
import bookstore.repository.UserRepository;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

   
    
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
            user.setBooksInCart(userForm.getBooksInCart());
            user.setPurchased(userForm.getPurchased());
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


    

  

}
