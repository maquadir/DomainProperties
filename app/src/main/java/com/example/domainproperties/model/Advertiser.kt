package com.example.domainproperties.model

data class Advertiser(
    val agency_listing_contacts: List<AgencyListingContacts>,
    val id: Int,
    val images: Images,
    val name: String,
    val preferred_color_hex: String
)