package com.example.jishiben_andrio.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jishiben_andrio.ui.screens.NoteEditorScreen
import com.example.jishiben_andrio.ui.screens.NoteListScreen
import com.example.jishiben_andrio.viewmodel.NoteViewModel

object Destinations {
    const val NOTE_LIST = "note_list"
    const val NOTE_EDITOR = "note_editor"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: NoteViewModel = viewModel()
    
    NavHost(
        navController = navController,
        startDestination = Destinations.NOTE_LIST
    ) {
        composable(Destinations.NOTE_LIST) {
            NoteListScreen(
                viewModel = viewModel,
                onNavigateToEditor = { navController.navigate(Destinations.NOTE_EDITOR) }
            )
        }
        
        composable(Destinations.NOTE_EDITOR) {
            NoteEditorScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
} 