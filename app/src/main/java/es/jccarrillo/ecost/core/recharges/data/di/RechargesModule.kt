package es.jccarrillo.ecost.core.recharges.data.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.jccarrillo.ecost.core.recharges.data.RechargeRepositoryImpl
import es.jccarrillo.ecost.core.recharges.data.local.LocalRechargeDao
import es.jccarrillo.ecost.core.recharges.data.local.entity.LocalRechargeDatabase
import es.jccarrillo.ecost.core.recharges.domain.repository.RechargeRepository
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
interface RechargesModule {

    @Binds
    fun bindRepository(repository: RechargeRepositoryImpl): RechargeRepository

}

@Module
@InstallIn(SingletonComponent::class)
class LocalRechargesModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LocalRechargeDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = LocalRechargeDatabase::class.java,
            name = "localRecharges"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalRechargeDao(database: LocalRechargeDatabase): LocalRechargeDao {
        return database.localRechargeDao()
    }

}