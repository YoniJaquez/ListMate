package com.nocountry.listmate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nocountry.listmate.data.model.Task

@Composable
fun MyTaskItemComponent(myTask: Task, projectName: String, onStatusChange: (Boolean) -> Unit) {

    var status by remember { mutableStateOf(myTask.status) }

//    val backgroundColor = if (status) {
//        Color.LightGray  // Background color for true status
//    } else {
//        Color.Magenta  // Background color for false status
//    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = myTask.taskName)
                Text(text = "Project: $projectName")
            }
            Checkbox(checked = status, onCheckedChange = { newValue ->
                status = newValue
                onStatusChange(newValue)
            })
        }
    }
}