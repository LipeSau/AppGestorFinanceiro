package br.com.gestorfinanceiro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.gestorfinanceiro.R
import br.com.gestorfinanceiro.adapter.listner.CategoriaOnClickListner
import br.com.gestorfinanceiro.adapter.viewholder.CategoriaViewHolder
import br.com.gestorfinanceiro.model.CategoriaModel

class CategoriaListAdapter(
    private val categoriaList: List<CategoriaModel>,
    private val categoriaOnClickListner: CategoriaOnClickListner): RecyclerView.Adapter<CategoriaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_categoria, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoriaList.size
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = categoriaList[position]
        holder.textCategoria.text = categoria.categoria
        holder.itemView.setOnClickListener {
            categoriaOnClickListner.clickListner(categoria)
        }
    }
}