package android.rezkyaulia.com.hellokotlin.ankoTry

import android.R
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirstActivityUi().setContentView(this)

    }

    class FirstActivityUi : AnkoComponent<FirstActivity>{
        override fun createView(ui: AnkoContext<FirstActivity>): View = with(ui){
            verticalLayout(){
                padding = dip(16)

                val name = editText{
                    hint = "What's your name ?"
                }

                button("Say Hello"){
                    backgroundColor = Color.BLACK
                    textColor = Color.WHITE
                    onClick { toast("hello, my name is ${name.text}") }
                }.lparams(width = matchParent){
                    topMargin = dip(5)
                }
            }
        }

    }
}