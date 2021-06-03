package com.zeronine.project1.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.zeronine.project1.R

class HomeFragment : Fragment(R.layout.fragment_home){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val makeGroupButton = view.findViewById<Button>(R.id.makeGroupButton)
        val joinGroupButton = view.findViewById<Button>(R.id.joinGroupButton)

        makeGroupButton.setOnClickListener{
            activity?.let {
                startActivity(Intent(context, MakeGroupActivity::class.java))
            }
        }

    }
}