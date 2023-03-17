package com.example.domainproperties

import com.example.domainproperties.model.*

object UtilsTest {

    val FirstProp = SearchResult(
        listOf("furnished"), "Proposed LOT 14 Bracken Estate, Oberon",
        Advertiser(
            listOf(
                AgencyListingContacts(
                    "Lisa Behn",
                    "https://images.domain.com.au/img/15990/contact_900375.jpeg?buster=2023-03-15"
                )
            ),
            15990,
            Images("https://images.domain.com.au/img/Agencys/15990/logo_15990.png?buster=2023-03-15"),
            "Bowyer & Livermore",
            "#F10006"
        ),
        2,
        2,
        "2022-04-27T15:06:45+10:00",
        "Vacant land",
        GeoLocation(-33.70372, 149.854263),
        true,
        "BRACKEN ESTATE FINAL STAGES AVAILABLE NOW",
        false,
        2017762187,
        "2.0 ha",
        true,
        "Under Offer",
        "topspot",
        listOf(
            Media(
                "photo",
                "https://bucket-api.domain.com.au/v1/bucket/image/2017762034_1_1_220427_044629-w1800-h1200",
                "image"
            ),
            Media(
                "photo",
                "https://bucket-api.domain.com.au/v1/bucket/image/2017762034_2_1_220429_011232-w1697-h1200",
                "image"
            )
        ),
        Metadata(
            AddressComponents(
                "2787",
                "NSW",
                "Bracken Estate",
                "Proposed LOT 14",
                "OBERON",
                34122
            )
        ),
        "UNDER OFFER",
        Project(emptyList(), "","","","",0),
        "StandardPP",
        Topspot(30),
        0
    )

    val SecondProp = SearchResult(
        listOf("furnished"), "Proposed LOT 14 Bracken Estate, Oberon",
        Advertiser(
            listOf(
                AgencyListingContacts(
                    "Lisa Behn",
                    "https://images.domain.com.au/img/15990/contact_900375.jpeg?buster=2023-03-15"
                )
            ),
            15990,
            Images("https://images.domain.com.au/img/Agencys/15990/logo_15990.png?buster=2023-03-15"),
            "Bowyer & Livermore",
            "#F10006"
        ),
        2,
        2,
        "2022-04-27T15:06:45+10:00",
        "Vacant land",
        GeoLocation(-33.70372, 149.854263),
        true,
        "BRACKEN ESTATE FINAL STAGES AVAILABLE NOW",
        false,
        2017762187,
        "2.0 ha",
        true,
        "Under Offer",
        "topspot",
        listOf(
            Media(
                "photo",
                "https://bucket-api.domain.com.au/v1/bucket/image/2017762034_1_1_220427_044629-w1800-h1200",
                "image"
            ),
            Media(
                "photo",
                "https://bucket-api.domain.com.au/v1/bucket/image/2017762034_2_1_220429_011232-w1697-h1200",
                "image"
            )
        ),
        Metadata(
            AddressComponents(
                "2787",
                "NSW",
                "Bracken Estate",
                "Proposed LOT 14",
                "OBERON",
                34122
            )
        ),
        "UNDER OFFER",
        Project(emptyList(), "","","","",0),
        "StandardPP",
        Topspot(30),
        0
    )

    val ThirdProp = SearchResult(
        listOf("furnished"), "Proposed LOT 14 Bracken Estate, Oberon",
        Advertiser(
            listOf(
                AgencyListingContacts(
                    "Lisa Behn",
                    "https://images.domain.com.au/img/15990/contact_900375.jpeg?buster=2023-03-15"
                )
            ),
            15990,
            Images("https://images.domain.com.au/img/Agencys/15990/logo_15990.png?buster=2023-03-15"),
            "Bowyer & Livermore",
            "#F10006"
        ),
        2,
        2,
        "2022-04-27T15:06:45+10:00",
        "Vacant land",
        GeoLocation(-33.70372, 149.854263),
        true,
        "BRACKEN ESTATE FINAL STAGES AVAILABLE NOW",
        false,
        2017762187,
        "2.0 ha",
        true,
        "Under Offer",
        "topspot",
        listOf(
            Media(
                "photo",
                "https://bucket-api.domain.com.au/v1/bucket/image/2017762034_1_1_220427_044629-w1800-h1200",
                "image"
            ),
            Media(
                "photo",
                "https://bucket-api.domain.com.au/v1/bucket/image/2017762034_2_1_220429_011232-w1697-h1200",
                "image"
            )
        ),
        Metadata(
            AddressComponents(
                "2787",
                "NSW",
                "Bracken Estate",
                "Proposed LOT 14",
                "OBERON",
                34122
            )
        ),
        "UNDER OFFER",
        Project(emptyList(), "","","","",0),
        "StandardPP",
        Topspot(30),
        0
    )

    val properties = listOf(FirstProp, SecondProp, ThirdProp)
    val trackingMetadata = TrackingMetadata(
        "", "", "", emptyList(), 0.0, 0.0, 0,
        false, false, "",
        0, "", 0, emptyList(),
        emptyList(), emptyList(), false, emptyList(), "", 0, false, "",
        0, emptyList(), "", emptyList(), "",
        0.0, "", "", ""
    )
    val results = PropertyModel(
        false, 0, 200, 1200, "", SearchInsights(emptyList()), properties,
        Tracking(), trackingMetadata
    )
}