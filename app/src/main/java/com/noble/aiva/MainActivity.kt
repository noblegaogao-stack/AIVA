package com.noble.aiva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.noble.aiva.feature.home.HomeViewModel
import com.noble.aiva.feature.recording.RecordingViewModel
import com.noble.aiva.feature.result.ResultViewModel
import com.noble.aiva.navigation.AppNavGraph
import com.noble.aiva.ui.theme.AIVATheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIVATheme {
                AppNavGraph()
//                HomeScreen5(viewModel)
//                HomeScreen4()
//                Counter()
//                Welcome()

//                HomeScreen3()

//                HomeScreen2()
//                HomeScreen(count = viewModel.count.collectAsState().value, onIncrease = viewModel::increase)
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AIVATheme {
        Greeting("Android")
    }
}