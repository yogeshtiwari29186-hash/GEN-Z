package in.genz.delivery

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
        root.addView(TextView(this).apply { text = "GEN-Z DELIVERY"; textSize = 26f; setTextColor(green) })
        root.addView(TextView(this).apply { text = "Assigned delivery"; textSize = 18f; setTextColor(Color.DKGRAY); setPadding(0,16,0,12) })
        root.addView(TextView(this).apply { text = "No delivery assigned yet.\nWhen Owner assigns an order, pickup and drop details appear here."; textSize = 16f; setTextColor(Color.BLACK); setPadding(0,8,0,18) })
        root.addView(TextView(this).apply { text = "DELIVERY ESTIMATE (DEMO)"; textSize = 18f; setTextColor(green); setPadding(0,8,0,8) })
        val km = EditText(this).apply { hint = "Route distance in km"; inputType = 8194; setText("5") }
        root.addView(km)
        val price = TextView(this).apply { text = "Charge: set by Owner"; textSize = 22f; setTextColor(Color.BLACK); gravity = Gravity.CENTER; setPadding(0,16,0,16) }
        root.addView(price)
        root.addView(Button(this).apply { text = "Estimate (demo only)"; isAllCaps = false; setOnClickListener {
            val distance = km.text.toString().toDoubleOrNull()
            if (distance == null || distance <= 0) km.error = "Enter a valid distance"
            else price.text = "Distance: %.1f km\nFinal charge: Owner's saved rate".format(distance)
        } })
        listOf("Accept assignment", "Reached pickup", "Picked up", "On the way", "Delivered / Failed", "Share location (permission-based)").forEach { label ->
            root.addView(Button(this).apply { text = label; isAllCaps = false; setOnClickListener { Toast.makeText(this@MainActivity, "$label — backend/location setup pending", Toast.LENGTH_SHORT).show() } })
        }
        root.addView(TextView(this).apply { text = "Demo UI only: live location and order updates are not active until backend, permissions and foreground location service are implemented."; textSize = 12f; setTextColor(Color.GRAY); setPadding(0,18,0,0) })
        setContentView(ScrollView(this).apply { addView(root) })
    }
}
