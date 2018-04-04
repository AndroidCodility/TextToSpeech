package com.codility.textspeech

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                showMessage("This Language is not supported")
            } else {
                btSpeech.isEnabled = true
                speakOut()
            }
        } else {
            showMessage("Initialization Failed!")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)
    }

    fun speakOutView(view: View) {
        speakOut()
    }

    private fun speakOut() {
        val str = editText.text.toString()
        if (str.isEmpty() || str.isNullOrBlank()) {
            showMessage(getString(R.string.empty_msg))
            return
        }
        tts!!.speak(str, TextToSpeech.QUEUE_FLUSH, null)
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    public override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}