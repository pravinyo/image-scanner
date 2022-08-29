package factory

import OperationType

interface ImageOperationFactory<Type> {
    fun createInstance(operationType: OperationType): Type
}