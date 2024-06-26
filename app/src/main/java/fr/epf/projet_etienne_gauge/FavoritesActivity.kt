package fr.epf.projet_etienne_gauge

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesActivity : AppCompatActivity() {

    private lateinit var countryAdapter: CountryAdapter
    private lateinit var viewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val viewModelFactory = CountryViewModelFactory(applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CountryViewModel::class.java)

        setupRecyclerView()
        loadFavorites()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        countryAdapter = CountryAdapter(::onCountryClicked, ::onFavoriteClicked)
        recyclerView.adapter = countryAdapter
    }

    private fun loadFavorites() {
        val favorites = viewModel.getFavorites()
        if (favorites.isNotEmpty()) {
            countryAdapter.updateCountries(favorites)
        } else {
            Toast.makeText(this, "No favorites yet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onCountryClicked(country: Country) {
        val intent = Intent(this, CountryDetailsActivity::class.java)
        intent.putExtra("country", country)
        startActivity(intent)
    }

    private fun onFavoriteClicked(country: Country) {
        viewModel.toggleFavorite(country)
        loadFavorites()  // Refresh the list of favorites
    }
}
