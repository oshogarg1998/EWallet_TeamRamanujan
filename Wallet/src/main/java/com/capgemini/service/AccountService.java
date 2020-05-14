package com.capgemini.service;

import java.util.List;

import com.capgemini.entities.Account;
import com.capgemini.entities.LoginDetails;
import com.capgemini.entities.Transaction;
import com.capgemini.entities.Wallet;

public  interface AccountService {
		public Boolean addAccount(Account a);
		
		
		//public double withdraw(Wallet w, double amount);
		
		
		
		public List<Account> allUserDetails();
		
		public LoginDetails userLogin(String userName,String password);
}
