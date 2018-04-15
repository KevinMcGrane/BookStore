package bookstore.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bookstore.model.User;
import bookstore.service.BookService;
import bookstore.service.CommentService;
import bookstore.service.SecurityService;
import bookstore.service.UserService;
import bookstore.validator.UserValidator;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserValidator userValidator;
	

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		model.addAttribute("userForm", new User());

		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "login";
		}

		userService.save(userForm);

		securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

		if(userForm.isAdmin()==true) {
		return "redirect:/admin/home";
		}
		else {
			return "redirect:/customer/home";
		}
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model, String error, Principal p) {
		User currentUser = userService.findByUsername(p.getName());
		if(currentUser.isAdmin()) {
			return "redirect:/admin/home";
		}else

		return "redirect:/customer/home";
	}
}
