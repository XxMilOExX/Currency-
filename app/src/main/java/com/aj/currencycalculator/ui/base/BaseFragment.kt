package com.aj.currencycalculator.ui.base

import android.view.View
import androidx.fragment.app.Fragment
import com.aj.currencycalculator.R
import com.aj.currencycalculator.util.extension.showSnackBar
import io.github.rupinderjeet.kprogresshud.KProgressHUD

open class BaseFragment : Fragment() {

    private var hud: KProgressHUD? = null

    protected fun showProgressDialog() {
        activity?.let {
            hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Loading...")
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.2f)
                .show()
        }
    }

    protected fun hideProgressDialog() {
        hud?.dismiss()
    }

    protected fun showError(
        title: String? = context?.getString(R.string.error),
        msg: String? = context?.getString(R.string.oh_snap),
        parent: View
    ) {
        if (!title.isNullOrEmpty() && !msg.isNullOrEmpty()) {
            parent.showSnackBar(
                msg,
                title
            )
        }
    }
}
