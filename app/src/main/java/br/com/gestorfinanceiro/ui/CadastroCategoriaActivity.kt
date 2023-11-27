package br.com.gestorfinanceiro.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.gestorfinanceiro.R
import br.com.gestorfinanceiro.database.DBHelper
import br.com.gestorfinanceiro.databinding.ActivityCadastroCategoriaBinding

class CadastroCategoriaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroCategoriaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(applicationContext)
        val i = intent

        binding.buttonCadastrar.setOnClickListener {
            val categoria = binding.editCategoria.text.toString()

            if (!categoria.isNullOrEmpty()) {
                val res = db.inserirCategoria(categoria)

                if (res > 0) {
                    Toast.makeText(applicationContext, "Categoria cadastrada!", Toast.LENGTH_SHORT).show()
                    setResult(1, i)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Erro ao cadastrar categoria!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonCancelar.setOnClickListener {
            setResult(0, i)
            finish()
        }
    }
}