package com.example.domainproperties.model

data class ChildListings(
    val bathroom_count: Double,
    val bedroom_count: Double,
    val carspace_count: Int,
    val date_listed: String,
    val earliest_inspections: List<EarliestInspection>,
    val homepass_enabled: Boolean,
    val id: Int,
    val lifecycle_status: String,
    val price: String,
    val property_size: String
)