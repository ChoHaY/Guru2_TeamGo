package com.example.teamgo.Todo

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.EditText
import kotlinx.android.synthetic.main.todo_bottom_sheet.*
import kotlinx.android.synthetic.main.todo_writememo.*
import net.flow9.thisisKotlin.firebase.R

class WriteMemo(context: Context) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener) {
        onClickListener = listener
    }

    fun showDialog() {
        dialog.setContentView(R.layout.todo_writememo)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val edit_name2 = dialog.findViewById<EditText>(R.id.name_edit2)

        dialog.cancel_button2.setOnClickListener {
            dialog.dismiss()
        }

        dialog.finish_button2.setOnClickListener {
            onClickListener.onClicked(edit_name2.text.toString())
            dialog.dismiss()
        }

    }
    interface OnDialogClickListener {
        fun onClicked(name: String)


    }
}