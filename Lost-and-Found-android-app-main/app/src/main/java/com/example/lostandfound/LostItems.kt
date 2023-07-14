package com.example.lostandfound

data class LostItems(val fullName: String? = null,
                     val phoneNumber: String?,
                     val locationLost: String? = null,
                     val message: String? = null,
                     val image1Url: String? = null,
                     val image2Url: String? = null,
                     val image3Url: String? = null,
                     val image4Url: String? = null,
                     val image5Url: String? = null){
    constructor(): this("",
    "",
    "",
    "",
    "",
    "",
    "",
    "",
    "")
}