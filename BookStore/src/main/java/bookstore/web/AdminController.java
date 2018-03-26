package bookstore.web;

import java.security.Principal;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bookstore.model.Book;
import bookstore.model.User;
import bookstore.service.BookService;
import bookstore.service.SecurityService;
import bookstore.service.UserService;
import bookstore.validator.UserValidator;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		model.addAttribute("userForm", new User());

		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "adminlogin";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "adminlogin";
		}

		userService.save(userForm);

		securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

		return "redirect:/admin/home";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model, Principal p) {
		List<Book> bookList = bookService.
		model.addAttribute("currentUser", userService.findByUsername(p.getName()));
		return "adminhome";
	}
	
	@RequestMapping(value = "/addbook", method = RequestMethod.GET)
	public String get(Model model, Principal p) {
		model.addAttribute("currentUser", userService.findByUsername(p.getName()));
		model.addAttribute("bookForm", new Book());
		return "addbook";
	}
	
	@RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
	public String add(@PathVariable long bookId, Model model, Principal p) {
		
		return "book";
	}
	
	

}
