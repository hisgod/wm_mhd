package com.aib.entity

/**
 * 等待审核后面广告
 */
data class WaitVerifyEntity(
    val applyCountFake: Int,
    val applyQualification: String,
    val des: String,
    val id: Int,
    val loanDay: Int,
    val loanMax: Double,
    val loanMin: Double,
    val loanRate: Double,
    val logo: String,
    val material: String,
    val name: String,
    val processIcon: String,
    val requestUrl: String,
    val tag: String
)