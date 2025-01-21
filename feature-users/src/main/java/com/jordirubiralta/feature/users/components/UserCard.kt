package com.jordirubiralta.feature.users.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.jordirubiralta.feature.users.R
import com.jordirubiralta.feature.users.model.UserUIModel

@Composable
fun UserCard(
    uiModel: UserUIModel,
    onRowClicked: (String) -> Unit,
    onRemoveClicked: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User Image
            AsyncImage(
                model = uiModel.imageUrl ?: "https://via.placeholder.com/150",
                contentDescription = "User Image",
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.image_placeholder)
            )
            Column {
                // Name and Surname
                Text(
                    text = "${uiModel.name} ${uiModel.surname}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                // Email
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = uiModel.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                // Phone
                uiModel.phone?.takeIf { it.isNotBlank() }?.let { phone ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = phone,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { onRemoveClicked.invoke(uiModel.email) },
                modifier = Modifier.size(24.dp),
                content = {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(R.drawable.close),
                        contentDescription = stringResource(R.string.close),
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun UserCardPreview(
    @PreviewParameter(UserCardPreviewParams::class) previewParams: UserUIModel
) {
    UserCard(
        uiModel = previewParams,
        onRowClicked = {},
        onRemoveClicked = {}
    )
}
