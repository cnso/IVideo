package org.jash.network

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

/**
 * Created by Jash
 */
class StringSafeTypeAdapterFactory : TypeAdapterFactory {
    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T> {
        return StringSafeTypeAdapter(gson?.getDelegateAdapter(this, type)!!, type)
    }
}
class StringSafeTypeAdapter<T>(val adapter: TypeAdapter<T>, val type: TypeToken<T>?):TypeAdapter<T>() {
    override fun write(out: JsonWriter?, value: T) {
        adapter.write(out, value)
    }

    override fun read(`in`: JsonReader?): T? {
        if (type?.rawType != String::class.java && `in`?.peek() == JsonToken.STRING) {
            `in`.nextString()
            return null
        }
        return adapter.read(`in`)
    }
}