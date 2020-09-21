package com.verbatoria.domain.session.repository

import android.content.SharedPreferences
import com.verbatoria.domain.authorization.model.OfflineAuthorization
import com.verbatoria.domain.authorization.model.OnlineAuthorization
import com.verbatoria.domain.session.model.Session

/**
 * @author n.remnev
 */

private const val KEY_TOKEN = "com.verbatoria.domain.session.repository.SessionProvider.token"
private const val KEY_LOCAL = "com.verbatoria.domain.session.repository.SessionProvider.local"
private const val KEY_REMOVED = "com.verbatoria.domain.session.repository.SessionProvider.is_removed"

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
        }
        return editor.putBoolean(KEY_LOCAL, session.isOffline()).commit()
    }

    private fun loadSession(): Session? {
        var session: Session? = null
        if (preferences.contains(KEY_LOCAL)) {
            if (preferences.getBoolean(KEY_LOCAL, true)) {
                session = createOfflineSession()
            } else {
                session = createOnlineSession()
            }
        } else {
            session = createOnlineSession()
        }
        return session
    }

    private fun createOfflineSession(): Session =
        Session(
            OfflineAuthorization(),
            preferences.getBoolean(KEY_REMOVED, false)
        )

    private fun createOnlineSession(): Session =
        Session(
            OnlineAuthorization(preferences.getString(KEY_TOKEN, "") ?: ""),
            preferences.getBoolean(KEY_REMOVED, false)
        )

    private fun clearSession(): Boolean =
        preferences.edit()
            .remove(KEY_TOKEN)
            .remove(KEY_LOCAL)
            .remove(KEY_REMOVED)
            .commit()

}