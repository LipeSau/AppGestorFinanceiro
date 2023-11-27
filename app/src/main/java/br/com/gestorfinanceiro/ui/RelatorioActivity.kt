package br.com.gestorfinanceiro.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.gestorfinanceiro.database.DBHelper
import br.com.gestorfinanceiro.databinding.ActivityRelatorioBinding
import br.com.gestorfinanceiro.model.RelatorioModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.text.DecimalFormat


class RelatorioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRelatorioBinding
    private lateinit var dbHelper: DBHelper
    private lateinit var relatorioList: List<RelatorioModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRelatorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)
        relatorioList = dbHelper.getTotaisRelatorio()

        binding.buttonVoltar.setOnClickListener {
            finish()
        }

        val grafico: PieChart = binding.graficoTotalizadores

        val pieEntries = ArrayList<PieEntry>()
        val typeAmountMap: MutableMap<String, Int> = HashMap()

        relatorioList.forEach() {
            typeAmountMap[it.categoria] = it.total.toDouble().toInt()
        }

        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#304567"))
        colors.add(Color.parseColor("#309967"))
        colors.add(Color.parseColor("#476567"))
        colors.add(Color.parseColor("#890567"))
        colors.add(Color.parseColor("#a35567"))
        colors.add(Color.parseColor("#ff5f67"))
        colors.add(Color.parseColor("#3ca567"))

        for (type in typeAmountMap.keys) {
            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
        }

        val pieDataSet = PieDataSet(pieEntries, "")
        pieDataSet.valueTextSize = 12f
        pieDataSet.colors = colors
        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(true)
        grafico.setData(pieData)
        grafico.invalidate()


    }
}