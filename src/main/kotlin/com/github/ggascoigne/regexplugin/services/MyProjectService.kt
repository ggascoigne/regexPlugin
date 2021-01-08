package com.github.ggascoigne.regexplugin.services

import com.intellij.openapi.project.Project
import com.github.ggascoigne.regexplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
