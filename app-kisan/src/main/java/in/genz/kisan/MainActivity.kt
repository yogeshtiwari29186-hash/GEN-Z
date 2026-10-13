package in.genz.kisan

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {
    private val green = Color.rgb(30, 120, 65)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply { orientation = 1; setPadding(28, 32, 28, 24); setBackgroundColor(Color.rgb(247,250,246)) }
        root.addView(TextView(this).apply { text = "GEN-Z"; textSize = 30f; setTextColor(green); gravity = Gravity.CENTER_VERTICAL })
        root.addView(TextView(this).apply { text = "Kisan App"; textSize = 18f; setTextColor(Color.DKGRAY) })
        root.addView(TextView(this).apply { text = "Namaste, Kisan!\nApni fasal aasani se bechein."; textSize = 22f; setTextColor(Color.BLACK); setPadding(0,32,0,24) })
        listOf("Fasal bechein", "Mere orders", "Notifications", "Payment history", "Profile / Address").forEach { label ->
            root.addView(Button(this).apply { text = label; isAllCaps = false; setOnClickListener { Toast.makeText(this@MainActivity, "$label — backend integration pending", Toast.LENGTH_SHORT).show() } })
        }
        root.addView(TextView(this).apply { text = "Starter UI only. Crop requests and account data are not yet connected to Firebase."; textSize = 12f; setTextColor(Color.GRAY); setPadding(0,24,0,0) })
        setContentView(ScrollView(this).apply { addView(root) })
    }
}
