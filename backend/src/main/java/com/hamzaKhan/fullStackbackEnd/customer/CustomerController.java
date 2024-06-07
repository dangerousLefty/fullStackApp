package com.hamzaKhan.fullStackbackEnd.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
//----- adding the line above will let us reduce redundancy in defining the mapping annotations below
//----- The @Tag annotation comes from swagger-ui to document APIs
@Tag(name = "Customer Controller", description = "Create & retrieve contacts")
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
    @Operation(summary = "Retrieves customers", description = "Provides list of all customers from the postgresQL db")
    @ApiResponse(
            responseCode = "200",
            description = "Successful retrieval of customers",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Customer.class)))
    )
    @GetMapping/*("api/v1/customers")*/(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @Operation(summary = "Get customer by ID", description = "Returns a contact based on an ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer doesn't exist",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful retrieval of customer",
                    content = @Content(schema = @Schema(implementation = Customer.class)))
    })
    @GetMapping(/*"api/v1/customers/*/value = "{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomer(
            @PathVariable("customerId") Integer customerId) {
        return customerService.getCustomer(customerId);
    }

    @Operation(
            summary = "Create Contact",
            description = "Create a Customer from the provided payload"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request: unsuccessful submission!",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "201",
                    description = "Successful creation of customer"
            )
    })
    @PostMapping(/*"api/v1/customers"*/)
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request){
        customerService.addCustomer(request);
    }

    @Operation(
            summary = "Delete Contact",
            description = "Delete a Customer from the database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer doesn't exist",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful deletion of customer"
            )
    })
    @DeleteMapping("{customerID}")
    public void deleteCustomer(@PathVariable("customerID") Integer customerID){
        customerService.deleteCustomer(customerID);
    }

    @Operation(
            summary = "Update Customer",
            description = "Update a Customer in the database from the payload provided"
    )
    @PutMapping("{customerID}")
    public void updateCustomer(@PathVariable("customerID") Integer customerID, @RequestBody CustomerUpdateRequest updateRequest){
        customerService.updateCustomer(customerID, updateRequest);
    }
}
