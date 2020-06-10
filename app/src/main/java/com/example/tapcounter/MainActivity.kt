package com.example.tapcounter

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.tapcounter.R.string.score
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

  //var totaltime = time
    var totalcount: Int = 0
   var elapsed = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val value = findViewById<TextView>(R.id.value)

        val meter = findViewById<Chronometer>(R.id.c_meter)
        val btn = findViewById<Button>(R.id.tap_me)
        val sbtn = findViewById<Button>(R.id.start)
        val vbtn = findViewById<Button>(R.id.score)


        sbtn?.setOnClickListener(object: View.OnClickListener {
           var isWorking = false
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onClick(v: View?) {
                
                isWorking = if (!isWorking){
                    meter.base = SystemClock.elapsedRealtime() - elapsed;
                    meter.start()
                    elapsed = (SystemClock.elapsedRealtime() -  meter.base).toInt();
                    true
                }else{

                    meter.stop()
                    false
                }

                var time  = meter.base

                sbtn.setText(if (isWorking) R.string.start else R.string.stop)

                Toast.makeText(this@MainActivity, getString(
                    if (isWorking)
                        R.string.working
                    else
                        R.string.stopped),
                    Toast.LENGTH_SHORT).show()
            }
        })


       btn?.setOnClickListener{
           value.text = "" + ++totalcount
          // val total = value
       }

        vbtn?.setOnClickListener {
            // build alert dialog
            val builder = AlertDialog.Builder(this)

            // set message of alert dialog
            builder.setMessage("You tap the button: $totalcount  in $elapsed")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("ok", DialogInterface.OnClickListener {
                        dialog, id -> finish()
                })
                // negative button text and action
                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            // create dialog box
            val alert = builder.create()
            // set title for alert dialog box
            alert.setTitle("Score board")
            // show alert dialog
            alert.show()
        }

    }
}
