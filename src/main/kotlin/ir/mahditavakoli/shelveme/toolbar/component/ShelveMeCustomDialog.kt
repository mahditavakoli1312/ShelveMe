package ir.mahditavakoli.shelveme.toolbar.component

import javax.swing.JButton
import javax.swing.JDialog
import javax.swing.JLabel

class ShelveMeCustomDialog(
    val onClickListener: () -> Unit,
    val dialogTitle: String,
    val buttonTitle: String
) : JDialog() {
    private val dialogTitleJ = JLabel(dialogTitle)
    private val dialogButton = JButton(buttonTitle)

    init {

        title = dialogTitle

        add(dialogButton)

        dialogButton.addActionListener {
            onClickListener()
        }

        defaultCloseOperation = DISPOSE_ON_CLOSE
        setSize(300, 150)
    }
}