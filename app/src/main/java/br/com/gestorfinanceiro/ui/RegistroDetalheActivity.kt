package br.com.gestorfinanceiro.ui

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import android.widget.Toast
import br.com.gestorfinanceiro.R
import br.com.gestorfinanceiro.database.DBHelper
import br.com.gestorfinanceiro.databinding.ActivityRegistroDetalheBinding
import br.com.gestorfinanceiro.model.RegistroModel
import java.text.SimpleDateFormat
import java.util.Calendar

class RegistroDetalheActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityRegistroDetalheBinding
    private lateinit var db: DBHelper
    private var registroModel = RegistroModel()
    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroDetalheBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        val id = i.extras?.getInt("id")

        db = DBHelper(applicationContext)

        val tipos = listOf("Despesa", "Receita")
        val autoComplete: AutoCompleteTextView = binding.autoCompleteTipos
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipos)
        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val tipoSelecionado = parent.getItemAtPosition(position)
        }

        val categorias = db.getAllCategoriaLista()
        val autoCompleteCategoria: AutoCompleteTextView = binding.autoCompleteCategoria
        val adapterCategoria = ArrayAdapter(this, android.R.layout.simple_list_item_1, categorias)
        autoCompleteCategoria.setAdapter(adapterCategoria)

        autoCompleteCategoria.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
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

        if (id != null) {
            registroModel = db.getRegistro(id)
            binding.autoCompleteTipos.setText(registroModel.tipo, false)
            binding.textData.setText(formatter.format(registroModel.data))
            binding.autoCompleteCategoria.setText(registroModel.categoria, false)
            binding.editValor.setText(registroModel.valor.toString())
            binding.editDescricao.setText(registroModel.descricao)
        }

        binding.buttonCancelar.setOnClickListener {
            setResult(1, i)
            finish()
        }

        binding.buttonSalvar.setOnClickListener {

            val tipo = binding.autoCompleteTipos.text.toString()
            val data = binding.textData.text.toString()
            val categoria = binding.autoCompleteCategoria.text.toString()
            val valor = binding.editValor.text.toString()
            val descricao = binding.editDescricao.text.toString()

            var valorConvertido: Double = 0.0
            if (!valor.isNullOrEmpty()) {
                valorConvertido = valor.toDouble()
            }

            val res =db.atualizarRegistro(
                id = registroModel.id,
                data = data,
                tipo = tipo,
                categoria = categoria,
                valor = valorConvertido,
                descricao = descricao)

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
            val res = db.deletarRegistro(id = registroModel.id)
            if (res > 0) {
                Toast.makeText(applicationContext, "Registro deletado!", Toast.LENGTH_SHORT).show()
                setResult(1, i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Erro ao deletar registro!", Toast.LENGTH_SHORT).show()
                setResult(1, i)
                finish()
            }
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