package org.flepper.currencyconvertor.data.apppreference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okio.Path.Companion.toPath

class AppSettings(val settings: Settings) {



    var lasSuccessTimeStamp = settings.getLong(LAST_SUCCESS_ATTEMPT,0L)
    fun updateLasSuccessTimeStamp(value:Long) = settings.putLong(LAST_SUCCESS_ATTEMPT,value)


    var localLatestJson: String = settings.getString(LOCAL_LATEST_JSON,"")
     fun updateLocalLatestJson(value: String)  = settings.putString(LOCAL_LATEST_JSON, value ?: "")

    var localCurrencyJson: String  = settings.getString(LOCAL_CURRENCY_JSON,"")
    fun updateLocalCurrencyJson(value: String) = settings.putString(LOCAL_CURRENCY_JSON, value ?: "")


    companion object {
        const val LAST_SUCCESS_ATTEMPT = "LAST_SUCCESS_ATTEMPT"
        const val LOCAL_LATEST_JSON = "LOCAL_LATEST_JSON"
        const val LOCAL_CURRENCY_JSON = "LOCAL_CURRENCY_JSON"
    }
}

class AppDataStore(private val dataStore: DataStore<Preferences>){

    private val scope = CoroutineScope(Dispatchers.Default)

    val lasSuccessTimeStamp: Flow<Long> = getLong(LAST_SUCCESS_ATTEMPT)
    fun updateLasSuccessTimeStamp(value:Long) = saveToDatastore(LAST_SUCCESS_ATTEMPT,value)

    var localLatestJson = getString(LOCAL_LATEST_JSON)
    fun updateLocalLatestJson(value: String)  =  saveToDatastore(LOCAL_LATEST_JSON,value)

    var localCurrencyJson  = getString(LOCAL_CURRENCY_JSON)
    fun updateLocalCurrencyJson(value: String) = saveToDatastore(LOCAL_CURRENCY_JSON, value)


    private  fun getLong(key:Preferences.Key<Long>): Flow<Long> = dataStore.data.map { pref ->
        pref[key] ?: 0L
    }


    private fun getString(key:Preferences.Key<String>): Flow<String> = dataStore.data.map { pref ->
        pref[key] ?: ""
    }

    private fun  <T> saveToDatastore(key: Preferences.Key<T>,value: T) =  scope.launch {
        dataStore.edit { settings ->
            settings[key] = value
        }
    }




    companion object {
         val LAST_SUCCESS_ATTEMPT = longPreferencesKey("LAST_SUCCESS_ATTEMPT")
         val LOCAL_LATEST_JSON = stringPreferencesKey("LOCAL_LATEST_JSON")
         val LOCAL_CURRENCY_JSON = stringPreferencesKey("LOCAL_CURRENCY_JSON")
    }
}



fun createMainDataStore(producePath: () -> String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        corruptionHandler = null,
        migrations = emptyList(),
        scope = CoroutineScope(Dispatchers.Default + SupervisorJob()),
        produceFile = { producePath().toPath() },
    )
}

internal const val dataStoreFileName = "fl.preferences_pb"