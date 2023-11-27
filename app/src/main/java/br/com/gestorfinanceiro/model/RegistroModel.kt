package br.com.gestorfinanceiro.model

import java.time.LocalDate
import java.util.Date

data class RegistroModel(
    val id: Int = 0,
    val data: Date = Date(),
    val tipo: String = "",
    val categoria: String = "",
    val valor: Double = 0.0,
    val descricao: String = ""
)
