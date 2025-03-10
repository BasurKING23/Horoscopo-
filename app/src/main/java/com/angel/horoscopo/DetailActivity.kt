package com.angel.horoscopo

import android.annotation.SuppressLint
import android.content.Intent
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailActivity : AppCompatActivity() {

    lateinit var iconImageView: ImageView
    lateinit var nameTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var horoscope: Horoscope
    var isFavorite = false
    lateinit var favoriteMenu: MenuItem
    lateinit var sessionManager: SessionManager

    companion object {
        const val EXTRA_HOROSCOPE_ID = "horoscope_id"
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

        sessionManager = SessionManager(this)

        val id = intent.getStringExtra(EXTRA_HOROSCOPE_ID)!!
        horoscope = Horoscope.findById(id)

        initView()
        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_detail, menu)

        favoriteMenu = menu!!.findItem(R.id.action_favorite)
        setFavoriteIcon()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                isFavorite = !isFavorite
                sessionManager.setFavorite(horoscope.id, isFavorite)
                setFavoriteIcon()
                true
            }
            R.id.action_share -> {
                val sendIntent = Intent()
                sendIntent.setAction(Intent.ACTION_SEND)
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                sendIntent.setType("text/plain")

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                true
            }

            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadData() {
        supportActionBar?.setTitle(horoscope.name)
        supportActionBar?.setSubtitle(horoscope.date)

        iconImageView.setImageResource(horoscope.icon)
    //    nameTextView.text = getString(horoscope.name)
    //    dateTextView.text = getString(horoscope.date)
        isFavorite = sessionManager.isFavorite(horoscope.id)
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    //    nameTextView = findViewById(R.id.nameTextView)
    //    dateTextView = findViewById(R.id.datesTextView)
        iconImageView = findViewById(R.id.iconImageView)
    }

    private fun setFavoriteIcon() {
        if (isFavorite) {
            favoriteMenu.setIcon(R.drawable.favorite_selected)
        } else {
            favoriteMenu.setIcon(R.drawable.favorite)
        }
    }
    private fun getHoroscopeLuck () {
        CoroutineScope(Dispatchers.IO).launch {

            try{
                val url = URL("https://horoscope-app-api.vercel.app/api/v1/get-horoscope/daily?sign=$EXTRA_HOROSCOPE_ID&day=TODAY")
                urlConnection = url.openConnection() as HttpsURLConnection

                if (urlConnection.responseCode == 200) {
                    val rd = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    var line: String?
                    val stringBuilder = StringBuilder()
                    while ((rd.readLine().also { line = it }) != null) {
                        stringBuilder.append(line)
                    }
                    val result = stringBuilder.toString()
                    Log.i(tag"HTTP",result)

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }

}
                return null;

                var urlConnection: HttpsURLConnection? = null


            }
    }

}