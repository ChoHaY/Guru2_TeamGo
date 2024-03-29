package com.example.teamgo.Todo

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.example.teamgo.R

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

        val edit_name2 = dialog.findViewById<EditText>(R.id.Edit_Todo_ev)
        val button_finish = dialog.findViewById<Button>(R.id.Finish_btn3)
        val button_cancel = dialog.findViewById<Button>(R.id.Cancel_btn3)

        button_cancel.setOnClickListener {
            dialog.dismiss()
        }
        button_finish.setOnClickListener {
            onClickListener.onClicked(edit_name2.text.toString())
            dialog.dismiss()
        }
    }
    interface OnDialogClickListener {
        fun onClicked(name: String)
    }
}