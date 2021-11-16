package com.zeronine.project1.screen.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zeronine.project1.R
import com.zeronine.project1.databinding.FragmentHomeBinding
import com.zeronine.project1.screen.home.join.JoinGroupActivity
import com.zeronine.project1.screen.home.make.MakeGroupActivity

class HomeFragment : Fragment(R.layout.fragment_home){

    //lateinit var homeBinding : FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeBinding = FragmentHomeBinding.bind(view)
        //homeBinding = FragmentHomeBinding.inflate(layoutInflater)
        homeBinding.makeGroupButton.setOnClickListener{
            activity?.let {
                startActivity(Intent(context, MakeGroupActivity::class.java))
            }
        }

        homeBinding.joinGroupButton.setOnClickListener {
            activity?.let {
                startActivity(Intent(context, JoinGroupActivity::class.java))
            }
        }

    }
}