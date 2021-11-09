package io.instaleap.statusprogressbar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.instaleap.statusprogressbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setUpRecyclerView()
        setUpObservables()
        setUpListeners()
    }

    private fun setUpRecyclerView() {
        binding.rvCustomView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = DataModelAdapter()
        }
    }

    private fun setUpListeners() {
        with(binding) {
            btAdd.setOnClickListener {
                mainViewModel.addItem()
            }
            btPending.setOnClickListener {
                mainViewModel.pendingItem()
            }
            btRemoved.setOnClickListener {
                mainViewModel.removeItem()
            }
        }
    }

    private fun setUpObservables() {
        with(binding) {
            mainViewModel.dataModelView.observe(this@MainActivity) {
                linearBar.setDataModelView(it.dataList, it.totalValue)
            }
            mainViewModel.dataList.observe(this@MainActivity) { products ->
                rvCustomView.adapter?.let { adapter ->
                    (adapter as? DataModelAdapter)?.submitList(products)
                }
            }
        }
    }
}
