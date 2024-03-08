package com.example.recipeapp

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private lateinit var sharedPrefs: SharedPreferences

    fun init(context: Context) {
        sharedPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun getFavorites(): MutableSet<String> {
        val favoritesSet = sharedPrefs.getStringSet(FAVORITES_KEY, null)
        return favoritesSet?.toMutableSet() ?: mutableSetOf()
    }

    fun saveFavorites(idSet: Set<String>) {
        with(sharedPrefs.edit()) {
            putStringSet(FAVORITES_KEY, idSet)
            apply()
        }
    }

}