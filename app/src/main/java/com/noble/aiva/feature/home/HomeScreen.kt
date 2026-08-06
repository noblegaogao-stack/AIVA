package com.noble.aiva.feature.home

import androidx.compose.foundation.layout.Column

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import com.noble.aiva.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource


@Composable
fun HomeScreen(count: Int, onIncrease: () -> Unit){

    Column {
        Text(
            text = "当前数量：$count"
        )

        Button(
            onClick = onIncrease
        ) {
            Text("点击增加 + 1")
        }
    }
}
@Composable
fun HomeScreen2(){
    // day 5
    var showTitle by remember {
        mutableStateOf(true)
    }
    if (showTitle){
        Text("Welcome to AIVA")
    }
    Button(onClick = { showTitle = !showTitle }) {
        Text("Toggle")
    }
}
