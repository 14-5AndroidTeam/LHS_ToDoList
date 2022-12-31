package com.example.androidstudy_1_todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidstudy_1_todolist.UI.View.ListFragment
import com.example.androidstudy_1_todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(binding.layout.id, ListFragment())
            .commit()
    }
}