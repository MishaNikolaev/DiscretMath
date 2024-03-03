package com.example.discretmath

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.discretmath.ui.theme.DiscretMathTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiscretMathTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        App1()
                        App2()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App1() {
    var setA by remember { mutableStateOf(TextFieldValue()) }
    var setB by remember { mutableStateOf(TextFieldValue()) }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Лабораторная работа №1")

        OutlinedTextField(
            value = setA,
            onValueChange = { setA = it },
            label = { Text("Множество A") }
        )
        OutlinedTextField(
            value = setB,
            onValueChange = { setB = it },
            label = { Text("Множество B") }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            val processedSetA = processInput(setA.text)
            val processedSetB = processInput(setB.text)
            if (processedSetA != null && processedSetB != null) {
                result = if (isSubset(processedSetA, processedSetB)) {
                    "A является собственным подмножеством B"
                } else {
                    "A не является собственным подмножеством B"
                }
            } else {
                result = "Требуются буквы латинского алфавита"
            }
        }) {
            Text("Проверить A на подмножество B")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            val processedSetA = processInput(setA.text)
            val processedSetB = processInput(setB.text)
            if (processedSetA != null && processedSetB != null) {
                result =
                    "Объединение A U B: ${union(processedSetA, processedSetB).joinToString(", ")}"
            } else {
                result = "Требуются буквы латинского алфавита"
            }
        }) {
            Text("Вычислить объединение A U B")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            val processedSetA = processInput(setA.text)
            val processedSetB = processInput(setB.text)
            if (processedSetA != null && processedSetB != null) {
                result = "Пересечение A n B: ${
                    intersection(
                        processedSetA,
                        processedSetB
                    ).joinToString(", ")
                }"
            } else {
                result = "Требуются буквы латинского алфавита"
            }
        }) {
            Text("Вычислить пересечение A n B")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            val processedSetA = processInput(setA.text)
            val processedSetB = processInput(setB.text)
            if (processedSetA != null && processedSetB != null) {
                result = "Разность A \\ B: ${
                    difference(
                        processedSetA,
                        processedSetB
                    ).joinToString(", ")
                }"
            } else {
                result = "Требуются буквы латинского алфавита"
            }
        }) {
            Text("Вычислить разность A \\ B")
        }
        Text(text = result)
    }
}

fun processInput(input: String): Set<Char>? {
    if (input.any { it !in 'A'..'Z' && it !in 'a'..'z' && it != ' ' }) return null
    return input.uppercase(Locale.ROOT).filter { it in 'A'..'Z' }.toSortedSet()
}

fun isSubset(setA: Set<Char>, setB: Set<Char>): Boolean {
    if (setA.size >= setB.size) {
        return false
    }
    for (element in setA) {
        if (element !in setB) {
            return false
        }
    }
    return true
    /*
fun isSubset(setA: Set<Char>, setB: Set<Char>): Boolean {
    return setA.size < setB.size && setB.containsAll(setA)
}
 */
}

fun union(setA: Set<Char>, setB: Set<Char>): Set<Char> = (setA + setB).sorted().toSet()

fun intersection(setA: Set<Char>, setB: Set<Char>): Set<Char> {
    val resultSet = mutableSetOf<Char>()
    for (element in setA) {
        if (element in setB) {
            resultSet.add(element)
        }
    }
    return resultSet.sorted().toSet()
    /*fun intersection(setA: Set<Char>, setB: Set<Char>): Set<Char> {
return setA.intersect(setB).sorted().toSet()
} можно было так сделать*/
}

fun difference(setA: Set<Char>, setB: Set<Char>): Set<Char> {
    val resultSet = mutableSetOf<Char>()
    for (element in setA) {
        if (element !in setB) {
            resultSet.add(element)
        }
    }
    return resultSet.sorted().toSet()
    /*
fun difference(setA: Set<Char>, setB: Set<Char>): Set<Char> {
    return setA.minus(setB).sorted().toSet()
}*/
}
@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App2() {
    var setASize by remember { mutableStateOf("") }
    var setAElements by remember { mutableStateOf("") }
    var numberOfPairs by remember { mutableStateOf("") }
    var pairsInput by remember { mutableStateOf("") }
    var matrixResult by remember { mutableStateOf<List<String>>(listOf()) }
    var propertiesResult by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Лабораторная работа №2")

        OutlinedTextField(
            value = setASize,
            onValueChange = { setASize = it },
            label = { Text("Количество элементов множества А") }
        )

        OutlinedTextField(
            value = setAElements,
            onValueChange = { setAElements = it },
            label = { Text("Элементы множества A через пробел") }
        )

        OutlinedTextField(
            value = numberOfPairs,
            onValueChange = { numberOfPairs = it },
            label = { Text("Количество упорядоченных пар") }
        )

        OutlinedTextField(
            value = pairsInput,
            onValueChange = { pairsInput = it },
            label = { Text("Упорядоченные пары (a,b) через пробел") }
        )

        Button(onClick = {
            val setSize = setASize.toIntOrNull() ?: 0
            val setAList = setAElements.split(" ").filterNot { it.isBlank() }
            val pairsList = pairsInput.split(" ").chunked(2)
                .mapNotNull { if (it.size == 2) it[0] to it[1] else null }

            matrixResult = calculateMatrix(setSize, setAList, pairsList)
            propertiesResult = calculateProperties(matrixResult)
        }) {
            Text("Вывести матрицу")
        }

        if (matrixResult.isNotEmpty()) {
            Text("Матрица бинарного отношения R:")
            matrixResult.forEach { Text(it) }
            Text(propertiesResult)
        }
    }
}

fun calculateMatrix(
    setASize: Int,
    setAElements: List<String>,
    pairs: List<Pair<String, String>>
): List<String> {
    val matrix = Array(setASize) { Array(setASize) { 0 } }
    for (pair in pairs) {
        val rowIndex = setAElements.indexOf(pair.first)
        val colIndex = setAElements.indexOf(pair.second)
        if (rowIndex != -1 && colIndex != -1) {
            matrix[rowIndex][colIndex] = 1
        }
    }

    return matrix.map { row -> row.joinToString(" ") }
}

fun calculateProperties(matrixResult: List<String>): String {
    val matrix = matrixResult.map { it.split(" ").map(String::toInt).toTypedArray() }.toTypedArray()

    val isReflexive = isReflexive(matrix)
    val isSymmetric = isSymmetric(matrix)
    val isAntisymmetric = isAntisymmetric(matrix)
    val isTransitive = isTransitive(matrix)
    return "Рефлексивность: ${yesNo(isReflexive)}, Симметричность: ${yesNo(isSymmetric)}, Антисимметричность: ${yesNo(isAntisymmetric)}, Транзитивность: ${yesNo(isTransitive)}"
}

fun yesNo(value: Boolean): String = if (value) "да" else "нет"

fun isReflexive(matrix: Array<Array<Int>>): Boolean {
    for (i in matrix.indices) {
        if (matrix[i][i] != 1) return false
    }
    return true
}

fun isSymmetric(matrix: Array<Array<Int>>): Boolean {
    for (i in matrix.indices) {
        for (j in matrix.indices) {
            if (matrix[i][j] != matrix[j][i]) return false
        }
    }
    return true
}

fun isAntisymmetric(matrix: Array<Array<Int>>): Boolean {
    for (i in matrix.indices) {
        for (j in matrix.indices) {
            if (i != j && matrix[i][j] == 1 && matrix[j][i] == 1) return false
        }
    }
    return true
}

fun isTransitive(matrix: Array<Array<Int>>): Boolean {
    for (i in matrix.indices) {
        for (j in matrix.indices) {
            if (matrix[i][j] == 1) {
                for (k in matrix.indices) {
                    if (matrix[j][k] == 1 && matrix[i][k] != 1) return false
                }
            }
        }
    }
    return true
}