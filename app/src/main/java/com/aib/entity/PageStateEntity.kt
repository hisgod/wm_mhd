package com.aib.entity

data class PageStateEntity(
        val pageState: Int,
        val qualifiedInfo: QualifiedInfo
) {
    data class QualifiedInfo(
            val cid: Int,
            val qualifiedState: Int
    )
}

