package br.edu.ifpb.unipass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.edu.ifpb.unipass.navigation.AppNavHost
//import br.edu.ifpb.unipass_student_app.ui.theme.UnipassstudentappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            UnipassstudentappTheme {
                AppNavHost()
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    UnipassstudentappTheme {
//        Greeting("Android")
//    }
//}