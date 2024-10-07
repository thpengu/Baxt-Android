package uz.karsoft.baxt.data.models.main.home.detail.product

import uz.karsoft.baxt.data.models.main.home.detail.NavigationData
import uz.karsoft.baxt.data.models.main.search.Children

data class ProductNavigationData(
    val navigationList: MutableList<NavigationData>,
    val model: Children
)