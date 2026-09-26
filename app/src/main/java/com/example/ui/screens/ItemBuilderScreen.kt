package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.meta.ItemJson
import com.example.ui.theme.MechaError
import com.example.ui.theme.MechaOnSurface
import com.example.ui.theme.MechaOnPrimaryContainer
import com.example.ui.theme.MechaPrimary
import com.example.ui.theme.MechaPrimaryContainer
import com.example.ui.theme.MechaSecondary
import com.example.ui.theme.MechaSurface
import com.example.ui.theme.MechaSurfaceContainer
import com.example.ui.theme.MechaSurfaceContainerHigh
import com.example.ui.theme.MechaSurfaceContainerLow
import com.example.ui.viewmodel.MetaViewModel

@Composable
fun ItemBuilderScreen(
  viewModel: MetaViewModel,
  modifier: Modifier = Modifier
) {
  val allItems by viewModel.allItems.collectAsState()
  val savedBuilds by viewModel.customBuilds.collectAsState()
  val allChampions by viewModel.allChampions.collectAsState()
  var build by remember { mutableStateOf<List<ItemJson>>(emptyList()) }
  var showSaveDialog by remember { mutableStateOf(false) }
  var buildName by remember { mutableStateOf("") }
  var pickedChampion by remember { mutableStateOf<com.example.data.model.Champion?>(null) }
  var openedBuild by remember { mutableStateOf<com.example.data.local.CustomBuildEntity?>(null) }
  val context = LocalContext.current

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MechaSurface)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Column(modifier = Modifier.padding(top = 4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          Icon(Icons.Default.Build, null, tint = MechaSecondary, modifier = Modifier.size(22.dp))
          Text("Build Livre", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MechaPrimary)
        }
        Text(
          "Monte sua build de 6 itens reais. Toque para adicionar/remover.",
          fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // Build atual
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(14.dp))
          .background(MechaSurfaceContainer)
          .padding(12.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("SUA BUILD (${build.size}/6)", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = MechaSecondary, letterSpacing = 0.6.sp)
            Row {
              if (build.isNotEmpty()) {
                // SALVAR com nome (inventario)
                TextButton(onClick = { buildName = ""; showSaveDialog = true }) {
                  Text("Salvar build", fontWeight = FontWeight.Bold, color = MechaPrimaryContainer, fontSize = 13.sp)
                }
                IconButton(onClick = { build = emptyList() }) {
                  Icon(Icons.Default.Delete, "Limpar", tint = MechaError, modifier = Modifier.size(18.dp))
                }
              }
            }
          }
          Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
            repeat(6) { i ->
              Box(
                modifier = Modifier
                  .size(44.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (i < build.size) MechaSurfaceContainerHigh else MechaSurfaceContainerLow),
                contentAlignment = Alignment.Center
              ) {
                if (i < build.size) {
                  coil.compose.SubcomposeAsyncImage(
                    model = coil.request.ImageRequest.Builder(LocalContext.current)
                      .data("https://hokstats.gg/items/${build[i].itemId}.png")
                      .crossfade(true).build(),
                    contentDescription = build[i].name,
                    modifier = Modifier.size(40.dp),
                    loading = { Text("${i + 1}", color = MechaOnSurface, fontSize = 11.sp) },
                    error = { Text(build[i].name.take(2), color = MechaOnSurface, fontSize = 11.sp) }
                  )
                } else {
                  Text("${i + 1}", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                }
              }
            }
          }
        }
      }
    }

    // Minhas builds salvas
    if (savedBuilds.isNotEmpty()) {
      item {
        Text(
          "MINHAS BUILDS SALVAS (${savedBuilds.size})",
          fontSize = 11.sp,
          fontWeight = FontWeight.ExtraBold,
          color = MechaError,
          letterSpacing = 0.6.sp
        )
      }
      items(savedBuilds) { sb ->
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MechaSurfaceContainerLow)
            .clickable { openedBuild = sb }
            .padding(12.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
              Text(sb.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MechaPrimary)
              Text(
                (if (sb.championName.isNotBlank()) sb.championName + " • " else "") +
                  (sb.itemNames.split("|").take(3).joinToString(" • ") + "…"),
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
            }
            IconButton(onClick = { viewModel.deleteCustomBuild(sb.id) }) {
              Icon(Icons.Default.Delete, "Excluir", tint = MechaError, modifier = Modifier.size(18.dp))
            }
          }
        }
      }
    }

    // Grade de itens
    item {
      Text(
        "TODOS OS ITENS (${allItems.size})",
        fontSize = 11.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        letterSpacing = 0.6.sp
      )
    }
    items(allItems.chunked(4)) { rowItems ->
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        rowItems.forEach { item ->
          val inBuild = build.any { it.itemId == item.itemId }
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(if (inBuild) MechaPrimaryContainer.copy(alpha = 0.25f) else MechaSurfaceContainerLow)
              .clickable {
                build = if (inBuild) build.filterNot { it.itemId == item.itemId }
                else if (build.size < 6) build + item
                else build
              }
              .padding(vertical = 8.dp)
          ) {
            coil.compose.SubcomposeAsyncImage(
              model = coil.request.ImageRequest.Builder(LocalContext.current)
                .data("https://hokstats.gg/items/${item.itemId}.png")
                .crossfade(true).build(),
              contentDescription = item.name,
              modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)),
              loading = { Box(Modifier.size(40.dp).background(MechaSurfaceContainerHigh)) },
              error = { Box(Modifier.size(40.dp).background(MechaSurfaceContainerHigh)) }
            )
            Text(
              text = item.name,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              color = MechaOnSurface,
              maxLines = 2,
              lineHeight = 11.sp,
              modifier = Modifier.padding(top = 4.dp)
            )
          }
        }
        repeat(4 - rowItems.size) { Box(modifier = Modifier.weight(1f)) }
      }
    }
  }

  // â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€ DIÃLOGO: SALVAR BUILD â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
  if (showSaveDialog) {
    androidx.compose.material3.AlertDialog(
      onDismissRequest = { showSaveDialog = false },
      containerColor = MechaSurfaceContainer,
      title = { Text("Salvar Build", fontWeight = FontWeight.Bold, color = MechaPrimary) },
      text = {
        Column(
          verticalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
          Text("Nome da build", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MechaSecondary)
          OutlinedTextField(
            value = buildName,
            onValueChange = { buildName = it },
            placeholder = { Text("Ex.: Build do meu Lam jungle", fontSize = 12.sp) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = MechaSecondary,
              focusedTextColor = MechaOnSurface,
              unfocusedTextColor = MechaOnSurface
            ),
            modifier = Modifier.fillMaxWidth()
          )
          Text(
            if (pickedChampion == null) "Campeão (opcional)" else "Campeão: ${pickedChampion!!.name}",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MechaSecondary
          )
          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            items(allChampions, key = { it.id }) { champ ->
              Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                  .clip(RoundedCornerShape(10.dp))
                  .background(if (pickedChampion?.id == champ.id) MechaPrimaryContainer.copy(alpha = 0.3f) else MechaSurfaceContainerHigh)
                  .clickable { pickedChampion = if (pickedChampion?.id == champ.id) null else champ }
                  .padding(horizontal = 6.dp, vertical = 6.dp)
              ) {
                coil.compose.SubcomposeAsyncImage(
                  model = coil.request.ImageRequest.Builder(LocalContext.current)
                    .data(champ.imageUrl).crossfade(true).build(),
                  contentDescription = champ.name,
                  modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)),
                  loading = { Box(Modifier.size(40.dp).background(MechaSurfaceContainerHigh)) },
                  error = { Box(Modifier.size(40.dp).background(MechaSurfaceContainerHigh)) }
                )
                Text(champ.name, fontSize = 8.sp, color = MechaOnSurface, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(top = 3.dp))
              }
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.saveCustomBuild(buildName, pickedChampion, build)
            showSaveDialog = false
            pickedChampion = null
            Toast.makeText(context, "Build salva!", Toast.LENGTH_SHORT).show()
          },
          colors = ButtonDefaults.buttonColors(
            containerColor = MechaPrimaryContainer,
            contentColor = MechaOnPrimaryContainer
          ),
          enabled = buildName.isNotBlank()
        ) { Text("Salvar") }
      },
      dismissButton = {
        TextButton(onClick = { showSaveDialog = false }) { Text("Cancelar") }
      }
    )
  }

  // â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€ DIÃLOGO: CARD GRANDE DA BUILD SALVA â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
  openedBuild?.let { sb ->
    val ids = sb.itemIds.split("|")
    val names = sb.itemNames.split("|")
    androidx.compose.material3.AlertDialog(
      onDismissRequest = { openedBuild = null },
      containerColor = MechaSurfaceContainer,
      title = {
        Column {
          Text(sb.name, fontWeight = FontWeight.Bold, color = MechaPrimary, fontSize = 17.sp)
          Text(
            (if (sb.championName.isNotBlank()) sb.championName + " • " else "") + "${ids.size} itens na ordem de compra",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          ids.forEachIndexed { i, id ->
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
              Box(
                modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(MechaSurfaceContainerHigh),
                contentAlignment = Alignment.Center
              ) {
                coil.compose.SubcomposeAsyncImage(
                  model = coil.request.ImageRequest.Builder(LocalContext.current)
                    .data("https://hokstats.gg/items/$id.png")
                    .crossfade(true).build(),
                  contentDescription = names.getOrNull(i) ?: id,
                  modifier = Modifier.size(36.dp),
                  loading = { Text("${i + 1}", color = MechaOnSurface, fontSize = 11.sp) },
                  error = { Text(names.getOrNull(i)?.take(2) ?: "${i + 1}", color = MechaOnSurface, fontSize = 11.sp) }
                )
              }
              Text(
                text = "${i + 1}. ${names.getOrNull(i) ?: id}",
                fontSize = 14.sp,
                color = MechaOnSurface,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = { openedBuild = null },
          colors = ButtonDefaults.buttonColors(
            containerColor = MechaPrimaryContainer,
            contentColor = MechaOnPrimaryContainer
          )
        ) { Text("Fechar") }
      }
    )
  }
}

