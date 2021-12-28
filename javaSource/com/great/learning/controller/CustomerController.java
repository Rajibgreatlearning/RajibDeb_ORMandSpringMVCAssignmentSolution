package com.great.learning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.great.learning.model.Customer;
import com.great.learning.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping("/list")
	public String listCustomers(Model theModel) {

		System.out.println("Inside the books");
		// get the book from DB
		List<Customer> customerList = customerService.findAll();
		theModel.addAttribute("Customers", customerList);
		return "list-customers";    //list-books
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		Customer newCustomer = new Customer();
		theModel.addAttribute("Customer", newCustomer);

		return "Customer-form";  //Book-form

	}
	
	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {

		// get the Book from the service
		Customer theCustomer = customerService.findById(theId);

		// set Book as a model attribute to pre-populate the form
		theModel.addAttribute("Customer", theCustomer);

		// send over to our form
		return "Customer-form";
	}

	@PostMapping("/save")
	public String saveCustomer(@RequestParam("id") int id, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("email") String email) {

		Customer customer;
		if (id != 0) {
			customer = customerService.findById(id);
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			customer.setEmail(email);

		} else {
			customer = new Customer(firstName,lastName, email);
		}

		customerService.save(customer);

		return "redirect:/customers/list";

	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("customerId") int id) {
		// delete the customer
		customerService.deleteById(id);
		return "redirect:/customers/list";
	}

	@GetMapping("/search")
	public String search(@RequestParam("firstName") String firstName, @RequestParam("email") String email, Model theModel) {

		if (firstName.trim().isEmpty() && email.trim().isEmpty())
			return "redirect:/customers/list";
		else {
			List<Customer> theCustomers = customerService.searchBy(firstName, email);
			theModel.addAttribute("Customers", theCustomers);
			return "list-customers";
		}
	}

}
