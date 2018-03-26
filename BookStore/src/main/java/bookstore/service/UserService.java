package bookstore.service;

import java.util.List;

import bookstore.model.User;

public interface UserService {
    void save(User user);
    void update(User user);
    public User findByUsername(String username);
    public List<User> getAllUser();
	public User findUser(long id);


	

}