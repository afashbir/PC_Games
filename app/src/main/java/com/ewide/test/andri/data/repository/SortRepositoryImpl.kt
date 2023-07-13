package com.ewide.test.andri.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.ewide.test.andri.domain.model.SortPref
import com.ewide.test.andri.domain.repository.SortRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SortRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : SortRepository {

    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    override suspend fun storeSortPref(pref: SortPref) {
        preferences.edit()
            .putString(KEY_SORT_PREF, pref.toString())
            .apply()
    }

    override fun sortPref(): SortPref {
        val value = preferences
            .getString(KEY_SORT_PREF, null) ?: "ASC"

        return SortPref.valueOf(value)
    }

    companion object {
        private const val KEY_SORT_PREF = "key.sort.pref"
    }
}