package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.GroupWork
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.ui.theme.MechaError
import com.example.ui.theme.MechaOnPrimary
import com.example.ui.theme.MechaOnPrimaryContainer
import com.example.ui.theme.MechaOnSecondary
import com.example.ui.theme.MechaOnSurface
import com.example.ui.theme.MechaOutline
import com.example.ui.theme.MechaPrimary
import com.example.ui.theme.MechaPrimaryContainer
import com.example.ui.theme.MechaSecondary
import com.example.ui.theme.MechaSecondaryContainer
import com.example.ui.theme.MechaSecondaryFixed
import com.example.ui.theme.MechaSurface
import com.example.ui.theme.MechaSurfaceContainer
import com.example.ui.theme.MechaSurfaceContainerHigh
import com.example.ui.theme.MechaSurfaceContainerHighest
import com.example.ui.theme.MechaSurfaceContainerLow
import com.example.ui.theme.MechaSurfaceContainerLowest
import com.example.ui.theme.TrendGreen
import com.example.ui.viewmodel.MetaViewModel

@Composable
fun TierListScreen(
  viewModel: MetaViewModel,
  onNavigateToChampionDetail: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val selectedLane by viewModel.selectedLane.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()
  val allChampions by viewModel.allChampions.collectAsState()

  val ssHeroes = remember(allChampions, selectedLane, searchQuery) { viewModel.getFilteredTierHeroes(HeroTier.SS) }
  val sHeroes = remember(allChampions, selectedLane, searchQuery) { viewModel.getFilteredTierHeroes(HeroTier.S) }
  val aHeroes = remember(allChampions, selectedLane, searchQuery) { viewModel.getFilteredTierHeroes(HeroTier.A) }

  val bHeroes = remember(allChampions, selectedLane, searchQuery) { viewModel.getFilteredTierHeroes(HeroTier.B) }
  val cHeroes = remember(allChampions, selectedLane, searchQuery) { viewModel.getFilteredTierHeroes(HeroTier.C) }

  val totalVisible = ssHeroes.size + sHeroes.size + aHeroes.size + bHeroes.size + cHeroes.size

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MechaSurface)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Meta Analytics Title & Live Notice
    item {
      Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 4.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Insights,
              contentDescription = null,
              tint = MechaPrimaryContainer,
              modifier = Modifier.size(22.dp)
            )
            Text(
              text = "Meta Analytics",
              fontSize = 22.sp,
              fontWeight = FontWeight.Bold,
              color = MechaPrimary
            )
          }

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
              .clip(CircleShape)
              .background(MechaSurfaceContainerHigh)
              .padding(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(MechaPrimaryContainer)
            )
            val patchLabel by viewModel.patchLabel.collectAsState()
            Text(
              text = "${patchLabel.uppercase()} LIVE",
              color = MechaSecondary,
              fontSize = 10.sp,
              fontWeight = FontWeight.ExtraBold,
              letterSpacing = 0.6.sp
            )
          }
        }

        Text(
          text = "Classificação competitiva atualizada em tempo real com base no elo Grão-Mestre.",
          fontSize = 13.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // 2. Search Bar
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { viewModel.updateSearchQuery(it) },
        placeholder = {
          Text(
            text = "Buscar campeão na Tier List...",
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
                contentDescription = "Limpar busca",
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
          .testTag("tier_list_search_input")
      )
    }

    // 3. Lane Filter Chips
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
              .testTag("tier_lane_${lane.name}")
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

    // EMPTY STATE WHEN NO RESULTS
    if (totalVisible == 0) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MechaSurfaceContainerLow)
            .padding(24.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Box(
              modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MechaSurfaceContainer),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.SearchOff,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(28.dp)
              )
            }
            Text(
              text = "Nenhum campeão encontrado",
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = MechaPrimary
            )
            Text(
              text = "Tente ajustar o termo de pesquisa ou limpe o filtro de rotas.",
              fontSize = 13.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Button(
              onClick = {
                viewModel.updateSearchQuery("")
                viewModel.selectLane(Lane.TODAS)
              },
              colors = ButtonDefaults.buttonColors(
                containerColor = MechaPrimaryContainer,
                contentColor = MechaOnPrimaryContainer
              ),
              shape = RoundedCornerShape(8.dp)
            ) {
              Text("Limpar Filtros", fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // ==================== TIER SS ====================
    if (ssHeroes.isNotEmpty()) {
      item {
        TierSectionHeader(
          badgeText = "SS",
          title = "Dominantes",
          subtitle = "Ban quase obrigatório ou prioridade total no draft",
          countText = "${ssHeroes.size} Heróis",
          badgeBg = MechaPrimaryContainer,
          badgeColor = MechaOnPrimary
        )
      }

      items(ssHeroes, key = { it.id }) { hero ->
        TierSSHeroCard(
          champion = hero,
          onClick = { onNavigateToChampionDetail(hero.id) }
        )
      }
    }

    // ==================== TIER S ====================
    if (sHeroes.isNotEmpty()) {
      item {
        TierSectionHeader(
          badgeText = "S",
          title = "Muito Fortes no Meta",
          subtitle = "Excelente consistência e impacto decisivo",
          countText = "${sHeroes.size} Heróis",
          badgeBg = MechaSecondaryContainer,
          badgeColor = MechaSecondaryFixed
        )
      }

      items(sHeroes, key = { it.id }) { hero ->
        TierSHeroCard(
          champion = hero,
          onClick = { onNavigateToChampionDetail(hero.id) }
        )
      }
    }

    // ==================== TIER A ====================
    if (aHeroes.isNotEmpty()) {
      item {
        TierSectionHeader(
          badgeText = "A",
          title = "Escolhas Sólidas e Balanceadas",
          subtitle = "Eficientes em composições padronizadas",
          countText = "${aHeroes.size} Heróis",
          badgeBg = MechaSurfaceContainerHighest,
          badgeColor = MechaPrimary
        )
      }

      items(aHeroes, key = { it.id }) { hero ->
        TierAHeroCard(
          champion = hero,
          onClick = { onNavigateToChampionDetail(hero.id) }
        )
      }
    }
    // ==================== TIER B ====================
    if (bHeroes.isNotEmpty()) {
      item {
        TierSectionHeader(
          badgeText = "B",
          title = "Situacionais / Requer Sinergia",
          subtitle = "Dependem de counter-picks específicos ou comps dedicadas",
          countText = "${bHeroes.size} Heróis",
          badgeBg = MechaSurfaceContainerHigh,
          badgeColor = MechaOutline
        )
      }

      items(bHeroes, key = { it.id }) { hero ->
        TierAHeroCard(
          champion = hero,
          onClick = { onNavigateToChampionDetail(hero.id) }
        )
      }
    }

    // ==================== TIER C ====================
    if (cHeroes.isNotEmpty()) {
      item {
        TierSectionHeader(
          badgeText = "C",
          title = "Fora do Meta",
          subtitle = "Escolhas fracas no patch — jogue apenas com maestria",
          countText = "${cHeroes.size} Heróis",
          badgeBg = MechaSurfaceContainer,
          badgeColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      items(cHeroes, key = { it.id }) { hero ->
        TierAHeroCard(
          champion = hero,
          onClick = { onNavigateToChampionDetail(hero.id) }
        )
      }
    }

    // 5. Metodologia e Critérios de Avaliação Card
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(MechaSurfaceContainer)
          .padding(14.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Verified,
              contentDescription = null,
              tint = MechaPrimaryContainer,
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = "Legenda & Critérios de Avaliação",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = MechaPrimary
            )
          }
          Text(
            text = "Dados computados a partir de mais de 850.000 partidas ranqueadas nos elos Mestre e Grão-Mestre nos servidores Global/LATAM. As taxas de vitória consideram apenas heróis com presença relevante acima de 5% de taxa de escolha.",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 17.sp
          )
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Fonte: HoK MetaLab Engine",
              fontSize = 11.sp,
              color = MechaOutline
            )
            Text(
              text = "Sincronizado há 14m",
              fontSize = 11.sp,
              color = MechaOutline
            )
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Composable
fun TierSectionHeader(
  badgeText: String,
  title: String,
  subtitle: String,
  countText: String,
  badgeBg: Color,
  badgeColor: Color
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.weight(1f)
    ) {
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(4.dp))
          .background(badgeBg)
          .padding(horizontal = 8.dp, vertical = 2.dp)
      ) {
        Text(
          text = badgeText,
          fontSize = 16.sp,
          fontWeight = FontWeight.Black,
          color = badgeColor
        )
      }

      Column {
        Text(
          text = title,
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = MechaPrimary,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Text(
          text = subtitle,
          fontSize = 11.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }
    }

    Spacer(modifier = Modifier.width(8.dp))

    Box(
      modifier = Modifier
        .clip(CircleShape)
        .background(MechaSurfaceContainerHighest)
        .padding(horizontal = 10.dp, vertical = 3.dp)
    ) {
      Text(
        text = countText,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = badgeColor,
        maxLines = 1,
        softWrap = false
      )
    }
  }
}

@Composable
fun TierSSHeroCard(
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
      .testTag("tier_ss_${champion.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Portrait
      Box(
        modifier = Modifier
          .size(56.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(MechaSurfaceContainerHigh)
      ) {
        com.example.ui.components.ChampionAvatar(
          champion = champion,
          size = 56.dp,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxSize()
        )
        Box(
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .clip(RoundedCornerShape(topStart = 6.dp))
            .background(MechaSurfaceContainerLowest.copy(alpha = 0.9f))
            .padding(2.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Stars,
            contentDescription = null,
            tint = MechaSecondary,
            modifier = Modifier.size(13.dp)
          )
        }
      }

      // Information & Stats
      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = champion.name,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = MechaOnSurface
            )
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(MechaSurfaceContainerHigh)
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = champion.lane.chipShort,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MechaSecondary
              )
            }
          }

          // Selo de destaque apenas quando o ban rate real é prioritário (>= 20%)
          if (champion.isBanPriority) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
              Icon(
                imageVector = Icons.Default.PriorityHigh,
                contentDescription = null,
                tint = MechaError,
                modifier = Modifier.size(14.dp)
              )
              Text(
                text = "Ban Crucial",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MechaError
              )
            }
          }
        }

        // Stats row: Taxa Vitória, Taxa Ban, Taxa Escolha
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            Text(
              text = "Taxa Vitória",
              fontSize = 10.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = champion.winRate,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = MechaPrimary
            )
          }
          Column {
            Text(
              text = "Taxa Ban",
              fontSize = 10.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = champion.banRate,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = MechaError
            )
          }
          Column {
            Text(
              text = "Taxa Escolha",
              fontSize = 10.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = champion.pickRate,
              fontSize = 13.sp,
              color = MechaOnSurface
            )
          }
        }
      }

      // Ver Build button
      IconButton(
        onClick = onClick,
        modifier = Modifier
          .size(40.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(MechaSurfaceContainerHigh)
      ) {
        Icon(
          imageVector = Icons.Default.Construction,
          contentDescription = "Ver Build",
          tint = MechaSecondary,
          modifier = Modifier.size(20.dp)
        )
      }
    }
  }
}

@Composable
fun TierSHeroCard(
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
      .testTag("tier_s_${champion.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.weight(1f)
      ) {
        com.example.ui.components.ChampionAvatar(
          champion = champion,
          size = 48.dp,
          shape = RoundedCornerShape(10.dp)
        )

        Column {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = champion.name,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = MechaOnSurface
            )
            Text(
              text = champion.lane.chipShort,
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(top = 2.dp)
          ) {
            Text(
              text = "${champion.winRate} WR",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = MechaPrimary
            )
            Text(
              text = "•",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
            )
            Text(
              text = "${champion.pickRate} Pick",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
          containerColor = MechaSurfaceContainerHigh,
          contentColor = MechaSecondary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.height(36.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Tune,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
          )
          Text(text = "Ver Build", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun TierAHeroCard(
  champion: Champion,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(MechaSurfaceContainerLow)
      .clickable { onClick() }
      .padding(10.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        com.example.ui.components.ChampionAvatar(
          champion = champion,
          size = 40.dp,
          shape = RoundedCornerShape(8.dp)
        )

        Column {
          Text(
            text = champion.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MechaOnSurface
          )
          Text(
            text = "${champion.lane.chipShort} • WR ${champion.winRate}",
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(6.dp))
          .background(MechaSurfaceContainer)
          .padding(horizontal = 8.dp, vertical = 4.dp)
      ) {
        Text(
          text = "Pick ${champion.pickRate}",
          fontSize = 11.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}
