package com.alth.events.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FadedExpandableText(
    content: String,
    maxLines: Int = 2,
) {
    /**
     * If the text doesn't exceed maxLines, it shouldn't
     * be expanded AND it shouldn't be faded
     *
     * This is false if the content is less than maxLines
     *
     * TODO - Figure out a way of calculating this before rendering the text
     */
    var canBeExpanded by remember { mutableStateOf(true) }

    if (!canBeExpanded) {
        /**
         * Just show the text, we're happy without doing
         * anything fancy
         */
        NonExpandableText(
            content = content,
            textLayoutResult = {
                if (it.didOverflowHeight) {
                    canBeExpanded = true
                }
            },
            maxLines = maxLines,
        )
    } else {
        /**
         * Otherwise, clip the text and fade it or
         * show the entire text and don't fade it
         * (transition between the two states by clicking on it)
         */
        var expanded by remember { mutableStateOf(false) }
        if (expanded) {
            CollapsibleText(content = content) {
                expanded = false
            }
        } else {
            ExpandableText(
                content = content,
                maxLines = maxLines,
            ) {
                expanded = true
            }
        }
    }
}

@Composable
fun NonExpandableText(
    content: String,
    textLayoutResult: (result: TextLayoutResult) -> Unit,
    maxLines: Int,
) {
    Text(
        text = content,
        color = Color.Blue,
        onTextLayout = {
            textLayoutResult(it)
        },
        maxLines = maxLines
    )
}

@Composable
fun CollapsibleText(
    content: String,
    collapse: () -> Unit,
) {
    Text(
        modifier = Modifier.clickable { collapse() },
        text = content,
    )
}

@Composable
fun ExpandableText(
    content: String,
    maxLines: Int,
    expand: () -> Unit,
) {
    Text(
        modifier = Modifier
            .graphicsLayer { alpha = 0.99f }
            .drawWithContent {
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(
                        listOf(Color.Black, Color.Transparent)
                    ),
                    blendMode = BlendMode.DstIn,
                )
            }
            .clickable { expand() },
        text = content,
        maxLines = maxLines,
    )
}

@Composable
@Preview
fun Preview() {
    FadedExpandableText(
        content = "I'm a simple string that doesn't go more than 2 lines",
        maxLines = 2
    )
}
