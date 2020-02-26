package id.bazrira.madesubmission5.ui.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.NavInflater
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.bazrira.madesubmission5.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var navView: BottomNavigationView
    private lateinit var myNavHostFragment: NavHostFragment
    private lateinit var navInflater: NavInflater
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var searchView: SearchView

    companion object {
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)

        myNavHostFragment = nav_host_fragment as NavHostFragment
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.movie,
            R.id.tv_show,
            R.id.favorite
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navInflater = myNavHostFragment.navController.navInflater

        val graph = navInflater.inflate(R.navigation.mobile_navigation)
        myNavHostFragment.navController.graph = graph

        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setIconifiedByDefault(false)

        searchView.onActionViewExpanded()
        val closeButton: ImageView = searchView.findViewById(R.id.search_close_btn) as ImageView

        closeButton.setOnClickListener {
            val etSearch: EditText = searchView.findViewById(R.id.search_src_text) as EditText

            etSearch.setText("")

            searchView.setQuery("", false)
            searchView.onActionViewCollapsed()
            navView.menu.add(
                Menu.NONE,
                R.id.favorite, Menu.NONE, getString(
                    R.string.favorites
                )
            ).setIcon(R.drawable.ic_added_to_favorites)

            searchView.isIconified = false

            appBarConfiguration = AppBarConfiguration.Builder(
                R.id.movie,
                R.id.tv_show,
                R.id.favorite
            ).build()

            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

            val graph = navInflater.inflate(R.navigation.mobile_navigation)
            myNavHostFragment.navController.graph = graph
        }

        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                navView.menu.removeItem(R.id.favorite)

                val bundle = Bundle()
                bundle.putString(EXTRA_NAME, query)
                myNavHostFragment.navController.setGraph(
                    R.navigation.search_navigation,
                    bundle
                )

                navView.setOnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.movie -> myNavHostFragment.navController.navigate(
                            R.id.movie, bundle
                        )
                        R.id.tv_show -> myNavHostFragment.navController.navigate(
                            R.id.tv_show,
                            bundle
                        )
                    }
                    return@setOnNavigationItemSelectedListener true
                }

                return true
            }

            override fun onQueryTextChange(query: String): Boolean {

                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_settings -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }

            R.id.action_settings -> {
                val mIntent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(mIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
