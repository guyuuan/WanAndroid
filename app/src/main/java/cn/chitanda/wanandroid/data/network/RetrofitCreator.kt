package cn.chitanda.wanandroid.data.network

import android.util.Log
import com.tencent.mmkv.MMKV
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://www.wanandroid.com/"

object RetrofitCreator {
    private const val TAG = "RetrofitCreator"

    private val builder by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor { message ->
                    Log.i(
                        TAG,
                        message
                    )
                }.apply { level = HttpLoggingInterceptor.Level.BODY })
                    .addInterceptor(HttpLoggingInterceptor { log ->
                        Log.i("RetrofitLog", "log: $log")
                    }.apply { level = HttpLoggingInterceptor.Level.BASIC })
                    .addInterceptor(ReceivedCookiesInterceptor())
                    .addInterceptor(AddCookiesInterceptor())
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
    }

    private val retrofit by lazy {
        builder.build()
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}

class ReceivedCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!chain.request().url.toString().startsWith(BASE_URL)) return chain.proceed(chain.request())
        val response = chain.proceed(chain.request())
        if (response.headers("Set-Cookie").isNotEmpty()) {
            val cookies = HashSet<String>()
            response.headers("Set-Cookie").forEach { cookie ->
                cookies.add(cookie)
            }
            val mmkv = MMKV.defaultMMKV()
            mmkv?.encode("cookie", cookies)
        }
        return response
    }
}

class AddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!chain.request().url.toString().startsWith(BASE_URL)) return chain.proceed(chain.request())
        val mmkv = MMKV.defaultMMKV()
        val cookies = mmkv?.decodeStringSet("cookie", mutableSetOf()) ?: emptySet()
        val builder = chain.request().newBuilder()
        cookies.forEach { cookie ->
            builder.addHeader("Cookie", cookie)
        }
        return chain.proceed(builder.build())
    }
}