package com.example.bottonnavbar.ui.theme

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bottonnavbar.R
import com.example.bottonnavbar.ui.theme.screens.*

@Composable
fun MainScreen() {

    val navItemList = listOf(
        NavItem(stringResource(R.string.home), Icons.Default.Home),
        NavItem(stringResource(R.string.favorites), Icons.Default.Favorite),
        NavItem(stringResource(R.string.email), Icons.Default.Email),
        NavItem(stringResource(R.string.profile), Icons.Default.Person),
        NavItem(stringResource(R.string.location), Icons.Default.LocationOn)
    )

    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    var fabExpanded by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        //  Oculta en Location
        bottomBar = {
            if (selectedIndex != 4) {
                NavigationBar {
                    navItemList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {

                                when (index) {

                                    // EMAIL
                                    2 -> {
                                        val intent = Intent(Intent.ACTION_SENDTO)
                                        intent.data = Uri.parse("mailto:")
                                        context.startActivity(intent)
                                    }

                                    // PROFILE → abrir navegador
                                    3 -> {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
                                        context.startActivity(intent)
                                    }

                                    // FAVORITES → marcador
                                    1 -> {
                                        Toast.makeText(context, "Favoritos", Toast.LENGTH_SHORT).show()
                                        selectedIndex = index
                                    }

                                    else -> {
                                        selectedIndex = index
                                    }
                                }
                            
                            },
                            icon = {
                                Icon(navItem.icon, contentDescription = navItem.label)
                            },
                            label = {
                                Text(navItem.label)
                            }
                        )
                    }
                }
            }
        },

        //  FAB MULTI
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                if (fabExpanded) {

                    // 🔹 Compartir (Intent)
                    FloatingActionButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "text/plain"
                            intent.putExtra(Intent.EXTRA_TEXT, "Hola desde mi app 🚀")

                            context.startActivity(
                                Intent.createChooser(intent, "Compartir")
                            )
                        }
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }

                    // 🔹 Idioma (Intent REAL)
                    FloatingActionButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.setClassName(
                                "com.android.settings",
                                "com.android.settings.LanguageSettings"
                            )
                            context.startActivity(intent)
                        }
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = "Language")
                    }
                }

                // 🔘 FAB principal
                FloatingActionButton(
                    onClick = { fabExpanded = !fabExpanded }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        }

    ) { innerPadding ->

        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            selectedIndex = selectedIndex
        )
    }
}

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int
) {
    when (selectedIndex) {
        0 -> HomeScreen(modifier)
        1 -> NotificationScreen(modifier)
        2 -> SettingsScreen(modifier)
        3 -> ProfileScreen(modifier)
        4 -> LocationScreen(modifier)
    }
}