package br.com.gestorfinanceiro.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.gestorfinanceiro.R
import br.com.gestorfinanceiro.database.DBHelper
import br.com.gestorfinanceiro.databinding.ActivityCategoriaDetalheBinding
import br.com.gestorfinanceiro.model.CategoriaModel

class CategoriaDetalheActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriaDetalheBinding
    private lateinit var db: DBHelper
    private var categoriaModel = CategoriaModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriaDetalheBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        val id = i.extras?.getInt("id")

        db = DBHelper(applicationContext)

        if (id != null) {
            categoriaModel = db.getCategoria(id)
            binding.editCategoria.setText(categoriaModel.categoria)
        }

        binding.buttonCancelar.setOnClickListener {
            setResult(1, i)
            finish()
        }

        binding.buttonSalvar.setOnClickListener {
            val res =db.atualizarCategoria(id = categoriaModel.id, categoria = binding.editCategoria.text.toString())

            if (res > 0) {
                Toast.makeText(applicationContext, "Categoria editada!", Toast.LENGTH_SHORT).show()
                setResult(1, i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Erro ao editar categoria!", Toast.LENGTH_SHORT).show()
                setResult(0, i)
                finish()
            }
        }

        binding.buttonDeletar.setOnClickListener {
            val res = db.deletarCategoria(id = categoriaModel.id)
            if (res > 0) {
                Toast.makeText(applicationContext, "Categoria deletada!", Toast.LENGTH_SHORT).show()
                setResult(1, i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Erro ao deletar categoria!", Toast.LENGTH_SHORT).show()
                setResult(1, i)
                finish()
            }
        }

    }
}