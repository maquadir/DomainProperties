package com.example.domainproperties.model

data class Project(
    val child_listings: List<ChildListings>,
    val project_banner_image: String,
    val project_color_hex: String,
    val project_logo_image: String,
    val project_name: String,
    val project_price_from: Int?
)