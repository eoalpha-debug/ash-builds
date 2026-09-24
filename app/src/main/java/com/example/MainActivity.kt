package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.ui.components.AppHeader
import com.example.ui.components.AppTab
import com.example.ui.components.BottomNavBar
import com.example.ui.screens.ChampionDetailScreen
import com.example.ui.screens.ChampionsListScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OfflineModeScreen
import com.example.ui.screens.TierListScreen
import com.example.ui.theme.MechaSurface
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.MetaViewModel
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {

  private val metaViewModel: MetaViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    // Modo imersivo: esconde a barra de navegação do Android (reaparece com swipe)
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowCompat.getInsetsController(window, window.decorView).apply {
      systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
      hide(WindowInsetsCompat.Type.systemBars())
    }

    setContent {
      MyApplicationTheme {
        HoKMetaLabApp(viewModel = metaViewModel)
      }
    }
  }
}

@Composable
fun HoKMetaLabApp(
  viewModel: MetaViewModel,
  modifier: Modifier = Modifier
) {
  var currentTab by remember { mutableStateOf(AppTab.INICIO) }
  var detailChampionId by remember { mutableStateOf<String?>(null) }

  // Handle hardware back press when in detail screen
  BackHandler(enabled = detailChampionId != null) {
    detailChampionId = null
  }

  val screenSubtitle = when {
    detailChampionId != null -> "Builds & Estatísticas"
    currentTab == AppTab.INICIO -> "Início / Meta Geral"
    currentTab == AppTab.TIER_LIST -> "Classificação Tier List"
    currentTab == AppTab.CAMPEOES -> "Catálogo de Heróis"
    currentTab == AppTab.OFFLINE -> "Armazenamento Offline"
    else -> "Meta Global"
  }

  Scaffold(
    topBar = {
      AppHeader(
        screenSubtitle = screenSubtitle,
        onBackClick = if (detailChampionId != null) {
          { detailChampionId = null }
        } else null
      )
    },
    bottomBar = {
      BottomNavBar(
        currentTab = currentTab,
        onTabSelected = { tab ->
          detailChampionId = null
          currentTab = tab
        }
      )
    },
    containerColor = MechaSurface,
    modifier = modifier.fillMaxSize()
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(MechaSurface)
    ) {
      if (detailChampionId != null) {
        ChampionDetailScreen(
          championId = detailChampionId!!,
          viewModel = viewModel
        )
      } else {
        when (currentTab) {
          AppTab.INICIO -> {
            HomeScreen(
              viewModel = viewModel,
              onNavigateToChampionDetail = { champId ->
                detailChampionId = champId
              },
              onNavigateToOffline = {
                currentTab = AppTab.OFFLINE
              }
            )
          }
          AppTab.TIER_LIST -> {
            TierListScreen(
              viewModel = viewModel,
              onNavigateToChampionDetail = { champId ->
                detailChampionId = champId
              }
            )
          }
          AppTab.CAMPEOES -> {
            ChampionsListScreen(
              viewModel = viewModel,
              onNavigateToChampionDetail = { champId ->
                detailChampionId = champId
              }
            )
          }
          AppTab.OFFLINE -> {
            OfflineModeScreen(
              viewModel = viewModel,
              onOpenGuide = { champId ->
                detailChampionId = champId
              },
              onNavigateToCatalog = {
                currentTab = AppTab.CAMPEOES
              }
            )
          }
        }
      }
    }
  }
}
