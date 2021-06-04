package com.zeronine.project1.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.zeronine.project1.R

class GroupSettingDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val button = view.findViewById<Button>(R.id.yes)
        return inflater.inflate(R.layout.dialog_groupsetting, container, false)
        //return super.onCreateView(inflater, container, savedInstanceState)
    }

}