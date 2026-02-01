package com.example.greetingcard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greetingcard.ui.theme.GreetingCardTheme
import androidx.core.net.toUri

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
                        }
                    )
                }
            }
        }
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
    modifier: Modifier = Modifier
) {
    val image = painterResource(R.drawable.businesscardpic)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = image,
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxWidth().height(190.dp),
            contentScale = ContentScale.Crop,
        )
        MainText(
            name = name,
            id = id,
            modifier = Modifier
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onStartExplicit,
            modifier = modifier.fillMaxWidth()
        ) {
            Text("Start Activity Explicitly")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = "Implicit Intent",
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onStartImplicit,
            modifier = modifier.fillMaxWidth()
        ) {
            Text("Start Activity Implicitly")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Implicit Intent",
            )
        }
    }

}
