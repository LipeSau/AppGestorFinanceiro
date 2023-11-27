package br.com.gestorfinanceiro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.gestorfinanceiro.R
import br.com.gestorfinanceiro.adapter.listner.RegistroOnClickListner
import br.com.gestorfinanceiro.adapter.viewholder.RegistroViewHolder
import br.com.gestorfinanceiro.model.RegistroModel
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat

class RegistroListAdapter(
    private val registroList: List<RegistroModel>,
    private val registroOnClickListner: RegistroOnClickListner
): RecyclerView.Adapter<RegistroViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_registro, parent, false)
        return RegistroViewHolder(view)
    }

    override fun getItemCount(): Int {
        return registroList.size
    }

    override fun onBindViewHolder(holder: RegistroViewHolder, position: Int) {
        val format = SimpleDateFormat("dd/MM/yyyy")
        val formatter: NumberFormat = DecimalFormat("#0.00");


        val registro = registroList[position]
        holder.textCategoria.text = registro.categoria
        holder.textData.text = format.format(registro.data)

        if (registro.tipo == "Despesa") {
            holder.textValor.text = "-" + formatter.format(registro.valor) + " R$"
        } else {
            holder.textValor.text = formatter.format(registro.valor) + " R$"
        }

        holder.itemView.setOnClickListener {
            registroOnClickListner.clickListner(registro)
        }
    }
}