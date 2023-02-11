package com.example.mynotes.ui.customdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentAppInfoDialogBinding
import javax.inject.Inject

class AppInfoDialogFragment @Inject constructor() : DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.drawable.custom_dialog_shape)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAppInfoDialogBinding.inflate(layoutInflater,container,false).root
    }

}