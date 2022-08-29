package commands

import OperationType

interface Command {
    fun operationType() : OperationType
    fun execute()
}