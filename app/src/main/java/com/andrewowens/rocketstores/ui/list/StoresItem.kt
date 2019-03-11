package com.andrewowens.rocketstores.ui.list

import com.andrewowens.rocketstores.R
import com.andrewowens.rocketstores.data.db.entity.StoreEntry
import com.andrewowens.rocketstores.internal.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.store_item.*

/**
 * Item to be displayed in the recycler view
 */
class StoresItem(
    val storeEntry: StoreEntry
) : Item(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {

            textView_storeLocation.text = storeEntry.address
            textView_phoneNumber.text = storeEntry.phone
            updateStoreLogo()
        }
    }

    override fun getLayout() = R.layout.store_item

    private fun ViewHolder.updateStoreLogo() {
        GlideApp.with(this.containerView)
            .load(storeEntry.storeLogoURL)
            .into(imageView_logo)
    }
}