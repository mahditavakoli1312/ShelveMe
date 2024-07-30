package ir.mahditavakoli.shelveme.toolbar.component

import ir.mahditavakoli.shelveme.datasource.local.ShelveState
import org.jdesktop.swingx.VerticalLayout
import javax.swing.JLabel
import javax.swing.JPanel

class ShelveMeConfigStatus : JPanel() {

    init {
        layout = VerticalLayout(10)
        updateConfigStatus()
    }

    fun updateConfigStatus() {
        val state = ShelveState.getShelveSate()
        val titleLabel = JLabel("ShelveMe status :")
        val autoShelveLabel = JLabel("auto-shelve is on")
        val periodicallyShelveLabel = JLabel("periodically-shelve is on : ${state.periodDuration} min")

        removeAll()
        add(titleLabel)

        if (state.autoShelve)
            add(autoShelveLabel)
        if (state.periodically)
            add(periodicallyShelveLabel)

        repaint()
    }
}

