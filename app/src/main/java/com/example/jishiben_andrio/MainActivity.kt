package com.example.jishiben_andrio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.jishiben_andrio.ui.navigation.AppNavigation
import com.example.jishiben_andrio.ui.theme.Jishiben_andrioTheme

/**
 * 应用程序主入口Activity
 * 
 * 负责初始化应用并设置Jetpack Compose界面
 * 配置应用主题和导航
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 启用边缘到边缘模式，允许内容显示在状态栏和导航栏区域
        enableEdgeToEdge()
        // 设置Compose内容
        setContent {
            // 应用自定义主题
            Jishiben_andrioTheme {
                // 创建Surface作为应用根容器
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()  // 添加状态栏内边距，避免内容被状态栏遮挡
                        .navigationBarsPadding(),  // 添加导航栏内边距，避免内容被导航栏遮挡
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 设置应用导航
                    AppNavigation()
                }
            }
        }
    }
}