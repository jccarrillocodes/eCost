package es.jccarrillo.ecost.core.preferences.data.local

import androidx.datastore.core.Serializer
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

class PreferencesSerializer(override val defaultValue: LocalPreferences) :
    Serializer<LocalPreferences> {

    private val decoder: Gson = Gson()

    override suspend fun readFrom(input: InputStream): LocalPreferences = runCatching {
        return withContext(Dispatchers.IO) {
            return@withContext decoder.fromJson(input.reader(), LocalPreferences::class.java)
        }
    }.getOrElse {
        return defaultValue
    }

    override suspend fun writeTo(t: LocalPreferences, output: OutputStream) {
        withContext(Dispatchers.IO) {
            val json = decoder.toJson(t)
            output.write(json.encodeToByteArray())
        }
    }

}