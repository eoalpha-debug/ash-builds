package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.data.model.Champion
import com.example.data.model.Lane
import com.example.ui.theme.MechaPrimary
import com.example.ui.theme.MechaPrimaryContainer
import com.example.ui.theme.MechaSecondary
import com.example.ui.theme.MechaSurfaceContainerHigh
import com.example.ui.theme.MechaSurfaceContainerLowest

@Composable
fun ChampionAvatar(
  champion: Champion,
  modifier: Modifier = Modifier,
  size: Dp = 48.dp,
  shape: Shape = RoundedCornerShape(10.dp)
) {
  val initials = champion.name.take(2).uppercase()
  val laneGradient = when (champion.lane) {
    Lane.TOP -> Brush.verticalGradient(listOf(Color(0xFFE57373), Color(0xFFC62828)))
    Lane.SELVA -> Brush.verticalGradient(listOf(Color(0xFF81C784), Color(0xFF2E7D32)))
    Lane.MEIO -> Brush.verticalGradient(listOf(Color(0xFF64B5F6), Color(0xFF1565C0)))
    Lane.ADC -> Brush.verticalGradient(listOf(Color(0xFFFFD54F), Color(0xFFF57F17)))
    Lane.SUPORTE -> Brush.verticalGradient(listOf(Color(0xFFBA68C8), Color(0xFF6A1B9A)))
    else -> Brush.verticalGradient(listOf(MechaSecondary, MechaPrimaryContainer))
  }

  Box(
    modifier = modifier
      .size(size)
      .clip(shape)
      .background(MechaSurfaceContainerHigh)
      .border(1.dp, MechaSecondary.copy(alpha = 0.2f), shape),
    contentAlignment = Alignment.Center
  ) {
    if (champion.localDrawableRes != null) {
      Image(
        painter = painterResource(id = champion.localDrawableRes),
        contentDescription = champion.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
      )
    } else {
      SubcomposeAsyncImage(
        model = champion.imageUrl,
        contentDescription = champion.name,
        contentScale = ContentScale.Crop,
        loading = {
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(laneGradient),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = initials,
              color = Color.White,
              fontSize = (size.value * 0.36f).sp,
              fontWeight = FontWeight.ExtraBold
            )
          }
        },
        error = {
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(laneGradient),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = initials,
              color = Color.White,
              fontSize = (size.value * 0.36f).sp,
              fontWeight = FontWeight.ExtraBold
            )
          }
        },
        modifier = Modifier.fillMaxSize()
      )
    }
  }
}
