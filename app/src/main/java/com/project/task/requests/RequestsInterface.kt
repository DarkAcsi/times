package com.project.task.requests

import retrofit2.http.GET
import retrofit2.http.Query

interface RequestsInterface {

    @GET("/api/json/v1/1/categories.php")
    suspend fun getCategories(): Categories

    @GET("/api/json/v1/1/filter.php")
    suspend fun getDishes(
        @Query("s") category: String,
    ): Dishes

    @GET("/api/json/v1/1/lookup.php")
    suspend fun getDishById(
        @Query("i") index: Long,
    ): AboutDishes

}