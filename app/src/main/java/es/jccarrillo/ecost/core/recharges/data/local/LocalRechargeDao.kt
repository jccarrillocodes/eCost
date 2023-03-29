package es.jccarrillo.ecost.core.recharges.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import es.jccarrillo.ecost.core.recharges.data.local.entity.LocalRecharge

@Dao
interface LocalRechargeDao {

    @Query("SELECT * FROM LocalRecharge")
    fun getAll(): List<LocalRecharge>

    @Query("SELECT * FROM LocalRecharge WHERE timestampEpoch >= :epoc")
    fun getAllSince(epoc: Long): List<LocalRecharge>

    @Insert
    fun insert(data: LocalRecharge)

    @Query("DELETE FROM LocalRecharge")
    fun removeAll()

    @Query("DELETE FROM LocalRecharge WHERE timestampEpoch = :timestampEpoch")
    fun remove(timestampEpoch: Long)
}