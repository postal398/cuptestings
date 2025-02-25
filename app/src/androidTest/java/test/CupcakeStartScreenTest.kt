package test

import androidx.activity.ComponentActivity
import androidx.compose.ui.res.stringResource
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
            Pair(R.string.one_cupcake, 1),
            Pair(R.string.six_cupcakes, 6),
            Pair(R.string.twelve_cupcakes, 12)
        )

        // When SelectOptionScreen is loaded
        composeTestRule.setContent {
            StartOrderScreen(quantityOfCupcakes, {})
        }

        // Есть надпись Order Cupcakes
        val orderCupcakesText = composeTestRule.activity.getString(R.string.order_cupcakes)
        composeTestRule.onNodeWithText(orderCupcakesText).assertIsDisplayed()

        // Есть кнопка - One Cupcake
        val oneCupcakeText = composeTestRule.activity.getString(R.string.one_cupcake)
        composeTestRule.onNodeWithText(oneCupcakeText).assertIsEnabled()
    }
}