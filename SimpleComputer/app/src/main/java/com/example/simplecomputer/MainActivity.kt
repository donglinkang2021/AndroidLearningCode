package com.example.simplecomputer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.TextView
import com.example.simplecomputer.UI.*
import com.example.simplecomputer.utils.Calculator

class MainActivity : AppCompatActivity(){
    // region textview
    lateinit var showtext: TextView
        private set
    lateinit var scrollView: HorizontalScrollView
        private set
    // endregion


    companion object {
        val cal = Calculator() // 设置为静态变量 在用户短暂退出应用时，再次进入时历史记录不会被清空(旋转也是）
    }

    // region 解决在旋转屏幕或者用户短暂退出时，历史记录被清空的问题
    private var formula = ""
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        formula = showtext.text.toString()
        outState.putString("formula",formula)
    }
    // endregion

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_tableview_demo2)


        scrollView= findViewById(R.id.horizontalScrollView)
        showtext = findViewById(R.id.computeFormulaString)



        // region ctl button
        AddTextButton(findViewById(R.id.button_num_0),"0",this)
        AddTextButton(findViewById(R.id.button_num_1),"1",this)
        AddTextButton(findViewById(R.id.button_num_2),"2",this)
        AddTextButton(findViewById(R.id.button_num_3),"3",this)
        AddTextButton(findViewById(R.id.button_num_4),"4",this)
        AddTextButton(findViewById(R.id.button_num_5),"5",this)
        AddTextButton(findViewById(R.id.button_num_6),"6",this)
        AddTextButton(findViewById(R.id.button_num_7),"7",this)
        AddTextButton(findViewById(R.id.button_num_8),"8",this)
        AddTextButton(findViewById(R.id.button_num_9),"9",this)
        AddTextButton(findViewById(R.id.button_dot),".",this)

        AddTextButton(findViewById(R.id.button_op_plus),"+",this)
        AddTextButton(findViewById(R.id.button_op_minus),"-",this)
        AddTextButton(findViewById(R.id.button_op_multiply),"×",this)
        AddTextButton(findViewById(R.id.button_op_divide),"÷",this)
        AddTextButton(findViewById(R.id.button_op_power),"^",this)
        AddTextButton(findViewById(R.id.button_op_mod),"%",this)

        AddTextButton(findViewById(R.id.button_op_left_PT),"(",this)
        AddTextButton(findViewById(R.id.button_op_right_PT),")",this)

        EqualButton(findViewById(R.id.button_op_equal),cal,this)
        ClearButton(findViewById(R.id.button_op_clear),this)
        DeleteButton(findViewById(R.id.button_op_delete),this)
        HistoryButton(findViewById(R.id.button_history_last),findViewById(R.id.button_history_next),cal,this)

        // endregion

        if (savedInstanceState != null) {
            formula = savedInstanceState.getString("formula")!!
            showtext.text = formula
        }

    }

}