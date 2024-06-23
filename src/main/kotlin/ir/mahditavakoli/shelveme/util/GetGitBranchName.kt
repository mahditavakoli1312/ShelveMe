package ir.mahditavakoli.shelveme.util

import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.util.io.awaitExit
import com.intellij.util.io.readLineAsync
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


suspend fun getHeadBranchName(project: Project): ProcessResult {
    return executeCommand(project, listOf("git", "symbolic-ref", "--short", "HEAD"))
}

suspend fun executeCommand(project: Project, commandArgs: List<String>): ProcessResult = withContext(
    Dispatchers.IO
) {
    return@withContext coroutineScope {

        val path = ModuleRootManager.getInstance(ModuleManager.getInstance(project).modules[0])
            .sourceRoots[0].path

        val process = ProcessBuilder(commandArgs)
            .directory(File(path))
            .start()


        println("project path is : ${path}")

        val sb = StringBuilder()
        val job1 = launch {
            val reader = process.inputStream.bufferedReader()
            var line = reader.readLineAsync()
            while (line != null) {
                sb.append(line).append("\n")
                line = reader.readLineAsync()
            }
        }

        val sbError = StringBuilder()
        val jobError = launch {
            val reader = process.errorStream.bufferedReader()
            var line = reader.readLineAsync()
            while (line != null) {
                sb.append(line).append("\n")
                line = reader.readLineAsync()
            }
        }

        val exitCode = process.awaitExit()
        job1.join()
        jobError.join()
        println("------------------------------")
        println(exitCode)
        println(sb.toString())
        println("------------------------------")

        ProcessResult(
            exitCode = exitCode,
            message = sb.toString(),
            errorMessage = sbError.toString()
        )
    }
}


data class ProcessResult(val exitCode: Int, val message: String, val errorMessage: String)
