package com.example.teamgo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import net.flow9.thisisKotlin.firebase.R

class BottomSheet(context: Context): BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.activity_bottom_sheet, container, false)
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        view?.findViewById<TextView>(R.id.bottom_sheet)?.setOnClickListener {
            Toast.makeText(context, "Bottom Sheet 안의 버튼 클릭", Toast.LENGTH_SHORT).show()
            dismiss()
            //dialog = BottomSheet(this)
            //dialog.showDialog()
            //dialog.setOnClickListener(object : CustomDialogActivity2.OnDialogClickListener {
            //override fun onClicked(name: String) {
            // bottom_sheet.text = name
            //}
            // })
        }

    }
}