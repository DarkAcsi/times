package com.project.task

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.task.databinding.FragmentHomePageBinding
import com.project.task.requests.Category

class HomePageFragment : Fragment(R.layout.fragment_home_page) {

    private lateinit var binding: FragmentHomePageBinding
//    private val viewModel: HomePageViewModel by viewModels()
    private val viewModel = ViewModelProvider(this)[HomePageViewModel::class.java]

    private lateinit var bannersAdapter: BannersAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var dishesAdapter: DishesAdapter

//    private val apikey = "1"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomePageBinding.bind(view)
        initialBannersAdapter()
        initialCategoriesAdapter()
        initialDishesAdapter()

        bannersAdapter.items = getBanners()
        bannersAdapter.notifyDataSetChanged()


        viewModel.categories.observe(viewLifecycleOwner){
            categoriesAdapter.items = it
            categoriesAdapter.notifyDataSetChanged()
        }
        viewModel.dishes.observe(viewLifecycleOwner){
            dishesAdapter.items = it
            dishesAdapter.notifyDataSetChanged()
        }


    }

    fun getBanners(): List<String> {
        return listOf(
            "https://s3-alpha-sig.figma.com/img/0e5f/8769/bbdcde65e7e95920e9f71a180817df1a?Expires=1713139200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=kEt2jBoCKntC8RFvfuwgGSJNm4yslKYtS4TmFC8ZFcgU39LOGiMVduXq6Njwpm-ALfzM7vW0jnvmhqhls0iTPt6QpClCyH37xWW34t8V5773ZFJz86~-Fe1OIl9wPGt8A14XSsM2FZX~OBnasz96C43LvJR-y9iHLnyw5FMfLNheYbrQ0-pmjRRW2x0sOWYpyqg2f2Wg5jWLwo98stZ5ixgp3Lg~IjJ-GK9CQfhinObcQlxnn5EUj4XCPcdLMeuuMXe82pwqdYpQPJMlFq1pC2e77Ka4nE9FSvH0L7C4EUqlvtZZCrEEb3DWP02Po4NfKx6v2WhJ8mxSeX3TzQ-7IA__",
            "https://s3-alpha-sig.figma.com/img/b42b/b92c/f6acf7e8e259819d3dd44499cb49eb54?Expires=1713139200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=e9YP2Rme7pvV6O9WCzoKM2CD5R0NQ0ErodQrbADFu1diuZcTZkAjr~YX0NKBXLgtj0OKNkqWEZs4DaiFzSpNiCWlk12~jJ~qYjEswzCCE0iShsnzS4HSzTLNk6V7Q1z-tCToP0jTOpgUQRebOn9aMf-YHcSHtwtuywrdNGsAHzMKiQ6-u~4c87Hd8xwlfQYDZypdr0yNnGBNjaujzxcm6wdlg-rbM8qOuQQgdduGA0O7PVxzCA85b7VNkcg-nlvJFQw72ccpTWF~-wHbbwBvW3V5fBPVyNnwfw3lnjWKdUcRPwI3cUYTlT3vnaQn~PMt9cCtEmwC9syk0AGqWDUU8Q__",
        )
    }

    private fun initialBannersAdapter() {
        bannersAdapter = BannersAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.listBanners.layoutManager = layoutManager
        binding.listBanners.adapter = bannersAdapter
    }

    private fun initialCategoriesAdapter() {
        categoriesAdapter = CategoriesAdapter(object : CategoryListener {
            override fun changeCategory(category: Category) {
                viewModel.getDishes(category)
            }
        })
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.listCategories.layoutManager = layoutManager
        binding.listCategories.adapter = categoriesAdapter
    }

    private fun initialDishesAdapter() {
        dishesAdapter = DishesAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.listDishes.layoutManager = layoutManager
        binding.listDishes.adapter = dishesAdapter
    }

}