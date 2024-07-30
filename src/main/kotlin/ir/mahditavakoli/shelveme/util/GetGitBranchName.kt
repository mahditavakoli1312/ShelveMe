package ir.mahditavakoli.shelveme.util

import com.intellij.openapi.project.Project
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
        val sb = StringBuilder()
        val sbError = StringBuilder()
        var exitCode = -999

        try {
            val path = project.basePath

            val process = ProcessBuilder(commandArgs)
                .directory(File(path))
                .start()

            val job1 = launch {
                val reader = process.inputStream.bufferedReader()
                var line = reader.readLineAsync()
                while (line != null) {
                    sb.append(line).append("\n")
                    line = reader.readLineAsync()
                }
            }

            val jobError = launch {
                val reader = process.errorStream.bufferedReader()
                var line = reader.readLineAsync()
                while (line != null) {
                    sb.append(line).append("\n")
                    line = reader.readLineAsync()
                }
            }

            exitCode = process.awaitExit()
            job1.join()
            jobError.join()
        } catch (e: Exception) {
            println("error : $e")
        }

        ProcessResult(
            exitCode = exitCode,
            message = sb.toString(),
            errorMessage = sbError.toString()
        )
    }
}


data class ProcessResult(val exitCode: Int, val message: String, val errorMessage: String)
