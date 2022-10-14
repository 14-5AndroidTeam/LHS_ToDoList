package com.example.androidstudy_1_todolist

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidstudy_1_todolist.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        
        // 추가 버튼을 이용하여 Fragment AddFragment로 이동한다.
        btnAdd.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.ConstraintHome, AddFragment())
                .commit()
        }
    }
}


