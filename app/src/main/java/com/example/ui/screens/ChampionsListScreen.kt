package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Champion
import com.example.data.model.HeroTier
import com.example.data.model.Lane
import com.example.ui.theme.MechaOnPrimaryContainer
import com.example.ui.theme.MechaOnSurface
import com.example.ui.theme.MechaPrimary
import com.example.ui.theme.MechaPrimaryContainer
import com.example.ui.theme.MechaPrimaryFixedDim
import com.example.ui.theme.MechaSecondary
import com.example.ui.theme.MechaSurface
import com.example.ui.theme.MechaSurfaceContainer
import com.example.ui.theme.MechaSurfaceContainerHigh
import com.example.ui.theme.MechaSurfaceContainerHighest
import com.example.ui.theme.MechaSurfaceContainerLow
import com.example.ui.theme.MechaSurfaceVariant
import com.example.ui.viewmodel.MetaViewModel

@Composable
fun ChampionsListScreen(
  viewModel: MetaViewModel,
  onNavigateToChampionDetail: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val selectedLane by viewModel.selectedLane.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()
  val allChampions by viewModel.allChampions.collectAsState()
  val champions = remember(allChampions, selectedLane, searchQuery) {
    viewModel.getFilteredChampionsList()
  }

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
          Icon(
            imageVector = Icons.Default.SportsEsports,
            contentDescription = null,
            tint = MechaSecondary,
            modifier = Modifier.size(22.dp)
          )
          Text(
            text = "Catálogo de Campeões",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MechaPrimary
          )
        }
        Text(
          text = "Consulte estatísticas completas, combos, arcanas e builds recomendadas.",
          fontSize = 13.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // Search bar
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { viewModel.updateSearchQuery(it) },
        placeholder = {
          Text(
            text = "Pesquisar herói (ex: Lam, Mayene, Angela)...",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp
          )
        },
        leadingIcon = {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Buscar",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
          )
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { viewModel.updateSearchQuery("") }) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Limpar",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = MechaSurfaceContainerHighest,
          unfocusedContainerColor = MechaSurfaceContainerHigh,
          focusedBorderColor = MechaSecondary,
          unfocusedBorderColor = Color.Transparent,
          focusedTextColor = MechaOnSurface,
          unfocusedTextColor = MechaOnSurface
        ),
        singleLine = true,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("champ_search_input")
      )
    }

    // Lane Filter Row
    item {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        items(Lane.values()) { lane ->
          val isSelected = selectedLane == lane
          Box(
            modifier = Modifier
              .clip(CircleShape)
              .background(if (isSelected) MechaPrimaryContainer else MechaSurfaceContainer)
              .clickable { viewModel.selectLane(lane) }
              .padding(horizontal = 14.dp, vertical = 7.dp)
              .testTag("champ_lane_${lane.name}")
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Stars,
                contentDescription = null,
                tint = if (isSelected) MechaOnPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(15.dp)
              )
              Text(
                text = lane.labelPt,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) MechaOnPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      }
    }

    // Champions count header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "HERÓIS DISPONÍVEIS",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          letterSpacing = 0.5.sp
        )
        Text(
          text = "${champions.size} campeões",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = MechaSecondary
        )
      }
    }

    // Champions List
    items(champions, key = { it.id }) { champion ->
      ChampionListItem(
        champion = champion,
        onClick = { onNavigateToChampionDetail(champion.id) }
      )
    }

    item {
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Composable
fun ChampionListItem(
  champion: Champion,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(MechaSurfaceContainerLow)
      .clickable { onClick() }
      .padding(12.dp)
      .testTag("champ_item_${champion.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Avatar
      com.example.ui.components.ChampionAvatar(
        champion = champion,
        size = 56.dp,
        shape = RoundedCornerShape(12.dp)
      )

      // Details
      Column(modifier = Modifier.weight(1f)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Text(
            text = champion.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MechaPrimary
          )
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(if (champion.tier == HeroTier.SS) MechaPrimaryContainer else MechaSurfaceContainerHigh)
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "TIER ${champion.tier.badge}",
              fontSize = 9.sp,
              fontWeight = FontWeight.ExtraBold,
              color = if (champion.tier == HeroTier.SS) MechaOnPrimaryContainer else MechaSecondary
            )
          }
        }

        Text(
          text = champion.title,
          fontSize = 12.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.padding(top = 4.dp)
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(MechaSurfaceContainerHighest)
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "${champion.heroClass} • ${champion.lane.chipShort}",
              fontSize = 10.sp,
              color = MechaSecondary,
              fontWeight = FontWeight.Bold
            )
          }

          Text(
            text = "WR ${champion.winRate}",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MechaPrimary
          )
        }
      }

      // Difficulty rating stars
      Column(horizontalAlignment = Alignment.End) {
        Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
          repeat(5) { index ->
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = null,
              tint = if (index < champion.difficultyStars) MechaPrimaryFixedDim else MechaSurfaceVariant,
              modifier = Modifier.size(13.dp)
            )
          }
        }
        Icon(
          imageVector = Icons.Default.ChevronRight,
          contentDescription = "Abrir",
          tint = MechaSecondary,
          modifier = Modifier
            .padding(top = 8.dp)
            .size(20.dp)
        )
      }
    }
  }
}
