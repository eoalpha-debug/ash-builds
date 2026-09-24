package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.HeroTier
import com.example.data.model.MockMetaDatabase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun readStringFromContext() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Ash Builds", appName)
  }

  @Test
  fun testChampionDatabaseNotEmpty() {
    val champions = MockMetaDatabase.allChampions
    assertTrue("A lista de campeões não deve estar vazia", champions.isNotEmpty())
    
    val lam = MockMetaDatabase.getChampionById("lam")
    assertNotNull("Lam deve existir no banco de dados", lam)
    assertEquals(HeroTier.SS, lam?.tier)
    assertEquals(6, lam?.items?.size)
    assertEquals(3, lam?.arcanas?.size)
  }
}
