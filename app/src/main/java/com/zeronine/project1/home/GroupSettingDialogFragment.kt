package com.zeronine.project1.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.zeronine.project1.databinding.DialogGroupsettingBinding

class GroupSettingDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = DialogGroupsettingBinding.inflate(inflater, container, false)
        //val button = view.findViewById<Button>(R.id.yes)
        //return inflater.inflate(R.layout.dialog_groupsetting, container, false)

        return binding.root
        //return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding2 = DialogGroupsettingBinding.bind(view)
        binding2.yes.setOnClickListener {
            activity?.let {
                startActivity(Intent(context, WaitingGroupActivity::class.java))
            }
            dismiss()
        }
        binding2.no.setOnClickListener {
            dismiss()
        }
    }

}