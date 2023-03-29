package es.jccarrillo.ecost.main.calculation.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import es.jccarrillo.ecost.main.calculation.data.LastValueRepositoryImpl
import es.jccarrillo.ecost.main.calculation.domain.repository.LastValueRepository

@Module
@InstallIn(ActivityRetainedComponent::class)
interface LastValueModule {

    @Binds
    fun bindLastValueRepository(repo: LastValueRepositoryImpl): LastValueRepository
}