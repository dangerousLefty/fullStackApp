package com.hamzaKhan.fullStackbackEnd.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
//adding the line above will let us reduce redundancy in defining the mapping annotations below
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /*
   @RequestMapping(
           path = "api/v1/customer",
           method = RequestMethod.GET
   )*/
    @GetMapping/*("api/v1/customers")*/
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(/*"api/v1/customers/*/"{customerId}")
    public Customer getCustomer(
            @PathVariable("customerId") Integer customerId) {
        return customerService.getCustomer(customerId);
    }

    @PostMapping(/*"api/v1/customers"*/)
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request){
        customerService.addCustomer(request);
    }

    @DeleteMapping("{customerID}")
    public void deleteCustomer(@PathVariable("customerID") Integer customerID){
        customerService.deleteCustomer(customerID);
    }

    @PutMapping("{customerID}")
    public void updateCustomer(@PathVariable("customerID") Integer customerID, @RequestBody CustomerUpdateRequest updateRequest){
        customerService.updateCustomer(customerID, updateRequest);
    }
}
