package com.yangcong345.android.phone.application

import java.lang.RuntimeException
import java.util.*

/**
 * An implementation of DirectedGraph for handling dependencies of Application
 */
class DirectedGraph<T> {

    private val adjacencyList = mutableMapOf<T, LinkedList<T>>()

    fun addEdge(edge: Edge<T>) {
        if (adjacencyList[edge.from] == null) {
            adjacencyList[edge.from] = LinkedList()
        }
        if (adjacencyList[edge.to] == null) {
            adjacencyList[edge.to] = LinkedList()
        }
        adjacencyList[edge.to]!!.add(edge.from)

    }

    fun topologicalSort(): List<T> {
        var vertexCount = 0
        val result = mutableListOf<T>()
        val indegreeList = getIndegreeList()
        val queue = initializeQueue(indegreeList)
        while (queue.isNotEmpty()) {
            val vertex = queue.poll()
            result.add(vertex)
            adjacencyList[vertex]?.let { adj ->
                for (node in adj) {
                    indegreeList[node]?.let {
                        indegreeList[node] = it - 1
                        if (it - 1 == 0) {
                            queue.offer(node)
                        }
                    }
                }
            }
            vertexCount++
        }
        if (vertexCount != adjacencyList.size) {
            throw RuntimeException(MSG_CYCLE)
        }
        return result
    }

    private fun initializeQueue(indegreeList: MutableMap<T, Int>): LinkedList<T> {
        val queue = LinkedList<T>()
        indegreeList.forEach { (key, indegree) ->
            if (indegree == 0) {
                queue.offer(key)
            }
        }
        return queue
    }

    private fun getIndegreeList(): MutableMap<T, Int> {
        val indegreeList = mutableMapOf<T, Int>()
        adjacencyList.forEach { (key, list) ->
            if (indegreeList[key] == null) {
                indegreeList[key] = 0
            }
            list.forEach {
                if (indegreeList[it] == null) {
                    indegreeList[it] = 1
                } else {
                    indegreeList[it] = indegreeList[it]!! + 1
                }
            }
        }
        return indegreeList
    }

    class Edge<T>(val from: T, val to: T)
}

const val MSG_CYCLE = "There exists a cycle in the graph"