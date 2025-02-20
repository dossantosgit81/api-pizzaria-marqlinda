package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

public record AddressDto(Long id, String city, String state, String country, String street, Integer number, String zipcode, String neghborhood, String title, CustomerResDto customer) {
}
