package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView?=null;
    var lastNumeric=false;
    var lastDot=false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput=findViewById(R.id.tvInput)

    }

    /* here the view will be the method that called this method
    *  When the method is called the button along with its id, text
    *  and everything else calls it
    *  */
    fun onDigit(view: View){
        //Toast.makeText(this,"Button Clicked",Toast.LENGTH_SHORT).show();

        //we are using view as button because the view itself doesnt have
        //the text property

        tvInput?.append((view as Button) .text)
        lastNumeric=true;
        lastDot=false;
    }

    fun onClear(view: View){
        tvInput?.text=""
    }

    fun onDecimalPoint(view: View){
        //should not insert two dots continuously
        if(lastNumeric && !lastDot){
            tvInput?.append(".");
            lastDot=true;
            lastNumeric=false;
        }
        else{
            Toast.makeText(this, "Redundant points not allowed",Toast.LENGTH_SHORT).show();

        }

    }


    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue=tvInput?.text.toString();
            var prefix=""
            try{
                    /*
                    this block of code is added to fix a program crash
                    without this if we try to subtract from a negative number the program crashses
                    because the string is split in the beginning minus sign
                    so basically it becomes: "nothing" - "something"
                    hence the program crash
                    * */

                    if(tvValue.startsWith("-")){
                        prefix="-";
                        tvValue=tvValue.substring(1);//- is ignored
                    }

                    if(tvValue.contains("-")){
                        val splitValue= tvValue.split("-") //99-1 -> 99 & 1 in the form of an array
                        var one= splitValue[0];
                        var two=splitValue[1]


                        if(prefix.isNotEmpty()){
                            one=prefix+one;
                        }
                        tvInput?.text=(one.toDouble() - two.toDouble()).toString()

                    } else if(tvValue.contains("+")){
                    val splitValue= tvValue.split("+") //99+1 -> 99 & 1 in the form of an array
                    var one= splitValue[0];
                    var two=splitValue[1]


                    if(prefix.isNotEmpty()){
                        one=prefix+one;
                    }
                    tvInput?.text=(one.toDouble() + two.toDouble()).toString()

                }else if(tvValue.contains("*")){
                        val splitValue= tvValue.split("*") //99+1 -> 99 & 1 in the form of an array
                        var one= splitValue[0];
                        var two=splitValue[1]


                        if(prefix.isNotEmpty()){
                            one=prefix+one;
                        }
                        tvInput?.text=(one.toDouble() * two.toDouble()).toString()

                    }else if(tvValue.contains("/")){
                        val splitValue= tvValue.split("/") //99+1 -> 99 & 1 in the form of an array
                        var one= splitValue[0];
                        var two=splitValue[1]


                        if(prefix.isNotEmpty()){
                            one=prefix+one;
                        }
                        tvInput?.text=(one.toDouble() / two.toDouble()).toString()

                    }


            }catch (e:ArithmeticException){
                /*
                dividing by zero
                calc that work on arithmetic level
                */
                e.printStackTrace();
            }
        }
    }



    fun onOperator(view: View){

        //lastNumeric checked because we cant add operator directly after the .
        //last operator checked because we cant  insert two operators togethers
        tvInput?.text?.let{
            //here it contains the char sequence that is in the tvInput (if not null)
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric=false;
                lastDot=false;
            }
            else{
                Toast.makeText(this,"Invalid Sequence Entered",Toast.LENGTH_SHORT).show();
            }
        }

    }


    //this functions checks if operator is added
    //and takes in consideration that the string starts with -
    //i.e if the minus is at the beginning then it is not a operator but a negative number
    private fun isOperatorAdded(value: String):Boolean{
        //checking if number added starts with minus
        //i.e. negative number is added
        return if(value.startsWith("-")){
            false;
        }else{
            //contains returns a boolean
            value.contains("/")
                    ||value.contains("*")
                    ||value.contains("+")
                    ||value.contains("-")
        }
    }

    fun onDelete(view: View){
        val current=(tvInput?.text).toString();
        if(current.length !=0){
            val toReturn=current.subSequence(0,(current.length)-1);
            tvInput?.text=toReturn.toString();

        }else{
            Toast.makeText(this,"Nothing to delete",Toast.LENGTH_SHORT).show();
        }
    }
}