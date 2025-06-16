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

/**
 * 应用导航目的地常量
 * 
 * 定义应用内所有导航路径的常量
 */
object Destinations {
    /** 笔记列表屏幕路径 */
    const val NOTE_LIST = "note_list"
    
    /** 笔记编辑器屏幕路径 */
    const val NOTE_EDITOR = "note_editor"
}

/**
 * 应用导航组件
 * 
 * 管理应用内屏幕间的导航
 * 配置导航路由，并共享ViewModel以在屏幕间保持状态
 */
@Composable
fun AppNavigation() {
    // 创建导航控制器
    val navController = rememberNavController()
    // 创建ViewModel实例，将在导航期间保持
    val viewModel: NoteViewModel = viewModel()
    
    NavHost(
        navController = navController,
        startDestination = Destinations.NOTE_LIST // 设置笔记列表为起始屏幕
    ) {
        // 笔记列表屏幕路由配置
        composable(Destinations.NOTE_LIST) {
            NoteListScreen(
                viewModel = viewModel,
                onNavigateToEditor = { navController.navigate(Destinations.NOTE_EDITOR) }
            )
        }
        
        // 笔记编辑器屏幕路由配置
        composable(Destinations.NOTE_EDITOR) {
            NoteEditorScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
} 