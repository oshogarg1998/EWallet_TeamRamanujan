package com.capgemini.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.entities.Account;
import com.capgemini.entities.LoginDetails;
import com.capgemini.entities.Transaction;
import com.capgemini.entities.Wallet;
import com.capgemini.service.AccountService;

import com.capgemini.daos.AccountDAO;
import com.capgemini.daos.TransactionDAO;
import com.capgemini.daos.WalletDAO;
import com.capgemini.exception.ApplicationException;
import com.capgemini.exception.TransactionException;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountDAO dao;
	@Autowired
	WalletDAO walletDao;
	@Autowired
	TransactionDAO transactionDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean addAccount(Account a) {
		// System.out.println("Add dao");
		if (dao.existsById(a.getId()))
			throw new ApplicationException("Account already exists!!"); // throwing custom exception if product already
																		// exists
		dao.save(a); // otherwise saving it in database

		System.out.println("Account added to the database");
		return true;
	}

	

	
	
//	@Override
//	@Transactional(propagation = Propagation.REQUIRED)
//	public double withdraw(Wallet w, double amount) {
//		// TODO Auto-generated method stub
//		double new_balance = w.getBalance();
//		if(amount > 0) {
//			new_balance = w.getBalance() - amount;
//			if(new_balance < 1000.00) {
//				new_balance = w.getBalance();
//				System.out.println("Insufficient Balance : Balance Can't be less than 1000");
//				
//			}
//			else {
//				updateBalance(w.getAccountId(),new_balance );
//			}
//		}
//		return 0;
//	}



	

	

	

	@Override
	@Transactional(readOnly = true)
	public List<Account> allUserDetails() {
		// TODO Auto-generated method stub
		List<Account> users = dao.findAll();
		//System.out.println(users.get(1));
		return users;
	}
	
	
	public LoginDetails userLogin(String username, String pass){
	//	int count = this.dao.countUserByEmail(userName);
		//if (count != 0) {
			
			if (dao.findByUsernameAndPass(username, pass)!=null ) {
				Account userdata = dao.findByUsernameAndPass(username, pass);
				//Account userdata = user.get();
			
				
					LoginDetails details = new LoginDetails();
					details.setStatus(true);
					details.setUserId(userdata.getId());
					details.setName(userdata.getUsername());
					details.setAccountId(userdata.getWallet().getAccountId());
					return details;
			}
				else {
					LoginDetails details = new LoginDetails();
					details.setStatus(false);
					details.setUserId(0);
					details.setName("");
					details.setAccountId(0);
					return details;
				}
			
	}

	
	
}
