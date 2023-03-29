package es.jccarrillo.ecost.main.calculation.domain.repository

interface LastValueRepository {
    suspend fun set(value: String)

    suspend fun get(): String
}