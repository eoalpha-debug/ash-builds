package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MechaPrimaryContainer
import com.example.ui.theme.MechaSecondary
import com.example.ui.theme.MechaSurfaceContainerLowest

enum class AppTab(val label: String, val icon: ImageVector, val tag: String) {
  INICIO("Início", Icons.Default.GridView, "tab_inicio"),
  TIER_LIST("Tier List", Icons.Default.MilitaryTech, "tab_tier_list"),
  CAMPEOES("Campeões", Icons.Default.SportsEsports, "tab_campeoes"),
  BUILDER("Build", Icons.Default.Build, "tab_builder"),
  DRAFT("Draft", Icons.Default.Shield, "tab_draft"),
  OFFLINE("Offline", Icons.Default.CloudDownload, "tab_offline")
}

@Composable
fun BottomNavBar(
  currentTab: AppTab,
  onTabSelected: (AppTab) -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(72.dp)
      .background(MechaSurfaceContainerLowest.copy(alpha = 0.96f))
      .padding(horizontal = 8.dp),
    horizontalArrangement = Arrangement.SpaceAround,
    verticalAlignment = Alignment.CenterVertically
  ) {
    AppTab.values().forEach { tab ->
      val isSelected = currentTab == tab
      Column(
        modifier = Modifier
          .weight(1f)
          .clip(CircleShape)
          .clickable { onTabSelected(tab) }
          .padding(vertical = 8.dp)
          .testTag(tab.tag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Icon(
          imageVector = tab.icon,
          contentDescription = tab.label,
          tint = if (isSelected) MechaSecondary else MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.size(24.dp)
        )
        Text(
          text = tab.label,
          fontSize = 12.sp,
          fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
          color = if (isSelected) MechaSecondary else MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.padding(top = 2.dp)
        )
        if (isSelected) {
          Box(
            modifier = Modifier
              .padding(top = 3.dp)
              .size(4.dp)
              .clip(CircleShape)
              .background(MechaPrimaryContainer)
          )
        } else {
          Box(modifier = Modifier.padding(top = 3.dp).size(4.dp))
        }
      }
    }
  }
}
