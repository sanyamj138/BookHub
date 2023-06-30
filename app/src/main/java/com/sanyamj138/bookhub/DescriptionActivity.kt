package com.sanyamj138.bookhub

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sanyamj138.bookhub.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONObject

class DescriptionActivity : AppCompatActivity() {

    lateinit var txtBookName: TextView
    lateinit var txtBookAuthor:TextView
    lateinit var txtBookPrice: TextView
    lateinit var txtBookRating: TextView
    lateinit var imgBookImage: ImageView
    lateinit var txtBookDesc: TextView
    lateinit var btnAddToFav: Button
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    lateinit var toolbar: Toolbar

    var bookId: String ?= "100"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        txtBookName = findViewById(R.id.txtBookName)
        txtBookAuthor = findViewById(R.id.txtBookAuthor)
        txtBookPrice = findViewById(R.id.txtBookPrice)
        txtBookRating = findViewById(R.id.txtBookRating)
        imgBookImage = findViewById(R.id.imgBookImage)
        txtBookDesc = findViewById(R.id.txtBookDesc)
        btnAddToFav = findViewById(R.id.btnAddToFav)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE

        toolbar = findViewById (R. id. toolbar)
        setSupportActionBar (toolbar)
        supportActionBar?. title = "Book Details"

        if(intent != null){
            bookId = intent.getStringExtra("book_id")
        } else {
            finish ()
            Toast.makeText( this@DescriptionActivity, "Some unexpected error occurred!", Toast. LENGTH_SHORT).show()
        }

        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"
        val jsonParams = JSONObject ()
        jsonParams.put("book_id", bookId)

        if(ConnectionManager().checkConnectivity(this@DescriptionActivity)) {
            val jsonRequest = object: JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {
                try {
                    val success = it. getBoolean ("success")
                    if (success) {
                        val bookJsonobject = it. getJSONObject ("book_data")
                        progressLayout.visibility = View.GONE
                        Picasso.get().load (bookJsonobject.getString ("image")).error (R.drawable.default_book_cover).into(imgBookImage)
                        txtBookName. text = bookJsonobject. getString("name")
                        txtBookAuthor. text = bookJsonobject.getString("author")
                        txtBookPrice. text = bookJsonobject.getString ("price")
                        txtBookRating. text = bookJsonobject.getString ("rating")
                        txtBookDesc.text = bookJsonobject.getString ("description")

                    } else {
                        Toast.makeText(this@DescriptionActivity, "Some Error Occurred!", Toast.LENGTH_SHORT).show ()
                    }

                } catch (e: Exception) {
                    Toast.makeText(this@DescriptionActivity, "Some error occurred!", Toast.LENGTH_SHORT).show ()
                }
            }, Response.ErrorListener {
                Toast.makeText(this@DescriptionActivity, "Volley Error $it", Toast. LENGTH_SHORT).show ()
            }) {
                override fun getHeaders (): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "e1c9baa92f9610"
                    return headers
                }
            }
            queue.add(jsonRequest)
        }
        else {
            val dialog = AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Connection Not Found")
            dialog.setPositiveButton("Open Settings") {text, listener ->

                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)

                startActivity(settingsIntent)
                finish()
            }
            dialog.setNegativeButton("Cancel") {text, listener ->
                ActivityCompat.finishAffinity(this@DescriptionActivity)
            }
            dialog.create()
            dialog.show()
        }
    }
}