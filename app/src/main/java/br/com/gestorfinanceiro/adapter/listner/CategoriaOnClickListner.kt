package br.com.gestorfinanceiro.adapter.listner

import br.com.gestorfinanceiro.model.CategoriaModel

class CategoriaOnClickListner(val clickListner: (categoria: CategoriaModel) -> Unit) {
    fun onClick(categoria: CategoriaModel) = clickListner
}