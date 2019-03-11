package com.andrewowens.rocketstores.ui.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import com.andrewowens.rocketstores.R
import com.andrewowens.rocketstores.data.db.entity.StoreEntry
import com.andrewowens.rocketstores.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.stores_list_fragment.*
import kotlinx.coroutines.Dispatchers
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class StoresListFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    // Get instance using kodein
    private val viewModelFactory: StoresListViewModelFactory by instance()

    private lateinit var viewModel: StoresListViewModel

    private lateinit var groupAdapter: GroupAdapter<ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Since the application uses navigation have to set the toolbars title in each fragment
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.app_name)

        bindUI()

        return inflater.inflate(R.layout.stores_list_fragment, container, false)
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val stores = viewModel.storeEntries.await()

        stores.observe(this@StoresListFragment, Observer { entries ->
            if (entries == null) {
                Toast.makeText(this@StoresListFragment.context, "No data available check your connection",
                    Toast.LENGTH_LONG).show()
                return@Observer
            }

            group_loading.visibility = View.GONE
            initRecyclerView(entries)
        })
    }

    private fun initRecyclerView(entries: List<StoreEntry>) {
        groupAdapter.addAll(entries.toStoresItem())
        groupAdapter.notifyDataSetChanged()
    }

    private fun List<StoreEntry>.toStoresItem() : List<StoresItem> {
        return this.map {
            StoresItem(it)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(StoresListViewModel::class.java)
        groupAdapter = GroupAdapter()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@StoresListFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            (item as? StoresItem)?.let {
                showStoreDetail(it.storeEntry.storeID, it.storeEntry.name, view)
            }
        }
    }

    private fun showStoreDetail(storeId: String, storeName: String, view: View) {
        val actionDetail = StoresListFragmentDirections.actionDetail(storeId, storeName)
        Navigation.findNavController(view).navigate(actionDetail)
    }

}
