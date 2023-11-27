package br.com.gestorfinanceiro.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.gestorfinanceiro.model.CategoriaModel
import br.com.gestorfinanceiro.model.RegistroModel
import br.com.gestorfinanceiro.model.RelatorioModel
import br.com.gestorfinanceiro.model.UsuarioModel
import java.text.SimpleDateFormat
import java.util.Date

class DBHelper(context: Context) : SQLiteOpenHelper(context, "database.db", null, 1) {

    val format = SimpleDateFormat("dd/MM/yyyy")

    val sql = arrayOf(
        "CREATE TABLE usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT UNIQUE, senha TEXT)",
        "CREATE TABLE categorias (id INTEGER PRIMARY KEY AUTOINCREMENT, categoria TEXT UNIQUE)",
        "INSERT INTO categorias (categoria) VALUES ('Salário')",
        "INSERT INTO categorias (categoria) VALUES ('Alimentação')",
        "INSERT INTO categorias (categoria) VALUES ('Educação')",
        "INSERT INTO categorias (categoria) VALUES ('Lazer')",
        "INSERT INTO categorias (categoria) VALUES ('Saúde')",
        "INSERT INTO categorias (categoria) VALUES ('Transporte')",
        "CREATE TABLE registros (id INTEGER PRIMARY KEY AUTOINCREMENT, data DATE, tipo TEXT, categoria TEXT, valor DECIMAL(10, 2), descricao TEXT)"
    )

    override fun onCreate(db: SQLiteDatabase) {
        sql.forEach {
            db.execSQL(it)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


    /*----------------------------------------------------------------------------------------------------------------------------------------------------------------
                CRUD USUARIOS
     ----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    fun inserirUsuario(nome: String, senha: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nome", nome)
        contentValues.put("senha", senha)
        val res = db.insert("usuarios", null, contentValues)
        db.close()
        return res
    }

    fun atualizarUsuario(id: Int, nome: String, senha: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nome", nome)
        contentValues.put("senha", senha)
        val res = db.update("usuarios", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun deletarUsuario(id: Int): Int {
        val db = this.writableDatabase
        val res = db.delete("usuarios", "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun getUsuario(nome: String, senha: String): UsuarioModel {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM usuarios WHERE nome=? AND senha=?", arrayOf(nome, senha))
        var usuarioModel = UsuarioModel()
        if (c.count == 1) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val nomeIndex = c.getColumnIndex("nome")
            val senhaIndex = c.getColumnIndex("senha")

            usuarioModel = UsuarioModel(
                id = c.getInt(idIndex),
                nome = c.getString(nomeIndex),
                senha = c.getString(senhaIndex)
            )
        }
        db.close()
        return usuarioModel
    }

    fun login(nome: String, senha: String): Boolean {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM usuarios WHERE nome=? AND senha=?", arrayOf(nome, senha))
        var usuarioModel = UsuarioModel()
        if (c.count == 1) {
            db.close()
            return true
        } else {
            db.close()
            return false
        }
    }

    /*----------------------------------------------------------------------------------------------------------------------------------------------------------------
                CRUD CATEGORIAS
     ----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    fun inserirCategoria(categoria: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("categoria", categoria)
        val res = db.insert("categorias", null, contentValues)
        db.close()
        return res
    }

    fun atualizarCategoria(id: Int, categoria: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("categoria", categoria)
        val res = db.update("categorias", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun deletarCategoria(id: Int): Int {
        val db = this.writableDatabase
        val res = db.delete("categorias", "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun getCategoria(id: Int): CategoriaModel {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM categorias WHERE id=?", arrayOf(id.toString()))
        var categoriaModel = CategoriaModel()
        if (c.count == 1) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val categoriaIndex = c.getColumnIndex("categoria")

            categoriaModel = CategoriaModel(
                id = c.getInt(idIndex),
                categoria = c.getString(categoriaIndex)
            )
        }
        db.close()
        return categoriaModel
    }

    fun getAllCategoria(): ArrayList<CategoriaModel> {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM categorias ORDER BY categoria ASC", null)
        var listCategoriaModel = ArrayList<CategoriaModel>()
        if (c.count > 0) {

            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val categoriaIndex = c.getColumnIndex("categoria")

            do {
                val categoriaModel = CategoriaModel(
                    id = c.getInt(idIndex),
                    categoria = c.getString(categoriaIndex)
                )
                listCategoriaModel.add(categoriaModel)
            } while (c.moveToNext())
        }
        db.close()
        return listCategoriaModel
    }

    fun getAllCategoriaLista(): ArrayList<String> {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT categoria FROM categorias ORDER BY categoria ASC", null)
        var listCategoria = ArrayList<String>()
        if (c.count > 0) {
            c.moveToFirst()
            val categoriaIndex = c.getColumnIndex("categoria")

            do {
                listCategoria.add(c.getString(categoriaIndex))
            } while (c.moveToNext())
        }
        db.close()
        return listCategoria
    }

    /*----------------------------------------------------------------------------------------------------------------------------------------------------------------
                CRUD REGISTROS
     ----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    fun inserirRegistro(data: String, tipo: String, categoria: String, valor: Double, descricao: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("data", data)
        contentValues.put("tipo", tipo)
        contentValues.put("categoria", categoria)
        contentValues.put("valor", valor)
        contentValues.put("descricao", descricao)
        val res = db.insert("registros", null, contentValues)
        db.close()
        return res
    }

    fun atualizarRegistro(
        id: Int,
        data: String,
        tipo: String,
        categoria: String,
        valor: Double,
        descricao: String
    ): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("data", data)
        contentValues.put("tipo", tipo)
        contentValues.put("categoria", categoria)
        contentValues.put("valor", valor)
        contentValues.put("descricao", descricao)
        val res = db.update("registros", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun deletarRegistro(id: Int): Int {
        val db = this.writableDatabase
        val res = db.delete("registros", "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun getRegistro(id: Int): RegistroModel {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM registros WHERE id=?", arrayOf(id.toString()))
        var registroModel = RegistroModel()
        if (c.count == 1) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val dataIndex = c.getColumnIndex("data")
            val categoriaIndex = c.getColumnIndex("categoria")
            val tipoIndex = c.getColumnIndex("tipo")
            val valorIndex = c.getColumnIndex("valor")
            val descricaoIndex = c.getColumnIndex("descricao")


            registroModel = RegistroModel(
                id = c.getInt(idIndex),
                data = format.parse(c.getString(dataIndex)),
                tipo = c.getString(tipoIndex),
                categoria = c.getString(categoriaIndex),
                valor = c.getDouble(valorIndex),
                descricao = c.getString(descricaoIndex)
            )
        }
        db.close()
        return registroModel
    }

    fun getAllRegistros(): ArrayList<RegistroModel> {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM registros ORDER BY data DESC", null)
        var listRegistroModel = ArrayList<RegistroModel>()

        if (c.count > 0) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val dataIndex = c.getColumnIndex("data")
            val tipoIndex = c.getColumnIndex("tipo")
            val categoriaIndex = c.getColumnIndex("categoria")
            val valorIndex = c.getColumnIndex("valor")
            val descricaoIndex = c.getColumnIndex("descricao")

            do {
                val registroModel = RegistroModel(
                    id = c.getInt(idIndex),
                    data = format.parse(c.getString(dataIndex)),
                    categoria = c.getString(categoriaIndex),
                    tipo = c.getString(tipoIndex),
                    valor = c.getDouble(valorIndex),
                    descricao = c.getString(descricaoIndex)
                )
                listRegistroModel.add(registroModel)
            } while (c.moveToNext())
        }
        db.close()
        return listRegistroModel
    }
    /*----------------------------------------------------------------------------------------------------------------------------------------------------------------
                Totalizadores
     ----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    fun getTotal(tipoTotal: String): Double {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT SUM(valor) as 'valor' FROM registros WHERE tipo=? GROUP BY tipo", arrayOf(tipoTotal))
        var valorTotal: Double = 0.0
        if (c.count == 1) {
            c.moveToFirst()
            val valorIndex = c.getColumnIndex("valor")
            valorTotal = c.getDouble(valorIndex)
        }
        db.close()
        return valorTotal
    }

    fun getTotaisRelatorio(): ArrayList<RelatorioModel> {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT SUM(valor) as total, categoria FROM registros WHERE tipo = 'Despesa' GROUP BY categoria ORDER BY categoria ASC", null)
        var listRelatorioModel = ArrayList<RelatorioModel>()
        if (c.count > 0) {

            c.moveToFirst()
            val categoriaIndex = c.getColumnIndex("categoria")
            val totalIndex = c.getColumnIndex("total")

            do {
                val relatorioModel = RelatorioModel(
                    categoria = c.getString(categoriaIndex),
                    total = c.getString(totalIndex)
                )
                listRelatorioModel.add(relatorioModel)
            } while (c.moveToNext())
        }
        db.close()
        return listRelatorioModel
    }
}