package com.letsee.kakaologin1

import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApi
import com.kakao.sdk.user.UserApiClient
import com.unity3d.player.UnityPlayer

class uKakao {
    fun kakaoLogin() : Boolean {
        var isSignedIn : Boolean = false

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                println("로그인 실패 : $error")
                // Log.e("KakaoLog", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                isSignedIn = false
                println(isSignedIn)
            } else if (token != null) {
                println("로그인 성공 ${token.accessToken}")
                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    if (error != null) {
//                        Log.e(TAG, "토큰 정보 보기 실패", error)
                        println("토큰 정보 보기 실패 : ${error}")
                        isSignedIn = false
                        println(isSignedIn)

                    }
                    else if (tokenInfo != null) {
//                        Log.i(TAG, "토큰 정보 보기 성공" +
//                                "\n회원번호: ${tokenInfo.id}" +
//                                "\n만료시간: ${tokenInfo.expiresIn} 초")


                        println("토큰 정보 보기 성공" +
                                "\n회원번호: ${tokenInfo.id}" +
                                "\n만료시간: ${tokenInfo.expiresIn} 초")
                        isSignedIn = true
                        println(isSignedIn)
                    }
                }

            }

        }

        if(UserApiClient.instance.isKakaoTalkLoginAvailable(UnityPlayer.currentActivity)) {
            UserApiClient.instance.loginWithKakaoTalk(UnityPlayer.currentActivity, callback = callback )
            return isSignedIn
        } else {
            UserApiClient.instance.loginWithKakaoAccount(UnityPlayer.currentActivity, callback = callback)
            return isSignedIn
        }

//        return isSignedIn
    }

    fun kakaoLogout() {

        UserApiClient.instance.logout { error ->
            if (error != null) {
                //                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                println("로그아웃 실패 SDK에서 토큰 삭제됨 : $error")
            }
            else {
                //                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                println("로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }

    fun GetUserId(): String {
        var userId : String = ""

        UserApiClient.instance.me { user, error ->
            if(error != null) {
                println("사용자 정보 요청 실패 : ${error}")
            }
            else if (user != null) {
                userId = user.id.toString()

                println("사용자 정보 요청 성공" +
                        "\n회원번호 : ${user.id}")
            }
        }
        return userId
    }
    fun GetUserEmail() {
        var userEmail : String

        UserApiClient.instance.me { user, error ->
            if(error != null) {
                println("사용자 정보 요청 실패 : ${error}")
            }
            else if (user != null) {
                userEmail = user.kakaoAccount?.email.toString()

                println("\n이메일 : ${user.kakaoAccount?.email}")
            }
        }
    }
}