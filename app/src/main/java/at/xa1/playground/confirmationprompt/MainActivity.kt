package at.xa1.playground.confirmationprompt

import android.os.Bundle
import android.security.ConfirmationPrompt
import androidx.appcompat.app.AppCompatActivity
import at.xa1.playground.confirmationprompt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var logView: LogView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            logView = LogView(logTextView, logScrollView)

            buttonShow.setOnClickListener {
                showConfirmationPrompt()
            }

            buttonShow.setOnLongClickListener {
                showConfirmationPrompt()
                showConfirmationPrompt()
                true
            }
        }

        showWelcomeMessage()
    }

    private fun showWelcomeMessage() {
        logView.log(
            "💡 Hey!\n" +
                "is ConfirmationPrompt supported on your device: " +
                "${ConfirmationPrompt.isSupported(this@MainActivity)}\n" +
                "press button show ConfirmationPrompt once, " +
                "long press to call showConfirmationPrompt twice.\n"+
                "---"
        )
    }

    private fun showConfirmationPrompt() {
        showConfirmationPrompt(
            context = this@MainActivity,
            promptText = binding.editTextPromptText.text.toString(),
            extraDataString = binding.editTextExtraData.text.toString(),
            logView = logView
        )
    }

    override fun onPause() {
        super.onPause()
        logView.log("Activity.onPause")
    }

    override fun onStop() {
        super.onStop()
        logView.log("Activity.onStop")
    }
}