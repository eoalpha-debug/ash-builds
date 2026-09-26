package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Champion
import com.example.data.model.MatchupInfo
import com.example.ui.components.ChampionAvatar
import com.example.ui.theme.MechaError
import com.example.ui.theme.MechaOnSurface
import com.example.ui.theme.MechaOutline
import com.example.ui.theme.MechaPrimary
import com.example.ui.theme.MechaPrimaryContainer
import com.example.ui.theme.MechaSecondary
import com.example.ui.theme.MechaSurface
import com.example.ui.theme.MechaSurfaceContainer
import com.example.ui.theme.MechaSurfaceContainerHigh
import com.example.ui.theme.MechaSurfaceContainerHighest
import com.example.ui.theme.MechaSurfaceContainerLow
import com.example.ui.theme.TrendGreen
import com.example.ui.viewmodel.MetaViewModel

@Composable
fun DraftCoachScreen(
  viewModel: MetaViewModel,
  onNavigateToChampionDetail: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val allChampions by viewModel.allChampions.collectAsState()
  var query by remember { mutableStateOf("") }
  var selectedEnemy by remember { mutableStateOf<Champion?>(null) }

  val filtered = remember(allChampions, query) {
    val q = query.trim().lowercase()
    if (q.isEmpty()) allChampions else allChampions.filter { it.name.lowercase().contains(q) }
  }

  val enemy = selectedEnemy
  val countersOfEnemy = enemy?.counters.orEmpty()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MechaSurface)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Header
    item {
      Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(top = 4.dp)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(Icons.Default.Shield, null, tint = MechaSecondary, modifier = Modifier.size(22.dp))
          Text("Draft Coach", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MechaPrimary)
        }
        Text(
          text = "Toque no herói inimigo para descobrir os melhores counters.",
          fontSize = 13.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // Busca
    item {
      OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        placeholder = { Text("Digite o herói inimigo (ex.: Lam, Florentino)...", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) },
        leadingIcon = { Icon(Icons.Default.Search, null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant) },
        trailingIcon = {
          if (query.isNotEmpty()) {
            IconButton(onClick = { query = "" }) {
              Icon(Icons.Default.Close, null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = MechaSurfaceContainerHighest,
          unfocusedContainerColor = MechaSurfaceContainerHigh,
          focusedBorderColor = MechaSecondary,
          unfocusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
          focusedTextColor = MechaOnSurface,
          unfocusedTextColor = MechaOnSurface
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
      )
    }

    // Herói inimigo selecionado
    if (enemy != null) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MechaSurfaceContainer)
            .padding(12.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ChampionAvatar(champion = enemy, size = 52.dp, shape = RoundedCornerShape(12.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text("Inimigo selecionado", fontSize = 10.sp, color = MechaSecondary, fontWeight = FontWeight.Bold)
              Text(enemy.name, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = MechaPrimary)
              Text("WR ${enemy.winRate} • ${enemy.lane.chipShort}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(onClick = { selectedEnemy = null }) {
              Icon(Icons.Default.Close, "Trocar", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }
        }
      }

      // Counters
      item {
        Text(
          text = "MELHORES COUNTERS (${countersOfEnemy.size})",
          fontSize = 11.sp,
          fontWeight = FontWeight.ExtraBold,
          color = MechaError,
          letterSpacing = 0.6.sp
        )
      }
      if (countersOfEnemy.isEmpty()) {
        item {
          Text(
            text = "Counters ainda não minerados para este herói.",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      } else {
        items(countersOfEnemy) { m: MatchupInfo ->
          val known = allChampions.map { it.name }
          val resolvedName = com.example.data.meta.ChampionJsonMapper.resolveMatchupName(m.name, known)
          val champ = allChampions.firstOrNull { it.name.equals(resolvedName, ignoreCase = true) }
          if (champ != null) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MechaSurfaceContainerLow)
                .clickable { onNavigateToChampionDetail(champ.id) }
                .padding(12.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ChampionAvatar(champion = champ, size = 46.dp, shape = RoundedCornerShape(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Text(champ.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MechaOnSurface)
                  Text(
                    text = "Tier ${champ.tier.badge} • ${champ.lane.chipShort} • WR ${champ.winRate}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              }
            }
          }
        }
      }

      // CTA
      item {
        Text(
          text = "Toque em um counter para abrir o guia com build recomendada.",
          fontSize = 11.sp,
          color = MechaOutline,
          modifier = Modifier.padding(top = 4.dp)
        )
      }
    }

    // Grade de seleção do inimigo
    item {
      Text(
        text = if (enemy == null) "ESCOLHA O HERÓI INIMIGO" else "TROCAR INIMIGO (${filtered.size})",
        fontSize = 11.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        letterSpacing = 0.6.sp
      )
    }
    items(filtered.chunked(4)) { rowChampions ->
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        rowChampions.forEach { champ ->
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(if (selectedEnemy?.id == champ.id) MechaPrimaryContainer.copy(alpha = 0.2f) else MechaSurfaceContainerLow)
              .clickable { selectedEnemy = champ }
              .padding(vertical = 8.dp)
          ) {
            ChampionAvatar(champion = champ, size = 44.dp, shape = CircleShape)
            Text(
              text = champ.name,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = MechaOnSurface,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              modifier = Modifier.padding(top = 4.dp)
            )
          }
        }
        repeat(4 - rowChampions.size) { Box(modifier = Modifier.weight(1f)) }
      }
    }
  }
}
