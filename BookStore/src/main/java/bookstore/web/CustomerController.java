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
		User cust = userService.findByUsername(name);
		commentService.save(commentForm, cust, book);
		bookService.save(book);
		return "redirect:/customer/book/{id}";
	}
	

}
