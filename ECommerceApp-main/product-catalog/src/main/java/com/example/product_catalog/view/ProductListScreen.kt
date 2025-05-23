package com.example.product_catalog.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.formatToUSD
import com.example.product_catalog.model.Category
import com.example.product_catalog.model.Product
import com.example.product_catalog.viewmodel.ProductViewModel

@Composable
fun ProductListItem(
    name: String,
    imageId: Int,
    modifier: Modifier = Modifier,
    price: Double
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(4.dp),
    ) {
        Column {
            if (imageId > 0) {
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = null,
                    modifier = Modifier.height(100.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(modifier = Modifier.padding(8.dp),
                text = name
            )
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                text = price.formatToUSD(),
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductListScreen(
    categories: List<Category> = emptyList<Category>(),
    products: List<Product> = emptyList<Product>(),
    viewModel: ProductViewModel? = null,
    navigateToProductDetails : (Int?) -> Unit = {}
) {
    val products = viewModel?.products?.collectAsStateWithLifecycle()?.value
        ?: products
    val categories = viewModel?.categories?.collectAsStateWithLifecycle()?.value
        ?: categories

    var isRefreshing = viewModel?.isRefreshing?.collectAsStateWithLifecycle()
    val state = rememberPullRefreshState(isRefreshing?.value == true, {
        viewModel?.getProductList()
    })

    var selectedChipIndex by remember { mutableIntStateOf(0) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach {
                val index = categories.indexOf(it)
                FilterChip(
                    onClick = {
                        selectedChipIndex = index
                    },
                    label = {
                        Text(it.name)
                    },
                    selected = index == selectedChipIndex,
                )
            }
        }
        Box(
            modifier = Modifier.pullRefresh(state)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products.size) { index -> // products.value.size
                    val product = products[index]
                    ProductListItem(
                        modifier = Modifier.clickable {
                            navigateToProductDetails(product.id)
                        },
                        name = product.name,
                        imageId = product.imageId,
                        price = product.price
                    )
                }
            }
            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = isRefreshing?.value == true,
                state = state
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {
    ProductListScreen()
}