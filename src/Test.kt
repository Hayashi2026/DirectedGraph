package com.yangcong345.android.phone.application

fun main(args: Array<String>) {

    val moduleA = object: ApplicationTask {
        override fun toString(): String {
            return "A"
        }
    }
    val moduleB = object: ApplicationTask {
        override fun toString(): String {
            return "B"
        }
    }
    val moduleC = object: ApplicationTask {
        override fun toString(): String {
            return "C"
        }
    }
    val moduleD = object: ApplicationTask {
        override fun toString(): String {
            return "D"
        }
    }
    val moduleE = object: ApplicationTask {
        override fun toString(): String {
            return "E"
        }
    }
    val moduleF = object: ApplicationTask {
        override fun toString(): String {
            return "F"
        }
    }

    moduleA.dependsOn(moduleB, moduleC)
    moduleC.dependsOn(moduleD)
    moduleF.dependsOn(moduleE, moduleA)
    moduleE.dependsOn(moduleD)
//    moduleE.dependsOn(moduleF)
//    moduleB.dependsOn(moduleE)

    println(graph.topologicalSort().toString())
}

val graph = DirectedGraph<ApplicationTask>()

fun ApplicationTask.dependsOn(vararg tasks: ApplicationTask) {
    for (task in tasks) {
        graph.addEdge(DirectedGraph.Edge(this, task))
    }
}