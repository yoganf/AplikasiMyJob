package com.yoga.aplikasimyjob.Dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoga.aplikasimyjob.R
import com.yoga.aplikasimyjob.User

class recomendasiAdapter(private var data : List<User>,
                         private var listener: (User) -> Unit)
    : RecyclerView.Adapter<recomendasiAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder (
        parent: ViewGroup,
        viewType: Int
    ): recomendasiAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_recomendasi, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: recomendasiAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val tvNama: TextView = view.findViewById(R.id.tx)
        private val tvAlamat: TextView = view.findViewById(R.id.tv_alamat)
        private val tvTitle: TextView = view.findViewById(R.id.tv_role)
        private val ivImage: ImageView = view.findViewById(R.id.iv_poster_image)

        fun bindItem(data: User, listener: (User) -> kotlin.Unit, context: Context) {
            tvTitle.setText(data.role)
            tvNama.setText(data.nama)
            tvAlamat.setText(data.alamat)
            Glide.with(context)
                .load(data.urlportofolio)
                .into(ivImage)

            itemView.setOnClickListener{
                listener(data)
            }
        }
    }
}
