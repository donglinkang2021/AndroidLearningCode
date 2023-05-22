package com.linkdom.learningcompose2

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Chip
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun TopicChip(
    topic: Topic,
    selected: Boolean,
) {
    val radius by animateDpAsState(
        if (selected) {
            20.dp
        } else {
            0.dp
        }
    )
    Card(
        shape = RoundedCornerShape(
            topStart = radius,
//            topEnd = radius,
//            bottomStart = radius,
//            bottomEnd = radius
        ),
    ){
        Row{
            Box{
//                Image(
//                    painter = rememberImagePainter(topic.imageUrl),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(width = 72.dp, height = 72.dp)
//                        .aspectRatio(1f)
//                )
                if (selected){
                    Icon(Icons.Filled.Done, contentDescription = null, modifier = Modifier.fillMaxSize())
                }
            }

            Text(
                text = topic.name,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}