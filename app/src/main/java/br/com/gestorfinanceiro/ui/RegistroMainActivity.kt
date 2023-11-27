package br.com.gestorfinanceiro.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.gestorfinanceiro.adapter.CategoriaListAdapter
import br.com.gestorfinanceiro.adapter.RegistroListAdapter
import br.com.gestorfinanceiro.adapter.listner.CategoriaOnClickListner
import br.com.gestorfinanceiro.adapter.listner.RegistroOnClickListner
import br.com.gestorfinanceiro.database.DBHelper
import br.com.gestorfinanceiro.databinding.ActivityMainBinding
import br.com.gestorfinanceiro.model.RegistroModel
import java.text.DecimalFormat
import java.text.NumberFormat

class RegistroMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var registrosList: ArrayList<RegistroModel>
    private lateinit var result: ActivityResultLauncher<Intent>
    private lateinit var adapter: RegistroListAdapter
    private lateinit var dbHelper: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)
        dbHelper = DBHelper(this)


        binding.recyclerViewRegistros.layoutManager = LinearLayoutManager(applicationContext)
        atualizarLista()


        binding.buttonAddRegistro.setOnClickListener {
            result.launch(Intent(applicationContext, CadastroRegistroActivity::class.java))
        }

        binding.buttonCategorias.setOnClickListener {
            startActivity(Intent(this, CategoriaMainActivity::class.java))
        }

        binding.buttonRelatorio.setOnClickListener {
            startActivity(Intent(this, RelatorioActivity::class.java))
        }

        binding.buttonLogout.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("nome", "")
            editor.apply()
            finish()
        }

        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                atualizarLista()
            } else  if (it.data != null && it.resultCode == 0) {
                Toast.makeText(applicationContext, "Operação cancelada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun atualizarLista() {

        val formatter: NumberFormat = DecimalFormat("#0.00");

        registrosList = dbHelper.getAllRegistros()
        adapter = RegistroListAdapter(registrosList, RegistroOnClickListner { registro ->
            val intent = Intent(applicationContext, RegistroDetalheActivity::class.java)
            intent.putExtra("id", registro.id)
            result.launch(intent)
        })
        binding.recyclerViewRegistros.adapter = adapter

        binding.textTotalReceitas.text = "R$ " + formatter.format(dbHelper.getTotal("Receita"))
        binding.textTotalDespesas.text = "R$ -" + formatter.format(dbHelper.getTotal("Despesa"))

    }
}