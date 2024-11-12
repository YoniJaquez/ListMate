package com.nocountry.listmate.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.nocountry.listmate.R
import com.nocountry.listmate.data.UsuarioManager
import com.nocountry.listmate.singleton.GlobalUser
import com.nocountry.listmate.ui.components.Input
import com.nocountry.listmate.ui.navigation.Destinations


@Composable
@Preview
fun ProfileScreenPreview(){

    ProfileScreen(rememberNavController())

}
@Composable
fun  ProfileScreen(navHostController: NavHostController){
   // val db = FirebaseFirestore.getInstance()
    var name by remember { mutableStateOf(GlobalUser.name) }
    var lastname by remember { mutableStateOf(GlobalUser.lastName) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffF5fafc)),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopProfile(navHostController)
        Spacer(modifier = Modifier.height(25.dp))

        Box(

            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Black, CircleShape)
        ) {
            Image(
                painter = painterResource(R.drawable.profile_icon),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(15.dp)
        ) {

            Text(text = GlobalUser.name)
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = GlobalUser.lastName)

            Input(label = "Name", value = name) {
                name = it
            }
            Input(label = "Lastname", value = lastname) {
                lastname = it
            }

        }

        Spacer(
            modifier = Modifier.fillMaxHeight(.2f)
        )

        Button(
            onClick = {
                      if (name.isNotEmpty() && lastname.isNotEmpty()){
                          GlobalUser.name = name
                          GlobalUser.lastName = lastname
                          val usuario = GlobalUser.getUserObject()

                          UsuarioManager().actualizarNombreApellidoUsuario(GlobalUser.userKey, usuario)
                      }
            },

            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .fillMaxWidth(.9f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xffcce5ff)),
            shape = RoundedCornerShape(10.dp)

        ) {
            Text(
                text = "Save", color = Color.Black
            )

        }


    }
}
@Composable
fun TopProfile(
    navHostController: NavHostController
){
    var expanded by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(R.drawable.arrow_back),
            contentDescription = null,
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.CenterVertically)
                .clickable { navHostController.navigate(Destinations.HOME) {
                    popUpTo(Destinations.LOGIN) { inclusive = false }
                }   }

        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Profile",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier.align(Alignment.CenterVertically)

        )
        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.height(50.dp),
            onClick = { expanded = true},
           colors = ButtonDefaults.buttonColors(containerColor = Color(0x00cce5ff)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ellipsis_vertical),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterVertically)

            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {expanded = false }
            ) {
                DropdownMenuItem(text = { Text(text = "Log out") }, onClick = {
                    expanded = false
                    GlobalUser.name = ""
                    GlobalUser.lastName = ""
                    GlobalUser.email = ""
                    GlobalUser.uid = ""
                    FirebaseAuth.getInstance().signOut()
                    navHostController.popBackStack()
                })

            }
        }
    }

}
