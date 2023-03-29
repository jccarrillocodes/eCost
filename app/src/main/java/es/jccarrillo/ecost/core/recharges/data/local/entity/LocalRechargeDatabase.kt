package es.jccarrillo.ecost.core.recharges.data.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import es.jccarrillo.ecost.core.recharges.data.local.LocalRechargeDao

@Database(entities = [LocalRecharge::class], version = 1)
abstract class LocalRechargeDatabase : RoomDatabase() {
    abstract fun localRechargeDao(): LocalRechargeDao
}