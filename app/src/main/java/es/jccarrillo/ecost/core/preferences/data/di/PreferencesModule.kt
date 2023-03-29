package es.jccarrillo.ecost.core.preferences.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.jccarrillo.ecost.core.preferences.data.PreferencesRepositoryImpl
import es.jccarrillo.ecost.core.preferences.data.local.*
import es.jccarrillo.ecost.core.preferences.domain.PreferencesRepository
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
interface PreferencesModule {

    @Binds
    fun bindPreferencesRepository(repo: PreferencesRepositoryImpl): PreferencesRepository
}

@Module
@InstallIn(SingletonComponent::class)
class LocalPreferencesModule {

    private val default = LocalPreferences(
        carName = "My car 🚙",
        currencyFormat = "%.3f€",
        consumptionLabel = "€/kWh",
        kwPer100KM = 24.0,
        gasReferences = LocalGasReferences(
            diesel = LocalGasReference(
                pricePerLiter = 1.6,
                litersPer100KM = 5.0
            ),
            gasoline = LocalGasReference(
                pricePerLiter = 1.6,
                litersPer100KM = 8.0
            )
        )
    )

    private val Context.dataStore by dataStore("preferences.json", PreferencesSerializer(default))


    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<LocalPreferences> {
        return context.dataStore
    }

}