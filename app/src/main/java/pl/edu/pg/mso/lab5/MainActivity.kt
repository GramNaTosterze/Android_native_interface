package pl.edu.pg.mso.lab5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import pl.edu.pg.mso.lab5.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var array: IntArray = IntArray(ARRAY_SIZE) {0}

        findViewById<Button>(R.id.generate).setOnClickListener {
            array = IntArray(ARRAY_SIZE) { (0..10000).random() }
            printArray(array)
        }

        findViewById<Button>(R.id.sort).setOnClickListener {
            val ktArray = array.copyOf()
            val cppArray = array.copyOf()
            var sortTimes = ""

            sortTimes += "kt:  ${measureNanoTime { ktArray.sort()}} ns\n"
            val cppMeasureJNI = measureNanoTime { cppSort(array) }
            sortTimes += "cpp: $cppMeasureJNI ns\n"
            val cppMeasureNoJNI = cppMeasure(cppArray)
            sortTimes += "cpp[no JNI]: $cppMeasureNoJNI ns\n"
            sortTimes += "~JNI: ${cppMeasureJNI - cppMeasureNoJNI} ns"

            findViewById<TextView>(R.id.times).text = sortTimes

            printArray(array)
        }
    }

    private fun printArray(array: IntArray) {
        val textViewArray = findViewById<TextView>(R.id.array);
        var elementList: String = ""
        for (element in array)
            elementList += "$element "
        textViewArray.text = elementList
    }

    /**
     * A native method that is implemented by the 'lab5' native library,
     * which is packaged with this application.
     */
    private external fun cppSort(array: IntArray): Void
    private external fun cppMeasure(array: IntArray): Long

    companion object {
        // Used to load the 'lab5' library on application startup.
        init {
            System.loadLibrary("lab5")
        }

        private const val ARRAY_SIZE = 100
    }
}