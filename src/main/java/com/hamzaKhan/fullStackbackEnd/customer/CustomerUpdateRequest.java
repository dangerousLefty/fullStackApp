package com.hamzaKhan.fullStackbackEnd.customer;

public record CustomerUpdateRequest (
    String name,
    String email,
    Integer age
) {
}
