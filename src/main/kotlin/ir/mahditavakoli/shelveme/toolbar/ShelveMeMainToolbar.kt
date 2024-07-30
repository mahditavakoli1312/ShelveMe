package ir.mahditavakoli.shelveme.toolbar

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.JBColor
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import com.intellij.util.ui.JBUI
import ir.mahditavakoli.shelveme.datasource.local.ShelveState
import ir.mahditavakoli.shelveme.toolbar.component.ShelveMeConfigStatus
import ir.mahditavakoli.shelveme.toolbar.component.ShelveMeCustomJComboBox
import ir.mahditavakoli.shelveme.util.askUserToRestartIDE
import ir.mahditavakoli.shelveme.util.createPatch
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jdesktop.swingx.VerticalLayout
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class ShelveMeMainToolbar : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow(project)
        val contentFactory = ApplicationManager.getApplication().getService(ContentFactory::class.java)
        val content: Content = contentFactory.createContent(myToolWindow.content, "ShelveMe", true)
        toolWindow.contentManager.addContent(content)
    }
}

@OptIn(DelicateCoroutinesApi::class)
class MyToolWindow(
    val project: Project
) {

    val content: JPanel = JPanel().apply {
        border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(JBColor.BLACK), // Outer border
            JBUI.Borders.empty(10) // Inner padding
        )
        layout = VerticalLayout(10)
        alignmentX = JPanel.CENTER_ALIGNMENT
        alignmentY = JPanel.CENTER_ALIGNMENT
    }

    private val state = ShelveState.getShelveSate()

    private val shelveBtn: JButton = JButton("Create patch")
    private val autoShelveLabel = JLabel("Do you want to get a shelve when you leave ?")
    private val autoShelveButton: JButton = JButton("Auto shelve")

    private val periodicallyShelveLabel = JLabel("Do you want to periodically get a shelve from your changes ?")
    private val periodicallyShelveButton: JButton = JButton("Periodically shelve")

    private val shelveMeConfigStatus = ShelveMeConfigStatus()

    init {

        content.add(shelveMeConfigStatus)
        content.add(autoShelveLabel)
        content.add(autoShelveButton)
        content.add(periodicallyShelveLabel)
        content.add(periodicallyShelveButton)
        content.add(ShelveMeCustomJComboBox(
            "Enter Period Duration :",
            mapOf("1" to "1 min", "10" to "10 min", "30" to "30 min", "60" to "60 min")
        ) { key, value ->
            println("select $value mins to shelve auto")
            kotlin.runCatching {
                ShelveState.savePeriodDuration(value.toLong())
                shelveMeConfigStatus.updateConfigStatus()
                askUserToRestartIDE()
            }
        })

        content.add(JLabel())

        content.add(shelveBtn)

        // region auto-shelve
        autoShelveButton.addActionListener {
            ShelveState.saveAutoShelveState(!state.autoShelve)
            shelveMeConfigStatus.updateConfigStatus()
        }
        // endregion auto-shelve

        // region patch button
        shelveBtn.addActionListener {
            GlobalScope.launch(Dispatchers.IO) {
                createPatch(project)
            }
        }
        // endregion patch button

        // region periodically-shelve
        periodicallyShelveButton.addActionListener {
            ShelveState.savePeriodicallyShelveState(!state.periodically)
            shelveMeConfigStatus.updateConfigStatus()
        }
        // endregion periodically-shelve

    }
}
