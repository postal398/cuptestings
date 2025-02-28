package test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.cupcake.R
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.StartOrderScreen
import org.junit.Rule
import org.junit.Test

class CupcakeSummaryScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    val testOrderUiState = OrderUiState(
        quantity = 6,
        flavor = "Chocolate",
        date = "2023-10-15",
        price = "$18.00"
    )

    @Test
    fun summaryScreen_verifyContent() {
        // When SelectOptionScreen is loaded
        composeTestRule.setContent {
            OrderSummaryScreen(
                testOrderUiState,
                onCancelButtonClicked = {  }, // Заглушка: игнорирует параметры
                onSendButtonClicked = { _, _ -> }   // Заглушка: игнорирует параметры
            )
        }
        // Есть надпись quantity
//        val quantityCupcakesText = composeTestRule.activity.getString(R.string.quantity)
        composeTestRule.onNodeWithText("6 cupcakes").assertIsDisplayed()

        composeTestRule.onNodeWithStringId(R.string.next).assertIsEnabled()
    }
}