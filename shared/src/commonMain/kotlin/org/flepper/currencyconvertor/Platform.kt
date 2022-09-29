package org.flepper.currencyconvertor

interface Platform {
    val name: String
}

//expect fun getPlatform(): Platform