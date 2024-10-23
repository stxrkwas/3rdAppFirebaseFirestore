package com.example.a3rdappfirebase

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a3rdappfirebase.ui.theme._3rdAppFirebaseTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {

    private val db = Firebase.firestore

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
    val clientes = remember { mutableStateOf<List<Cliente>>(emptyList()) }

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
                style = TextStyle(
                    fontSize = 20.sp
                )
            )
        } // Fim da linha 1

        Spacer(modifier = Modifier.height(20.dp))

        // Caixa:
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {

            // Imagem:
            Image(
                painter = painterResource(id = R.drawable.malu),
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                contentDescription = "Maria Luiza Passo Silva")

        } //Fim da caixa

        // Linha 2:
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Maria Luiza Passo Silva \n3º DS A manhã",
                style = TextStyle(
                    // Centraliza o texto dentro do Text:
                    textAlign = TextAlign.Center))
        } //Fim da linha 2

        // Linha 3:
        Row(Modifier.fillMaxWidth()){

            // Coluna 1:
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp))
            {
                // Campo de texto
                TextField(
                    modifier = Modifier.fillMaxWidth(1f),
                    //Valor do campo de texto
                    value = nome,
                    onValueChange = {nome = it},
                    // Rótulo do campo de texto
                    label = {Text(text = "Nome:")},
                    // Texto que aparece quando o campo estiver vazio
                    placeholder = {Text(text = "Insira o seu nome")}
                )
            }
        } // Fim da linha 3

        // Linha 4:
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)){

            // Coluna 1:
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)){

                // Campo de texto
                TextField(
                    modifier = Modifier.fillMaxWidth(1f),
                    //Valor do campo de texto
                    value = telefone,
                    onValueChange = {telefone = it},
                    // Rótulo do campo de texto
                    label = {Text(text = "Telefone:")},
                    // Texto que aparece quando o campo estiver vazio
                    placeholder = {Text(text = "Insira o seu telefone")}
                )
            }// Fim da coluna 1

        }// Fim da linha 4

        // Linha 5:
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center
        ){

            // Botão para cadastrar o cliente:
            Button(
                // Função que será executada ao clicar o botão:
                onClick = {

                    // Variável que cria um objeto cliente, que armazena os dados inseridos:
                    val cliente = Cliente(nome, telefone)

                    // Inserindo os dados no banco de dados:
                    db.collection("Clientes").add(cliente)

                        // Mensagem de sucesso:
                        .addOnSuccessListener { documentReference ->

                            // Limpando os campos de texto:
                            Log.d(
                                TAG,
                                "DocumentSnapshot added with ID: ${documentReference.id}"
                            )

                            // Atualizando a lista de clientes após a inserção:
                            LaunchedEffect(Unit) {
                                db.collection("Clientes").get()

                                    // Mensagem de sucesso:
                                    .addOnSuccessListener { result->

                                        // Limpa a lista de clientes antes de adicionar novos itens:
                                        clientes = result.map { document ->
                                            Cliente(
                                                nome = document.getString("nome") ?: "--",
                                                telefone = document.getString("telefone") ?: "--"
                                            )
                                        }
                                    }
                            }

                        // Limpando os campos de texto:
                        nome = ""
                        telefone = ""
                        }

                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e) }

                }){

                // Texto do botão:
                Text(text = "Cadastrar")
            }
        }// Fim da linha 5

        // Listagem dos clientes cadastrados:

            LazyColumn(modifier = Modifier.fillMaxWidth()) {

                //
                item{

                    // Linha:
                    Row(Modifier.fillMaxWidth()){

                        //Coluna 1:
                        Column(modifier = Modifier.weight(0.5f)) {

                            //Texto:
                            Text(text = "Nome")
                        }

                        //Coluna 2:
                        Column(modifier = Modifier.weight(0.5f)) {

                            //Texto:
                            Text(text = "Telefone")
                        }
                    }
                }

                items(clientes){ cliente -> //Usa a lista de clientes atualizada

                    // Linha:
                    Row(modifier = Modifier.fillMaxWidth()){

                        //Coluna 1:
                        Column(modifier = Modifier.weight(0.5f)) {

                            //Texto que acessa a propriedade nome do cliente:
                            Text(text = cliente.nome)
                        }

                        // Coluna 2:
                        Column(modifier = Modifier.weight(0.5f)) {

                            //Texto que acessa a propriedade telefone do cliente:
                            Text(text = cliente.telefone)
                        }
                    }
                }
            }
        }
    } // Fim da coluna
}