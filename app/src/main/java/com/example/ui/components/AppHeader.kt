package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.MechaOnPrimary
import com.example.ui.theme.MechaOnSurface
import com.example.ui.theme.MechaPrimary
import com.example.ui.theme.MechaPrimaryContainer
import com.example.ui.theme.MechaSecondary
import com.example.ui.theme.MechaSurface
import com.example.ui.theme.MechaSurfaceContainerHigh
import com.example.ui.theme.MechaSurfaceContainerHighest

@Composable
fun AppHeader(
  screenSubtitle: String,
  onBackClick: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .background(MechaSurface.copy(alpha = 0.95f))
      .statusBarsPadding()
      .height(88.dp)
      .padding(horizontal = 20.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      if (onBackClick != null) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MechaSurfaceContainerHigh)
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
            contentDescription = "Voltar",
            tint = MechaOnSurface,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      Image(
        painter = painterResource(id = R.drawable.app_icon_logo),
        contentDescription = "Logo HoK MetaLab",
        modifier = Modifier
          .size(38.dp)
          .clip(RoundedCornerShape(10.dp))
      )

      Column(verticalArrangement = Arrangement.Center) {
        Text(
          text = if (onBackClick != null) "Detalhes Do Campeão" else "Ash Builds",
          color = MechaPrimary,
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          lineHeight = 20.sp
        )
        Text(
          text = screenSubtitle,
          color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium
        )
      }
    }

    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(0.dp),
      modifier = Modifier
    ) {
      // Online indicator pill (alinhado totalmente à direita)
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
          .clip(CircleShape)
          .background(MechaSurfaceContainerHighest)
          .padding(horizontal = 10.dp, vertical = 5.dp)
      ) {
        Box(
          modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(MechaPrimaryContainer)
        )
        Text(
          text = "ONLINE",
          color = MechaOnSurface,
          fontSize = 10.sp,
          fontWeight = FontWeight.ExtraBold,
          letterSpacing = 0.6.sp
        )
      }
    }
  }
}
