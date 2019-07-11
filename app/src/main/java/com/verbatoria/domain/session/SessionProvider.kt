package com.verbatoria.domain.session

import android.content.SharedPreferences
import com.verbatoria.domain.authorization.model.OfflineAuthorization
import com.verbatoria.domain.authorization.model.OnlineAuthorization
import com.verbatoria.domain.session.model.Session
import java.util.*

/**
 * @author n.remnev
 */

private const val KEY_TOKEN = "ru.tcsbank.agentref.domain.session.SessionProvider.token"
private const val KEY_LOCAL = "ru.tcsbank.agentref.domain.session.SessionProvider.local"
private const val KEY_LIFETIME = "ru.tcsbank.agentref.domain.session.SessionProvider.lifetime"
private const val KEY_USERNAME = "ru.tcsbank.agentref.domain.session.SessionProvider.username"
private const val KEY_UPDATE_DATE_TIME = "ru.tcsbank.agentref.domain.session.SessionProvider.update_date_time"
private const val KEY_SIGN_IN_DATE = "ru.tcsbank.agentref.domain.session.SessionProvider.signInDate"
private const val KEY_REMOVED = "ru.tcsbank.agentref.domain.session.SessionProvider.is_removed"

interface SessionProvider {

    fun setSession(session: Session)

    fun getSession(): Session?

    fun hasSession(): Boolean

    fun removeSession(): Boolean

    fun markSessionAsRemoved(): Boolean

}

class PreferencesSessionProvider(
    private var preferences: SharedPreferences
) : SessionProvider {

    @Volatile
    private var session: Session? = null

    override fun setSession(session: Session) {
        saveSession(session)
        this.session = session
    }

    override fun getSession(): Session? =
        session ?: loadSession()

    override fun hasSession(): Boolean {
        val session = getSession()
        return session != null && !session.isSessionRemoved
    }

    override fun removeSession(): Boolean =
        clearSession().also {
            session = null
        }

    override fun markSessionAsRemoved(): Boolean {
        session?.markSessionAsRemoved()
        return preferences.edit()
            .putBoolean(KEY_REMOVED, true)
            .commit()
    }

    private fun saveSession(session: Session): Boolean {
        val editor = preferences.edit()
        if (!session.isOffline()) {
            editor.putString(KEY_TOKEN, (session.authorization as OnlineAuthorization).token)
                .putLong(KEY_LIFETIME, session.lifetime)
                .putLong(KEY_SIGN_IN_DATE, Date().time)
                .putLong(KEY_UPDATE_DATE_TIME, session.updateDateTime)
        }
        return editor.putString(KEY_USERNAME, session.login)
            .putBoolean(KEY_LOCAL, session.isOffline())
            .commit()
    }

    private fun loadSession(): Session? {
        var session: Session? = null
        if (preferences.contains(KEY_LOCAL)) {
            if (preferences.getBoolean(KEY_LOCAL, true)) {
                session = createOfflineSession()
            } else {
                val timeInMillis = Date().time
                val lifetime = preferences.getLong(KEY_LIFETIME, timeInMillis)
                val signInDate = preferences.getLong(KEY_SIGN_IN_DATE, timeInMillis)
                val expiredTime = timeInMillis - signInDate
                if (lifetime > expiredTime) {
                    session = createOnlineSession(lifetime, expiredTime)
                } else {
                    removeSession()
                }
            }
        }
        return session
    }

    private fun createOfflineSession(): Session =
        Session(
            OfflineAuthorization(),
            preferences.getLong(KEY_LIFETIME, Date().time),
            preferences.getString(KEY_USERNAME, ""),
            preferences.getLong(KEY_UPDATE_DATE_TIME, 0),
            preferences.getBoolean(KEY_REMOVED, false)
        )

    private fun createOnlineSession(
        lifetime: Long,
        expiredTime: Long
    ): Session =
        Session(
            OnlineAuthorization(
                preferences.getString(
                    KEY_TOKEN,
                    ""
                )
            ),
            lifetime - expiredTime,
            preferences.getString(KEY_USERNAME, ""),
            preferences.getLong(KEY_UPDATE_DATE_TIME, 0),
            preferences.getBoolean(KEY_REMOVED, false)
        )

    private fun clearSession(): Boolean =
        preferences.edit()
            .remove(KEY_TOKEN)
            .remove(KEY_LIFETIME)
            .remove(KEY_LOCAL)
            .remove(KEY_USERNAME)
            .remove(KEY_UPDATE_DATE_TIME)
            .remove(KEY_REMOVED)
            .commit()

}