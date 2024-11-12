package com.nocountry.listmate.ui.screens.my_tasks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.repository.MyTasksRepositoryImpl
import com.nocountry.listmate.domain.MyTasksRepository
import com.nocountry.listmate.ui.components.BottomNavigationBar
import com.nocountry.listmate.ui.components.MyTaskItemComponent
import com.nocountry.listmate.ui.screens.sharedviewmodels.SharedViewModel

@Composable
fun MyTasksScreen(navHostController: NavHostController, sharedViewModel: SharedViewModel) {

    val userId by sharedViewModel.userId.collectAsState()

    val myTasksRepository: MyTasksRepository =
        MyTasksRepositoryImpl(FirebaseFirestore.getInstance())

    val myTasksViewModel: MyTasksViewModel = viewModel(
        factory = MyTasksViewModelFactory(myTasksRepository, userId)
    )

    val myTasks by myTasksViewModel.myTasks.observeAsState(mutableListOf())
    val projectNames by myTasksViewModel.projectNames.observeAsState(emptyMap())

    LaunchedEffect(userId) {
        userId.let {
            myTasksViewModel.getMyTasks(it)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navHostController)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            item {
                Text(
                    text = "My Tasks",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 15.dp)
                )
            }
            items(myTasks) { myTask ->
                val projectName = projectNames[myTask.projectId] ?: "Unknown Project"
                MyTaskItemComponent(myTask, projectName) { isChecked ->
                    myTasksViewModel.updateTaskStatus(myTask.id, isChecked)
                }
            }
        }

    }
}