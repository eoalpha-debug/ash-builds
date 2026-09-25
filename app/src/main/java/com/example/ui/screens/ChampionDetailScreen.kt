package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.Cyclone
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Snowshoeing
import androidx.compose.material.icons.filled.SportsMartialArts
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.filled.Token
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Waves
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.ArcanaItem
import com.example.data.model.Champion
import com.example.data.model.EquipmentItem
import com.example.data.model.HeroTier

import com.example.data.model.SituationalItem
import com.example.data.model.SkillInfo
import com.example.ui.theme.MechaError
import com.example.ui.theme.MechaOnPrimaryContainer
import com.example.ui.theme.MechaOnSurface
import com.example.ui.theme.MechaOutline
import com.example.ui.theme.MechaPrimary
import com.example.ui.theme.MechaPrimaryContainer
import com.example.ui.theme.MechaPrimaryFixedDim
import com.example.ui.theme.MechaSecondary
import com.example.ui.theme.MechaSecondaryContainer
import com.example.ui.theme.MechaSurface
import com.example.ui.theme.MechaSurfaceContainer
import com.example.ui.theme.MechaSurfaceContainerHigh
import com.example.ui.theme.MechaSurfaceContainerHighest
import com.example.ui.theme.MechaSurfaceContainerLow
import com.example.ui.theme.MechaSurfaceVariant
import com.example.ui.theme.TrendGreen
import com.example.ui.viewmodel.MetaViewModel

enum class DetailTab(val title: String) {
  BUILD("Build & Itens"),
  ARCANAS("Arcanas 150"),
  HABILIDADES("Habilidades")
}

@Composable
fun ChampionDetailScreen(
  championId: String,
  viewModel: MetaViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val allChampions by viewModel.allChampions.collectAsState()
  val champion = allChampions.firstOrNull {
    it.id.equals(championId, ignoreCase = true)
  } ?: run {
    Text(
      text = "Carregando guia...",
      color = Color(0xFFD1C5B1),
      modifier = modifier.fillMaxSize().padding(24.dp)
    )
    return
  }

  val savedGuides by viewModel.savedGuides.collectAsState()
  val isSaved = savedGuides.any { it.championId == champion.id }

  var selectedTab by remember { mutableStateOf(DetailTab.BUILD) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MechaSurface)
  ) {
    LazyColumn(
      modifier = Modifier
        .weight(1f)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // 1. HERO IDENTITY CARD
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MechaSurfaceContainerLow)
            .border(1.dp, MechaSecondary.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .padding(16.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(14.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(80.dp)
                  .clip(RoundedCornerShape(14.dp))
                  .background(MechaSurfaceContainerHigh)
              ) {
                com.example.ui.components.ChampionAvatar(
                  champion = champion,
                  size = 80.dp,
                  shape = RoundedCornerShape(14.dp),
                  modifier = Modifier.fillMaxSize()
                )
                Box(
                  modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(topStart = 8.dp))
                    .background(MechaSurfaceContainer.copy(alpha = 0.9f))
                    .padding(horizontal = 5.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = "TIER ${champion.tier.badge}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (champion.tier == HeroTier.SS) MechaPrimaryContainer else MechaSecondary
                  )
                }
              }

              Column(modifier = Modifier.weight(1f)) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(4.dp))
                      .background(MechaSurfaceContainerHigh)
                      .padding(horizontal = 6.dp, vertical = 2.dp)
                  ) {
                    Text(
                      text = "${champion.lane.chipShort.uppercase()} / ${champion.heroClass.uppercase()}",
                      fontSize = 10.sp,
                      fontWeight = FontWeight.ExtraBold,
                      color = MechaSecondary,
                      letterSpacing = 0.5.sp
                    )
                  }
                }

                Text(
                  text = champion.name,
                  fontSize = 24.sp,
                  fontWeight = FontWeight.Bold,
                  color = MechaPrimary,
                  modifier = Modifier.padding(top = 2.dp)
                )

                Text(
                  text = champion.title,
                  fontSize = 13.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(2.dp),
                  modifier = Modifier.padding(top = 4.dp)
                ) {
                  Text(
                    text = "Dificuldade: ",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                  repeat(5) { index ->
                    Icon(
                      imageVector = Icons.Default.Star,
                      contentDescription = null,
                      tint = if (index < champion.difficultyStars) MechaPrimaryFixedDim else MechaSurfaceVariant,
                      modifier = Modifier.size(13.dp)
                    )
                  }
                }
              }
            }

            // Metrics row: Win Rate, Pick Rate, Ban Rate
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MechaSurfaceContainerHigh)
                .padding(10.dp),
              horizontalArrangement = Arrangement.SpaceAround
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                  text = "TAXA VITÓRIA",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                  text = champion.winRate,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold,
                  color = MechaPrimaryContainer
                )
              }

              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                  text = "TAXA ESCOLHA",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                  text = champion.pickRate,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold,
                  color = MechaOnSurface
                )
              }

              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                  text = "TAXA BAN",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                  text = champion.banRate,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold,
                  color = MechaError
                )
              }
            }
          }
        }
      }

      // 1.5 GRÁFICO WR (histórico oficial dos últimos syncs)
      item {
        val history = remember(champion.name) { viewModel.getWrHistory(champion.name) }
        if (history.size >= 2) {
          WrHistoryCard(points = history)
        }
      }

      // 2. SUB-TABS SELECTOR
      item {
        ScrollableTabRow(
          selectedTabIndex = selectedTab.ordinal,
          containerColor = MechaSurfaceContainerHigh,
          contentColor = MechaSecondary,
          edgePadding = 8.dp,
          indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
              modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal]),
              color = MechaSecondary,
              height = 3.dp
            )
          },
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
        ) {
          DetailTab.values().forEach { tab ->
            Tab(
              selected = selectedTab == tab,
              onClick = { selectedTab = tab },
              text = {
                Text(
                  text = tab.title,
                  fontSize = 13.sp,
                  fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Medium,
                  color = if (selectedTab == tab) MechaSecondary else MaterialTheme.colorScheme.onSurfaceVariant
                )
              },
              modifier = Modifier.testTag("detail_tab_${tab.name}")
            )
          }
        }
      }

      // 3. TAB CONTENT
      when (selectedTab) {
        DetailTab.BUILD -> {
          item {
            BuildTabContent(champion = champion, viewModel = viewModel)
          }
        }
        DetailTab.ARCANAS -> {
          item {
            ArcanasTabContent(champion = champion)
          }
        }
        DetailTab.HABILIDADES -> {
          item {
            SkillsTabContent(champion = champion)
          }
        }
      }

      item {
        Spacer(modifier = Modifier.height(16.dp))
      }
    }

    // PERSISTENT BOTTOM ACTION BAR
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(MechaSurfaceContainerLow)
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Button(
          onClick = {
            viewModel.toggleSaveChampion(champion)
            val msg = if (isSaved) "Guia removido do armazenamento offline." else "Guia do ${champion.name} salvo com sucesso para consulta offline!"
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
          },
          colors = ButtonDefaults.buttonColors(
            containerColor = if (isSaved) MechaSurfaceContainerHigh else MechaPrimaryContainer,
            contentColor = if (isSaved) MechaSecondary else MechaOnPrimaryContainer
          ),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .weight(1f)
            .height(50.dp)
            .testTag("btn_toggle_save_champion")
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              imageVector = if (isSaved) Icons.Default.Check else Icons.Default.BookmarkBorder,
              contentDescription = null,
              modifier = Modifier.size(20.dp)
            )
            Text(
              text = if (isSaved) "Salvo no Modo Offline" else "Salvar Guia Offline",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        IconButton(
          onClick = {
            Toast.makeText(context, "Link da build copiado para a área de transferência!", Toast.LENGTH_SHORT).show()
          },
          modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MechaSurfaceContainerHigh)
        ) {
          Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Compartilhar",
            tint = MechaOnSurface
          )
        }
      }
    }
  }
}

// ================= BUILD TAB CONTENT =================
@Composable
fun BuildTabContent(champion: Champion, viewModel: MetaViewModel) {
  Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
    // Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = champion.buildTitle,
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          color = MechaPrimary
        )
        if (champion.proPlayerName.isNotBlank()) {
          Text(
            text = "Validada por Pro",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MechaSecondary,
            modifier = Modifier.padding(top = 3.dp)
          )
        }
        Text(
          text = champion.buildSubtitle,
          fontSize = 12.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // 6 Items list with details
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      champion.items.forEachIndexed { index, item ->
        EquipmentItemCard(itemNumber = index + 1, item = item)
      }
    }

    // Feitiço Recomendado
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(MechaSurfaceContainerLow)
        .padding(14.dp)
    ) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
      ) {
        Box(
          modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MechaPrimaryContainer),
          contentAlignment = Alignment.Center
        ) {
          if (champion.spellImageUrl != null) {
            coil.compose.SubcomposeAsyncImage(
              model = coil.request.ImageRequest.Builder(LocalContext.current)
                .data(champion.spellImageUrl)
                .crossfade(true)
                .build(),
              contentDescription = champion.spellName,
              modifier = Modifier.size(44.dp),
              loading = {
                Icon(Icons.Default.Colorize, null, tint = MechaOnPrimaryContainer, modifier = Modifier.size(24.dp))
              },
              error = {
                Icon(Icons.Default.Colorize, null, tint = MechaOnPrimaryContainer, modifier = Modifier.size(24.dp))
              }
            )
          } else {
            Icon(
              imageVector = Icons.Default.Colorize,
              contentDescription = null,
              tint = MechaOnPrimaryContainer,
              modifier = Modifier.size(24.dp)
            )
          }
        }

        Column {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = "Feitiço: ${champion.spellName}",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = MechaPrimary
            )
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(MechaSurfaceContainerHigh)
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = champion.spellSubtitle,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MechaSecondary
              )
            }
          }
          Text(
            text = champion.spellDescription,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 16.sp,
            modifier = Modifier.padding(top = 4.dp)
          )
        }
      }
    }

    // Situational Items
    if (champion.situationalItems.isNotEmpty()) {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Default.SwapHoriz,
            contentDescription = null,
            tint = MechaSecondary,
            modifier = Modifier.size(18.dp)
          )
          Text(
            text = "Itens Situacionais (Quando usar)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MechaPrimary
          )
        }

        champion.situationalItems.forEach { item ->
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(MechaSurfaceContainerLow)
              .padding(12.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              if (item.imageUrl != null) {
                coil.compose.SubcomposeAsyncImage(
                  model = coil.request.ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .build(),
                  contentDescription = item.name,
                  modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(end = 0.dp),
                  loading = {
                    Box(Modifier.size(36.dp).clip(RoundedCornerShape(8.dp)).background(MechaSurfaceContainerHigh))
                  },
                  error = {
                    Box(Modifier.size(36.dp).clip(RoundedCornerShape(8.dp)).background(MechaSurfaceContainerHigh))
                  }
                )
                Spacer(modifier = Modifier.width(10.dp))
              }
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = item.name,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = MechaOnSurface
                )
                Text(
                  text = item.reason,
                  fontSize = 12.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.padding(top = 2.dp)
                )
              }
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(MechaSurfaceContainerHigh)
                  .padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Text(
                  text = item.tag,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = MechaSecondary
                )
              }
            }
          }
        }
      }
    }

    // Matchups reais: counters, sinergias e quem o herói domina
    MatchupsSection(champion = champion, viewModel = viewModel)
  }
}

@Composable
fun MatchupsSection(champion: Champion, viewModel: MetaViewModel) {
  if (champion.counters.isEmpty() && champion.synergies.isEmpty() && champion.strongAgainst.isEmpty()) return

  // Resolve a imagem de cada matchup pelo nome do herói
  fun imageOf(name: String): String? {
    val clean = name.replace(Regex(" (Superior|Selva|Meio|Atirador|Suporte)$"), "").trim()
    return viewModel.allChampions.value.firstOrNull {
      it.name.equals(clean, ignoreCase = true)
    }?.imageUrl
  }

  Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
    Text(
      text = "Counters & Sinergias",
      fontSize = 17.sp,
      fontWeight = FontWeight.Bold,
      color = MechaPrimary
    )

    if (champion.counters.isNotEmpty()) {
      MatchupGroup(title = "Fraco contra", tint = MechaError, list = champion.counters, imageOf = ::imageOf)
    }
    if (champion.strongAgainst.isNotEmpty()) {
      MatchupGroup(title = "Forte contra", tint = TrendGreen, list = champion.strongAgainst, imageOf = ::imageOf)
    }
    if (champion.synergies.isNotEmpty()) {
      MatchupGroup(title = "Sinergia com", tint = MechaSecondary, list = champion.synergies, imageOf = ::imageOf)
    }
  }
}

@Composable
fun MatchupGroup(
  title: String,
  tint: androidx.compose.ui.graphics.Color,
  list: List<com.example.data.model.MatchupInfo>,
  imageOf: (String) -> String? = { null }
) {
  Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
    Text(
      text = title.uppercase(),
      fontSize = 11.sp,
      fontWeight = FontWeight.ExtraBold,
      color = tint,
      letterSpacing = 0.6.sp
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      list.forEach { m ->
        Column(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(MechaSurfaceContainerLow)
            .border(1.dp, tint.copy(alpha = 0.25f), RoundedCornerShape(10.dp))
            .padding(10.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          val img = imageOf(m.name)
          if (img != null) {
            coil.compose.SubcomposeAsyncImage(
              model = coil.request.ImageRequest.Builder(LocalContext.current)
                .data(img)
                .crossfade(true)
                .build(),
              contentDescription = m.name,
              modifier = Modifier
                .padding(bottom = 6.dp)
                .size(42.dp)
                .clip(RoundedCornerShape(10.dp)),
              loading = { Box(Modifier.size(42.dp).background(MechaSurfaceContainerHigh)) },
              error = { Box(Modifier.size(42.dp).background(MechaSurfaceContainerHigh)) }
            )
          }
          Text(
            text = m.name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MechaOnSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 14.sp
          )
          if (m.effectiveness > 0) {
            Text(
              text = "${m.effectiveness}% eficácia",
              fontSize = 10.sp,
              color = tint,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(top = 3.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
fun EquipmentItemCard(itemNumber: Int, item: EquipmentItem) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(MechaSurfaceContainerLow)
      .border(1.dp, MechaSecondary.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
      .padding(12.dp)
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      // Row 1: Number, Icon, Name + Category Badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Step badge
        Box(
          modifier = Modifier
            .size(26.dp)
            .clip(CircleShape)
            .background(MechaSurfaceContainerHigh),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "$itemNumber",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MechaSecondary
          )
        }

        // Icon box (imagem real do item quando disponível, senão ícone por categoria)
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MechaSurfaceContainerHigh),
          contentAlignment = Alignment.Center
        ) {
          val icon = when (item.iconSymbol) {
            "snowshoeing" -> Icons.Default.Snowshoeing
            "gavel" -> Icons.Default.Gavel
            "cyclone" -> Icons.Default.Cyclone
            "shield_with_heart", "shield" -> Icons.Default.Shield
            "colorize" -> Icons.Default.Colorize
            else -> Icons.Default.SportsMartialArts
          }
          if (item.imageUrl != null) {
            coil.compose.SubcomposeAsyncImage(
              model = coil.request.ImageRequest.Builder(LocalContext.current)
                .data(item.imageUrl)
                .crossfade(true)
                .build(),
              contentDescription = item.name,
              modifier = Modifier.size(38.dp),
              loading = {
                Icon(icon, null, tint = MechaSecondary, modifier = Modifier.size(20.dp))
              },
              error = {
                Icon(icon, null, tint = MechaSecondary, modifier = Modifier.size(20.dp))
              }
            )
          } else {
            Icon(
              imageVector = icon,
              contentDescription = null,
              tint = MechaSecondary,
              modifier = Modifier.size(20.dp)
            )
          }
        }

        // Texts
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Text(
            text = item.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = MechaOnSurface
          )
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(MechaSurfaceContainerHigh)
              .padding(horizontal = 7.dp, vertical = 2.dp)
          ) {
            Text(
              text = item.category,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = MechaSecondary
            )
          }
        }
      }

      // Row 2: Stats and full description
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 36.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
      ) {
        if (item.stats.isNotEmpty()) {
          Text(
            text = item.stats,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MechaPrimaryContainer
          )
        }
        Text(
          text = item.description,
          fontSize = 12.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          lineHeight = 16.sp
        )
      }
    }
  }
}

// ================= ARCANAS TAB CONTENT =================
@Composable
fun ArcanasTabContent(champion: Champion) {
  Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
    // Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "Conjunto de Arcanas Nível 150",
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          color = MechaPrimary
        )
        Text(
          text = "Distribuído especificamente para os atributos bases do ${champion.name}.",
          fontSize = 12.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
      Box(
        modifier = Modifier
          .clip(CircleShape)
          .background(MechaPrimaryContainer)
          .padding(horizontal = 8.dp, vertical = 3.dp)
      ) {
        Text(
          text = "30 PEDRAS",
          fontSize = 10.sp,
          fontWeight = FontWeight.ExtraBold,
          color = MechaOnPrimaryContainer
        )
      }
    }

    // The 3 Arcana Groups
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      champion.arcanas.forEach { arcana ->
        val dotColor = when (arcana.colorType) {
          "RED" -> MechaError
          "BLUE" -> MechaSecondary
          "PRO" -> MechaPrimaryContainer
          else -> MechaPrimaryContainer
        }
        val typeTitle = when (arcana.colorType) {
          "RED" -> "Cor Vermelha • Ataque"
          "BLUE" -> "Cor Azul • Mobilidade"
          "PRO" -> "Recomendada"
          else -> "Cor Verde • Perfuração"
        }

        // Card alinhado: coluna principal flexível, conteúdo longo vai para baixo
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MechaSurfaceContainerLow)
            .padding(14.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Ícone: imagem real da arcana (HOK Pro) ou círculo de cor
            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(dotColor.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              if (arcana.imageUrl != null) {
                coil.compose.SubcomposeAsyncImage(
                  model = coil.request.ImageRequest.Builder(LocalContext.current)
                    .data(arcana.imageUrl)
                    .crossfade(true)
                    .build(),
                  contentDescription = arcana.name,
                  modifier = Modifier.size(38.dp).clip(CircleShape),
                  loading = {
                    Box(Modifier.size(18.dp).clip(CircleShape).background(dotColor))
                  },
                  error = {
                    Box(Modifier.size(18.dp).clip(CircleShape).background(dotColor))
                  }
                )
              } else {
                Box(
                  modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(dotColor)
                )
              }
            }

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "${arcana.count}x ${arcana.name}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MechaOnSurface
              )
              Text(
                text = typeTitle,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 1.dp)
              )
              Text(
                text = arcana.perItemStat,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp)
              )
            }
          }

          if (arcana.totalHighlight.isNotBlank()) {
            Box(
              modifier = Modifier
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(MechaSurfaceContainerHigh)
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = arcana.totalHighlight,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = dotColor
              )
            }
          }
        }
      }
    }

    // Stat Summary Grid
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(MechaSurfaceContainerHigh)
        .padding(14.dp)
    ) {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Token,
            contentDescription = null,
            tint = MechaSecondary,
            modifier = Modifier.size(18.dp)
          )
          Text(
            text = "Resumo Total de Atributos das Arcanas",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MechaPrimary
          )
        }

        champion.arcanaStatsSummary.forEach { (label, value) ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = label,
              fontSize = 13.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = value,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = MechaPrimary
            )
          }
        }
      }
    }
  }
}

// ================= SKILLS TAB CONTENT =================
@Composable
fun SkillsTabContent(champion: Champion) {
  Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
    // Evolution priority
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .background(MechaSurfaceContainerLow)
        .padding(12.dp)
    ) {
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
            imageVector = Icons.Default.TrendingUp,
            contentDescription = null,
            tint = MechaPrimaryContainer,
            modifier = Modifier.size(18.dp)
          )
          Text(
            text = "Ordem de Evolução de Habilidades:",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
        Text(
          text = champion.skillPriority,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          color = MechaPrimaryContainer
        )
      }
    }

    // Skills list
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      champion.skills.forEach { skill ->
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MechaSurfaceContainerLow)
            .padding(14.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Box(
                  modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MechaSurfaceContainerHigh),
                  contentAlignment = Alignment.Center
                ) {
                  val icon = when (skill.iconSymbol) {
                    "waves" -> Icons.Default.Waves
                    "autorenew" -> Icons.Default.Autorenew
                    "thunderstorm" -> Icons.Default.Thunderstorm
                    else -> Icons.Default.Radar
                  }
                  Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (skill.isMaxPriority) MechaPrimaryContainer else MechaSecondary,
                    modifier = Modifier.size(18.dp)
                  )
                }

                Column {
                  Text(
                    text = skill.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MechaOnSurface
                  )
                  Text(
                    text = skill.subtitle,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              }

              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(if (skill.isMaxPriority) MechaPrimaryContainer else MechaSurfaceContainerHigh)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = skill.tag,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = if (skill.isMaxPriority) MechaOnPrimaryContainer else MechaSecondary
                )
              }
            }

            Text(
              text = skill.description,
              fontSize = 13.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              lineHeight = 18.sp
            )
          }
        }
      }
    }
  }
}

// ================= COMBOS TAB CONTENT =================
@Composable
fun CombosTabContent(champion: Champion) {
  Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
    // Header
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Icon(
        imageVector = Icons.Default.SportsMartialArts,
        contentDescription = null,
        tint = MechaSecondary,
        modifier = Modifier.size(20.dp)
      )
      Text(
        text = "Combos Competitivos (Passo a Passo)",
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        color = MechaPrimary
      )
    }

    // Step by step cards
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      champion.combos.forEach { combo ->
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MechaSurfaceContainerLow)
            .padding(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top
          ) {
            Box(
              modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(MechaSecondaryContainer),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "${combo.stepNumber}",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MechaSecondary
              )
            }

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = combo.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MechaOnSurface
              )
              Text(
                text = combo.description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp,
                modifier = Modifier.padding(top = 2.dp)
              )
            }
          }
        }
      }
    }

    // Pro Tips
    if (champion.proTips.isNotEmpty()) {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Default.TipsAndUpdates,
            contentDescription = null,
            tint = MechaPrimaryContainer,
            modifier = Modifier.size(18.dp)
          )
          Text(
            text = "Dicas de Mestre & Macetes",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = MechaPrimary
          )
        }

        champion.proTips.forEach { tip ->
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(MechaSurfaceContainerHigh)
              .padding(12.dp)
          ) {
            Column {
              Text(
                text = tip.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MechaSecondary
              )
              Text(
                text = tip.description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp,
                modifier = Modifier.padding(top = 2.dp)
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun WrHistoryCard(points: List<Pair<String, Double>>) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(MechaSurfaceContainerHigh)
      .padding(12.dp)
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "WR OFICIAL — ÚLTIMAS ATUALIZAÇÕES",
          fontSize = 10.sp,
          fontWeight = FontWeight.ExtraBold,
          color = MechaSecondary,
          letterSpacing = 0.5.sp
        )
        val first = points.first().second
        val last = points.last().second
        val delta = last - first
        Text(
          text = "${if (delta >= 0) "+" else ""}${"%.1f".format(delta)}%",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = if (delta >= 0) TrendGreen else MechaError
        )
      }
      androidx.compose.foundation.Canvas(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
      ) {
        val pts = points.map { it.second.toFloat() }
        val min = pts.min() - 0.5f
        val max = pts.max() + 0.5f
        val stepX = if (pts.size > 1) size.width / (pts.size - 1) else size.width
        val path = androidx.compose.ui.graphics.Path()
        pts.forEachIndexed { i, v ->
          val x = stepX * i
          val y = size.height - ((v - min) / (max - min)) * size.height
          if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
          drawCircle(color = MechaPrimaryContainer, radius = 3.dp.toPx(), center = androidx.compose.ui.geometry.Offset(x, y))
        }
        drawPath(path, color = MechaPrimaryContainer, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx()))
      }
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = points.first().first, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = "${"%.1f".format(points.first().second)}% → ${"%.1f".format(points.last().second)}%", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = points.last().first, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    }
  }
}
