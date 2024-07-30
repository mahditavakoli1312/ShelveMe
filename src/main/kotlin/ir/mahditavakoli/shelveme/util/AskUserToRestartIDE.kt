package ir.mahditavakoli.shelveme.util

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.Messages

fun askUserToRestartIDE() {
    val response = Messages.showYesNoDialog(
        "The IDE needs to be restarted to apply changes. Do you want to restart now?",
        "Restart IDE",
        Messages.getQuestionIcon()
    )

    if (response == Messages.YES) {
        ApplicationManager.getApplication().restart()
    }
}