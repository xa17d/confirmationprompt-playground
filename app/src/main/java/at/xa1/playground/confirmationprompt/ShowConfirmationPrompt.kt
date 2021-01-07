package at.xa1.playground.confirmationprompt

import android.content.Context
import android.security.ConfirmationAlreadyPresentingException
import android.security.ConfirmationCallback
import android.security.ConfirmationNotAvailableException
import android.security.ConfirmationPrompt
import java.util.concurrent.Executor

fun showConfirmationPrompt(
    context: Context,
    promptText: String,
    extraDataString: String,
    logView: LogView
) {

    val logPrefix = "[${++showConfirmationPromptCounter}] "
    logView.log("${logPrefix}start showConfirmationPrompt")

    // Copied from https://developer.android.com/training/articles/security-android-protected-confirmation
    // and only slightly adjusted:

    val extraData: ByteArray = extraDataString.toByteArray()
    val threadReceivingCallback = Executor { runnable -> runnable.run() }
    val callback = object : ConfirmationCallback() {
        override fun onConfirmed(dataThatWasConfirmed: ByteArray) {
            super.onConfirmed(dataThatWasConfirmed)
            logView.log(
                "${logPrefix}onConfirmed: dataThatWasConfirmed =\n" +
                    "      hex: ${dataThatWasConfirmed.toHex()}\n" +
                    "      escaped ascii: ${dataThatWasConfirmed.toAsciiOrHex()}"
            )
        }

        override fun onDismissed() {
            super.onDismissed()
            logView.log("${logPrefix}onDismissed")
        }

        override fun onCanceled() {
            super.onCanceled()
            logView.log("${logPrefix}onCanceled")
        }

        override fun onError(e: Throwable?) {
            super.onError(e)
            logView.log("${logPrefix}onError: e = $e")
        }
    }

    val dialog = try {
        ConfirmationPrompt.Builder(context)
            .setPromptText(promptText)
            .setExtraData(extraData)
            .build()
    } catch (e: IllegalArgumentException) {
        // is thrown when promptText is empty or null
        logView.error("${logPrefix}ConfirmationPrompt.Builder.build throws", e)
        return
    }

    try {
        dialog.presentPrompt(threadReceivingCallback, callback)
    } catch (e: ConfirmationAlreadyPresentingException) {
        // another confirmation is currently visible
        logView.error("${logPrefix}presentPrompt throws", e)
    } catch (e: ConfirmationNotAvailableException) {
        // device doesn't support ConfirmationPrompt
        logView.error("${logPrefix}presentPrompt throws", e)
    } catch (e: IllegalArgumentException) {
        // invalid input, e.g. a '\n' in promptText
        logView.error("${logPrefix}presentPrompt throws", e)
    }
}

var showConfirmationPromptCounter = 0