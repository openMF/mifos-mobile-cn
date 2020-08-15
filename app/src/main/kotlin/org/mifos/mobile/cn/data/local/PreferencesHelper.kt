package org.mifos.mobile.cn.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.mifos.mobile.cn.data.models.Authentication
import org.mifos.mobile.cn.injection.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesHelper
@Inject
constructor(@ApplicationContext context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(
            PreferenceKey.PREF_MIFOS, Context.MODE_PRIVATE)
    private val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz")
            .create()

    fun getInt(preferenceKey: String, preferenceDefaultValue: Int): Int {
        return preferences.getInt(preferenceKey, preferenceDefaultValue)
    }

    fun putInt(preferenceKey: String, preferenceValue: Int) {
        preferences.edit().putInt(preferenceKey, preferenceValue).apply()
    }

    fun getLong(preferenceKey: String, preferenceDefaultValue: Long): Long {
        return preferences.getLong(preferenceKey, preferenceDefaultValue)
    }

    fun putLong(preferenceKey: String, preferenceValue: Long) {
        preferences.edit().putLong(preferenceKey, preferenceValue).apply()
    }

    fun getFloat(preferenceKey: String, preferenceDefaultValue: Float): Float {
        return preferences.getFloat(preferenceKey, preferenceDefaultValue)
    }

    fun putFloat(preferenceKey: String, preferenceValue: Float) {
        preferences.edit().putFloat(preferenceKey, preferenceValue).apply()
    }

    fun getBoolean(preferenceKey: String, preferenceDefaultValue: Boolean): Boolean {
        return preferences.getBoolean(preferenceKey, preferenceDefaultValue)
    }

    fun putBoolean(preferenceKey: String, preferenceValue: Boolean) {
        preferences.edit().putBoolean(preferenceKey, preferenceValue).apply()
    }

    fun getString(preferenceKey: String, preferenceDefaultValue: String): String? {
        return preferences.getString(preferenceKey, preferenceDefaultValue)
    }

    fun putString(preferenceKey: String, preferenceValue: String) {
        preferences.edit().putString(preferenceKey, preferenceValue).apply()
    }

    fun putStringSet(preferenceKey: String, stringSet: Set<String>) {
        preferences.edit().putStringSet(preferenceKey, stringSet).apply()
    }

    fun getStringSet(preferenceKey: String): Set<String>? {
        return preferences.getStringSet(preferenceKey, null)
    }

    fun clear() {
        preferences.edit().clear().apply()
    }

    fun putAccessToken(accessToken: String) {
        preferences.edit().putString(
                PreferenceKey.PREF_KEY_ACCESS_TOKEN, accessToken).apply()
    }

    fun getAccessToken(): String? {
        return preferences.getString(PreferenceKey.PREF_KEY_ACCESS_TOKEN, null)
    }

    fun putLoginStatus(loginStatus: Boolean) {
        preferences.edit().putBoolean(PreferenceKey.PREF_KEY_LOGIN_STATUS, loginStatus).apply()
    }

    val loginStatus: Boolean
        get() = preferences.getBoolean(PreferenceKey.PREF_KEY_LOGIN_STATUS, false)

    fun putUsername(username: String) {
        preferences.edit().putString(PreferenceKey.PREF_KEY_USER_NAME, username).apply()
    }

    fun getUserName(): String? {
        return preferences.getString(PreferenceKey.PREF_KEY_USER_NAME, null)
    }
    fun getSignedInUser(): Authentication? {
        val userJson = preferences.getString(PreferenceKey.PREF_KEY_SIGNED_IN_USER, null)
                ?: return null
        return gson.fromJson(userJson, Authentication::class.java)
    }

    fun putSignInUser(user: Authentication) {
        preferences.edit().putString(PreferenceKey.PREF_KEY_SIGNED_IN_USER,
                gson.toJson(user)).apply()
    }
    fun putTenantIdentifier(tenantIdentifier: String) {
        preferences.edit().putString(PreferenceKey.PREF_KEY_TENANT_IDENTIFIER,
                tenantIdentifier).apply()
    }

    fun getTenantIdentifier(): String? {
        return preferences.getString(PreferenceKey.PREF_KEY_TENANT_IDENTIFIER, null)
    }

    //TODO: remove the password prefernce helper
 /*   fun putPassword(password: String) {
        preferences.edit().putString(PreferenceKey.PREF_KEY_PASSWORD, password).apply()
    }

    val password: String
        get() = preferences.getString(PreferenceKey.PREF_KEY_PASSWORD, null)
*/
}
