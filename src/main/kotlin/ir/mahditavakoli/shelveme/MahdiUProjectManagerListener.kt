package ir.mahditavakoli.shelveme

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.vcs.VcsException
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.intellij.openapi.vcs.changes.shelf.ShelveChangesManager
import java.text.SimpleDateFormat
import java.util.*

class MahdiUProjectManagerListener : ProjectManagerListener {

    override fun projectClosing(project: Project) {
        println(project.name)
        val dateTime = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Date())
        val shelfName = "Shelve_$dateTime"

        val changeListManager = ChangeListManager.getInstance(project)
        val changes = changeListManager.allChanges
        println("changes "+changes)

        try {
            val shelveManager = ShelveChangesManager.getInstance(project)
            val shelvedChangeList = shelveManager.shelveChanges(changes.toList(), shelfName, true)
            println("shelvedChangeList : "+shelvedChangeList)
            println("shelfName : "+shelfName)
            if (shelvedChangeList.changes?.isNotEmpty() == true) {
                println("Changes successfully shelved: $shelfName")
                println("Changes successfully shelved in : ${shelvedChangeList.path}")
                println("Changes successfully shelved in : ${shelvedChangeList.name}")
                println("Changes successfully shelved in : ${shelvedChangeList.displayName}")
            } else {
                println("No changes to shelve.")
            }
        } catch (e: VcsException) {
            e.printStackTrace()
        }
    }
}
