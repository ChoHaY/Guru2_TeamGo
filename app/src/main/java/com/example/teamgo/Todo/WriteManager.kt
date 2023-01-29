package com.example.teamgo.Todo

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.EditText
import com.example.teamgo.R
import kotlinx.android.synthetic.main.todo_writemanager.*
import kotlinx.android.synthetic.main.todo_writememo.*



class WriteManager(context: Context) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener) {
        onClickListener = listener
    }

    fun showDialog() {
        dialog.setContentView(R.layout.todo_writemanager)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val edit_name3 = dialog.findViewById<EditText>(R.id.name_edit3)

        dialog.cancel_button3.setOnClickListener {
            dialog.dismiss()
        }

        dialog.finish_button3.setOnClickListener {
            onClickListener.onClicked(edit_name3.text.toString())
            dialog.dismiss()
        }




    }
    interface OnDialogClickListener {
        fun onClicked(name: String)


    }
}