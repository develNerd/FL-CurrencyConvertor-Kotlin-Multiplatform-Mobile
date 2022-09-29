package org.flepper.currencyconvertor.android.utils

import java.text.DecimalFormat


fun parseAmount(value:String):Double{
    return try{
        val digitRegex = Regex("\\d")
        val fullStopRegex = Regex("\\.")
        val amount =  value.trim().filter { digitRegex.matches(it.toString()) || fullStopRegex.matches(it.toString()) }
        amount.toDouble()
    }catch(e:Exception){
        0.0
    }

}

fun String.toLocalCurrency():String{
    return if(this.last().toString() == "."){
        this
    }else{
        val df = DecimalFormat("###,###.##") // or pattern "###,###.##$"
        df.format(parseAmount(this))
    }
}