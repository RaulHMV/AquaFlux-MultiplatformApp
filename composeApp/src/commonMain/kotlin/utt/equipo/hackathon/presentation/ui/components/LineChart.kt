package utt.equipo.hackathon.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utt.equipo.hackathon.data.models.ChartDataPoint
import kotlin.math.roundToInt

@Composable
fun LineChart(
    data: List<ChartDataPoint>,
    modifier: Modifier = Modifier,
    lineColor: Color = Color(0xFFFDD835),
    backgroundColor: Color = Color(0xFF1E3A5F)
) {
    if (data.isEmpty()) {
        Box(
            modifier = modifier.fillMaxWidth().height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No hay datos disponibles",
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 14.sp
            )
        }
        return
    }

    Column(modifier = modifier) {
        // Chart Canvas
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
        ) {
            val width = size.width
            val height = size.height
            val maxValue = data.maxOfOrNull { it.value }?.toFloat() ?: 100f
            val minValue = data.minOfOrNull { it.value }?.toFloat() ?: 0f
            val valueRange = maxValue - minValue
            val adjustedRange = if (valueRange == 0f) 1f else valueRange

            if (data.size > 1) {
                val path = Path()
                val stepX = width / (data.size - 1).coerceAtLeast(1)

                // Create path for line
                data.forEachIndexed { index, point ->
                    val x = index * stepX
                    val normalizedValue = ((point.value.toFloat() - minValue) / adjustedRange)
                    val y = height - (normalizedValue * height * 0.9f) - (height * 0.05f)

                    if (index == 0) {
                        path.moveTo(x, y)
                    } else {
                        path.lineTo(x, y)
                    }
                }

                // Draw line
                drawPath(
                    path = path,
                    color = lineColor,
                    style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                )

                // Draw points
                data.forEachIndexed { index, point ->
                    val x = index * stepX
                    val normalizedValue = ((point.value.toFloat() - minValue) / adjustedRange)
                    val y = height - (normalizedValue * height * 0.9f) - (height * 0.05f)

                    drawCircle(
                        color = lineColor,
                        radius = 6.dp.toPx(),
                        center = Offset(x, y)
                    )
                }
            }

            // Draw horizontal grid lines
            for (i in 0..4) {
                val y = height * i / 4f
                drawLine(
                    color = Color.White.copy(alpha = 0.1f),
                    start = Offset(0f, y),
                    end = Offset(width, y),
                    strokeWidth = 1.dp.toPx()
                )
            }
        }

        // Legend with min and max values
        val minValueInt = data.minOfOrNull { it.value }?.toInt() ?: 0
        val maxValueInt = data.maxOfOrNull { it.value }?.toInt() ?: 0

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Min: $minValueInt L",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Max: $maxValueInt L",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
