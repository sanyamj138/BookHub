package com.sanyamj138.bookhub.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sanyamj138.bookhub.DescriptionActivity
import com.sanyamj138.bookhub.R
import com.sanyamj138.bookhub.model.Book
import com.squareup.picasso.Picasso
import java.util.ArrayList

class DashboardRecyclerAdapter(val context: Context, val itemList: ArrayList<Book>): RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>(){
    class DashboardViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val txtBookName : TextView = view.findViewById(R.id.txtBookName)
        val txtAuthor : TextView = view.findViewById(R.id.txtAuthor)
        val txtPrice : TextView = view.findViewById(R.id.txtPrice)
        val txtRating : TextView = view.findViewById(R.id.txtRating)
        val bookImage : ImageView = view.findViewById(R.id.imageBook)
        val content: LinearLayout = view.findViewById(R.id.content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater. from (parent. context).inflate(R.layout.recycler_view_dashboard, parent, false)

        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book = itemList[position]
        holder.txtBookName.text = book.bookName
        holder.txtAuthor.text = book.bookAuthor
        holder.txtPrice.text = book.bookPrice
        holder.txtRating.text = book.bookRating
//        holder.bookImage.setImageResource(book.bookImage)
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.bookImage)

        holder.content.setOnClickListener {
            val intent = Intent(context, DescriptionActivity::class.java)
            intent.putExtra("book_id", book.bookId)
            context.startActivity(intent)
        }
    }
}
