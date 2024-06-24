package ir.mahditavakoli.shelveme.toolbar.component

import com.jetbrains.rd.util.first
import org.apache.commons.lang3.tuple.MutablePair
import org.jdesktop.swingx.HorizontalLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JLabel
import javax.swing.JPanel

class ShelveMeCustomJComboBox(
    title: String,
    items: Map<String, String>,
    onItemSelected: (String, String) -> Unit
) : JPanel() {

    private var itemMap: Map<String, String>
    private val comboBox: JComboBox<String>
    private val label: JLabel
    private val btn_submit: JButton
    private var selectedItem = MutablePair<String, String>()

    init {

        selectedItem.setRight(items.first().key)
        selectedItem.setLeft(items.first().value)

        layout = HorizontalLayout(10)

        btn_submit = JButton("Submit")
        // Initialize the map and combo box
        itemMap = items
        comboBox = JComboBox<String>()
        for (key in itemMap.values) {
            comboBox.addItem(key)
        }

        // Add action listener to the combo box
        comboBox.addActionListener {
            val selectedKey = comboBox.selectedItem as? String ?: return@addActionListener
            val value = itemMap[selectedKey] ?: return@addActionListener

            selectedItem.setRight(selectedKey)
            selectedItem.setLeft(value)

            onItemSelected(selectedItem.right, selectedItem.left)

        }

        btn_submit.addActionListener(
            ButtonClickListener(
                onItemSelected,
                selectedItem
            )
        )

        // Create label for the title
        label = JLabel(title)

        add(label)
        // Add combo box to the panel
        add(comboBox)
        add(btn_submit)

    }

    fun updateItems(
        items: Map<String, String>
    ) {
        comboBox.removeAllItems()
        itemMap = items
        for (key in items.keys) {
            comboBox.addItem(key)
        }
    }
}


class ButtonClickListener(
    val onItemSelected: (String, String) -> Unit,
    val selectedItem: MutablePair<String, String>
) : ActionListener {
    override fun actionPerformed(e: ActionEvent) {
        onItemSelected(selectedItem.right, selectedItem.left)
    }
}
