package com.example.CouponSystem.service;

import com.example.CouponSystem.beans.Customer;
import com.example.CouponSystem.beans.CustomerDTO;
import com.example.CouponSystem.enums.ErrorMessage;
import com.example.CouponSystem.exception.AuthorizationException;
import com.example.CouponSystem.exception.CustomerException;
import com.example.CouponSystem.repo.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;



    /*
      The function gets a customer and adds the customer to the database, to the table: customers.
      To add a customer you have to enter the correct details such as: not an email that already exists
   */

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) throws CustomerException, AuthorizationException {

        Customer customer = this.modelMapper.map(customerDTO, Customer.class);
        if (this.customerRepository.existsById(customer.getId())) {
            throw new CustomerException(ErrorMessage.ID_ALREADY_EXIST);
        }

        if (this.customerRepository.existsByEmail(customerDTO.getEmail())){
            throw new CustomerException(ErrorMessage.EMAIL_EXIST);
        }
        Customer customerFromDB = this.customerRepository.save(customer);
        return this.modelMapper.map(customerFromDB, CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> getCustomerList() throws AuthorizationException {
        return this.customerRepository.findAll().stream().map(customer -> this.modelMapper.map(customer,CustomerDTO.class)).collect(Collectors.toList());
    }

    /*
      The function gets a customer's id and return the customer with the id from the database, from the table: customers
      To get a customer you have to enter an id that exists in the customers table otherwise the program will throw an exception.
    */
    @Override
    public CustomerDTO getSingleCustomer(int id) throws CustomerException, AuthorizationException {

        Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new CustomerException(ErrorMessage.ID_NOT_FOUND));
        return this.modelMapper.map(customer, CustomerDTO.class);
    }

    /*
        The function gets a customer and a customer's id and updates the customer in the database, in the table: customers.
        To update a customer you have to enter an id that exists in customers table.
    */
    @Override
    public void updateCustomer(int id, CustomerDTO customerDTO) throws CustomerException, AuthorizationException {

        if (!this.customerRepository.existsById(id)){
            throw new CustomerException((ErrorMessage.ID_NOT_FOUND));
        }

        if(this.customerRepository.existsByEmail(customerDTO.getEmail()) && this.customerRepository.findByEmail(customerDTO.getEmail()).getId() != id){
            throw new CustomerException(ErrorMessage.EMAIL_EXIST);
        }

        Customer customer = this.modelMapper.map(customerDTO, Customer.class);
        customer.setId(id);
        customer.setEmail(customerDTO.getEmail());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        this.customerRepository.save(customer);
    }

    /*
        The function gets a customer's id and deletes the customer from the database, from the table: customers
        To delete a customer you have to enter an id that exists in customers table
        The function deletes the customer and all the customer's purchase history
    */
    @Override
    public void deleteCustomer(int id) throws CustomerException, AuthorizationException {

        if (!this.customerRepository.existsById(id)) {
            throw new CustomerException(ErrorMessage.ID_NOT_FOUND);
        }
        this.customerRepository.deleteById(id);
    }


    @Override
    public Customer findByEmail(String email) {
        return this.customerRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmailAndPassword(String email, String password){
        return this.customerRepository.existsByEmailAndPassword(email, password);
    }

    @Override
    public boolean isExist(Customer customer){
        return this.customerRepository.existsById(customer.getId());
    }
}
