package ir.mahditavakoli.shelveme

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.vcs.VcsException
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.intellij.openapi.vcs.changes.shelf.ShelveChangesManager
import ir.mahditavakoli.shelveme.util.getHeadBranchName
import ir.mahditavakoli.shelveme.util.getPersianCurrentDateYMD
import ir.mahditavakoli.shelveme.util.getProjectName
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MahdiUProjectManagerListener : ProjectManagerListener {

    @OptIn(DelicateCoroutinesApi::class)
    override fun projectClosing(project: Project) {
        println(project.name)
        val dateTime = getPersianCurrentDateYMD()
        val projectName = getProjectName(project)
        var shelfName = "ShelveMe__${dateTime}__$projectName"
        GlobalScope.launch(Dispatchers.IO) {
            val branchName = getHeadBranchName(project)
            shelfName = "ShelveMe__${dateTime}__${projectName}__${branchName}"
        }

        val changeListManager = ChangeListManager.getInstance(project)
        val changes = changeListManager.allChanges
        println("changes " + changes)

        try {
            val shelveManager = ShelveChangesManager.getInstance(project)
            val shelvedChangeList = shelveManager.shelveChanges(changes.toList(), shelfName, true)
            println("shelvedChangeList : " + shelvedChangeList)
            println("shelfName : " + shelfName)
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
