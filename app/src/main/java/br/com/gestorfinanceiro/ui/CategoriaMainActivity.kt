package br.com.gestorfinanceiro.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.gestorfinanceiro.adapter.CategoriaListAdapter
import br.com.gestorfinanceiro.adapter.listner.CategoriaOnClickListner
import br.com.gestorfinanceiro.database.DBHelper
import br.com.gestorfinanceiro.databinding.ActivityCategoriaBinding
import br.com.gestorfinanceiro.model.CategoriaModel

class CategoriaMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriaBinding
    private lateinit var categoriaList: List<CategoriaModel>
    private lateinit var adapter: CategoriaListAdapter
    private lateinit var result: ActivityResultLauncher<Intent>
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.buttonAddCategoria.setOnClickListener {
            result.launch(Intent(applicationContext, CadastroCategoriaActivity::class.java))
        }

        binding.buttonVoltar.setOnClickListener {
            finish()
        }

        binding.recyclerViewCategorias.layoutManager = LinearLayoutManager(applicationContext)
        atualizarLista()

        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                atualizarLista()
                //adapter.notifyDataSetChanged()
            } else  if (it.data != null && it.resultCode == 0) {
                Toast.makeText(applicationContext, "Operação cancelada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun atualizarLista() {
        categoriaList = dbHelper.getAllCategoria().sortedWith(compareBy { it.categoria })
        adapter = CategoriaListAdapter(categoriaList, CategoriaOnClickListner { categoria ->
            val intent = Intent(applicationContext, CategoriaDetalheActivity::class.java)
            intent.putExtra("id", categoria.id,)
            result.launch(intent)
        })

        binding.recyclerViewCategorias.adapter = adapter
    }
}