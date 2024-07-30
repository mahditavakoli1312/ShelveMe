package ir.mahditavakoli.shelveme.service

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import ir.mahditavakoli.shelveme.datasource.local.ShelveState
import ir.mahditavakoli.shelveme.util.createPatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MahdiUProjectManagerListener : ProjectManagerListener {

    private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
    private val state = ShelveState.getShelveSate()

    override fun projectOpened(project: Project) {
        super.projectOpened(project)

        scheduler.scheduleAtFixedRate({
            GlobalScope.launch(Dispatchers.IO) {
                createPatch(project)
            }
        }, 0, state.periodDuration, TimeUnit.MINUTES)

    }

    override fun projectClosingBeforeSave(project: Project) {
        super.projectClosingBeforeSave(project)
        val state = ShelveState.getShelveSate()

        if (state.autoShelve) {
            GlobalScope.launch(Dispatchers.IO) {
                createPatch(project)
            }
        }
    }
}
