package com.noble.aiva.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


@Composable
fun HomeScreen(){
//    Text(text = "Welcome to AIVA")
//    Text(
//        text = stringResource(R.string.app_name) ,
//        style = MaterialTheme.typography.titleLarge
//    )

    var count by remember {
        mutableStateOf(0)
    }

    Column {
        Text(
            text = "当前数量：$count"
        )

        Button(
            onClick = {
                count = count + 1
            }
        ) {
            Text("点击+1")
        }
    }
}