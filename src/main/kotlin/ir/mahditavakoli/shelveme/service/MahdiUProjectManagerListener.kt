package ir.mahditavakoli.shelveme.service

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import ir.mahditavakoli.shelveme.datasource.local.ShelveState
import ir.mahditavakoli.shelveme.toolbar.component.ShelveMeCustomDialog
import ir.mahditavakoli.shelveme.util.shelveChanges
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MahdiUProjectManagerListener : ProjectManagerListener {

    private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
    private val state = ShelveState.getShelveSate()

    override fun projectOpened(project: Project) {
        super.projectOpened(project)
        println("Project open : ${project.name}")

        scheduler.scheduleAtFixedRate({
            shelveChanges(project)
        }, 0, state.periodDuration, TimeUnit.MINUTES)

    }

    override fun projectClosingBeforeSave(project: Project) {
        super.projectClosingBeforeSave(project)
        val state = ShelveState.getShelveSate()

        println("start shelve me process")
        if (state.autoShelve) {
            println("start shelve me process : auto-shelve")
            shelveChanges(project)
        }
    }
}
