package com.example.domainproperties.model

data class AddressComponents(
    val postcode: String,
    val state_short: String,
    val street: String,
    val street_number: String,
    val suburb: String,
    val suburb_id: Int
)