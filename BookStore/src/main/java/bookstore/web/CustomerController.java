package bookstore.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookstore.model.Book;
import bookstore.model.Comment;
import bookstore.model.Purchase;
import bookstore.model.User;
import bookstore.service.BookService;
import bookstore.service.CommentService;
import bookstore.service.PurchaseService;
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
	
	@Autowired
	private PurchaseService purchaseService;
	
	
	
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model, Principal p) {
		List<Book> bookList = bookService.findAll();
		User currentUser = userService.findByUsername(p.getName());
		int cartSize = currentUser.getBooksInCart().size();
		model.addAttribute("cartSize", cartSize);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("bookList", bookList);
		return "home";
	}
	
	@RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
	public String getBook(@PathVariable Long id, Model model, Principal p) {
		User currentUser = userService.findByUsername(p.getName());
		Comment commentForm = new Comment();
		commentForm.setId(null);
		Book book =bookService.findById((long) 1);
		List<Comment> comments = commentService.findByBook(book);
		int cartSize = currentUser.getBooksInCart().size();
		model.addAttribute("cartSize", cartSize);
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
		User currentUser = userService.findByUsername(p.getName());
		int cartSize = currentUser.getBooksInCart().size();
		model.addAttribute("cartSize", cartSize);
		model.addAttribute("userForm", userService.findByUsername(name));
		model.addAttribute("currentUser", userService.findByUsername(name));


		return "account";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model, Principal principal) {
		String name = principal.getName();
		userService.update(userForm, userService.findByUsername(name));


			return "redirect:/customer/account";
		}
	
	@RequestMapping(value = "/book/addtocart/{id}", method = RequestMethod.POST)
	public String addToCart(@PathVariable Long id, Model model, Principal p) {
		User currentUser = userService.findByUsername(p.getName());
		Book book = bookService.findById(id);
		bookService.addBookToCart(book, currentUser);
		return "redirect:/customer/book/{id}";
	}
	
	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String cart(Model model, String error, Principal p) {
		User currentUser = userService.findByUsername(p.getName());
		List<Book> cartList = currentUser.getBooksInCart();
		int cartSize = currentUser.getBooksInCart().size();
		float total = 0;
		
		for(Book book : cartList) {
			total += book.getPrice();
			
		}
		
		model.addAttribute("total", total);
		model.addAttribute("cartSize", cartSize);
		model.addAttribute("cartList",cartList);
		model.addAttribute("currentUser", currentUser);


		return "cart";
	}
	
	@RequestMapping(value = "/cart/checkout", method = RequestMethod.GET)
	public String checkout(Model model, String error, Principal p) {
		User currentUser = userService.findByUsername(p.getName());
		List<Book> cartList = currentUser.getBooksInCart();
		int cartSize = currentUser.getBooksInCart().size();
		float total = 0;
		for(Book book : cartList) {
			total += book.getPrice();
			
		}
		
		model.addAttribute("total", total);
		model.addAttribute("cartSize", cartSize);
		model.addAttribute("cartList",cartList);
		model.addAttribute("userForm", currentUser);
		return "checkoutpage";
	}
	

	@RequestMapping(value = "/cart/checkout", method = RequestMethod.POST)
	public String checkoutPost(@ModelAttribute User userForm, Model model, String error, Principal p) {
		User currentUser = userService.findByUsername(p.getName());
		userService.update(userForm, currentUser);
		userService.checkout(currentUser);

		return "redirect:/customer/cart";
	}
	
	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String users(@RequestParam("searchString") String searchString, Model model, Principal principal) {
		List<Book> allList = bookService.findAll();
		List<Book> searchList = new ArrayList<>();
		for (Book book : allList) {
			if (book.getTitle().toLowerCase().contains(searchString.toLowerCase()) || book.getAuthor().toLowerCase().contains(searchString.toLowerCase()) || book.getCategory().toLowerCase().contains(searchString.toLowerCase())) {
			searchList.add(book);
			}
		}
		User currentUser = userService.findByUsername(principal.getName());
		int cartSize = currentUser.getBooksInCart().size();
		model.addAttribute("cartSize", cartSize);
		
		model.addAttribute("bookList", searchList);
		return "home";
	}
	
	@RequestMapping(value = "/home/{sort}", method = RequestMethod.GET)
	public String homeSort(@PathVariable String sort,Model model, Principal p) {
		
		List<Book> bookList = bookService.findAll();
//		Collections.sort(bookList, new Comparator<Book>() {
//	        @Override
//	        public int compare(Book o1, Book o2) {
//	        	if(sort.equals("author")) {
//	        		return o1.getAuthor().compareTo(o2.getAuthor());
//	        	}
//	            
//	        }
//	    });
		if (sort.equals("author")) {
			System.out.println(bookList);
		    Collections.sort(bookList, (p1, p2) -> p1.getAuthor().compareTo(p2.getAuthor()));
		    System.out.println(bookList);
		}
		if (sort.equals("category")) {
			System.out.println(bookList);
		    Collections.sort(bookList, (p1, p2) -> p1.getCategory().compareTo(p2.getCategory()));
		    System.out.println(bookList);
		}
		if (sort.equals("title")) {
			System.out.println(bookList);
		    Collections.sort(bookList, (p1, p2) -> p1.getTitle().compareTo(p2.getTitle()));
		    System.out.println(bookList);
		}
		int cartSize = userService.findByUsername(p.getName()).getBooksInCart().size();
		model.addAttribute("cartSize", cartSize);
		model.addAttribute("currentUser", userService.findByUsername(p.getName()));
		model.addAttribute("bookList", bookList);
		return "home";
		}
	
	@RequestMapping(value = { "/purchase/history" }, method = RequestMethod.GET)
	public String hist(Model model, Principal principal) {
		User currentUser = userService.findByUsername(principal.getName());
		List<Purchase> purchaseList = purchaseService.findByUser(currentUser);
		int cartSize = currentUser.getBooksInCart().size();
		model.addAttribute("cartSize", cartSize);
		model.addAttribute("purchaseList", purchaseList);
		model.addAttribute("currentUser", currentUser);
		return "purchasehist";
	}
	@RequestMapping(value = { "/purchase/{id}" }, method = RequestMethod.GET)
	public String hist1(@PathVariable Long id, Model model, Principal principal) {
		User currentUser = userService.findByUsername(principal.getName());
		Purchase purchase = purchaseService.findById(id);
		List<Book> books = bookService.findByPurchase(purchase);
		int cartSize = currentUser.getBooksInCart().size();
		model.addAttribute("cartSize", cartSize);
		model.addAttribute("purchase", purchase);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("books", books);
		return "purchase";
	}
	
	}
	


