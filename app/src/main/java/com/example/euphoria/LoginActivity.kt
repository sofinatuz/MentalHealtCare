package com.example.euphoria

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar


class LoginActivity : AppCompatActivity() {
    //Declaration EditTexts
    var editTextNoHP: EditText? = null
    var editTextPassword: EditText? = null

    //Declaration Button
    var loginAkun: Button? = null

    //Declaration Visible
    var isPasswordVisible = false

    //Declaration SqliteHelper
    var sqliteHelper: SqliteHelper? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        sqliteHelper = SqliteHelper(this)
        initCreateAccountTextView()
        initViews()
        supportActionBar?.hide()

        editTextPassword!!.setOnTouchListener(View.OnTouchListener { v, event ->
            val RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= editTextPassword!!.right - editTextPassword!!.compoundDrawables[RIGHT].bounds.width()) {
                    val selection = editTextPassword!!.selectionEnd
                    if (isPasswordVisible) {
                        // set drawable image
                        editTextPassword!!.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_visibility_off_24,
                            0
                        )
                        // hide Password
                        editTextPassword!!.transformationMethod =
                            PasswordTransformationMethod.getInstance()
                        isPasswordVisible = false
                    } else {
                        // set drawable image
                        editTextPassword!!.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_visibility_24,
                            0
                        )
                        // show Password
                        editTextPassword!!.transformationMethod =
                            HideReturnsTransformationMethod.getInstance()
                        isPasswordVisible = true
                    }
                    editTextPassword!!.setSelection(selection)
                    return@OnTouchListener true
                }
            }
            false
        })

        //set click event of login button
        loginAkun!!.setOnClickListener {
            //Check user input is correct or not
            if (validate()) {

                //Get values from EditText fields
                val NoHP = editTextNoHP!!.text.toString()
                val Password = editTextPassword!!.text.toString()

                //Authenticate user
                val currentUser: User? = sqliteHelper!!.Authenticate(User(null, null, NoHP, Password))

                //Check Authentication is successful or not
                if (currentUser != null) {
                    Snackbar.make(loginAkun!!, "Berhasil Login", Snackbar.LENGTH_LONG).show()
                    //User Logged in Successfully Launch You home screen activity
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {

                    //User Logged in Failed
                    Snackbar.make(loginAkun!!, "Gagal Login", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    //this method used to set Create account TextView text and click event( maltipal colors
    // for TextView yet not supported in Xml so i have done it programmatically)
    private fun initCreateAccountTextView() {
        val textViewCreateAccount = findViewById<View>(R.id.textViewCreateAccount) as TextView
        textViewCreateAccount.text =
            fromHtml("<font color='#868686'>Saya belum memiliki akun. </font><font color='#0c0099'>Buat akun</font>")
        textViewCreateAccount.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    //this method is used to connect XML views to its Objects
    private fun initViews() {
        editTextNoHP = findViewById<View>(R.id.email) as EditText
        editTextPassword = findViewById<View>(R.id.password) as EditText
        loginAkun = findViewById<View>(R.id.loginBtn) as Button
    }

    //This method is used to validate input given by user
    fun validate(): Boolean {
        var valid = false

        //Get values from EditText fields
        val NoHP = editTextNoHP!!.text.toString()
        val Password = editTextPassword!!.text.toString()

        //Handling validation for NoHP field
        if (NoHP.isEmpty()) {
            valid = false
            editTextNoHP!!.error = "Email tidak boleh kosong!"
        } else {
            if (NoHP.length > 10) {
                valid = true
                editTextNoHP!!.error = null
            } else {
                valid = false
                editTextNoHP!!.error = "Masukkan Email yang benar!"
            }
        }
        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false
            editTextPassword!!.error = "Masukkan password dengan benar!"
        } else {
            if (Password.length > 5) {
                valid = true
                editTextPassword!!.error = null
            } else {
                valid = false
                editTextPassword!!.error = "Password terlalu pendek."
            }
        }
        return valid
    }

    companion object {
        //This method is for handling fromHtml method deprecation
        fun fromHtml(html: String?): Spanned {
            val result: Spanned
            result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(html)
            }
            return result
        }
    }
}