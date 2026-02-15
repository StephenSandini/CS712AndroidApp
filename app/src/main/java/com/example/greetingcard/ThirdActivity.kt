package com.example.greetingcard

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.greetingcard.ui.theme.GreetingCardTheme

class ThirdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreetingCardTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ThirdActivityScreen()
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name="ThirdActivity"
)
@Composable
fun ThirdActivityPreview(){
    GreetingCardTheme{
        ThirdActivityPreview()
    }
}

@Composable
fun ThirdActivityScreen(modifier: Modifier=Modifier) {
    var capturedImage by remember {mutableStateOf<Bitmap?>(null)}

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) {bitmap: Bitmap? ->
        capturedImage = bitmap
    }

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {cameraLauncher.launch(null)},
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Capture Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (capturedImage != null) {
            Image(
                bitmap = capturedImage!!.asImageBitmap(),
                contentDescription = "Captured image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            )
        } else {
            Text("Try capturing an image.")
        }

    }

}