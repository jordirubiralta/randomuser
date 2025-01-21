package com.jordirubiralta.feature.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jordirubiralta.feature.detail.model.UserDetailUIModel

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    user: UserDetailUIModel
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(user.imageUrl)
            .crossfade(true)
            .build()
        AsyncImage(
            model = imageRequest,
            contentDescription = "User Image",
            modifier = Modifier
                .padding(16.dp)
                .size(200.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "${user.name} ${user.surname}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Email: ${user.email}",
            style = MaterialTheme.typography.bodyLarge
        )
        user.phone?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Phone: $it",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        user.location?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Location: $it",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        user.gender?.let { gender ->
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Gender: ${gender.replaceFirstChar { it.uppercase() }}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        user.registeredDate?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Registered $it",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
