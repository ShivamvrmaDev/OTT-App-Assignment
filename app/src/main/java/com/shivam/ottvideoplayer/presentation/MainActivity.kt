package com.shivam.ottvideoplayer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.shivam.ottvideoplayer.R
import com.shivam.ottvideoplayer.databinding.ActivityMainBinding
import com.shivam.ottvideoplayer.presentation.ui.utils.MyViewModel

class MainActivity : AppCompatActivity() {
    val viewModel: MyViewModel by viewModels()

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}