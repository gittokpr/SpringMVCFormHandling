package com.finalHandson.SpringMVCFormHandling.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.finalHandson.SpringMVCFormHandling.model.User;

@Controller
public class FormController {
	
	List<User> userList = new ArrayList<>();
	
	@GetMapping("/")
	public String allUsers(Model model) {
		model.addAttribute("userList", userList);
		return "userList";
	}

	@GetMapping(path = {"/edit","/edit/{id}"})
	public String registerFom ( Model model, @PathVariable("id") Optional<String> name) {
		if(name.isPresent()) {
			List<User> userFinal = userList.parallelStream().filter(user -> user.getName().equals(name.get())).collect(Collectors.toList());
			model.addAttribute("user",userFinal.get(0));
		}else {
			model.addAttribute("user", new User());
		}	
		return "registerForm";
	}
	
	@PostMapping("/register")
	public String submitForm( @ModelAttribute("user") User user) {
		userList = userList.parallelStream().filter(luser -> !luser.getName().equals(user.getName())).collect(Collectors.toList());
		userList.add(user);
		return "redirect:/";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteUser(Model model, @PathVariable("id") String name) {
		userList = userList.parallelStream().filter(luser -> !luser.getName().equals(name)).collect(Collectors.toList());
		return "redirect:/";
	}
}
