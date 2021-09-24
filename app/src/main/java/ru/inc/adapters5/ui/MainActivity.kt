package ru.inc.adapters5.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.inc.adapters5.MyApp
import ru.inc.adapters5.databinding.ActivityMainBinding
import ru.inc.adapters5.extensions.adapters
import ru.inc.adapters5.models.Apple
import ru.inc.adapters5.viewmodel.MainViewModel
import ru.inc.adapters5.viewmodel.MainViewModelFactory
import ru.inc.adapters5.viewmodel.MainViewState
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    private val log = Logger.getLogger(MainActivity::class.java.name)

    private lateinit var view: ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        view = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        initListeners()
        initRecyclerView()
        initViewModel()
    }

    private fun initListeners() {
        view.addBasket.setOnClickListener {
            viewModel.addBasket()
        }

        view.addApple.setOnClickListener {
            viewModel.addApple()
        }

        view.deleteAllBaskets.setOnClickListener {
            viewModel.deleteAllBaskets()
        }
    }

    private fun initViewModel() {
        val viewModelFactory = MainViewModelFactory(MyApp.instance.interactor)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.liveData.observe(this) { renderData(it) }
        viewModel.updateData()
    }

    private fun renderData(state: MainViewState) {
        when (state) {
            is MainViewState.Success -> {
                log.adapters("renderData Success: ${state.list}")
                adapter.submitList(state.list)
            }
            is MainViewState.UnableToAddApple -> Toast.makeText(
                this,
                "Нельзя положить больше ${state.count} яблок",
                Toast.LENGTH_LONG
            ).show()
            is MainViewState.NoApplesInStock -> Toast.makeText(
                this,
                "на складе не осталось яблок",
                Toast.LENGTH_LONG
            ).show()
            else -> throw IllegalArgumentException("unknown state")
        }
    }

    private fun initRecyclerView() {

        val listeners = object : Listeners {
            override fun eatApple(): (apple: Apple?) -> Unit = {
                if (it != null) {
                    viewModel.eatApple(it)
                } else {
                    Toast.makeText(this@MainActivity, "Нельзя кушать яблоки со склада", Toast.LENGTH_SHORT).show()
                }
            }

            override fun addAppleToBasket(): (basketId: Long) -> Unit = { viewModel.addAppleToBasket(it) }

            override fun deleteBasket(): (basketId: Long) -> Unit = { viewModel.deleteBasket(it) }
        }

        adapter = MainAdapter(listeners)
        view.recycler.adapter = adapter
    }
}