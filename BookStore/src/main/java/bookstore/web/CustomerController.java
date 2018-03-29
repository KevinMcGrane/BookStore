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
import bookstore.model.User;
import bookstore.service.BookService;
import bookstore.service.CommentService;
import bookstore.service.SecurityService;
import bookstore.service.UserService;
import bookstore.validator.UserValidator;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	private static final boolean Comment = false;

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
	
	
	
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model, Principal p) {
		List<Book> bookList = bookService.findAll();
		model.addAttribute("currentUser", userService.findByUsername(p.getName()));
		model.addAttribute("bookList", bookList);
		return "home";
	}
	
	@RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
	public String getBook(@PathVariable Long id, Model model, Principal p) {
		Comment commentForm = new Comment();
		commentForm.setId(null);
		Book book =bookService.findById((long) 1);
		List<Comment> comments = commentService.findByBook(book);
		model.addAttribute("commentForm", commentForm);
		model.addAttribute("comments", commentService.findByBook(bookService.findById(id)));
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
		User cust = userService.findByUsername(name);
		commentService.save(commentForm, cust, book);
		bookService.save(book);
		return "redirect:/customer/book/{id}";
	}
	
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String login(Model model, String error, Principal p) {
		String name = p.getName();
		model.addAttribute("userForm", userService.findByUsername(name));
		model.addAttribute("currentUser", userService.findByUsername(name));


		return "account";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model, Principal principal) {
		String name = principal.getName();
		userService.update(userForm, name);


			return "redirect:/customer/account";
		}
	
	@RequestMapping(value = "/book/addtocart/{id}", method = RequestMethod.POST)
	public String addToCart(@PathVariable Long id, Model model, Principal p) {
		User currentUser = userService.findByUsername(p.getName());
		bookService.addToCart(id, user);
		return "cart";
	}
	}
	


