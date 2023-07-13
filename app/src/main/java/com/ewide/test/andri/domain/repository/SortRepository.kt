package com.ewide.test.andri.domain.repository

import com.ewide.test.andri.domain.model.SortPref

interface SortRepository {
    suspend fun storeSortPref(pref: SortPref)

    fun sortPref(): SortPref
}