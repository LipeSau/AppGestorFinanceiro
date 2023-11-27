package br.com.gestorfinanceiro.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.gestorfinanceiro.R

class RegistroViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val textData: TextView = view.findViewById(R.id.text_data_row)
    val textCategoria: TextView = view.findViewById(R.id.text_categoria_registro_row)
    val textValor: TextView = view.findViewById(R.id.text_valor_row)
}