package com.neophron.network.base

import com.neophron.network.BuildConfig

internal object BaseUrl {
    val url = "https://api.openweathermap.org/"
    val apiVersion = "data/2.5/"
    val iconUrl = "https://openweathermap.org/img/wn/#@4x.png"
    val bearerTokenParam = "appid"
    val bearerToken = BuildConfig.BEARER_TOKEN
}

object Icon {
    val url = BaseUrl.iconUrl
    val placeHolder = "#"
}
