package at.xa1.playground.confirmationprompt

import android.os.Bundle
import android.security.ConfirmationPrompt
import androidx.appcompat.app.AppCompatActivity
import at.xa1.playground.confirmationprompt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val logView = LogView(logTextView, logScrollView)

            fun showConfirmationPrompt() {
                showConfirmationPrompt(
                    context = this@MainActivity,
                    promptText = editTextPromptText.text.toString(),
                    extraDataString = editTextExtraData.text.toString(),
                    logView = logView
                )
            }

            buttonShow.setOnClickListener {
                showConfirmationPrompt()
            }

            buttonShow.setOnLongClickListener {
                showConfirmationPrompt()
                showConfirmationPrompt()
                true
            }

            logView.log(
                "💡 Hey!\n" +
                    "is ConfirmationPrompt supported on your device: " +
                    "${ConfirmationPrompt.isSupported(this@MainActivity)}\n" +
                    "press button show ConfirmationPrompt once, " +
                    "long press to call showConfirmationPrompt twice.\n"+
                    "---"
            )
        }
    }
}