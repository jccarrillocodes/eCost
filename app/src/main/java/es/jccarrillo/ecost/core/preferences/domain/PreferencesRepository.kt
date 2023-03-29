package es.jccarrillo.ecost.core.preferences.domain

import kotlinx.coroutines.flow.Flow


interface PreferencesRepository {
    val data: Flow<PreferencesData>

    suspend fun set(value: PreferencesData)
}