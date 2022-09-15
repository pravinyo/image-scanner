package factory

import utils.OperationType

interface ImageOperationFactory<Type> {
    fun createInstance(operationType: OperationType): Type
}