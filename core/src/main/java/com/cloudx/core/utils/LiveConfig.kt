package com.cloudx.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.SparseArray
import com.cloudx.core.error.CodeBean
import com.cloudx.core.error.ErrorCodeKts
import com.cloudx.core.interceptor.LogInterceptor
import com.cloudx.core.interceptor.RequestInterceptor
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull

/**
 * Created by Petterp
 * on 2020-01-13
 * Function:
 */
object LiveConfig {
    val config = Config()

    class Config {
        lateinit var mContext: Context
        var mIsCache = true
        var mWriteTimeout = 30L
        var mConnectTimeout = 10L
        lateinit var mBaseUrl: String
        var mInterceptorList: ArrayList<Interceptor> = ArrayList(5)
        val mGson = Gson()
        val mediaType = "application/json;charset=UTF-8".toMediaTypeOrNull()
        @SuppressLint("NewApi")
        var downloadName="livehttp"
    }


    /**
     *  初始化
     */
    fun initDefault(context: Context, url: String) {
        config.mContext = context
        config.mBaseUrl = url
        config.mInterceptorList.add(LogInterceptor())
        config.downloadName=context.packageName
    }

    fun baseUrl(url: String): LiveConfig {
        config.mBaseUrl = url
        return this
    }

    fun context(context: Context): LiveConfig {
        config.mContext = context
        return this
    }

    fun connectTimeout(time: Long): LiveConfig {
        config.mConnectTimeout = time
        return this
    }

    fun WriteTimeout(time: Long): LiveConfig {
        config.mWriteTimeout = time
        return this
    }

    fun isCache(cache: Boolean): LiveConfig {
        config.mIsCache = cache
        if (config.mIsCache) config.mInterceptorList.add(RequestInterceptor())
        return this
    }

    fun interCeptors(list: ArrayList<Interceptor>): LiveConfig {
        config.mInterceptorList = list
        return this
    }

    fun interCeptor(interceptor: Interceptor): LiveConfig {
        config.mInterceptorList.add(interceptor)
        return this
    }

    fun errorKtx(code: Int, codeBean: CodeBean): LiveConfig {
        ErrorCodeKts.putCode(code, codeBean)
        return this
    }

    fun errorKtx(sprArray: SparseArray<CodeBean>): LiveConfig {
        ErrorCodeKts.putCodeAll(sprArray)
        return this
    }

}
