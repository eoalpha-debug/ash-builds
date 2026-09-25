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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.FolderZip
import androidx.compose.material.icons.filled.OfflinePin
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.local.SavedGuideEntity
import com.example.ui.theme.MechaError
import com.example.ui.theme.MechaOnPrimaryContainer
import com.example.ui.theme.MechaOnSurface
import com.example.ui.theme.MechaOutline
import com.example.ui.theme.MechaPrimary
import com.example.ui.theme.MechaPrimaryContainer
import com.example.ui.theme.MechaSecondary
import com.example.ui.theme.MechaSecondaryContainer
import com.example.ui.theme.MechaSurface
import com.example.ui.theme.MechaSurfaceContainer
import com.example.ui.theme.MechaSurfaceContainerHigh
import com.example.ui.theme.MechaSurfaceContainerHighest
import com.example.ui.theme.MechaSurfaceContainerLow
import com.example.ui.theme.TrendGreen
import com.example.ui.viewmodel.MetaViewModel

@Composable
fun OfflineModeScreen(
  viewModel: MetaViewModel,
  onOpenGuide: (String) -> Unit,
  onNavigateToCatalog: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val savedGuides by viewModel.savedGuides.collectAsState()
  val isOfflineModeActive by viewModel.isOfflineModeActive.collectAsState()
  val autoDownload by viewModel.autoDownloadTopTiers.collectAsState()
  val dataSaver by viewModel.dataSaverMode.collectAsState()
  val isSyncing by viewModel.isSyncing.collectAsState()
  val syncMessage by viewModel.syncMessage.collectAsState()
  val allChampions by viewModel.allChampions.collectAsState()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MechaSurface)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. STATUS CARD
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
                .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(6.dp)
                  .clip(CircleShape)
                  .background(TrendGreen)
              )
              Text(
                text = "STATUS: MODO OFFLINE ATIVO",
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MechaOnPrimaryContainer
              )
            }

            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                imageVector = Icons.Default.WifiOff,
                contentDescription = null,
                tint = MechaSecondary,
                modifier = Modifier.size(14.dp)
              )
              Text(
                text = "SEM CONEXÃO",
                color = MechaSecondary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Text(
            text = "Guias e Arcanas Salvos",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MechaPrimary
          )

          Text(
            text = "Acesso instantâneo a todos os builds, feitiços e cálculos salvos no armazenamento local sem gastar dados móveis.",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          // Storage info box
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(MechaSurfaceContainer)
              .padding(12.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = "ARMAZENAMENTO LOCAL EM USO",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  letterSpacing = 0.5.sp
                )
                Text(
                  text = "${(savedGuides.size * 3.8).toInt() + 4}.2 MB",
                  fontSize = 20.sp,
                  fontWeight = FontWeight.Bold,
                  color = MechaPrimary
                )
              }

              Column(horizontalAlignment = Alignment.End) {
                Text(
                  text = "${savedGuides.size} Builds Completas",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = MechaSecondary
                )
                val patchLabel by viewModel.patchLabel.collectAsState()
                Text(
                  text = "$patchLabel Salvo",
                  fontSize = 11.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }
      }
    }

    // 2. TOGGLES DE SINCRONIZAÇÃO
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
          text = "CONFIGURAÇÕES DO MODO OFFLINE",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          letterSpacing = 0.5.sp
        )

        // Switch 1: Auto Download Top Tiers
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MechaSurfaceContainerLow)
            .padding(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Auto-Download Top Tiers (SS & S)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MechaPrimary
              )
              Text(
                text = "Baixar automaticamente builds dos heróis dominantes quando conectado ao Wi-Fi.",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp)
              )
            }

            Switch(
              checked = autoDownload,
              onCheckedChange = { viewModel.toggleAutoDownload() },
              colors = SwitchDefaults.colors(
                checkedThumbColor = MechaPrimary,
                checkedTrackColor = MechaPrimaryContainer,
                uncheckedThumbColor = MechaOutline,
                uncheckedTrackColor = MechaSurfaceContainerHighest
              ),
              modifier = Modifier.testTag("switch_auto_download")
            )
          }
        }

        // Switch 2: Economia de Dados Móveis
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MechaSurfaceContainerLow)
            .padding(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Economia de Dados Móveis",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MechaPrimary
              )
              Text(
                text = "Restringir atualizações de dados e imagens apenas quando houver Wi-Fi.",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp)
              )
            }

            Switch(
              checked = dataSaver,
              onCheckedChange = { viewModel.toggleDataSaver() },
              colors = SwitchDefaults.colors(
                checkedThumbColor = MechaPrimary,
                checkedTrackColor = MechaPrimaryContainer,
                uncheckedThumbColor = MechaOutline,
                uncheckedTrackColor = MechaSurfaceContainerHighest
              ),
              modifier = Modifier.testTag("switch_data_saver")
            )
          }
        }
      }
    }

    // 3. LISTA DE GUIAS SALVOS
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "GUIAS PRONTOS PARA USO OFFLINE",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          letterSpacing = 0.5.sp
        )
        Text(
          text = "${savedGuides.size} heróis",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = MechaSecondary
        )
      }
    }

    if (savedGuides.isEmpty()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MechaSurfaceContainerLow)
            .padding(24.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Icon(
              imageVector = Icons.Default.CloudDownload,
              contentDescription = null,
              tint = MechaSecondary,
              modifier = Modifier.size(36.dp)
            )
            Text(
              text = "Nenhum guia salvo offline",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = MechaPrimary
            )
            Text(
              text = "Abra qualquer campeão e clique em 'Salvar Guia Offline' para consultar durante partidas sem sinal.",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Button(
              onClick = onNavigateToCatalog,
              colors = ButtonDefaults.buttonColors(
                containerColor = MechaPrimaryContainer,
                contentColor = MechaOnPrimaryContainer
              ),
              shape = RoundedCornerShape(8.dp)
            ) {
              Text("Explorar Campeões", fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    } else {
      items(savedGuides) { guide ->
        SavedGuideCard(
          guide = guide,
          champion = allChampions.firstOrNull { it.id.equals(guide.championId, ignoreCase = true) },
          onOpen = { onOpenGuide(guide.championId) },
          onDelete = {
            viewModel.removeSavedGuide(guide.championId)
            Toast.makeText(context, "Guia do ${guide.championName} removido.", Toast.LENGTH_SHORT).show()
          }
        )
      }
    }

    // 4. ACTION BUTTONS (SINCRONIZAR E LIMPAR CACHE)
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(
          onClick = {
            viewModel.syncMetaUpdates()
            Toast.makeText(context, "Sincronizando dados do meta global...", Toast.LENGTH_SHORT).show()
          },
          colors = ButtonDefaults.buttonColors(
            containerColor = MechaPrimaryContainer,
            contentColor = MechaOnPrimaryContainer
          ),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("btn_sync_offline_meta")
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            if (isSyncing) {
              CircularProgressIndicator(
                color = MechaOnPrimaryContainer,
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp
              )
            } else {
              Icon(
                imageVector = Icons.Default.Sync,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
              )
            }
            Text(
              text = if (isSyncing) "Sincronizando Meta..." else "Sincronizar Atualizações do Meta",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        OutlinedButton(
          onClick = {
            viewModel.clearOldCache {
              Toast.makeText(context, "Cache e builds desatualizadas limpos com sucesso!", Toast.LENGTH_SHORT).show()
            }
          },
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .testTag("btn_clear_old_cache")
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.DeleteOutline,
              contentDescription = null,
              tint = MechaSecondary,
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = "Limpar Cache Antigo",
              fontSize = 13.sp,
              color = MechaSecondary,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp),
          horizontalArrangement = Arrangement.Center
        ) {
          Text(
            text = syncMessage,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Composable
fun SavedGuideCard(
  guide: SavedGuideEntity,
  champion: com.example.data.model.Champion? = null,
  onOpen: () -> Unit,
  onDelete: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(MechaSurfaceContainerLow)
      .padding(12.dp)
      .testTag("saved_guide_${guide.championId}")
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (champion != null) {
          com.example.ui.components.ChampionAvatar(
            champion = champion,
            size = 52.dp,
            shape = RoundedCornerShape(10.dp)
          )
        } else {
          Box(
            modifier = Modifier
              .size(52.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(MechaSurfaceContainerHigh)
          ) {
            AsyncImage(
              model = guide.imageUrl,
              contentDescription = guide.championName,
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )
          }
        }

        Column(modifier = Modifier.weight(1f)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = guide.championName,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = MechaPrimary
            )
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(MechaSurfaceContainerHigh)
                .padding(horizontal = 5.dp, vertical = 1.dp)
            ) {
              Text(
                text = "${guide.heroClass} • ${guide.lane}",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MechaSecondary
              )
            }
          }

          Text(
            text = run {
              val lines = mutableListOf(guide.buildSummary)
              if (guide.itemsText.isNotBlank()) lines += "🎒 " + guide.itemsText.split("|").take(3).joinToString(", ")
              if (guide.arcanasText.isNotBlank()) lines += "🔯 " + guide.arcanasText.split("|").take(2).joinToString(", ")
              if (guide.spellName.isNotBlank()) lines += "✨ " + guide.spellName
              lines.joinToString("\n")
            },
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(top = 2.dp)
          ) {
            Text(
              text = "WR ${guide.winRate}",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = MechaPrimary
            )
            Text(
              text = "•",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = "Tamanho: ${guide.cacheSize}",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        IconButton(
          onClick = onDelete,
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(MechaSurfaceContainerHigh)
        ) {
          Icon(
            imageVector = Icons.Default.DeleteOutline,
            contentDescription = "Remover",
            tint = MechaError,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      Button(
        onClick = onOpen,
        colors = ButtonDefaults.buttonColors(
          containerColor = MechaSurfaceContainerHigh,
          contentColor = MechaSecondary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(36.dp)
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
          Text(text = "Consultar Guia Completo", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
