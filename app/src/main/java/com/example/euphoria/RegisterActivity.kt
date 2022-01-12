package com.example.euphoria

import android.os.Bundle
import android.os.Handler
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


class RegisterActivity : AppCompatActivity() {
    //Declaration EditTexts
    var editTextUserName: EditText? = null
    var editTextNoHP: EditText? = null
    var editTextPassword: EditText? = null

    //Declaration Button
    var buttonRegister: Button? = null

    //Declaration Visible
    var isPasswordVisible = false

    //Declaration SqliteHelper
    var sqliteHelper: SqliteHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        sqliteHelper = SqliteHelper(this)
        initTextViewLogin()
        initViews()
        buttonRegister!!.setOnClickListener {
            if (validate()) {
                val UserName = editTextUserName!!.text.toString()
                val NoHP = editTextNoHP!!.text.toString()
                val Password = editTextPassword!!.text.toString()

                //Check in the database is there any user associated with  this NoHP
                if (!sqliteHelper!!.isnohpExists(NoHP)) {

                    //nohp does not exist now add new user to database
                    sqliteHelper!!.addUser(
                        User(
                            null,
                            UserName,
                            NoHP,
                            Password
                        )
                    )
                    Snackbar.make(
                        buttonRegister!!,
                        "User created successfully! Please Login ",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Handler().postDelayed({ finish() }, Snackbar.LENGTH_LONG.toLong())
                } else {

                    //nohp exists with nohp input provided so show error user already exist
                    Snackbar.make(
                        buttonRegister!!,
                        "User already exists with same email ",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

        editTextPassword!!.setOnTouchListener(View.OnTouchListener { view, event ->
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
    }

    //this method used to set Login TextView click event
    private fun initTextViewLogin() {
        val textViewLogin = findViewById<View>(R.id.textViewLogin) as TextView
        textViewLogin.text =
            LoginActivity.fromHtml("<font color='#868686'>Saya sudah memiliki akun. </font><font color='#0c0099'>Masuk akun</font>")
        textViewLogin.setOnClickListener { finish() }
    }

    //this method is used to connect XML views to its Objects
    private fun initViews() {
        editTextUserName = findViewById<View>(R.id.nama) as EditText
        editTextNoHP = findViewById<View>(R.id.email) as EditText
        editTextPassword = findViewById<View>(R.id.password) as EditText
        buttonRegister = findViewById<View>(R.id.registBtn) as Button
    }

    //This method is used to validate input given by user
    fun validate(): Boolean {
        var valid = false

        //Get values from EditText fields
        val UserName = editTextUserName!!.text.toString()
        val nohp = editTextNoHP!!.text.toString()
        val Password = editTextPassword!!.text.toString()

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false
            editTextUserName!!.error = "Masukkan username dengan benar!"
        } else {
            if (UserName.length > 0) {
                valid = true
                editTextUserName!!.error = null
            }
        }

        //Handling validation for NoHP field
        if (nohp.isEmpty()) {
            valid = false
            editTextNoHP!!.error = "Email tidak boleh kosong!"
        } else {
            if (nohp.length > 10) {
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
                editTextPassword!!.error = "Password terlalu singkat."
            }
        }
        return valid
    }
}