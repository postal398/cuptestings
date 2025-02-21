package test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.cupcake.R
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen
import org.junit.Rule
import org.junit.Test

class CupcakeStartScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun startOrderScreen_verifyContent() {
        // Given list of options
        val quantityOfCupcakes = listOf(
            Pair(1, 2),   // 1 кекс за 2 бакса
            Pair(6, 12),  // 6 кексов за 12 баксов
            Pair(12, 24)  // 12 кексов за 24 бакса
        )

        // When SelectOptionScreen is loaded
        composeTestRule.setContent {
            StartOrderScreen(quantityOfCupcakes, {})
        }

        // Есть надпись Order Cupccakes
        composeTestRule.onNodeWithText("Order Cupcakes").assertIsDisplayed()

        // Есть кнопка - One Cupcake
        composeTestRule.onNodeWithText("One Сupcake").assertIsEnabled()
    }
}