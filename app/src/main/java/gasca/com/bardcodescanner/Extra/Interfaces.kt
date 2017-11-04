package gasca.com.bardcodescanner.Extra

import gasca.com.bardcodescanner.Models.Product

/**
 * Created by in-qu on 24/10/2017.
 */
object Interfaces {
    interface OptionsFragmentCallbacks{
        fun onFlashChanged(active: Boolean)
        fun onAutoFocusChanged(active: Boolean)
        fun onManualInput()
    }

    interface ProductAdapterCallbacks{
        fun onProductClicked(product: Product)
    }

    interface SettingsFragmentCallbacks {
        fun onBack()
    }
}