package bookstore.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bookstore.model.Book;
import bookstore.model.Comment;
import bookstore.model.Customer;
import bookstore.model.User;
import bookstore.service.BookService;
import bookstore.service.CommentService;
import bookstore.service.CustomerService;
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
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private CustomerService customerService;	
	
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
		List<Book> bookList = bookService.findAll();
		model.addAttribute("currentUser", userService.findByUsername(p.getName()));
		model.addAttribute("bookList", bookList);
		return "adminhome";
	}
	
	@RequestMapping(value = "/addbook", method = RequestMethod.GET)
	public String get(Model model, Principal p) {
		model.addAttribute("currentUser", userService.findByUsername(p.getName()));
		model.addAttribute("bookForm", new Book());
		return "addbook";
	}
	
	@RequestMapping(value = "/addbook", method = RequestMethod.POST)
	public String add(@ModelAttribute("bookForm") Book book, Model model, Principal p) {
		bookService.save(book);
		return "redirect:/admin/home";
	}
	
	@RequestMapping(value = "/book/edit/{id}", method = RequestMethod.GET)
	public String get(@PathVariable Long id, Model model, Principal p) {
		model.addAttribute("bookForm", bookService.findById(id));
		return "bookedit";
	}
	
	@RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
	public String getBook(@PathVariable Long id, Model model, Principal p) {
		model.addAttribute("commentForm", new Comment());
		model.addAttribute("book", bookService.findById(id));
		return "book";
	}
	
	@RequestMapping(value = "/comment/{id}", method = RequestMethod.POST)
	public String postComment(@PathVariable Long id, Book book,
			@ModelAttribute("commentForm") Comment commentForm, BindingResult bindingResult, Model model,
			Principal principal) {
		if (bindingResult.hasErrors()) {
			return "book";
		}
		book = bookService.findById(id);
		String name = principal.getName();
		Customer cust = customerService.findByUsername(name);
		commentService.save(commentForm, cust, book);
		bookService.save(book);
		return "redirect:/comment/{postTextId}";
	}
	
	@RequestMapping(value = "/book/edit/{id}", method = RequestMethod.POST)
	public String update(@ModelAttribute("bookForm") Book book, Model model, Principal p) {
		bookService.save(book);
		return "bookedit";
	}
	

}
