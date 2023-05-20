package com.example.jetaudio.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetaudio.R
import com.example.jetaudio.ui.theme.JetAudioTheme

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(50.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.pas_foto),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth().size(250.dp)
        )

        Text(
            text = stringResource(id = R.string.name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold
            )

        )

        Text(
            text = stringResource(id = R.string.email),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Normal
            )
        )

    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    JetAudioTheme {
        AboutScreen()
    }
}