package com.great.learning.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.great.learning.model.Customer;

@Repository
public class CustomerServiceImpl implements CustomerService {

	private SessionFactory sessionFactory;
	private Session session;

	@Autowired
	CustomerServiceImpl(SessionFactory sessionFactory) { // Constructor dependency
														// injection
		this.sessionFactory = sessionFactory;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
	}

	@Transactional
	public List<Customer> findAll() {
		Transaction transaction = session.beginTransaction();
		List<Customer> customers = session.createQuery("from Customer").list();
		transaction.commit();
		return customers;
	}

	@Transactional
	public Customer findById(int id) {
		Customer myCustomer;
		//Transaction transaction = session.beginTransaction();
		myCustomer = session.get(Customer.class, id);
		//transaction.commit();
		return myCustomer;

	}

	@Transactional
	public void save(Customer myCustomer) {
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(myCustomer);
		transaction.commit();

	}

	@Transactional
	public void deleteById(int id) {
		Customer myCustomer;
		Transaction transaction = session.beginTransaction();
		myCustomer = session.get(Customer.class, id);
		session.delete(myCustomer);
		transaction.commit();
		

	}

	@Transactional
	public List<Customer> searchBy(String firstName, String email) {
		
		String query = "";

		if (firstName.length() != 0 && email.length() != 0) {
			query = "from Customer where firstname like '%" + firstName + "%' or email like '%" + email + "%'";
		} else if (firstName.length() != 0) {
			query = "from Customer where firstname like '%" + firstName + "%'";
		} else if (email.length() != 0) {
			query = "from Customer where email like '%" + email + "%'";
		} else {
			System.out.println("Cannot search without input data!");
		}
		List<Customer> customers = session.createQuery(query).list();

		return customers;
	}
	
	@Transactional
	public void print(List<Customer> customers){
		for(Customer customer: customers){
			System.out.println(customer);
		}
	}
	

}
