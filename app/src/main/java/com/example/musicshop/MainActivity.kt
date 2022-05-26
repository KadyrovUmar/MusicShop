package com.example.musicshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.musicshop.models.Product
import java.util.ArrayList
import java.util.HashMap

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var userName: EditText? = null
    private var spinner: Spinner? = null
    private var productImage: ImageView? = null
    private var price: TextView? = null
    private var btnPlus: Button? = null
    private var btnMinus: Button? = null
    private var quantity: TextView? = null
    private var addToCartBtn: Button? = null
    private var goodName: String = ""

    private var spinnerAdapter: ArrayAdapter<String>? = null
    private var productList = ArrayList<Product>()
    private var database: HashMap<String, Double>? = HashMap<String, Double>()
    private val titleList: ArrayList<String> = ArrayList<String>()
    private var amount = 1

    private var calcPrice: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        Initialization = Инициализация
        userName = findViewById(R.id.userName)
        productImage = findViewById(R.id.productImage)
        price = findViewById(R.id.price)
        btnPlus = findViewById(R.id.btnPlus)
        btnMinus = findViewById(R.id.btnMinus)
        quantity = findViewById(R.id.quantityText)
        addToCartBtn = findViewById(R.id.button2)

        //        List for spinner
        titleList.add("Drums")
        titleList.add("Guitar")
        titleList.add("Rock")
        titleList.add("Keyboard")

        //Spinner
        spinner = findViewById(R.id.spinner)
        spinner?.onItemSelectedListener = this
        spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item, titleList
        )
        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = spinnerAdapter

        // Database for Spinner
        database?.put("Guitar", 500.0)
        database?.put("Keyboard", 1000.0)
        database?.put("Drums", 700.0)
        database?.put("Rock", 1500.0)


        btnPlus?.setOnClickListener {
            amount++
            quantity?.text = amount.toString()
            price?.text = (calcPrice * amount).toString()
        }

        btnMinus?.setOnClickListener {
            amount--
            if (amount < 0) {
                amount = 0
            }
            quantity?.text = amount.toString()
            price?.text = (calcPrice * amount).toString()
        }

        addToCartBtn?.setOnClickListener {
            if (userName?.text!!.isNotEmpty()) {
                addToCart()
            } else {
                Toast.makeText(baseContext, "Все поля должны быть заполнены", Toast.LENGTH_SHORT)
                    .show()
            }

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.cart_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (productList.isEmpty()){
            Toast.makeText(baseContext, "Корзинка пуста", Toast.LENGTH_SHORT).show()
        }else{
            val intent = Intent(this, CardActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("MyCart", productList)
            intent.putExtra("BundleCart", bundle)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }




    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

        price?.text = database?.get(spinner?.selectedItem.toString()).toString() // возвращает цену
        calcPrice = price?.text.toString().toDouble()
        amount = 1
        quantity?.text = amount.toString()

        when (spinner?.selectedItem.toString()) {
            "Guitar" -> productImage?.setImageResource(R.drawable.guitar)
            "Keyboard" -> productImage?.setImageResource(R.drawable.keyboard)
            "Drums" -> productImage?.setImageResource(R.drawable.drums)
            "Rock" -> productImage?.setImageResource(R.drawable.rock)
        }
        goodName = spinner?.selectedItem.toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(this, "Ничего не нажали", Toast.LENGTH_SHORT).show()
    }


    fun addToCart() {
        val product = Product(
            goodName,                           // передаю название товара
            quantity?.text.toString().toInt(), // передаю количество
            price?.text.toString().toDouble().toInt(),     // передаю цену
            userName?.text.toString()        // передаю userName
        )
        if (productList.size>0){
            val answer = isHas(goodName, productList)
            if (answer!=null){
                answer.productPrice = answer.productPrice+price?.text.toString().toDouble().toInt()
                answer.productCount = answer.productCount+quantity?.text.toString().toInt()
            }
            else{
                productList.add(product)
            }
        }
        else{
            productList.add(product)
        }

        Toast.makeText(baseContext, "Вы добавили товар в корзину", Toast.LENGTH_SHORT).show()
    }


    fun isHas(productName: String, productList: List<Product>): Product? {
        var product: Product? = null
        for (p: Product in productList) {
            if (p.productTitle == productName) {
                product = p
            }
        }
        return product
    }

}

