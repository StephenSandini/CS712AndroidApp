package com.example.greetingcard

import android.content.Intent
import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
//region Foundation
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
//endregion Foundation
//region Material Icons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Face
//endregion Material Icons
//region Material 3
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
//endregion Material 3
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
//region UI
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//endregion UI
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.greetingcard.ui.theme.GreetingCardTheme


class MainActivity : ComponentActivity() {
    private lateinit var myReceiver: MyBroadcastReceiver
    companion object {
        const val MY_CUSTOM_ACTION = "com.example.MY_CUSTOM_ACTION"
    }
    @SuppressLint("UnspecifiedRegisterReceiverFlag", "UnsafeImplicitIntentLaunch")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myReceiver = MyBroadcastReceiver()
        val filter = IntentFilter(MY_CUSTOM_ACTION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(myReceiver, filter, RECEIVER_NOT_EXPORTED)
        } else {
            @Suppress("DEPRECATION")
            registerReceiver(myReceiver, filter)
        }
        enableEdgeToEdge()
        setContent {

            val notificationPermissionLauncher =
                rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()){}
            LaunchedEffect(Unit) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    notificationPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }
            }

            GreetingCardTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    MainActivityScreen(
                        name = stringResource(R.string.name_text),
                        id = stringResource(R.string.id_text),
                        onStartExplicit = {
                            val intent = Intent(this, SecondActivity::class.java)
                            startActivity(intent)
                        },
                        onStartImplicit = {
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = "app://challenges".toUri()
                            }
                            startActivity(intent)
                        },
                         onSendBroadcast = {
                             val intent = Intent(MY_CUSTOM_ACTION).apply {
                                 setPackage(packageName)
                             }
                            sendBroadcast(intent)
                        },
                        onStartThirdActivity = {
                            val intent = Intent(this, ThirdActivity::class.java)
                            startActivity(intent)
                        },
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myReceiver)
    }
}

@Composable
fun MainText(
    name: String,
    id: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(24.dp)
    ) {
        Text(text = name, fontSize = 25.sp, color = Color.Black, fontWeight = FontWeight(800))
        Text(text = id, fontSize = 25.sp, color = Color.Black, fontWeight = FontWeight(800))
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "My Preview"
)
@Composable
fun GreetingPreview() {
    GreetingCardTheme {
        MainActivityScreen(
            "Name: Joe Doe",
            "ID: 1234567",
            onStartExplicit = {},
            onStartImplicit = {},
            onSendBroadcast = {},
            onStartThirdActivity = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun MainActivityScreen(
    name: String,
    id: String,
    onStartExplicit: () -> Unit,
    onStartImplicit: () -> Unit,
    onSendBroadcast: () -> Unit,
    onStartThirdActivity: () -> Unit,
    modifier: Modifier = Modifier
) {
    val image = painterResource(R.drawable.businesscardpic)
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = image,
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp),
            contentScale = ContentScale.Crop,
        )
        MainText(
            name = name,
            id = id,
            modifier = Modifier
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))
        //region Second Assignment
        Row {
            Button(
                onClick = onStartExplicit,
                modifier = Modifier.weight(1f)
            ) {
                Text("Start Activity Explicitly",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill=true)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "Implicit Intent",
                    tint = Color.White
                )
            }
            Button(
                onClick = onStartImplicit,
                modifier = Modifier.weight(1f)
            ) {
                Text("Start Activity Implicitly",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill=true)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Implicit Intent",
                    tint = Color.White
                )
            }
        }
        //endregion Second Assignment
        //region Third Assignment
        Row {
            Button(
                onClick = {
                    val intent = Intent(context, ForegroundService::class.java)
                    ContextCompat.startForegroundService(context, intent)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Start Service",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill=true)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Start Service",
                    tint = Color.White
                )
            }
            Button(
                onClick = onSendBroadcast,
                modifier = Modifier.weight(1f)
            ) {
                Text("Send Broadcast",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill=true)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send Broadcast",
                    tint = Color.White
                )
            }
        }
        //endregion Third Assignment
        //region Fifth Assignment
        Row {
            Button(
                onClick = onStartThirdActivity,
                modifier = Modifier.weight(1f)
            ) {
                Text("View Image Activity",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill=true)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Image Activity",
                    tint = Color.White
                )
            }
        }
        //endregion Fifth Assignment


    }

}
