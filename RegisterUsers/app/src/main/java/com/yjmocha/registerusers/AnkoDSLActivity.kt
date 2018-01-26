package com.yjmocha.registerusers

import android.app.Activity
import android.os.Bundle
import org.jetbrains.anko.button
import org.jetbrains.anko.editText
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import org.jetbrains.anko.verticalLayout

/**
 * Created by yunjeonghwang on 2018. 1. 26..
 */
class AnkoDSLActivity : Activity() {
//    xml 파일을 생성하지 않아도 된다. 클래스 하나로 내용 구성 가능
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            val name = editText {
                hint = "이름을 넣으세요"
                textSize = 20f
            }
            button("Show") {
                onClick { toast("안녕하세여, ${name.text}!") }
            }
            button("종료") {
                onClick {finish()}
            }
        }
    }
}