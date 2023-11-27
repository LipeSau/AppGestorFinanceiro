package br.com.gestorfinanceiro.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.gestorfinanceiro.R

class CategoriaViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val textCategoria: TextView = view.findViewById(R.id.text_categoria_row)
}