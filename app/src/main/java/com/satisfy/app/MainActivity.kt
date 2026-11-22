package com.satisfy.app

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {

    private val bg = Color.rgb(12, 16, 24)
    private val panel = Color.rgb(24, 31, 43)
    private val blue = Color.rgb(0, 145, 255)
    private val white = Color.WHITE
    private val muted = Color.rgb(155, 168, 185)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(bg)
        }

        val scroll = ScrollView(this)
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(22, 28, 22, 32)
        }

        val top = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val logo = TextView(this).apply {
            text = "S"
            textSize = 34f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(blue)
            gravity = Gravity.CENTER
            setBackgroundColor(panel)
        }

        top.addView(logo, LinearLayout.LayoutParams(58, 58))

        val brand = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(14, 0, 0, 0)
        }

        brand.addView(label("SATISFY", 23f, white, true))
        brand.addView(label("STUDIO", 12f, blue, true))
        top.addView(brand)

        content.addView(top)
        content.addView(space(32))

        content.addView(label("Welcome to SATISFY Studio", 25f, white, true))
        content.addView(label(
            "Your game creation workspace",
            14f, muted, false
        ))

        content.addView(space(26))

        val create = TextView(this).apply {
            text = "+   CREATE NEW PROJECT"
            textSize = 16f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(white)
            gravity = Gravity.CENTER
            setBackgroundColor(Color.rgb(0, 105, 210))
            setPadding(16, 20, 16, 20)
            setOnClickListener { showCreateProjectDialog() }
        }

        content.addView(create)
        content.addView(space(30))

        content.addView(sectionTitle("WORKSPACE"))

        content.addView(menuCard(
            "My Projects",
            "Manage your game projects",
            "PROJECTS"
        ) {
            showMessage("My Projects", "Your project workspace is ready for development.")
        })

        content.addView(menuCard(
            "Asset Library",
            "Models, materials, textures and audio",
            "ASSETS"
        ) {
            showMessage("Asset Library", "Asset Library module is planned for the next phase.")
        })

        content.addView(menuCard(
            "Engine Settings",
            "Configure graphics and engine systems",
            "ENGINE"
        ) {
            showMessage("Engine Settings", "C++ core and Vulkan renderer integration will be added in a later phase.")
        })

        content.addView(space(26))
        content.addView(sectionTitle("ENGINE STATUS"))

        val status = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(18, 18, 18, 18)
            setBackgroundColor(panel)
        }

        status.addView(label("SATISFY ENGINE", 16f, white, true))
        status.addView(space(8))
        status.addView(label("Studio interface: Active", 14f, muted, false))
        status.addView(label("C++ Core: Not integrated yet", 14f, muted, false))
        status.addView(label("Vulkan Renderer: Not integrated yet", 14f, muted, false))

        content.addView(status)
        content.addView(space(28))
        content.addView(label(
            "SATISFY STUDIO  •  VERSION 1.0",
            11f, muted, true
        ))

        scroll.addView(content)
        root.addView(scroll)
        setContentView(root)
    }

    private fun label(
        value: String,
        size: Float,
        color: Int,
        bold: Boolean
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            if (bold) typeface = Typeface.DEFAULT_BOLD
        }
    }

    private fun sectionTitle(value: String): TextView {
        return label(value, 13f, blue, true).apply {
            setPadding(0, 0, 0, 12)
        }
    }

    private fun space(height: Int): View {
        return View(this).apply {
            layoutParams = LinearLayout.LayoutParams(1, height)
        }
    }

    private fun menuCard(
        title: String,
        description: String,
        tag: String,
        action: () -> Unit
    ): LinearLayout {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(18, 18, 18, 18)
            setBackgroundColor(panel)
            setOnClickListener { action() }
        }

        card.addView(label(title, 18f, white, true))
        card.addView(space(6))
        card.addView(label(description, 13f, muted, false))
        card.addView(space(8))
        card.addView(label(tag, 10f, blue, true))

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.bottomMargin = 12
        card.layoutParams = params

        return card
    }

    private fun showCreateProjectDialog() {
        val input = EditText(this).apply {
            hint = "Enter project name"
            setSingleLine(true)
        }

        AlertDialog.Builder(this)
            .setTitle("Create New Project")
            .setMessage("Start a new SATISFY game project.")
            .setView(input)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Create") { _, _ ->
                val name = input.text.toString().trim()
                if (name.isNotEmpty()) {
                    showMessage("Project Created", "$name is ready for the next development phase.")
                } else {
                    showMessage("Project Name Required", "Please enter a project name.")
                }
            }
            .show()
    }

    private fun showMessage(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
