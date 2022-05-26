package com.example.musicshop

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicshop.models.Product

class CartAdapter() : RecyclerView.Adapter<CartAdapter.Holder>() {

    var productList: List<Product> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productImg: ImageView? = null
        var productTitle: TextView? = null
        var productPrice: TextView? = null
        var productQuantity: TextView? = null

        init {
            productImg = itemView.findViewById(R.id.cartProductImage)
            productTitle = itemView.findViewById(R.id.cartProductTitle)
            productPrice = itemView.findViewById(R.id.cartProductPrice)
            productQuantity = itemView.findViewById(R.id.cartProductQuantity)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: CartAdapter.Holder, position: Int) {
        val productTitle: String? = productList[position].productTitle
        holder.productTitle?.text = productTitle
        holder.productQuantity?.text = productList[position].productCount.toString()
        holder.productPrice?.text = productList[position].productPrice.toString()
        when (productTitle) {
            "Guitar" -> holder.productImg?.setImageResource(R.drawable.guitar)
            "Keyboard" -> holder.productImg?.setImageResource(R.drawable.keyboard)
            "Drums" -> holder.productImg?.setImageResource(R.drawable.drums)
            "Rock" -> holder.productImg?.setImageResource(R.drawable.rock)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}