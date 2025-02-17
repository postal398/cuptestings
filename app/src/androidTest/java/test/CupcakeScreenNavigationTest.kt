package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.testing.TestNavHostController
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cupcake.CupcakeApp
import com.example.cupcake.CupcakeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertEquals
import test.assertCurrentRouteName
import test.onNodeWithStringId
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@RunWith(AndroidJUnit4::class)
class CupcakeScreenNavigatorTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupCupcakeNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            CupcakeApp(navController = navController)
        }
    }

    private fun navigateToFlavorScreen() {
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.one_cupcake)
            .performClick()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.chocolate)
            .performClick()
    }

    private fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(java.util.Calendar.DATE, 1)
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    private fun performNavigateUp() {
        val backText = composeTestRule.activity.getString(com.example.cupcake.R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }

    private fun performCancelButton() {
        val cancelButton = composeTestRule.activity.getString(com.example.cupcake.R.string.cancel)
        composeTestRule.onNodeWithText(cancelButton).performClick()
    }



    @Test
    fun cupcakeNavHost_verifyStartDestination() {
        // Проверяем, что начальный экран приложения — это CupcakeScreen.Start.
        // Тест убеждается, что текущий маршрут в NavController равен "Start".
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    @Test
    fun cupcakeNavHost_verifyBackNavigationNotShownOnStartOrderScreen() {
        // Получаем текст кнопки "Назад" из строковых ресурсов.
        val backText = composeTestRule.activity.getString(com.example.cupcake.R.string.back_button)
        // Проверяем, что кнопка "Назад" не отображается на начальном экране (CupcakeScreen.Start).
        // Это логично, так как на первом экране некуда возвращаться.
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun cupcakeNavHost_clickOneCupcake_navigatesToSelectFlavorScreen() {
        // Находим элемент интерфейса с текстом "One cupcake" (один кекс) и имитируем клик по нему.
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.one_cupcake)
            .performClick()
        // Проверяем, что после клика произошел переход на экран выбора вкуса (CupcakeScreen.Flavor).
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }

    @Test
    fun cupcakeNavHost_clickOneCupcake_navigatesToPickupScreen() {
        // Вызываем функцию navigateToFlavorScreen(), которая имитирует переход на экран выбора вкуса.
        navigateToFlavorScreen()
        // Находим кнопку "Next" (Далее) на экране выбора вкуса и имитируем клик по ней.
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next)
            .performClick()
        // Проверяем, что после клика произошел переход на экран выбора даты (CupcakeScreen.Pickup).
        navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)
    }

    @Test
    fun cupcakeNavHost_clickOneCupcake_navigatesToSummaryScreen() {
        // Вызываем функцию navigateToFlavorScreen(), которая имитирует переход на экран выбора вкуса.
        navigateToFlavorScreen()
        // Находим кнопку "Next" (Далее) на экране выбора вкуса и имитируем клик по ней.
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next)
            .performClick()
        // Находим элемент интерфейса с текстом выбранной даты (например, "Today") и имитируем выбор даты.
        composeTestRule.onNodeWithText(getFormattedDate())
            .performClick()
        // Находим кнопку "Next" (Далее) на экране выбора даты и имитируем клик по ней.
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next)
            .performClick()
        // Проверяем, что после всех действий произошел переход на экран подтверждения заказа (CupcakeScreen.Summary).
        navController.assertCurrentRouteName(CupcakeScreen.Summary.name)
    }

    //Проверяем что при нажатии стрелки назад в экране вкуса оказываемся на начальном экране
    @Test
    fun cupcakeNavHost_clickBackOnChooseFlavorScreen() {
        navigateToFlavorScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    //Проверяем что при нажатии кнопки отмена в экране вкуса оказываемся на начальном экране
    @Test
    fun cupcakeNavHost_clickCancelOnChooseFlavorScreen() {
        navigateToFlavorScreen()
        performCancelButton()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    //Проверяем переход на Пикап Скрин
    @Test
    fun cupcakeNavHost_navigateToPickupScreen() {
        navigateToFlavorScreen()
        // Находим кнопку "Next" (Далее) на экране выбора вкуса и имитируем клик по ней.
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next)
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)
    }

    //Navigating to the Flavor screen by clicking the Up button from the Pickup screen
    @Test
    fun cupcakeNavHost_clickUpOnPickupScreen() {
        navigateToFlavorScreen()
        // Находим кнопку "Next" (Далее) на экране выбора вкуса и имитируем клик по ней.
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next)
            .performClick()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }

    //Navigating to the Start screen by clicking the Cancel button from the Pickup screen
    @Test
    fun cupcakeNavHost_clickCancelInPickupScreen() {
        navigateToFlavorScreen()
        // Находим кнопку "Next" (Далее) на экране выбора вкуса и имитируем клик по ней.
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next)
            .performClick()
        performCancelButton()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    //Navigating to the Summary screen
    @Test
    fun cupcakeNavHost_navigatesToSummaryScreen() {
        // Вызываем функцию navigateToFlavorScreen(), которая имитирует переход на экран выбора вкуса.
        navigateToFlavorScreen()
        // Находим кнопку "Next" (Далее) на экране выбора вкуса и имитируем клик по ней.
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next)
            .performClick()
        // Находим элемент интерфейса с текстом выбранной даты (например, "Today") и имитируем выбор даты.
        composeTestRule.onNodeWithText(getFormattedDate())
            .performClick()
        // Находим кнопку "Next" (Далее) на экране выбора даты и имитируем клик по ней.
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next)
            .performClick()
        // Проверяем, что после всех действий произошел переход на экран подтверждения заказа (CupcakeScreen.Summary).
        navController.assertCurrentRouteName(CupcakeScreen.Summary.name)
    }

    //Navigating to the Start screen by clicking the Cancel button from the Summary screen
    @Test
    fun cupcakeNavHost_clickOneCupcake_navigatesToStartScreenByCancelFromSummary() {
        // Вызываем функцию navigateToFlavorScreen(), которая имитирует переход на экран выбора вкуса.
        navigateToFlavorScreen()
        // Находим кнопку "Next" (Далее) на экране выбора вкуса и имитируем клик по ней.
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next)
            .performClick()
        // Находим элемент интерфейса с текстом выбранной даты (например, "Today") и имитируем выбор даты.
        composeTestRule.onNodeWithText(getFormattedDate())
            .performClick()
        // Находим кнопку "Next" (Далее) на экране выбора даты и имитируем клик по ней.
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next)
            .performClick()
        performCancelButton()
        // Проверяем, что после всех действий произошел переход на экран старта заказа (CupcakeScreen.Start).
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }
}