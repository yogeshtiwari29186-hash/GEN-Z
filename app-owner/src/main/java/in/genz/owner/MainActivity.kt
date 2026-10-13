package in.genz.owner

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {
    private val green = Color.rgb(30, 120, 65)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply { orientation = 1; setPadding(24, 28, 24, 24); setBackgroundColor(Color.rgb(247,250,246)) }
        root.addView(TextView(this).apply { text = "GEN-Z OWNER"; textSize = 26f; setTextColor(green) })
        root.addView(TextView(this).apply { text = "Purchase & Delivery Dashboard"; textSize = 16f; setTextColor(Color.DKGRAY); setPadding(0,4,0,18) })
        listOf("Pending purchase requests", "Today's purchases", "Payments & outstanding", "Monthly / yearly reports", "Delivery boy management").forEach { label ->
            root.addView(Button(this).apply { text = label; isAllCaps = false; setOnClickListener { Toast.makeText(this@MainActivity, "$label — backend integration pending", Toast.LENGTH_SHORT).show() } })
        }
        root.addView(TextView(this).apply { text = "DELIVERY RATE SETTINGS"; textSize = 18f; setTextColor(green); setPadding(0,24,0,8) })
        val value = TextView(this).apply { text = "₹10 per km"; textSize = 24f; setTextColor(Color.BLACK); gravity = Gravity.CENTER }
        root.addView(value)
        root.addView(SeekBar(this).apply {
            max = 10
            progress = 0
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(s: SeekBar?, p: Int, fromUser: Boolean) { value.text = "₹${10 + p} per km" }
                override fun onStartTrackingTouch(s: SeekBar?) {}
                override fun onStopTrackingTouch(s: SeekBar?) {}
            })
        })
        root.addView(TextView(this).apply { text = "Owner sets the rate from ₹10 to ₹20/km. Save the rate snapshot on every assigned delivery; Delivery Boy cannot edit it."; textSize = 12f; setTextColor(Color.GRAY); setPadding(0,8,0,0) })
        setContentView(ScrollView(this).apply { addView(root) })
    }
}
