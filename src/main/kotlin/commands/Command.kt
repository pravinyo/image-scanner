package commands

import utils.OperationType

interface Command {
    fun operationType() : OperationType
    fun execute()
}