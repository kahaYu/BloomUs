package com.yurikolesnikov.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp

/**
 * Dynamically measures size of provided composable.
 */
@Composable
fun DynamicSizeLayout(
    modifier: Modifier = Modifier,
    contentToMeasure: @Composable () -> Unit,
    contentToDraw: @Composable (measuredWidth: Dp, measuredHeight: Dp) -> Unit,
) {
    SubcomposeLayout(
        modifier = modifier
    ) { constraints ->
        val measuredWidth = subcompose("viewToMeasure", contentToMeasure)[0]
            .measure(Constraints()).width.toDp()
        val measuredHeight = subcompose("viewToMeasure", contentToMeasure)[0]
            .measure(Constraints()).height.toDp()

        val contentPlaceable = subcompose("content") {
            contentToDraw(measuredWidth, measuredHeight)
        }[0].measure(constraints)
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.place(0, 0)
        }
    }
}