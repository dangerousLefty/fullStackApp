package com.hamzaKhan.fullStackbackEnd.customer;

public record CustomerRegistrationRequest(
    String name,
    String email,
    Integer age
){

}
