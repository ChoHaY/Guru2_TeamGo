package com.example.teamgo.Todo

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.EditText
import com.example.teamgo.R
import kotlinx.android.synthetic.main.todo_memo.*

class TodoMemo(context: Context) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener) {
        onClickListener = listener
    }
    fun showDialog() {
        dialog.setContentView(R.layout.todo_memo)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val edit_name = dialog.findViewById<EditText>(R.id.Name_ev)

        dialog.Cancel_btn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.Add_btn.setOnClickListener {
            onClickListener.onClicked(edit_name.text.toString())
            dialog.dismiss()
        }
    }
    interface OnDialogClickListener {
        fun onClicked(name: String)
    }
}
