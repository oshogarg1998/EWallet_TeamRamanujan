package com.capgemini.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.daos.AccountDAO;
import com.capgemini.daos.TransactionDAO;
import com.capgemini.daos.WalletDAO;
import com.capgemini.entities.Transaction;
import com.capgemini.entities.Wallet;
import com.capgemini.exception.ApplicationException;
import com.capgemini.exception.TransactionException;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService
{

	@Autowired
	AccountDAO dao;
	@Autowired
	WalletDAO walletDao;
	@Autowired
	TransactionDAO transactionDao;
	
	@Override
	@Transactional(readOnly = true)
	public Wallet findAccount(int accId) {
		// TODO Auto-generated method stub
		Optional<Wallet> a = walletDao.findById(accId);
		
		if (a.isPresent()) 
			return a.get();
		else
			throw new ApplicationException("AccountId  not found!"); // throwing custom exception if account doesn't exist
		
	}
	@Override
	@Transactional(readOnly = true)
	public List<Transaction> transactionHistory(int senderId) {
		// TODO Auto-generated method stub
		List<Transaction>history = transactionDao.findBySenderAccId(senderId);
		System.out.println(history.get(0));
		return history;
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean TransferAmount(Transaction t) {
		// TODO Auto-generated method stub
		if(t.getAmount()<=0) {
		throw new TransactionException("Transfer Can't be happen due to invalid amount ")			;
		}
			
			
			
			Wallet sender = findAccount(t.getSenderAccId());
			Wallet receiver = findAccount(t.getReceiverAccId());
			double sender_new_balance = sender.getBalance()-t.getAmount();
			double receiver_new_balance = receiver.getBalance() +t.getAmount();
			
			
			updateBalance(sender.getAccountId(),sender_new_balance);
			updateBalance(receiver.getAccountId(),receiver_new_balance);
		
			transactionDao.save(t);
		return true;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBalance(int accId, double amount) {
		// TODO Auto-generated method stub
		Wallet w;
		Optional<Wallet> p = walletDao.findById(accId);
		if (p.isPresent())
			w = p.get();
		else
			throw new ApplicationException("Account not found!"); 

		w.setBalance(amount);

	}

}
