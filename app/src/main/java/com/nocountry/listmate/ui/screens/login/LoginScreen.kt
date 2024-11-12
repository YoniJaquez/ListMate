package com.nocountry.listmate.ui.screens.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.nocountry.listmate.singleton.GlobalUser
import com.nocountry.listmate.ui.components.Input
import com.nocountry.listmate.ui.components.TopBar
import com.nocountry.listmate.ui.navigation.Destinations
import com.nocountry.listmate.ui.screens.sharedviewmodels.SharedViewModel

//@Composable
//@Preview
//fun LogInPreview() {
//    val navController = rememberNavController()
//    val sharedViewModel = SharedViewModel()
//    LoginScreen(navController, sharedViewModel)
//}

@Composable
fun LoginScreen(navHostController: NavHostController, sharedViewModel: SharedViewModel) {
    var email by remember { mutableStateOf("belletommasi@gmail.com") }
    var password by remember { mutableStateOf("belle111") }
    var passwordVisible by rememberSaveable { mutableStateOf(true) }
    var displayAlert by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xffF0F2F5)
            )
    ) {
        TopBar(titulo = "Log In")
        Spacer(modifier = Modifier.height(20.dp))
       Column(
           modifier = Modifier
               .fillMaxHeight(1f)
               .padding(20.dp),
           verticalArrangement = Arrangement.Center,


       ) {
           Input(label = "Email", value = email){ email = it }

            Spacer(modifier = Modifier.height(16.dp))

           Input(label = "Password", value = password, isPassword = true){ password = it }

           Spacer(modifier = Modifier.height(25.dp))

            HyperlinkText(text = "¿Forgot password?", modifier = Modifier.align(alignment = Alignment.Start)) {  }

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .fillMaxWidth(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff31628D)),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    if(email.isNotBlank() && password.isNotBlank()){
                        FirebaseAuth.getInstance()
                            .signInWithEmailAndPassword(email , password)
                            .addOnCompleteListener{
                                if(it.isSuccessful){
                                    //recuperar los datos del usuario
                                    val db = Firebase.firestore
                                    db.collection("users")
                                        .whereEqualTo("uid", FirebaseAuth.getInstance().currentUser!!.uid)
                                        .get()
                                        .addOnSuccessListener { result ->
                                            if (result.isEmpty) {
                                                Log.d(TAG, "No user found with email: $email")
                                            } else {
                                                for (document in result) {
                                                    GlobalUser.initialize(document)
                                                    navHostController.navigate(Destinations.HOME)
                                                }
                                            }
                                        }
                                        .addOnFailureListener { exception ->
                                            Log.w(TAG, "Error getting documents.", exception)
                                        }

                                    //guardar los datos delusuario en Globaluser

                                    //GlobalUser.initialize(user) --> pista


                                }
                                else {
                                    displayAlert = true
                                }
                            }
                    }
                }
            ) {
                Text(
                    text = "Log in",
                    fontSize = 23.sp
                )
            }
            Spacer(modifier = Modifier.height(25.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "¿New user?")
                Spacer(modifier = Modifier.width(10.dp))
                HyperlinkText(
                    text = "Sign Up",
                    color = Color.Black
                ) {
                    navHostController.navigate(Destinations.SIGNUP)
                }
            }
        }
    }


    if (displayAlert) {
        AlertDialog(
            title = {
                Text(text = "No se pudo registrar")
            },
            text = {
                Text(text = "No se pudo registrar el usuario. Intente en unos minutos.")
            },
            onDismissRequest = {
            },
            confirmButton = {
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        displayAlert = false
                    }
                ) {
                    Text("Entendido")
                }
            }
        )
    }
}

@Composable
fun HyperlinkText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    onClick: (() -> Unit)? = null
) {
    val annotatedString = with(AnnotatedString.Builder()) {
        pushStringAnnotation(
            tag = "LINK",
            annotation = text
        )
        append(text)
        pop()
        toAnnotatedString()
    }

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = "LINK",
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                onClick?.invoke()
            }
        },
        style = TextStyle(color = color, textDecoration = TextDecoration.None)
    )
}