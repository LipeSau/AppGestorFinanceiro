package br.com.gestorfinanceiro.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.gestorfinanceiro.R
import br.com.gestorfinanceiro.database.DBHelper
import br.com.gestorfinanceiro.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)

        sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)
        val nomeSalvo = sharedPreferences.getString("nome", "")
        if (!nomeSalvo.isNullOrEmpty()) {
            startActivity(Intent(this, RegistroMainActivity::class.java))
        }

        binding.buttonLogin.setOnClickListener {
            val nome = binding.editNomeUsuario.text.toString()
            val senha = binding.editSenha.text.toString()
            val manterConectado = binding.checkboxManterConectado.isChecked

            if (nome.isNotEmpty() && senha.isNotEmpty()) {
                if (db.login(nome, senha)) {
                    if (manterConectado) {
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("nome", nome)
                        editor.apply()
                    }
                    startActivity(Intent(this, RegistroMainActivity::class.java))
                } else {
                    Toast.makeText(applicationContext,getString(R.string.erro_no_login), Toast.LENGTH_SHORT).show()
                    binding.editNomeUsuario.setText("")
                    binding.editSenha.setText("")
                }
            } else {
                Toast.makeText(applicationContext, getString(R.string.por_favor_preencha_todas_as_informaces), Toast.LENGTH_SHORT).show()
            }

        }

        binding.textCadastrar.setOnClickListener {
            startActivity(Intent(this, CadastroUsuarioActivity::class.java))
        }
    }
}