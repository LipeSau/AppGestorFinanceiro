package br.com.gestorfinanceiro.adapter.listner

import br.com.gestorfinanceiro.model.RegistroModel

class RegistroOnClickListner(val clickListner: (registro: RegistroModel) -> Unit) {
    fun onClick(registro: RegistroModel) = clickListner
}