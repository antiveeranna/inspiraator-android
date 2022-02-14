package com.masendav.inspiraator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masendav.inspiraator.ui.theme.ComposerTheme
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.InputStream

@Serializable
data class Inspiration (
    var locations: List<String>,
    val emotions: List<String>,
    val objects: List<String>,
    val relations: List<String>,
    val characters: List<String>,
    val attributes: List<String>
)

class MainActivity : ComponentActivity() {

    private val inspirationViewModel by viewModels<InspirationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // is this even the right place to read JSON? - maybe I will figure out something more sensible later on

        try {
            val inputStream: InputStream = assets.open("inspiration.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            val read = inputStream.read(buffer)
            val string = String(buffer)
            val parsed = Json{ignoreUnknownKeys = true}.decodeFromString<Inspiration>(string)

            inspirationViewModel.loadData(parsed)

        } catch (e: IOException) {
            e.printStackTrace()
        }

        setContent {
            ComposerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App(inspirationViewModel)
                }
            }
        }
    }
}


@Composable
fun Block(title: String, content: String) {
    Text(
        title,
        color = MaterialTheme.colors.secondaryVariant,
        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
    )
    Text(content, style = MaterialTheme.typography.h5, modifier = Modifier.padding(start = 16.dp))
    Spacer(modifier = Modifier.height(16.dp))
    Divider()
}

@Composable
fun App(inspirationViewModel: InspirationViewModel) {

    val location: String by inspirationViewModel.location.observeAsState("")
    val emotion: String by inspirationViewModel.emotion.observeAsState("")
    val thing: String by inspirationViewModel.thing.observeAsState("")
    val relation: String by inspirationViewModel.relation.observeAsState("")
    val random2: String by inspirationViewModel.random2.observeAsState("")


    Column(modifier=Modifier.verticalScroll(rememberScrollState())) {
        TopAppBar(title = { Text("Inspiraator") })
        Block("Asukoht", location)
        Block("Emotsioon", emotion)
        Block("Enam-vähem loogiline suhe", relation)
        Block("Ese", thing)
        Block("2 täiesti juhuslikku tegelast ja nende omadust", random2)

        Spacer(modifier = Modifier.weight(1f))
        Row(horizontalArrangement = Arrangement.Center, modifier=Modifier.fillMaxWidth().height(64.dp)) {
            Button(
                onClick = { inspirationViewModel.onNewChoice() },
                shape = MaterialTheme.shapes.medium

            )
            {
                Text("Uus valik", modifier=Modifier.padding(8.dp))
            }
        }



    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    var dummy = InspirationViewModel()
    ComposerTheme {
        App(dummy)
    }
}

fun newChoice() {
    Log.d("INSP", "Getting new choices");
}