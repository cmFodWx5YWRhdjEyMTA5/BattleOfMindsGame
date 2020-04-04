class StartPipelinePlugin : Plugin<Project> {
    override fun apply(project: Project) {

        project.task("startPipeline") {
            description = "Start tasks `runDetekt` and `runTests`"
            group = "pipeline"
            dependsOn("runTests", "runDetekt", ":app:assemble")
            doLast {
                println("startPipeline passed")
            }
        }

        project.task("runDetekt") {
            description = "Run detekt for all projects"
            group = "pipeline"

            project.childProjects.forEach {
                dependsOn("${it.value.path}:detekt")
            }
            doLast {
                println("runCodeReview passed")
            }
        }

        project.task("runTests") {
            description = "Run tests for all projects"
            group = "pipeline"

            project.allprojects.forEach {
                if (it.childProjects.isEmpty()) {
                    dependsOn("${it.path}:test")
                }
            }
            doLast {
                println("runTests passed")
            }
        }
    }
}
apply<StartPipelinePlugin>()
