package com.sergeikuchin.asambeauty.domain

/**
 * This interface serves as kind of a mediator between Repository and ViewModel.
 * Here, we map data from Repository to data that the View can accept.
 */
abstract class UseCase<Q : UseCase.Query> {

    protected var query: Q? = null

    interface Query
    interface Result {
        val status: ResultStatus
    }
    interface RefreshResult {
        val status: ResultStatus
    }
}

sealed class ResultStatus {

    object Success : ResultStatus()
    data class Error(val e: ResultError) : ResultStatus()
}

sealed class ResultError(val errorMessage: String, cause: Throwable?) :
    Exception(errorMessage, cause)