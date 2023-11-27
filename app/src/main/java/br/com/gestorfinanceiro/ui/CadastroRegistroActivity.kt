package br.com.gestorfinanceiro.ui

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import android.widget.Toast
import br.com.gestorfinanceiro.database.DBHelper
import br.com.gestorfinanceiro.databinding.ActivityCadastroRegistroBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class CadastroRegistroActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityCadastroRegistroBinding
    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(applicationContext)
        val i = intent

        val tipos = listOf("Despesa", "Receita")
        val autoComplete: AutoCompleteTextView = binding.autoCompleteTipos
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipos)
        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val tipoSelecionado = parent.getItemAtPosition(position)
            }

        val categorias = db.getAllCategoriaLista()
        val autoCompleteCategoria: AutoCompleteTextView = binding.autoCompleteCategoria
        val adapterCategoria = ArrayAdapter(this, android.R.layout.simple_list_item_1, categorias)
        autoCompleteCategoria.setAdapter(adapterCategoria)

        autoCompleteCategoria.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val categoriaSelecionada = parent.getItemAtPosition(position)
            }

        binding.textData.setOnClickListener {
            DatePickerDialog(
                this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.buttonCadastrar.setOnClickListener {
            val tipo = binding.autoCompleteTipos.text.toString()
            val data = binding.textData.text.toString()
            val categoria = binding.autoCompleteCategoria.text.toString()
            val valor = binding.editValor.text.toString()
            val descricao = binding.editDescricao.text.toString()

            var valorConvertido: Double = 0.0

            if (!valor.isNullOrEmpty()) {
                valorConvertido = valor.toDouble()
            }


            if (!tipo.isNullOrEmpty() && !data.isNullOrEmpty() && !categoria.isNullOrEmpty() &&
                valorConvertido > 0 && !descricao.isNullOrEmpty()) {
                val res = db.inserirRegistro(data, tipo, categoria, valorConvertido, descricao)

                if (res > 0) {
                    Toast.makeText(applicationContext, "Registro cadastrado!", Toast.LENGTH_SHORT).show()
                    setResult(1, i)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Erro ao cadastrar registro!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonCancelar.setOnClickListener {
            finish()
        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
        mostraDataFormatada(calendar.timeInMillis)
    }

    private fun mostraDataFormatada(timestamp: Long) {
        binding.textData.text = formatter.format(timestamp)
    }
}