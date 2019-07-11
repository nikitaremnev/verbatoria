package com.verbatoria.domain.authorization

/**
 * @author n.remnev
 */
//
//interface AuthorizationManager {
//
//    fun login(login: String, password: String, deviceId: String): Boolean
//
//    fun loginByPinCode(pinHash: String, deviceId: String): Boolean
//
//    fun setPinCode(pinHash: String, deviceId: String): Boolean
//
//    fun removePinCode()
//
//    fun logout()
//
//    fun hasPin(): Boolean
//
//    fun isAuthorized(): Boolean
//
//}
//
//class AuthorizationManagerImpl(
//    private val loginEndpoint: LoginEndpoint,
//    private val pinCodeEndpoint: PinCodeEndpoint,
//    private val sessionManager: SessionManager,
//    private val authorizationRepository: AuthorizationRepository
//) : AuthorizationManager {
//
//    override fun login(login: String, password: String, deviceId: String): Boolean {
//        val response = loginEndpoint.login(
//            LoginRequestParamsDto(
//                login = login,
//                password = password,
//                partitionName = APIConfig.PARTITION_NAME,
//                appVersion = APIConfig.VERSION_NAME,
//                deviceId = deviceId,
//                origin = APIConfig.ORIGIN
//            )
//        )
//
//        if (response.isSuccess()) {
//            sessionManager.startSession(
//                OnlineAuthorization(response.getToken()),
//                response.getLifetime()
//            )
//        }
//
//        return response.isSuccess()
//    }
//
//    override fun loginByPinCode(pinHash: String, deviceId: String): Boolean {
//        val response = pinCodeEndpoint.loginByPinCode(
//            LoginByPinRequestParamsDto(
//                partitionName = APIConfig.PARTITION_NAME,
//                deviceId = deviceId,
//                origin = APIConfig.ORIGIN,
//                pinHash = pinHash
//            )
//        )
//
//        if (response.isSuccess()) {
//            sessionManager.startSession(
//                OnlineAuthorization(response.getToken()),
//                response.getLifetime()
//            )
//        }
//
//        return response.isSuccess()
//    }
//
//    override fun setPinCode(pinHash: String, deviceId: String): Boolean {
//        pinCodeEndpoint.setPinCode(
//            SetPinCodeRequestParamsDto(
//                pinHash = pinHash,
//                deviceId = deviceId
//            )
//        )
//        authorizationRepository.putHasPin(true)
//        return true
//    }
//
//    override fun removePinCode() {
//        authorizationRepository.putHasPin(false)
//    }
//
//    override fun logout() {
//        authorizationRepository.putHasPin(false)
//        sessionManager.closeSession("Session manager close from authorization manager")
//    }
//
//    override fun hasPin(): Boolean =
//        authorizationRepository.hasPin()
//
//    override fun isAuthorized(): Boolean =
//        sessionManager.hasActiveSession()
//
//}