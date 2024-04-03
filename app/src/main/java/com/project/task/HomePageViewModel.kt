package com.project.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.task.requests.Category
import com.project.task.requests.ItemDish
import com.project.task.requests.RequestsInterface
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomePageViewModel: ViewModel() {

    private var requestsInterface: RequestsInterface
//    private var categories = Categories(listOf())

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _dishes = MutableLiveData<List<ItemDish>>()
    val dishes: LiveData<List<ItemDish>> = _dishes

    init {
        val retrofit = Retrofit.Builder().baseUrl("www.themealdb.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
        requestsInterface = retrofit.create(RequestsInterface::class.java)
        getCategories()
    }

    fun getCategories(){
        viewModelScope.launch{
            _categories.postValue(requestsInterface.getCategories().categories)
            val selectedCategory = categories.value?.get(0)
            getDishes(selectedCategory)
        }
    }

    fun getDishes(category: Category?){
        viewModelScope.launch {
            val listDishes = mutableListOf<ItemDish>()
            val simpleDishes = requestsInterface.getDishes(category?.strCategory ?: "")
            simpleDishes.meals.forEach{
                val aboutDish = requestsInterface.getDishById(it.idMeal).meals[0]
                listDishes.add(aboutDish.toItemDish())
            }
            _dishes.postValue(listDishes)
        }
    }

}