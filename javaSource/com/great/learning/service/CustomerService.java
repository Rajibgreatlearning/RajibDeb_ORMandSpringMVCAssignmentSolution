package com.great.learning.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.great.learning.model.Customer;

@Service
public interface CustomerService {
	
	List<Customer> findAll();
	Customer findById(int id);
	void save(Customer myBook);
	void deleteById(int id);
	List<Customer> searchBy(String firstName, String email);

}
