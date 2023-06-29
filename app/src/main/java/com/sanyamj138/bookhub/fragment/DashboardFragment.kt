package com.sanyamj138.bookhub.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sanyamj138.bookhub.R
import com.sanyamj138.bookhub.adapter.DashboardRecyclerAdapter
import com.sanyamj138.bookhub.model.Book
import com.sanyamj138.bookhub.util.ConnectionManager


class DashboardFragment : Fragment() {

    lateinit var dashboardRecycler: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var btn_dashboard: Button

    val bookList = arrayListOf(
        "P.S. I love You",
        "The Great Gatsby",
        "Anna Karenina",
        "Madame Bovary",
        "War and Peace",
        "Lolita",
        "Middlemarch",
        "The Adventures of Huckleberry Finn",
        "Moby-Dick",
        "The Lord of the Rings"
    )

    val bookInfoList = arrayListOf<Book>(
        Book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5", R.drawable.ps_ily),
        Book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1", R.drawable.great_gatsby),
        Book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3", R.drawable.anna_kare),
        Book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0", R.drawable.madame),
        Book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8", R.drawable.war_and_peace),
        Book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9", R.drawable.lolita),
        Book("Middlemarch", "George Eliot", "Rs. 599", "4.2", R.drawable.middlemarch),
        Book("The Adventures of Huckleberry Finn", "Mark Twain", "Rs. 699", "4.5", R.drawable.adventures_finn),
        Book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5", R.drawable.moby_dick),
        Book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0", R.drawable.lord_of_rings)
    )


    lateinit var recyclerAdapter: DashboardRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        dashboardRecycler = view.findViewById(R.id.dashboardRecycler)

        layoutManager = LinearLayoutManager(activity)

        btn_dashboard = view.findViewById(R.id.btn_dashboard)

        btn_dashboard.setOnClickListener {
            if(ConnectionManager().checkConnectivity(activity as Context)) {
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("Connection Found")
                dialog.setPositiveButton("Ok") {text, listener ->
                }
                dialog.setNegativeButton("Cancel") {text, listener ->
                }
                dialog.create()
                dialog.show()
            } else {
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Connection Not Found")
                dialog.setPositiveButton("Ok") {text, listener ->
                }
                dialog.setNegativeButton("Cancel") {text, listener ->
                }
                dialog.create()
                dialog.show()
            }
        }

        recyclerAdapter = DashboardRecyclerAdapter(activity as Context, bookInfoList)

        dashboardRecycler.adapter = recyclerAdapter

        dashboardRecycler.layoutManager = layoutManager

        dashboardRecycler. addItemDecoration (
            DividerItemDecoration (
                dashboardRecycler.context,(layoutManager as LinearLayoutManager).orientation
            )
        )

        val queue = Volley.newRequestQueue(activity as Context)

        val url = "http://13.235.250.119/vl/book/fetch_books/"

        val jsonObjectRequest = object :JsonObjectRequest(Method.GET, url, null, Response.Listener {

        }, Response.ErrorListener {

        }) {
            override fun getHeaders(): MutableMap<String, String> {

                val headers = HashMap<String, String> ()
                headers["Content-type"] = "application/json"
                headers["token"] = "e1c9baa92f9610"
                return headers
            }
        }

        return view
    }
}