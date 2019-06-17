package com.example.projetoapp2.mvp.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.projetoapp2.R
import com.example.projetoapp2.mvp.contract.LoginContract
import com.example.projetoapp2.mvp.presenter.LoginPresenter
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View, View.OnClickListener {

    override lateinit var presenter : LoginPresenter
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var loginInput : EditText
    lateinit var passwordInput : EditText
    lateinit var btnLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Bind do Presenter com a nossa View
        presenter = LoginPresenter(this)

        presenter.start() // Chama o método responsável por dizer a View o que deve ser inicializado.

        btnLogin.setOnClickListener {
            //Comunica e Transfere a responsabilidade do Login para o Presenter
            //Que então irá validar se o Login é válido ou não.
            presenter.isLoginValid(loginInput.text.toString(), passwordInput.text.toString())
        }

        // Button listeners
        signInButton.setOnClickListener(this)
        signOutButton.setOnClickListener(this)
        disconnectButton.setOnClickListener(this)
        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // [END config_signin]

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        // [END initialize_auth]
    }

    override fun displayErrorMessage() {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
        loginInput.error = "Login Failed!"
        passwordInput.error = "Login Failed!"
    }

    override fun displaySucessToast() {
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
    }

    override fun startHomeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun bindViews() {
        loginInput = findViewById(R.id.txtLogin)
        passwordInput = findViewById(R.id.txtSenha)
        btnLogin = findViewById(R.id.btnLogin)
    }
    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    // [END on_start_check_user]

    // [START onactivityresult]
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

            }
    }
    // [END auth_with_google]

    // [START signin]
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END signin]

    private fun signOut() {
        // Firebase sign out
        auth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
            updateUI(null)
        }
    }

    private fun revokeAccess() {
        // Firebase sign out
        auth.signOut()

        // Google revoke access
        googleSignInClient.revokeAccess().addOnCompleteListener(this) {
            updateUI(null)
        }
    }

    private fun updateUI(user: FirebaseUser?) {

        if (user != null) {
            status.text = getString(R.string.google_status_fmt, user.email)
            detail.text = getString(R.string.firebase_status_fmt, user.uid)

            signInButton.visibility = View.GONE
            signOutAndDisconnect.visibility = View.VISIBLE
        } else {
            status.setText(R.string.signed_out)
            detail.text = null

            signInButton.visibility = View.VISIBLE
            signOutAndDisconnect.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        val i = v.id
        when (i) {
            R.id.signInButton -> signIn()
            R.id.signOutButton -> signOut()
            R.id.disconnectButton -> revokeAccess()
        }
    }


    companion object {

        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}