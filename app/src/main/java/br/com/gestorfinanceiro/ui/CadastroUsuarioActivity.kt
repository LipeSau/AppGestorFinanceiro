package br.com.gestorfinanceiro.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.gestorfinanceiro.R
import br.com.gestorfinanceiro.database.DBHelper
import br.com.gestorfinanceiro.databinding.ActivityCadastroUsuarioBinding

class CadastroUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)

        binding.buttonCadastrar.setOnClickListener {
            val nome = binding.editNomeUsuario.text.toString()
            val senha = binding.editSenha.text.toString()
            val confimaSenha = binding.editConfirmarSenha.text.toString()

            if (nome.isNotEmpty() && senha.isNotEmpty() && confimaSenha.isNotEmpty()) {
                if (senha == confimaSenha) {
                    val res = db.inserirUsuario(nome, senha)
                    if (res > 0) {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.usuario_cadastrado), Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.erro_ao_cadastrar_usuario), Toast.LENGTH_SHORT
                        ).show()
                        binding.editNomeUsuario.setText("")
                        binding.editSenha.setText("")
                        binding.editConfirmarSenha.setText("")
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.as_senhas_nao_sao_iguais), Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.por_favor_preencha_todas_as_informaces), Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
}