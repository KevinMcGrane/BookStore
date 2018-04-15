package bookstore.service;

import java.util.List;

import bookstore.model.User;

public interface UserService {
    void save(User user);
    public User findByUsername(String username);
    public List<User> getAllUser();
	public User findUser(long id);
	public void update(User userForm, User user);
	public void checkout(User user);


	

}
