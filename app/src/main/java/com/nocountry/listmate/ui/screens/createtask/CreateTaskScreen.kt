package com.nocountry.listmate.ui.screens.createtask

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nocountry.listmate.R
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.data.model.User
import com.nocountry.listmate.ui.components.InputTextFieldComponent
import com.nocountry.listmate.ui.components.TopBarComponent
import com.nocountry.listmate.ui.components.ButtonComponent
import com.nocountry.listmate.ui.navigation.Destinations
import com.nocountry.listmate.ui.screens.sharedviewmodels.CreateProjectTaskSharedViewModel
import com.nocountry.listmate.ui.screens.sharedviewmodels.SharedViewModel
import com.nocountry.listmate.ui.theme.ListMateTheme


@Composable
fun CreateTaskScreen(
    navHostController: NavHostController,
    createProjectTaskSharedViewModel: CreateProjectTaskSharedViewModel,
) {

    var taskTitle by rememberSaveable { mutableStateOf("") }
    var taskDescription by rememberSaveable { mutableStateOf("") }
    val selectedParticipant: MutableState<String> = rememberSaveable { mutableStateOf("") }
    val selectedParticipantId: MutableState<String> = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val projectParticipants by createProjectTaskSharedViewModel.projectParticipants.observeAsState(mutableListOf())
    val task by createProjectTaskSharedViewModel.tasks.observeAsState(mutableListOf())

    Scaffold(
        topBar = {
            TopBarComponent(title = R.string.create_task, navigationIcon = {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Arrow back"
                    )
                }
            })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InputTextFieldComponent(
                value = taskTitle,
                onValueChange = { taskTitle = it },
                label = R.string.task_name_input_label,
                leadingIcon = null,
                trailingIcon = { },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier.fillMaxWidth(),
                placeholder = null
            )
            Spacer(modifier = Modifier.padding(0.dp, 2.dp))
            Text(text = "Assigned to:", style = MaterialTheme.typography.bodyMedium)
            DropdownMenu(selectedParticipant, selectedParticipantId, projectParticipants)
            Spacer(modifier = Modifier.padding(0.dp, 10.dp))
            Text(text = "Add task description:", style = MaterialTheme.typography.bodyMedium)
            InputTextFieldComponent(
                value = taskDescription,
                onValueChange = { taskDescription = it },
                label = null,
                leadingIcon = null,
                trailingIcon = { },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                placeholder = null
            )
            Spacer(modifier = Modifier.weight(1f))
            ButtonComponent(
                text = R.string.addtask_button_label,
                onClick = {
                    addTaskValidation(
                        taskTitle,
                        taskDescription,
                        selectedParticipant,
                        selectedParticipantId,
                        navHostController,
                        context,
                        task,
                        createProjectTaskSharedViewModel,
                    )
                },
                backgroundColor = MaterialTheme.colorScheme.inversePrimary,
                icon = null,
                textColor = MaterialTheme.colorScheme.surfaceTint,
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(selectedParticipant: MutableState<String>, selectedParticipantId: MutableState<String>, projectParticipants: List<User>) {

    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = selectedParticipant.value,
                onValueChange = {},
                placeholder = {
                    Text(
                        text = "Select participant",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isExpanded
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                modifier = Modifier.fillMaxWidth(),
            ) {
                projectParticipants.forEachIndexed { _, user ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = {
                            Text(
                                text = "${user.name} ${user.lastName}", style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                        },
                        onClick = {
                            selectedParticipant.value = user.name + " " + user.lastName
                            selectedParticipantId.value = user.uid
                            isExpanded = false
                        },
                    )
                }
            }
        }
    }
}

private fun addTaskValidation(
    taskTitle: String,
    taskDescription: String,
    selectedParticipant: MutableState<String>,
    selectedParticipantId: MutableState<String>,
    navHostController: NavHostController,
    context: Context,
    task: MutableList<Task>,
    createProjectTaskSharedViewModel: CreateProjectTaskSharedViewModel,
) {
    if (taskTitle.isNotBlank() && selectedParticipant.value.isNotBlank()) {
        val newTask = Task("", "", taskTitle, selectedParticipant.value, selectedParticipantId.value , taskDescription, false)
        task.add(newTask)
        createProjectTaskSharedViewModel.setTasks(task)
        navHostController.navigate(Destinations.CREATE_PROJECT)
        createProjectTaskSharedViewModel.onSearchTextChange("")
    } else {
        Toast.makeText(
            context,
            "Please complete all required fields",
            Toast.LENGTH_SHORT
        ).show()
    }
}


//@Preview(showBackground = true)
//@Composable
//fun CreateTaskScreenPreview() {
//    ListMateTheme {
//        val mockViewModel = CreateProjectTaskSharedViewModel()
//        CreateTaskScreen(
//            navHostController = NavHostController(LocalContext.current),
//            createProjectTaskSharedViewModel = mockViewModel
//        )
//    }
//}