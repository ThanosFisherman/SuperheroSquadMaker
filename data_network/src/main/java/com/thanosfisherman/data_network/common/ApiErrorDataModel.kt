package com.thanosfisherman.data_network.common

import com.thanosfisherman.domain.common.DomainMappable
import com.thanosfisherman.domain.model.ErrorModel

data class ApiErrorDataModel(
    val code: String,
    val detail: String? = null,
    val errors: Map<String, List<String>>? = null
) : DomainMappable<ErrorModel> {
    override fun asDomain(): ErrorModel = ErrorModel.ServerError(
        this.code,
        this.detail,
        this.errors
    )
}