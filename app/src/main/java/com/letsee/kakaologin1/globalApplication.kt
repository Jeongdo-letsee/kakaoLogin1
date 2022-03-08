package com.letsee.kakaologin1

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class globalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "3607a2d562f37198d5f1aa7c71af9f73")

        val keyHash = Utility.getKeyHash(this)
        println("키 해쉬 : $keyHash")



    }

}