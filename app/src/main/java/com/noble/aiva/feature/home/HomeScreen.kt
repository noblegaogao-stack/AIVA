package com.noble.aiva.feature.home

import androidx.compose.foundation.layout.Column

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import com.noble.aiva.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
@Composable
fun HomeScreen3(){
    Column() {
        Counter()
        Welcome()
    }
}
@Composable
fun Counter(){
    var count by remember {
        mutableStateOf(0)
    }
    Button(onClick = { count++ }) {
        Text("Count: $count")
        println("Counter Recompose")
    }
}
@Composable
fun Welcome(){
    println("Welcome Recompose")
    Text("Welcome to AIVA")
}

@Composable
fun HomeScreen4(){
    Column() {
        PrintText()
    }
}
@Composable
fun PrintText(){
//    var text by remember {
//        mutableStateOf("")
//    }
    //屏幕旋转，可以保存状态
    var text by rememberSaveable() {
        mutableStateOf("")
    }
    OutlinedTextField(value = text, onValueChange = { text = it }, label = { Text("请输入内容") })
}
//=====================================================================================
//@Composable
//fun HomeScreen5(viewModel: HomeViewModel){
//    val uiState by viewModel.uiState.collectAsState()
//
//    Column {
////        这个地方获取默认的值，welcomeText: String = "Welcome to AIVA"
//        Text(text = uiState.welcomeText)
////        点击button ，触发changeWelcomeText()，viewModel重新赋值调用的是 内部的 可变状态流容器 MutableStateFlow
//        Button(onClick = { viewModel.changeWelcomeText() }) {
//            Text("Change Welcome Text")
//        }
//    }
//}


//========================================================================
@Composable
fun HomeScreen6(viewModel: HomeViewModel){


    val uiState by viewModel.uiState.collectAsState()
    Button(onClick = {
        viewModel.onEvent(HomeEvent.RecordClicked)
    }) {
        Text(text = uiState.welcomeText)
    }
}














