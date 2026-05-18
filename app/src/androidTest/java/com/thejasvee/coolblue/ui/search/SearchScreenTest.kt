package com.thejasvee.coolblue.ui.search

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.thejasvee.coolblue.core.result.ErrorMessages
import com.thejasvee.coolblue.domain.model.Product
import com.thejasvee.coolblue.ui.theme.CoolblueTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pagination_error_footer_is_shown_and_retry_click_emits_load_next_page() {
        val emittedEvents = mutableListOf<SearchUiEvent>()

        val state = SearchUiState(
            query = "apple",
            products = listOf(
                product(id = 1),
                product(id = 2),
                product(id = 3),
                product(id = 4),
                product(id = 5),
                product(id = 6)
            ),
            totalResults = 12,
            currentPage = 1,
            pageCount = 2,
            isInitialLoading = false,
            isLoadingMore = false,
            errorMessage = null,
            paginationErrorMessage = ErrorMessages.NETWORK,
            hasSearched = true
        )

        composeTestRule.setContent {
            CoolblueTheme {
                SearchScreen(
                    state = state,
                    onEvent = { event ->
                        emittedEvents.add(event)
                    }
                )
            }
        }

        composeTestRule
            .onNodeWithTag("ProductGrid")
            .performScrollToNode(hasTestTag("PaginationErrorFooter"))

        composeTestRule
            .onNodeWithTag("PaginationErrorFooter")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("PaginationRetryButton")
            .assertIsDisplayed()
            .performClick()

        assertTrue(emittedEvents.contains(SearchUiEvent.LoadNextPage))
    }

    private fun product(
        id: Int,
        name: String = "Apple iPhone"
    ): Product {
        return Product(
            id = id,
            name = name,
            imageUrl = null,
            price = 999.0,
            listPrice = null,
            productReviewSummary = null,
            usps = listOf(
                "64 GB Storage",
                "6,1 Inch"
            ),
            promo = null,
            nextDayDelivery = true
        )
    }
}