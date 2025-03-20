package com.angel.horoscopo


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
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
    lateinit var horoscopeLuckTextView: TextView
    lateinit var progressBar: LinearProgressIndicator
    lateinit var bottomNavigation: BottomNavigationView


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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        iconImageView = findViewById(R.id.iconImageView)
        horoscopeLuckTextView = findViewById(R.id.horoscopeLuckTextView)
        progressBar = findViewById(R.id.progressBar)
        bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigationView)
        bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.action_daily -> {
                    getHoroscopeLuck("daily")
                    true
                }
                R.id.action_weekly -> {
                    getHoroscopeLuck("weekly")
                    true
                }
                R.id.action_month -> {
                    getHoroscopeLuck("monthly")
                    true
                }
                else -> false
            }
        }
    }

    private fun loadData() {
        supportActionBar?.setTitle(horoscope.name)
        supportActionBar?.setSubtitle(horoscope.date)
        iconImageView.setImageResource(horoscope.icon)
        isFavorite = sessionManager.isFavorite(horoscope.id)

        getHoroscopeLuck("monthly")
    }

    private fun getHoroscopeLuck(period: String) {
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            var urlConnection: HttpsURLConnection? = null

            try {
                val url =
                    URL("https://horoscope-app-api.vercel.app/api/v1/get-horoscope/$period?sign=${horoscope.id}&day=TODAY")
                urlConnection = url.openConnection() as HttpsURLConnection

                if (urlConnection.responseCode == 200) {
                    val rd = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    var line: String?
                    val stringBuilder = StringBuilder()
                    while ((rd.readLine().also { line = it }) != null) {
                        stringBuilder.append(line)
                    }
                    val result = stringBuilder.toString()

                    val jsonObject = JSONObject(result)
                    val horoscopeLuck = jsonObject.getJSONObject("data").getString("horoscope_data")

                    CoroutineScope(Dispatchers.Main).launch {
                        horoscopeLuckTextView.text = horoscopeLuck
                        progressBar.visibility = View.GONE
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection?.disconnect()
            }
        }
    }
}
