package com.genz.seofixer

import android.app.Activity
import android.os.Bundle
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.*
import android.text.InputType
import android.graphics.drawable.GradientDrawable

class MainActivity : Activity() {
    private val bg = Color.rgb(246, 248, 252)
    private val ink = Color.rgb(24, 34, 54)
    private val muted = Color.rgb(103, 116, 139)
    private val blue = Color.rgb(48, 91, 230)
    private val green = Color.rgb(20, 143, 99)
    private lateinit var linkInput: EditText
    private lateinit var titleInput: EditText
    private lateinit var descInput: EditText
    private lateinit var tagsInput: EditText
    private lateinit var results: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        buildUi()
    }

    private fun buildUi() {
        val scroll = ScrollView(this).apply { setBackgroundColor(bg); isFillViewport = true }
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(20), dp(18), dp(20), dp(28)) }
        scroll.addView(root)
        setContentView(scroll)

        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        val mark = TextView(this).apply {
            text = "S"; textSize = 22f; setTextColor(Color.WHITE); gravity = Gravity.CENTER
            typeface = Typeface.DEFAULT_BOLD; background = shape(blue, 16); layoutParams = LinearLayout.LayoutParams(dp(48), dp(48))
        }
        top.addView(mark)
        val brand = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(12), 0, 0, 0) }
        brand.addView(label("CREATOR SEO FIXER", 17f, ink, true))
        brand.addView(label("YouTube audit • No API • Offline checks", 12f, muted, false))
        top.addView(brand, LinearLayout.LayoutParams(0, -2, 1f))
        root.addView(top)
        root.addView(space(22))
        root.addView(label("Make every detail count.", 29f, ink, true))
        root.addView(label("Check your video metadata, find basic SEO gaps, and get practical fixes.", 15f, muted, false))
        root.addView(space(18))

        val note = TextView(this).apply {
            text = "OFFLINE SEO CHECK\nNo sign-in, API key, Firebase or paid service. We only analyze the details you enter."
            textSize = 13f; setTextColor(ink); setPadding(dp(14), dp(13), dp(14), dp(13)); background = shape(Color.rgb(231, 239, 255), 14)
        }
        root.addView(note)
        root.addView(space(18))
        root.addView(label("1  •  Video or channel link", 17f, ink, true))
        linkInput = edit("Paste a YouTube video or channel URL", false)
        root.addView(linkInput)
        root.addView(space(14))
        root.addView(label("2  •  Video details", 17f, ink, true))
        root.addView(label("Link details are not fetched automatically. Enter metadata below for an honest local audit.", 13f, muted, false))
        root.addView(space(8))
        titleInput = edit("Video title", false); root.addView(titleInput)
        root.addView(space(10))
        descInput = edit("Video description", true); root.addView(descInput)
        root.addView(space(10))
        tagsInput = edit("Tags, separated by commas", true); root.addView(tagsInput)
        root.addView(space(16))
        val scan = button("Scan SEO issues", blue)
        root.addView(scan)
        scan.setOnClickListener { runAudit() }
        root.addView(space(18))
        results = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        root.addView(results)
        root.addView(space(16))
        root.addView(label("What this app can and cannot do", 17f, ink, true))
        root.addView(space(6))
        root.addView(label("It checks the URL format and the title, description and tags you provide. It cannot verify live views, subscribers, CTR, hidden tags or channel data without an official data source. Scores are rule-based guidance, not a YouTube ranking prediction.", 13f, muted, false))
    }

    private fun runAudit() {
        results.removeAllViews()
        val url = linkInput.text.toString().trim()
        val title = titleInput.text.toString().trim()
        val desc = descInput.text.toString().trim()
        val tags = tagsInput.text.toString().split(",").map { it.trim() }.filter { it.isNotBlank() }
        val problems = mutableListOf<Pair<String, String>>()
        val suggestions = mutableListOf<Pair<String, String>>()
        if (url.isBlank()) {
            problems.add("YouTube link missing" to "Add a video or channel URL before scanning.")
        } else if (!isYouTubeUrl(url)) {
            problems.add("Link format looks invalid" to "Use a youtube.com or youtu.be link. This check does not fetch data from YouTube.")
        }
        if (title.isBlank()) {
            problems.add("Title not provided" to "Enter the current title to analyze it.")
        } else {
            if (title.length < 30) problems.add("Title may be too short" to "Current length: ${title.length} characters. Add specific context if it helps viewers.")
            if (title.length > 70) problems.add("Title may be too long" to "Current length: ${title.length} characters. Consider making the main topic clearer and more concise.")
            if (title.length in 30..70) suggestions.add("Title length looks reasonable" to "Keep the main topic understandable and avoid misleading promises.")
            if (title.count { it == '|' || it == '•' || it == '-' || it == ':' } >= 4) problems.add("Title may feel cluttered" to "Try reducing separators and keeping one clear main idea.")
        }
        if (desc.isBlank()) {
            problems.add("Description missing" to "Write 2–3 useful opening sentences that accurately explain the video.")
        } else {
            if (desc.length < 120) problems.add("Description is very short" to "Current length: ${desc.length} characters. Add useful context, chapters or relevant links where appropriate.")
            else suggestions.add("Description has useful detail" to "Make sure the first lines explain the actual video and naturally include its topic.")
            if (!desc.contains("http://") && !desc.contains("https://")) suggestions.add("No links detected" to "If relevant, add genuine resources or related videos. Do not add unrelated links.")
        }
        if (tags.isEmpty()) problems.add("No tags entered" to "Tags are optional and not a guarantee of ranking. Add only accurate terms if you use them.")
        else {
            if (tags.size > 20) problems.add("Many tags entered" to "Review for duplicates and irrelevant terms.")
            if (tags.any { it.length > 30 }) problems.add("Some tags are long" to "Shorten tags to clear, relevant phrases.")
            if (tags.map { it.lowercase() }.distinct().size < tags.size) problems.add("Duplicate tags found" to "Remove repeated tags.")
        }
        val score = (100 - problems.sumBy {
            when {
                it.first.contains("invalid") || it.first.contains("missing") -> 18
                it.first.contains("short") || it.first.contains("long") -> 10
                else -> 6
            }
        }).coerceIn(0, 100)
        val card = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(16), dp(16), dp(16), dp(16)); background = shape(Color.WHITE, 18) }
        card.addView(label("YOUR LOCAL SEO REPORT", 12f, muted, true))
        card.addView(space(8))
        card.addView(label("$score / 100", 34f, if (score >= 75) green else blue, true))
        card.addView(label("Rule-based checklist score • not a YouTube ranking score", 12f, muted, false))
        card.addView(space(12))
        card.addView(label("${problems.size} issue(s) • ${suggestions.size} positive note(s)", 14f, ink, true))
        results.addView(card)
        results.addView(space(12))
        if (problems.isEmpty()) results.addView(infoCard("No basic issues found", "This only means the entered details passed these simple checks. It does not prove that the video is optimized or will rank."))
        problems.forEach { results.addView(space(8)); results.addView(infoCard("⚠  ${it.first}", it.second)) }
        suggestions.forEach { results.addView(space(8)); results.addView(infoCard("✓  ${it.first}", it.second)) }

        val topic = title.split(" ", ":", "|", "-").map { it.trim() }.filter { it.length > 2 }.take(5).joinToString(" ")
        val suggestedTitle = when {
            title.isBlank() -> "Enter your video topic to create a title suggestion"
            title.length > 70 -> title.take(66).trimEnd() + "…"
            title.length < 30 -> "$title | Complete Guide"
            else -> title
        }
        val suggestedDesc = when {
            title.isBlank() -> "Add a title first to create a description suggestion."
            else -> "In this video, we cover $topic. Watch for the key points, practical tips, and important details.\n\nChapters and useful links: add only if they appear in your video.\n\n#${topic.split(" ").firstOrNull()?.filter { it.isLetterOrDigit() } ?: "YouTube"}"
        }
        results.addView(space(12))
        results.addView(label("SUGGESTED FIXES", 17f, ink, true))
        results.addView(space(8))
        results.addView(infoCard("Suggested title", suggestedTitle))
        results.addView(copyButton("Copy suggested title", suggestedTitle))
        results.addView(space(8))
        results.addView(infoCard("Description draft", suggestedDesc))
        results.addView(copyButton("Copy description draft", suggestedDesc))
        val tagSuggestion = (tags + topic.split(" ").filter { it.length > 3 }).distinctBy { it.lowercase() }.take(12).joinToString(", ")
        results.addView(space(8))
        results.addView(infoCard("Tag ideas", if (tagSuggestion.isBlank()) "Enter a title and tags to see basic keyword ideas." else tagSuggestion))
        results.addView(copyButton("Copy tag ideas", tagSuggestion))
    }

    private fun isYouTubeUrl(value: String): Boolean {
        return try {
            val uri = android.net.Uri.parse(value)
            val host = uri.host?.lowercase() ?: return false
            (host == "youtube.com" || host.endsWith(".youtube.com") || host == "youtu.be" || host.endsWith(".youtu.be")) &&
                (uri.scheme == "https" || uri.scheme == "http")
        } catch (_: Exception) { false }
    }

    private fun infoCard(title: String, body: String): View {
        val box = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(14), dp(13), dp(14), dp(13)); background = shape(Color.WHITE, 15) }
        box.addView(label(title, 15f, ink, true))
        box.addView(space(5))
        box.addView(label(body, 13f, muted, false))
        return box
    }

    private fun copyButton(text: String, value: String): View {
        val b = button(text, Color.rgb(235, 240, 255))
        b.setTextColor(blue)
        b.setOnClickListener {
            if (value.isNotBlank()) {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(ClipData.newPlainText("SEO suggestion", value))
                Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(this, "Nothing to copy yet", Toast.LENGTH_SHORT).show()
        }
        return b
    }

    private fun edit(hintText: String, multi: Boolean): EditText {
        return EditText(this).apply {
            hint = hintText; textSize = 15f; setTextColor(ink); setHintTextColor(muted)
            setPadding(dp(14), dp(12), dp(14), dp(12)); background = shape(Color.WHITE, 13)
            inputType = if (multi) InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES else InputType.TYPE_CLASS_TEXT
            if (multi) { minLines = 3; gravity = Gravity.TOP or Gravity.START }
            layoutParams = LinearLayout.LayoutParams(-1, if (multi) dp(104) else dp(54))
        }
    }

    private fun button(text: String, color: Int): Button = Button(this).apply {
        this.text = text; textSize = 14f; isAllCaps = false; typeface = Typeface.DEFAULT_BOLD
        setTextColor(if (color == blue) Color.WHITE else ink)
        background = shape(color, 14)
        layoutParams = LinearLayout.LayoutParams(-1, dp(50))
    }

    private fun label(text: String, size: Float, color: Int, bold: Boolean): TextView = TextView(this).apply {
        this.text = text; textSize = size; setTextColor(color)
        if (bold) typeface = Typeface.DEFAULT_BOLD
        setLineSpacing(dp(2).toFloat(), 1f)
    }

    private fun space(height: Int): View = View(this).apply { layoutParams = LinearLayout.LayoutParams(1, dp(height)) }
    private fun dp(v: Int): Int = (v * resources.displayMetrics.density).toInt()
    private fun shape(color: Int, radius: Int): GradientDrawable = GradientDrawable().apply { setColor(color); cornerRadius = dp(radius).toFloat() }
}
