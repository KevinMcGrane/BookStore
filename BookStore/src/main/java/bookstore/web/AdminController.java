package bookstore.web;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

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
	private HttpServletRequest request;

	@Autowired
	private CommentService commentService;
	@Autowired
	private PurchaseService purchaseService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model, Principal p) {
		List<Book> bookList = bookService.findAll();
		model.addAttribute("currentUser", userService.findByUsername(p.getName()));
		model.addAttribute("bookList", bookList);
		Collections.sort(bookList, (p1, p2) -> p1.getAuthor().compareTo(p2.getAuthor()));
		System.out.println(bookList);
		return "home";
	}

	@RequestMapping(value = "/home/{sort}", method = RequestMethod.GET)
	public String home(@PathVariable String sort, Model model, Principal p) {

		List<Book> bookList = bookService.findAll();
		// Collections.sort(bookList, new Comparator<Book>() {
		// @Override
		// public int compare(Book o1, Book o2) {
		// if(sort.equals("author")) {
		// return o1.getAuthor().compareTo(o2.getAuthor());
		// }
		//
		// }
		// });
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

		model.addAttribute("currentUser", userService.findByUsername(p.getName()));
		model.addAttribute("bookList", bookList);
		return "home";
	}

	@RequestMapping(value = "/addbook", method = RequestMethod.GET)
	public String get(Model model, Principal p) {
		model.addAttribute("currentUser", userService.findByUsername(p.getName()));
		model.addAttribute("bookForm", new Book());
		return "addbook";
	}

	@RequestMapping(value = "/addbook", method = RequestMethod.POST)
	public String add(@ModelAttribute("bookForm") Book book, Model model, Principal p)
			throws IllegalStateException, IOException {

		bookService.save(book);
		return "redirect:/admin/home";
	}

	@RequestMapping(value = "/add/{id}/profilepic", method = RequestMethod.POST)
	public String add(@RequestPart("image") MultipartFile file, @PathVariable Long id, Model model, Principal p)
			throws IllegalStateException, IOException {

		Book book = bookService.findById(id);
		String fileName = book.getId() + file.getOriginalFilename();
		String realPathtoUploads = "C:\\Users\\Kevin\\Documents\\Business Computing\\Software Patters\\maven.1522152624118\\BookStore\\src\\main\\webapp\\resources\\images\\"
				+ fileName;
		System.out.println(realPathtoUploads);
		file.transferTo(new File(realPathtoUploads));
		book.setImage(fileName);
		bookService.save(book);

		return "redirect:/admin/book/{id}";
	}

	@RequestMapping(value = "/book/edit/{id}", method = RequestMethod.GET)
	public String get(@PathVariable Long id, Model model, Principal p) {
		model.addAttribute("bookForm", bookService.findById(id));
		System.out.print(bookService.findById(id).getImage());

		return "bookedit";
	}

	@RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
	public String getBook(@PathVariable Long id, Model model, Principal p) {
		model.addAttribute("commentForm", new Comment());
		model.addAttribute("book", bookService.findById(id));
		return "book";
	}

	@RequestMapping(value = "/comment/{id}", method = RequestMethod.POST)
	public String postComment(@PathVariable Long id, Book book, @ModelAttribute("commentForm") Comment commentForm,
			BindingResult bindingResult, Model model, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "book";
		}
		book = bookService.findById(id);
		String name = principal.getName();
		User cust = userService.findByUsername(name);
		commentService.save(commentForm, cust, book);
		bookService.save(book);
		return "redirect:/comment/{postTextId}";
	}

	@RequestMapping(value = "/book/edit/{id}", method = RequestMethod.POST)
	public String update(@ModelAttribute("bookForm") Book book, Model model, Principal p) {
		book.setImage(bookService.findById(book.getId()).getImage());
		System.out.println(book.getImage());
		bookService.save(book);
		return "bookedit";
	}

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String users(@RequestParam("searchString") String searchString, Model model, Principal principal) {
		List<Book> allList = bookService.findAll();
		List<Book> searchList = new ArrayList<>();
		for (Book book : allList) {
			if (book.getTitle().toLowerCase().contains(searchString.toLowerCase())
					|| book.getAuthor().toLowerCase().contains(searchString.toLowerCase())
					|| book.getCategory().toLowerCase().equals(searchString.toLowerCase())) {
				searchList.add(book);
			}
		}

		model.addAttribute("bookList", searchList);
		return "home";
	}

	@RequestMapping(value = { "/purchase/history" }, method = RequestMethod.GET)
	public String hist(Model model, Principal principal) {
		User currentUser = userService.findByUsername(principal.getName());
		List<Purchase> purchaseList = purchaseService.findAll();
		int cartSize = currentUser.getBooksInCart().size();
		model.addAttribute("cartSize", cartSize);
		model.addAttribute("purchaseList", purchaseList);
		model.addAttribute("currentUser", currentUser);
		return "purchasehist";
	}

	@RequestMapping(value = { "/purchase/{id}/{username}" }, method = RequestMethod.GET)
	public String hist1(@PathVariable Long id, @PathVariable String username, Model model, Principal principal) {
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
