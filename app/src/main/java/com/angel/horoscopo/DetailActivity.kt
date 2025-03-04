package com.angel.horoscopo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailActivity : AppCompatActivity() {

    lateinit var iconImageView: ImageView
    lateinit var nameTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var horoscope: Horoscope

    companion object{
        const val  EXTRA_HOROSCOPE_ID = "horoscope_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_detail)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getStringExtra(EXTRA_HOROSCOPE_ID)!!
        horoscope = Horoscope.findById(id)

        initView()
        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                Log.i("MENU", "Menu favorito")
                true
            }
            R.id.share -> {
                Log.i("MENU", "Menu compartir")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
        private fun loadData () {
            supportActionBar?.setTitle(horoscope.name)
            supportActionBar?.setSubtitle(horoscope.date)


            iconImageView.setImageResource(horoscope.icon)
            nameTextView.text = getString(horoscope.name)
            dateTextView.text = getString(horoscope.date)
        }

    private fun initView () {
        nameTextView = findViewById(R.id.nameTextView)
        dateTextView = findViewById(R.id.datesTextView)
        iconImageView = findViewById(R.id.iconImageView)

    }
}