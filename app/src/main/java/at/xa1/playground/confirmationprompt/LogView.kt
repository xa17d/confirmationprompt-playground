package at.xa1.playground.confirmationprompt

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ScrollView
import android.widget.TextView

class LogView(
    private val logTextView: TextView,
    private val scrollView: ScrollView
) {
    private val handler = Handler(Looper.getMainLooper())

    fun log(message: String) {
        logTextView.post {
            @SuppressLint("SetTextI18n")
            logTextView.text = logTextView.text.toString() + message + "\n"
        }
        scrollToBottom()
    }

    fun error(message: String, throwable: Throwable) {
        log("❌ $message ${throwable::class.java.simpleName}: ${throwable.message}")
    }

    private fun scrollToBottom() {
        scrollView.postDelayed({
            scrollView.fullScroll(View.FOCUS_DOWN)
        }, 250)
    }
}