package es.jccarrillo.ecost.core.preferences.data

import androidx.datastore.core.DataStore
import es.jccarrillo.ecost.core.preferences.data.local.LocalPreferences
import es.jccarrillo.ecost.core.preferences.data.mapper.toData
import es.jccarrillo.ecost.core.preferences.data.mapper.toDomain
import es.jccarrillo.ecost.core.preferences.domain.PreferencesData
import es.jccarrillo.ecost.core.preferences.domain.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(private val dataStore: DataStore<LocalPreferences>) :
    PreferencesRepository {

    override val data: Flow<PreferencesData>
        get() = dataStore.data.map { it.toDomain() }

    override suspend fun set(value: PreferencesData) {
        dataStore.updateData {
            value.toData()
        }
    }
}