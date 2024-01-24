package com.hamzakhan.customer;

import com.hamzakhan.exception.DuplicateResourceException;
import com.hamzakhan.exception.RequestValidationException;
import com.hamzakhan.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService(@Qualifier("jdbc") CustomerDAO customerDAO){
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers(){
        return customerDAO.selectAllCustomers();
    }

    public Customer getCustomer(Integer id){
        return customerDAO.getCustomerByID(id)
                .orElseThrow(() -> new ResourceNotFound(
                        "customer with id [%s] not found".formatted(id)
                ));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationService){
        //check if email exists
        String emailInQuestion = customerRegistrationService.email();

        if (customerDAO.existsPersonWithEmail(emailInQuestion)){
            throw new DuplicateResourceException("email already taken");
        }

        Customer customer = new Customer(
            customerRegistrationService.name(),
            customerRegistrationService.email(), 
            customerRegistrationService.age()
            );

        customerDAO.insertCustomer(customer);
    }

    public void deleteCustomer(Integer cID){
        //check if customer exists
        if (!customerDAO.existsCustomerById(cID)){
            throw new ResourceNotFound("customer with id [%s] not found".formatted(cID));
        }

        customerDAO.deleteCustomerById(cID);

    }

    public void updateCustomer(Integer cID, CustomerUpdateRequest updateRequest) {
        //check if customer with ID exists
        if (!customerDAO.existsCustomerById(cID)){
            throw new ResourceNotFound("customer with id [%s] not found".formatted(cID));
        }

        //update customer logic
        Customer customerToUpdate = getCustomer(cID);

        boolean changesExist = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(customerToUpdate.getName())){
            customerToUpdate.setName(updateRequest.name());
            changesExist = true;
        }
        else {
            customerToUpdate.setName(null);
        }

        if (updateRequest.age() != null && !updateRequest.age().equals(customerToUpdate.getAge())) {
            customerToUpdate.setAge(updateRequest.age());
            changesExist = true;
        }
        else {
            customerToUpdate.setAge(null);
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(customerToUpdate.getEmail())){
            customerToUpdate.setEmail(updateRequest.email());
            changesExist = true;
        }
        else{
            customerToUpdate.setEmail(null);
        }

        if (changesExist) 
        customerDAO.updateCustomer(customerToUpdate);
            
        else {
            throw new RequestValidationException("Error! No new values to update in Customer");
        }

    }


}
