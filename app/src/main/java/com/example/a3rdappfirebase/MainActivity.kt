package com.example.a3rdappfirebase

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a3rdappfirebase.ui.theme._3rdAppFirebaseTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _3rdAppFirebaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    App(db)
                }
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
    _3rdAppFirebaseTheme {
        Greeting("Android")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(db: FirebaseFirestore){

    //Variáveis

    // Variável que vai armazenar os nomes inseridos
    var nome by remember { mutableStateOf("") }

    // Variável que vai armazenar os telefones inseridos
    var telefone by remember { mutableStateOf("") }

    // Variável que vai instanciar o banco de dados
    val clientes = remember { mutableStateOf("") }

    // Interface do app:

    //Coluna para alinhar todos elementos da tela:
    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ){
        // Linha 1:
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            horizontalArrangement = Arrangement.Center){

            // Texto:
            Text(text = "App FirebaseFirestore 2",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 20.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Caixa:
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {

            // Imagem:
            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.malu),
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                contentDescription = "Maria Luiza Passo Silva")

        }

        // Linha 2:
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {}

        // Linha 3:
        Row(Modifier.fillMaxWidth()){

            // Coluna 1:
            Column(Modifier
                .fillMaxWidth()
                .padding(20.dp))
            {
                TextField(
                    modifier = Modifier.fillMaxWidth(1f),
                    value = nome,
                    onValueChange = {nome = it},
                    label = {Text(text = "Nome:")},
                    placeholder = {Text(text = "Insira o seu nome")}
                )
            }
        }
    }
}