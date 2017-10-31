package gasca.com.bardcodescanner.Extra

/**
 * Created by in-qu on 24/10/2017.
 */
object Interfaces {
    interface OptionsFragmentCallbacks{
        fun onFlashChanged(active: Boolean)
        fun onAutoFocusChanged(active: Boolean)
        fun onManualInput()
    }

    interface SettingsFragmentCallbacks {
        fun onBack()
    }
}