package com.capgemini.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.service.AccountService;

import com.capgemini.entities.Account;
import com.capgemini.entities.LoginDetails;
import com.capgemini.entities.Transaction;
import com.capgemini.entities.User;
import com.capgemini.entities.Wallet;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	@Autowired AccountService service;

	
	@PostMapping(value = "/new", consumes = { "application/json" })
	public Account addAccount(@RequestBody User user) {
		
		Account account = new Account(user.getUsername(),user.getPass(),user.getPhoneNo(),user.getEmailId(), new Wallet(1000.0));
		service.addAccount(account);
		return account;
	}

	@PostMapping(value = "/userLogin/{userName}/{password}")
	public LoginDetails userLoginDetails(@PathVariable String userName,@PathVariable String password) {
	
		return this.service.userLogin(userName,password);
		 
	}


	@GetMapping(value = "/allUserDetails")
	public List<Account> allUserDetails() {
		List<Account> users = service.allUserDetails();
		return users; 
	}
	

	
}
