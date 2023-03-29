package es.jccarrillo.ecost.main.calculation.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import es.jccarrillo.ecost.main.calculation.domain.repository.LastValueRepository
import kotlinx.coroutines.flow.firstOrNull
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LastValueRepositoryImpl @Inject constructor(@ApplicationContext context: Context) :
    LastValueRepository {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "lastValue")

    private val dataStore = context.dataStore

    override suspend fun set(value: String) {
        dataStore.edit {
            it[LAST_VALUE] = value
        }
    }

    override suspend fun get(): String {
        return dataStore.data.firstOrNull()?.get(LAST_VALUE) ?: ""
    }

    companion object {

        val LAST_VALUE = stringPreferencesKey("LAST_VALUE")

    }
}