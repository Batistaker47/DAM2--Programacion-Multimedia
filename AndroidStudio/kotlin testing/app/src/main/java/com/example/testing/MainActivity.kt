package com.example.testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnChangeAct = findViewById<Button>(R.id.goNext)
        btnChangeAct.setOnClickListener {
            val intent = Intent(this, Test::class.java)
            startActivity(intent)
        }

        val swDarkMode = findViewById<SwitchMaterial>(R.id.swDarkMode)

        swDarkMode.setOnCheckedChangeListener { _, isSelected ->
            if (isSelected){
                enableDarkMode()
            }else{
                disableDarkMode()
            }
        }
    }

    private fun enableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        delegate.applyDayNight()
    }

    private fun disableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        delegate.applyDayNight()
    }
}