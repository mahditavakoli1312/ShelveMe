package ir.mahditavakoli.shelveme.util

import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager

fun getProjectName(project: Project): String {
    return ModuleRootManager.getInstance(ModuleManager.getInstance(project).modules[0])
        .module.name.toString()
}