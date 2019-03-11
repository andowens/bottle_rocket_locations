package com.andrewowens.rocketstores.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.andrewowens.rocketstores.R
import com.andrewowens.rocketstores.internal.GlideApp
import com.andrewowens.rocketstores.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.stores_detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory

class StoresDetailFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewModelFactory: ((String) -> StoresDetailViewModelFactory) by factory()

    private lateinit var viewModel: StoresDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.stores_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { StoresDetailFragmentArgs.fromBundle(it) }
        val storeId = safeArgs?.storeId ?: ""
        val storeName = safeArgs?.storeName ?: ""
        setStoreTitle(storeName)

        viewModel = ViewModelProviders.of(this, viewModelFactory(storeId))
            .get(StoresDetailViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {

        val store = viewModel.store.await()

        store.observe(this@StoresDetailFragment, Observer { storeEntry ->
            if (storeEntry == null) return@Observer

            group_loading.visibility = View.GONE

            textView_address.text = storeEntry.address

            textView_phoneNumber.text = storeEntry.phone

            textView_bottomAddressLine.text = buildBottomLine(storeEntry.city, storeEntry.state, storeEntry.zipcode)

            address_container.setOnClickListener {
                launchMapActivity(storeEntry.latitude, storeEntry.longitude)
            }

            textView_phoneNumber.setOnClickListener {
                launchPhoneActivity(storeEntry.phone)
            }

            GlideApp.with(this@StoresDetailFragment)
                .load(storeEntry.storeLogoURL)
                .into(imageView_storeLogo)
        })

    }

    private fun buildBottomLine(city: String, state: String, zipcode: String): String {
        return "$city, $state $zipcode"
    }

    private fun setStoreTitle(storeName: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = storeName
    }

    private fun launchMapActivity(lat: String, lng: String) {

        val gmmIntentUri = Uri.parse("geo:$lat,$lng")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private fun launchPhoneActivity(number: String) {
        val numberUri = Uri.parse("tel:${number.replace("-", "", true)}")
        val phoneIntent = Intent(Intent.ACTION_DIAL, numberUri)
        startActivity(phoneIntent)
    }
}
