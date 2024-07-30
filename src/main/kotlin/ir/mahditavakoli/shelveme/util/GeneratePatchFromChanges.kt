package ir.mahditavakoli.shelveme.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.VcsException
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.intellij.openapi.vcs.changes.CommitContext
import com.intellij.openapi.vcs.changes.patch.CreatePatchCommitExecutor
import com.intellij.openapi.vcs.changes.patch.PatchWriter
import java.io.File

suspend fun createPatch(project: Project) {

    val currentDate = getPersianCurrentDateYMD()
    val projectName = getProjectName(project)
    var headBranchName = ""

    val changeListManager = ChangeListManager.getInstance(project)
    val changes = changeListManager.allChanges

    val insResult = getHeadBranchName(project)
    headBranchName = insResult.message + "e" + insResult.errorMessage

    val patchDir = project.basePath + "/ShelveMe Patch:$projectName:$headBranchName"

    // Ensure the directory exists
    val patchDirFile = File(patchDir)
    if (!patchDirFile.exists()) {
        patchDirFile.mkdirs()
    }

    val patchFile = File(patchDir + "/" + "$currentDate.patch")

    // Ensure the file exists
    if (!patchFile.exists()) {
        patchFile.createNewFile()
    }

    try {

        // build patch
        val patches = CreatePatchCommitExecutor.DefaultPatchBuilder(project).buildPatches(
            patchDirFile.toPath(),
            changes,
            true,
            false
        )

        PatchWriter.writePatches(
            project,
            patchFile.toPath(),
            patchDirFile.toPath(),
            patches,
            CommitContext()
        )
        println("create patch in : ${patchDirFile.path} ")

    } catch (e: VcsException) {
        e.printStackTrace()
    }
}