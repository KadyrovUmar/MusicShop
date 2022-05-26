package com.example.musicshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicshop.models.Product
import java.io.Serializable

class CardActivity : AppCompatActivity() {
    var cardRV:RecyclerView?=null
    var proceedOrderBtn:Button?=null
    var adapter:CartAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        proceedOrderBtn = findViewById(R.id.proceedOrder)
        cardRV = findViewById(R.id.cartRV)
        cardRV?.layoutManager = LinearLayoutManager(applicationContext)
        cardRV?.hasFixedSize()

        val intenl = intent
        val bundle = intenl.getBundleExtra("BundleCart")

        val bundleList: Serializable? = bundle?.getSerializable("MyCart")
        val productList:ArrayList<Product> = bundleList as ArrayList<Product>


        adapter = CartAdapter()
        adapter?.productList = productList
        cardRV?.adapter = adapter


    }
}
//01.18.00