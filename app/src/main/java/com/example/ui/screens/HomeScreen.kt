package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.OfflineBolt
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.filled.Token
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.example.ui.theme.MechaOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.ui.theme.MechaErrorContainer
import com.example.ui.theme.MechaOnPrimary
import com.example.ui.theme.MechaOnPrimaryContainer
import com.example.ui.theme.MechaOnSurface
import com.example.ui.theme.MechaPrimary
import com.example.ui.theme.MechaPrimaryContainer
import com.example.ui.theme.MechaSecondary
import com.example.ui.theme.MechaSecondaryContainer
import com.example.ui.theme.MechaSurface
import com.example.ui.theme.MechaSurfaceContainer
import com.example.ui.theme.MechaSurfaceContainerHigh
import com.example.ui.theme.MechaSurfaceContainerHighest
import com.example.ui.theme.MechaSurfaceContainerLow
import com.example.ui.theme.MechaSurfaceContainerLowest
import com.example.ui.theme.TrendGreen
import com.example.ui.viewmodel.MetaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
  viewModel: MetaViewModel,
  onNavigateToChampionDetail: (String) -> Unit,
  onNavigateToOffline: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  val selectedLane by viewModel.selectedLane.collectAsState()
  val isSyncing by viewModel.isSyncing.collectAsState()
  val patchLabel by viewModel.patchLabel.collectAsState()

  val allChampions by viewModel.allChampions.collectAsState()
  val patchNotices by viewModel.patchNotices.collectAsState()

  val trendingChampions = allChampions.filter { it.tier == HeroTier.SS || it.tier == HeroTier.S }

  // Herói em destaque: maior win rate real do meta atual
  if (allChampions.isEmpty()) {
    // Tela de carregamento enquanto o cache local é lido (evita tela em branco)
    Column(
      modifier = modifier.fillMaxSize().background(MechaSurface),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      CircularProgressIndicator(color = MechaSecondary)
      Text(
        text = "Carregando dados do meta global...",
        fontSize = 13.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(top = 14.dp)
      )
    }
    return
  }
  val pinnedId by viewModel.featuredHeroId.collectAsState()
  val bannerMessage by viewModel.bannerMessage.collectAsState()
  // Herói em destaque: pin manual do painel admin ou maior WR do meta
  val featuredHero = allChampions.firstOrNull { it.id.equals(pinnedId, ignoreCase = true) }
    ?: allChampions.maxByOrNull {
      it.winRate.removeSuffix("%").replace(",", ".").toDoubleOrNull() ?: 0.0
    } ?: return

  var isSavedLocally by remember { mutableStateOf(false) }
  var isSavingGuide by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MechaSurface)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. CARROSSEL: HERÓIS EM ALTA NO META (Tier S & S+)
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
              tint = MechaSecondary,
              modifier = Modifier.size(20.dp)
            )
            Text(
              text = "Heróis em Alta no Meta",
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = MechaOnSurface
            )
          }
          Text(
            text = "Tier S & S+",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MechaSecondary
          )
        }

        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(trendingChampions) { champion ->
            HeroTrendingCard(
              champion = champion,
              onClick = { onNavigateToChampionDetail(champion.id) },
              onOpenBuild = { onNavigateToChampionDetail(champion.id) }
            )
          }
        }
      }
    }

    // 1.5 BANNER DE AVISO DO PAINEL ADMIN (quando configurado)
    if (!bannerMessage.isNullOrBlank()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MechaPrimaryContainer.copy(alpha = 0.15f))
            .border(1.dp, MechaPrimaryContainer.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
            .padding(12.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Icon(
              imageVector = Icons.Default.WorkspacePremium,
              contentDescription = null,
              tint = MechaPrimaryContainer,
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = bannerMessage!!,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = MechaPrimary
            )
          }
        }
      }
    }

    // 2. BANNER SUPERIOR: DESTAQUE DO PATCH
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(MechaSurfaceContainerHigh)
          .border(1.dp, MechaSecondary.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
          .padding(16.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp),
              modifier = Modifier
                .clip(CircleShape)
                .background(MechaPrimaryContainer)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Icon(
                imageVector = Icons.Default.WorkspacePremium,
                contentDescription = null,
                tint = MechaOnPrimaryContainer,
                modifier = Modifier.size(13.dp)
              )
              Text(
                text = "RANKED ${patchLabel.substringBefore(" • ").uppercase()} • GLOBAL",
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MechaOnPrimaryContainer
              )
            }

            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(6.dp)
                  .clip(CircleShape)
                  .background(MechaSecondary)
              )
              Text(
                text = "CALCULADO HOJE",
                color = MechaSecondary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Column {
            Text(
              text = "$patchLabel Meta Global",
              fontSize = 22.sp,
              fontWeight = FontWeight.Bold,
              color = MechaPrimary
            )
            Text(
              text = "Taxas de vitória pós-ajustes calibradas com os dados do servidor competitivo.",
              fontSize = 13.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }

    // 2. FILTRAR POR ROTA (Fast Chips)
    item {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "FILTRAR POR ROTA",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 0.5.sp
          )
          Text(
            text = "Deslize para ver mais",
            fontSize = 11.sp,
            color = MechaPrimary
          )
        }

        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(Lane.values()) { lane ->
            val isSelected = selectedLane == lane
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSelected) MechaPrimaryContainer else MechaSurfaceContainerHigh)
                .clickable { viewModel.selectLane(lane) }
                .padding(horizontal = 14.dp, vertical = 8.dp)
                .testTag("filter_lane_${lane.name}")
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Stars,
                  contentDescription = null,
                  tint = if (isSelected) MechaOnPrimaryContainer else MechaOnSurface,
                  modifier = Modifier.size(16.dp)
                )
                Text(
                  text = lane.chipShort,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) MechaOnPrimaryContainer else MechaOnSurface
                )
              }
            }
          }
        }
      }
    }

    // 3. MELHORES BUILDS DA SEMANA (Top 1 Challenger)
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
              imageVector = Icons.Default.MilitaryTech,
              contentDescription = null,
              tint = MechaPrimaryContainer,
              modifier = Modifier.size(20.dp)
            )
            Text(
              text = "Melhores Builds da Semana",
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = MechaOnSurface
            )
          }
          Text(
            text = "Top 1 Challenger",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        // Detailed Featured Build Card (Redesigned Pro Esport Card)
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MechaSurfaceContainer)
            .border(1.dp, MechaOutline.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            .padding(16.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Champion Header
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(62.dp)
                  .clip(RoundedCornerShape(14.dp))
                  .background(MechaSurfaceContainerLowest)
              ) {
                com.example.ui.components.ChampionAvatar(
                  champion = featuredHero,
                  size = 62.dp,
                  shape = RoundedCornerShape(14.dp),
                  modifier = Modifier.fillMaxSize()
                )
                Box(
                  modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(MechaSurfaceContainerLowest.copy(alpha = 0.9f))
                    .padding(vertical = 2.dp),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = featuredHero.lane.chipShort.uppercase(),
                    color = MechaSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold
                  )
                }
              }

              Column(modifier = Modifier.weight(1f)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = featuredHero.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MechaPrimary
                  )
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(6.dp))
                      .background(MechaPrimaryContainer)
                      .padding(horizontal = 8.dp, vertical = 3.dp)
                  ) {
                    Text(
                      text = "TIER ${featuredHero.tier.badge} • BUILD #1",
                      fontSize = 10.sp,
                      fontWeight = FontWeight.ExtraBold,
                      color = MechaOnPrimaryContainer
                    )
                  }
                }

                Text(
                  text = featuredHero.title,
                  fontSize = 12.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.padding(top = 1.dp)
                )

                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp),
                  modifier = Modifier.padding(top = 6.dp)
                ) {
                  // Spell badge
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(4.dp))
                      .background(MechaSurfaceContainerHigh)
                      .padding(horizontal = 6.dp, vertical = 2.dp)
                  ) {
                    Text(
                      text = featuredHero.spellName,
                      fontSize = 11.sp,
                      fontWeight = FontWeight.SemiBold,
                      color = MechaSecondary
                    )
                  }
                  // Win Rate badge
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(4.dp))
                      .background(Color(0xFF2E7D32).copy(alpha = 0.2f))
                      .padding(horizontal = 6.dp, vertical = 2.dp)
                  ) {
                    Text(
                      text = "${featuredHero.winRate} WR",
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold,
                      color = Color(0xFF81C784)
                    )
                  }
                  // Pick Rate badge
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(4.dp))
                      .background(MechaSurfaceContainerHigh)
                      .padding(horizontal = 6.dp, vertical = 2.dp)
                  ) {
                    Text(
                      text = "${featuredHero.pickRate} Pick",
                      fontSize = 11.sp,
                      color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                  }
                }
              }
            }

            // 6 Items Grid (Organized in 2 rows of 3 columns with full names and stats)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "ORDEM DE COMPRA DOS ITENS",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  letterSpacing = 0.5.sp
                )
                Text(
                  text = "Build Recomendada",
                  fontSize = 11.sp,
                  color = MechaSecondary,
                  fontWeight = FontWeight.SemiBold
                )
              }

              // Row 1 (Items 1 to 3)
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                featuredHero.items.take(3).forEachIndexed { index, item ->
                  val itemNumber = index + 1
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(10.dp))
                      .background(MechaSurfaceContainerHigh)
                      .border(1.dp, MechaOutline.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                      .padding(8.dp)
                  ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                      Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                      ) {
                        Box(
                          modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(MechaPrimaryContainer),
                          contentAlignment = Alignment.Center
                        ) {
                          Text(
                            text = "$itemNumber",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MechaOnPrimaryContainer
                          )
                        }
                        Text(
                          text = item.category,
                          fontSize = 9.sp,
                          color = MechaSecondary,
                          fontWeight = FontWeight.Bold
                        )
                      }
                      if (item.imageUrl != null) {
                        coil.compose.SubcomposeAsyncImage(
                          model = coil.request.ImageRequest.Builder(LocalContext.current)
                            .data(item.imageUrl)
                            .crossfade(true)
                            .build(),
                          contentDescription = item.name,
                          modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp)),
                          loading = {
                            Box(Modifier.fillMaxSize().background(MechaSurfaceContainer))
                          },
                          error = {
                            Box(Modifier.fillMaxSize().background(MechaSurfaceContainer))
                          }
                        )
                      }
                      Text(
                        text = item.name,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MechaOnSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 13.sp
                      )
                      Text(
                        text = item.stats,
                        fontSize = 9.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                      )
                    }
                  }
                }
              }

              // Row 2 (Items 4 to 6)
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                featuredHero.items.drop(3).take(3).forEachIndexed { index, item ->
                  val itemNumber = index + 4
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(10.dp))
                      .background(MechaSurfaceContainerHigh)
                      .border(1.dp, MechaOutline.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                      .padding(8.dp)
                  ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                      Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                      ) {
                        Box(
                          modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(MechaPrimaryContainer),
                          contentAlignment = Alignment.Center
                        ) {
                          Text(
                            text = "$itemNumber",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MechaOnPrimaryContainer
                          )
                        }
                        Text(
                          text = item.category,
                          fontSize = 9.sp,
                          color = MechaSecondary,
                          fontWeight = FontWeight.Bold
                        )
                      }
                      if (item.imageUrl != null) {
                        coil.compose.SubcomposeAsyncImage(
                          model = coil.request.ImageRequest.Builder(LocalContext.current)
                            .data(item.imageUrl)
                            .crossfade(true)
                            .build(),
                          contentDescription = item.name,
                          modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp)),
                          loading = {
                            Box(Modifier.fillMaxSize().background(MechaSurfaceContainer))
                          },
                          error = {
                            Box(Modifier.fillMaxSize().background(MechaSurfaceContainer))
                          }
                        )
                      }
                      Text(
                        text = item.name,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MechaOnSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 13.sp
                      )
                      Text(
                        text = item.stats,
                        fontSize = 9.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                      )
                    }
                  }
                }
              }
            }

            // Arcana Set Summary (Clean 3-row layout with full readable descriptions)
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MechaSurfaceContainerHigh)
                .border(1.dp, MechaOutline.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                .padding(12.dp)
            ) {
              Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
                      imageVector = Icons.Default.Token,
                      contentDescription = null,
                      tint = MechaSecondary,
                      modifier = Modifier.size(16.dp)
                    )
                    Text(
                      text = "Conjunto de Arcanas",
                      fontSize = 13.sp,
                      fontWeight = FontWeight.Bold,
                      color = MechaPrimary
                    )
                  }
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(4.dp))
                      .background(MechaSurfaceContainer)
                      .padding(horizontal = 6.dp, vertical = 2.dp)
                  ) {
                    Text(
                      text = "NÍVEL 150",
                      fontSize = 10.sp,
                      fontWeight = FontWeight.ExtraBold,
                      color = MechaSecondary
                    )
                  }
                }

                featuredHero.arcanas.forEach { arcana ->
                  val dotColor = when (arcana.colorType) {
                    "RED" -> Color(0xFFEF5350)
                    "BLUE" -> Color(0xFF42A5F5)
                    else -> Color(0xFF66BB6A)
                  }
                  Row(
                    modifier = Modifier
                      .fillMaxWidth()
                      .clip(RoundedCornerShape(8.dp))
                      .background(MechaSurfaceContainer)
                      .padding(horizontal = 10.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                    Row(
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                      if (arcana.imageUrl != null) {
                        coil.compose.SubcomposeAsyncImage(
                          model = coil.request.ImageRequest.Builder(LocalContext.current)
                            .data(arcana.imageUrl)
                            .crossfade(true)
                            .build(),
                          contentDescription = arcana.name,
                          modifier = Modifier.size(22.dp).clip(CircleShape),
                          loading = { Box(Modifier.size(10.dp).clip(CircleShape).background(dotColor)) },
                          error = { Box(Modifier.size(10.dp).clip(CircleShape).background(dotColor)) }
                        )
                      } else {
                        Box(
                          modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(dotColor)
                        )
                      }
                      Text(
                        text = "${arcana.count}x ${arcana.name}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MechaOnSurface
                      )
                    }
                    Text(
                      text = arcana.totalHighlight,
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Medium,
                      color = MechaSecondary
                    )
                  }
                }
              }
            }

            // Quick Microplay Rotation Tip
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MechaSurfaceContainerHigh)
                .border(1.dp, MechaOutline.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                .padding(12.dp)
            ) {
              Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Top
              ) {
                Icon(
                  imageVector = Icons.Default.TipsAndUpdates,
                  contentDescription = null,
                  tint = MechaPrimaryContainer,
                  modifier = Modifier.size(20.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                  Text(
                    text = "Dica Tática",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MechaPrimary
                  )
                  Text(
                    text = featuredHero.shortTip,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 17.sp
                  )
                }
              }
            }

            // Ação principal: Ver guia completo (botão em largura total, nada cortado)
            Button(
              onClick = { onNavigateToChampionDetail(featuredHero.id) },
              colors = ButtonDefaults.buttonColors(
                containerColor = MechaPrimaryContainer,
                contentColor = MechaOnPrimaryContainer
              ),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_view_full_guide")
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Text(
                  text = "Ver Guia Completo",
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp
                )
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                  contentDescription = null,
                  modifier = Modifier.size(16.dp)
                )
              }
            }

            OutlinedButton(
              onClick = {
                if (!isSavedLocally) {
                  isSavingGuide = true
                  coroutineScope.launch {
                    delay(500)
                    viewModel.toggleSaveChampion(featuredHero)
                    isSavingGuide = false
                    isSavedLocally = true
                    Toast.makeText(context, "Guia do ${featuredHero.name} salvo offline!", Toast.LENGTH_SHORT).show()
                  }
                } else {
                  onNavigateToOffline()
                }
              },
              colors = ButtonDefaults.outlinedButtonColors(
                contentColor = if (isSavedLocally) Color(0xFF81C784) else MechaOnSurface
              ),
              border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (isSavedLocally) Color(0xFF81C784) else MechaOutline.copy(alpha = 0.5f)
              ),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_save_offline_home")
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Icon(
                  imageVector = if (isSavedLocally) Icons.Default.CheckCircle else Icons.Default.BookmarkBorder,
                  contentDescription = null,
                  modifier = Modifier.size(16.dp)
                )
                Text(
                  text = if (isSavingGuide) "Salvando..." else if (isSavedLocally) "Salvo Offline" else "Salvar Offline",
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp
                )
              }
            }
          }
        }
      }
    }

    // 5. AVISOS DO PATCH 10.4 (Buffs & Nerfs)
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
              imageVector = Icons.Default.SwapVert,
              contentDescription = null,
              tint = MechaSecondary,
              modifier = Modifier.size(20.dp)
            )
            Text(
              text = "Mudanças da $patchLabel",
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = MechaOnSurface
            )
          }
          Text(
            text = "Notas do Servidor",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          var selectedNotice by remember { mutableStateOf<com.example.data.model.PatchHeroNotice?>(null) }
          patchNotices.forEach { notice ->
            val hero = allChampions.firstOrNull {
              it.name.equals(notice.heroName, ignoreCase = true)
            }
            PatchNoticeCard(notice = notice, champion = hero, onClick = { selectedNotice = notice })
          }

          // Card de detalhes da mudança (estilo nota oficial de patch)
          selectedNotice?.let { notice ->
            val hero = allChampions.firstOrNull { it.name.equals(notice.heroName, ignoreCase = true) }
            androidx.compose.material3.AlertDialog(
              onDismissRequest = { selectedNotice = null },
              confirmButton = {
                Button(
                  onClick = { selectedNotice = null },
                  colors = ButtonDefaults.buttonColors(
                    containerColor = MechaPrimaryContainer,
                    contentColor = MechaOnPrimaryContainer
                  )
                ) { Text("Entendi") }
              },
              containerColor = MechaSurfaceContainer,
              title = {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                  if (hero != null) {
                    com.example.ui.components.ChampionAvatar(
                      champion = hero,
                      size = 44.dp,
                      shape = RoundedCornerShape(10.dp)
                    )
                  }
                  Column {
                    Text(
                      text = notice.heroName,
                      fontWeight = FontWeight.Bold,
                      color = MechaPrimary,
                      fontSize = 17.sp
                    )
                    Text(
                      text = "${notice.kind} • ${notice.lane}",
                      fontSize = 11.sp,
                      color = when (notice.kind) {
                        "BUFF" -> MechaPrimaryContainer
                        "NERF" -> MechaError
                        else -> Color(0xFFEDB25A)
                      },
                      fontWeight = FontWeight.ExtraBold
                    )
                  }
                }
              },
              text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                  Text(
                    text = if (notice.details.isNotBlank()) notice.details else notice.description,
                    fontSize = 13.sp,
                    color = MechaOnSurface,
                    lineHeight = 18.sp
                  )
                  Text(
                    text = "Base: notas oficiais da $patchLabel (honorofkings.com/br)",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              }
            )
          }
        }
      }
    }

    // 6. BANNER DE SINCRONIZAÇÃO META OFFLINE
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(MechaSurfaceContainerHigh)
          .padding(16.dp)
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
            Box(
              modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MechaSecondaryContainer),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.OfflineBolt,
                contentDescription = null,
                tint = MechaSecondary,
                modifier = Modifier.size(22.dp)
              )
            }
            Column {
              Text(
                text = "Sincronização Meta Offline",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MechaPrimary
              )
              Text(
                text = "Guarde 120 builds para consulta durante a fase de picks.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }

          Button(
            onClick = {
              viewModel.syncMetaUpdates()
              Toast.makeText(context, "Sincronizando meta atualizado...", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(
              containerColor = MechaSurfaceContainer,
              contentColor = MechaSecondary
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(start = 8.dp)
          ) {
            Text(
              text = if (isSyncing) "Atualizando..." else "Atualizar",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
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
fun PatchMetricCard(
  title: String,
  value: String,
  valueColor: Color,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp))
      .background(MechaSurfaceContainer)
      .padding(8.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = title,
        fontSize = 9.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        letterSpacing = 0.3.sp
      )
      Text(
        text = value,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = valueColor,
        modifier = Modifier.padding(top = 2.dp)
      )
    }
  }
}

@Composable
fun HeroTrendingCard(
  champion: Champion,
  onClick: () -> Unit,
  onOpenBuild: () -> Unit
) {
  Box(
    modifier = Modifier
      .width(240.dp)
      .clip(RoundedCornerShape(14.dp))
      .background(MechaSurfaceContainerHigh)
      .clickable { onClick() }
      .padding(14.dp)
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        com.example.ui.components.ChampionAvatar(
          champion = champion,
          size = 48.dp,
          shape = RoundedCornerShape(10.dp)
        )

        Column(horizontalAlignment = Alignment.End) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(if (champion.tier == HeroTier.SS) MechaPrimaryContainer else MechaSurfaceContainer)
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "TIER ${champion.tier.badge}",
              fontSize = 10.sp,
              fontWeight = FontWeight.ExtraBold,
              color = if (champion.tier == HeroTier.SS) MechaOnPrimaryContainer else MechaSecondary
            )
          }
          Text(
            text = champion.lane.chipShort,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 2.dp)
          )
        }
      }

      Column {
        Text(
          text = champion.name,
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          color = MechaPrimary
        )
        Text(
          text = champion.title,
          fontSize = 12.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }

      // Win rate box
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(MechaSurfaceContainer)
          .padding(8.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "WIN RATE",
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = champion.winRate,
              fontSize = 16.sp,
              fontWeight = FontWeight.ExtraBold,
              color = MechaPrimaryContainer
            )
          }

        }
      }

      // Footer
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Core: ${champion.coreItemSummary}",
          fontSize = 11.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          modifier = Modifier.weight(1f)
        )

        Button(
          onClick = onOpenBuild,
          colors = ButtonDefaults.buttonColors(
            containerColor = MechaPrimaryContainer,
            contentColor = MechaOnPrimaryContainer
          ),
          shape = RoundedCornerShape(8.dp),
          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
          modifier = Modifier.height(30.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
          ) {
            Text(
              text = "Build",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = null,
              modifier = Modifier.size(14.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
fun PatchNoticeCard(
  notice: com.example.data.model.PatchHeroNotice,
  champion: com.example.data.model.Champion? = null,
  onClick: () -> Unit = {}
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(10.dp))
      .clickable { onClick() }
      .background(MechaSurfaceContainer)
      .padding(12.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.weight(1f)
      ) {
        if (champion != null) {
          // Imagem real do herói afetado pelo patch
          com.example.ui.components.ChampionAvatar(
            champion = champion,
            size = 40.dp,
            shape = RoundedCornerShape(10.dp)
          )
        } else {
          Box(
            modifier = Modifier
              .size(40.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(MechaSurfaceContainerHigh),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Security,
              contentDescription = null,
              tint = if (notice.isBuff) MechaSecondary else MechaError,
              modifier = Modifier.size(20.dp)
            )
          }
        }

        Column(modifier = Modifier.weight(1f)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = notice.heroName,
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
                text = notice.lane,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = MechaSecondary
              )
            }
          }
          Text(
            text = notice.description,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
          )
        }
      }

      val noticeColor = when (notice.kind) {
        "BUFF" -> MechaPrimaryContainer
        "NERF" -> MechaError
        else -> Color(0xFFEDB25A) // REAJUSTE = âmbar (balanceamento)
      }
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(6.dp))
          .background(
            when (notice.kind) {
              "BUFF" -> MechaSurfaceContainerHigh
              "NERF" -> MechaErrorContainer
              else -> Color(0xFF2E263C)
            }
          )
          .padding(horizontal = 8.dp, vertical = 4.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Icon(
            imageVector = when (notice.kind) {
              "BUFF" -> Icons.Default.ArrowUpward
              "NERF" -> Icons.Default.ArrowDownward
              else -> Icons.Default.SwapHoriz
            },
            contentDescription = null,
            tint = noticeColor,
            modifier = Modifier.size(13.dp)
          )
          Text(
            text = notice.kind,
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold,
            color = noticeColor
          )
        }
      }
    }
  }
}
