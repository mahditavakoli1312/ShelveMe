package ir.mahditavakoli.shelveme.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.VcsException
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.intellij.openapi.vcs.changes.shelf.ShelveChangesManager
import com.intellij.openapi.vcs.changes.shelf.ShelvedChangeList
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
fun shelveChanges(project: Project) {
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
    println("changes $changes")

    // shelve process
    try {
        val shelveManager = ShelveChangesManager.getInstance(project)
        val shelvedChangeList = shelveManager.shelveChanges(changes.toList(), shelfName, true)

        println("shelvedChangeList : $shelvedChangeList")
        println("shelfName : $shelfName")
        if (shelvedChangeList.changes?.isNotEmpty() == true) {

            println("Changes successfully shelved: $shelfName")
            println("Changes successfully shelved in : ${shelvedChangeList.path}")
            println("Changes successfully shelved in : ${shelvedChangeList.name}")
            println("Changes successfully shelved in : ${shelvedChangeList.displayName}")
            // Unshelve changes
            unshelveChanges(project, shelvedChangeList)
        } else {
            println("No changes to shelve.")
        }
    } catch (e: VcsException) {
        e.printStackTrace()
    }

}

private fun unshelveChanges(project: Project, shelvedList: ShelvedChangeList) {
    val shelveManager = ShelveChangesManager.getInstance(project)
    try {
        val changesToUnshelve = shelvedList.changes
        if (changesToUnshelve?.isNotEmpty() == true) {
            shelveManager.unshelveChangeList(shelvedList, changesToUnshelve.toList(), null, null, true)
            println("Unshelved changes for project: ${project.name}")
        }
    } catch (e: VcsException) {
        e.printStackTrace()
    }
}