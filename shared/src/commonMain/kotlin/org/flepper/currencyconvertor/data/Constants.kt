package org.flepper.currencyconvertor.data


const val appBaseUrl = "openexchangerates.org"
/**
 * this should be added to local.properties file in a production app
 * NB : It's here to enable smooth running of the sample app
 * e.g
 * val key: String = gradleLocalProperties(rootDir).getProperty("RATE_APP_ID")
 * & set it like this for each build type
 *  buildConfigField("String", "RATE_APP_ID", key)
 *
 * */
const val RATE_APP_ID = "43f0b208ccef428694a82bb5d9b1456e"
const val GRID_COLUMN_COUNT = 3
 val THIRTY_MIN_INTERVAL:Long = (30 * 60000)
