package com.espinoza.rudencio.laboratoriorecuperacioncalificado02

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.espinoza.rudencio.laboratoriorecuperacioncalificado02.databinding.ActivityEjercio02DetailBinding

class Ejercicio02Detail : AppCompatActivity() {
    private lateinit var binding: ActivityEjercio02DetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEjercio02DetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listeningComponent()
        intent.extras?.let { showData(it) }
    }

    private fun showData(paramExtras: Bundle) {
        val fullName = paramExtras.getString(FULL_NAME_KEY)
        val phone = paramExtras.getString(PHONE_KEY)
        val products = paramExtras.getString(PRODUCTS_KEY)
        val direcction = paramExtras.getString(DIRECCTION_KEY)
        binding.tvName.text = fullName
        binding.tvPhone.text = phone
        binding.tvProducts.text = products
        binding.tvDirecction.text = direcction
    }

    private fun listeningComponent() {
        binding.btnPhone.setOnClickListener { phone() }
        binding.btnWsp.setOnClickListener { wspMessage() }
        binding.btnMaps.setOnClickListener { maps() }
    }

    private fun phone() {
        val phone = binding.tvPhone.text.toString().replace("\\s*".toRegex(), "")
        if (phone.length == 9) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+51$phone")
            startActivity(intent)
        } else {
            Toast.makeText(this, "Ingrese un número de teléfono válido (9 dígitos)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun maps() {
        val address = binding.tvDirecction.text.toString()
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("geo:0,0?q=$address")
        startActivity(intent)
    }

    private fun wspMessage() {
        val phone = binding.tvPhone.text.toString()
        val name = binding.tvName.text.toString()
        val products = binding.tvProducts.text.toString()
        val address = binding.tvDirecction.text.toString()
        val message = "Hola $name, Tus productos: $products están en camino a la dirección: $address"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$phone&text=${Uri.encode(message)}")
        startActivity(intent)
    }

    companion object {
        const val FULL_NAME_KEY = "full_name"
        const val PHONE_KEY = "phone"
        const val PRODUCTS_KEY = "products"
        const val DIRECCTION_KEY = "direction"
    }
}
