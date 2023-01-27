package com.example.teamgo.Todo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.todo_bottom_sheet.*
import net.flow9.thisisKotlin.firebase.R


class BottomSheet(context: Context): BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.todo_bottom_sheet, container, false)
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        bottom_sheet.setOnClickListener {
            Toast.makeText(context, "메모작성 클릭", Toast.LENGTH_SHORT).show()
            dismiss()
//                val dialog = WriteMemo(this)
//                dialog.showDialog()
//                dialog.setOnClickListener(object : WriteMemo.OnDialogClickListener {
//                    override fun onClicked(name: String) {
//                        bottom_sheet.text = name
//                    }
//                })

//            val dialog = new Dialog(WriteMemo())
//            dialog.setContentView(R.layout.activity_writememo)
//            dialog.show()

            }

     }
        }



