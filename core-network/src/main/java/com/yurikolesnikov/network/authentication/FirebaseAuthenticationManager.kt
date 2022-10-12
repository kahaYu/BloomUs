package com.yurikolesnikov.network.authentication

import android.app.Application
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import com.yurikolesnikov.network.Resource
import com.yurikolesnikov.uicomponents.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

typealias OneTapSignInResponse = Flow<Resource<BeginSignInResult>>
typealias SignInWithGoogleResponse = Flow<Resource<Unit>>

private const val WEBCLIENT_ID =
    "72714829077-8p24uf73235qtovjojsql7ecdde6cng8.apps.googleusercontent.com"

object FirebaseAuthenticationManager {

    private val auth = FirebaseAuth.getInstance()
    private var mApplication: Application? = null
    private val oneTapClient by lazy {
        mApplication?.let { application -> Identity.getSignInClient(application) }
    }
    private val signInRequest by lazy {
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(WEBCLIENT_ID)
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
    private val signUpRequest by lazy {
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(WEBCLIENT_ID)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    fun isLoggedIn(): Boolean = auth.currentUser != null

    fun initialize(application: Application) {
        mApplication = application
    }

    fun registerUser(email: String, password: String): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            if (email.isNotEmpty() && password.isNotEmpty()) {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    emit(Resource.Success())
                } catch (exception: Exception) {
                    emit(Resource.Error())
                }
            }
        }
    }

    fun logInUserWithEmailAndPassword(email: String, password: String): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            if (email.isNotEmpty() && password.isNotEmpty()) {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    emit(Resource.Success())
                } catch (exception: Exception) {
                    emit(Resource.Error())
                }
            }
        }
    }

    fun oneTapLogInWithGoogle(): OneTapSignInResponse {
        return flow {
            emit(Resource.Loading())
            oneTapClient?.let { oneTapClient ->
                try {
                    val signInResult = oneTapClient.beginSignIn(signInRequest).await()
                    emit(Resource.Success(signInResult))
                } catch (e: Exception) {
                    try {
                        val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                        emit(Resource.Success(signUpResult))
                    } catch (e: Exception) {
                        emit(Resource.Error(message = UiText.DynamicString("Some error during Google signing in")))
                    }
                }
            }
                ?: emit(Resource.Error(message = UiText.DynamicString("Initialize FirebaseAuthenticationManager in BloomUs application")))
        }
    }

    fun logInWithGoogle(result: ActivityResult): SignInWithGoogleResponse {
        return flow {
            emit(Resource.Loading())
            oneTapClient?.let { oneTapClient ->
                try {
                    val credentials = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val googleIdToken = credentials.googleIdToken
                    val googleCredentials = getCredential(googleIdToken, null)
                    val authResult = auth.signInWithCredential(googleCredentials).await()
                    val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
/*                    if (isNewUser) {
                        addUserToFirestore()
                    }*/
                    emit(Resource.Success())
                } catch (e: Exception) {
                    emit(Resource.Error(message = UiText.DynamicString("Some error during Google signing in")))
                }
            }
                ?: emit(Resource.Error(message = UiText.DynamicString("Initialize FirebaseAuthenticationManager in BloomUs application")))
        }
    }
}