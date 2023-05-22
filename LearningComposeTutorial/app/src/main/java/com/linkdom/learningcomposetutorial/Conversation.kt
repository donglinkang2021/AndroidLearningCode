package com.linkdom.learningcomposetutorial

// ...
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.linkdom.learningcomposetutorial.ui.theme.LearningComposeTutorialTheme

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    LearningComposeTutorialTheme  {
        Conversation(SampleData.conversationSample)
    }
}

