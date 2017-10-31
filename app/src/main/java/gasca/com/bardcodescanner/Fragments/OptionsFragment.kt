package gasca.com.bardcodescanner.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gasca.com.bardcodescanner.Extra.Interfaces
import gasca.com.bardcodescanner.R
import kotlinx.android.synthetic.main.fragment_options.*

/**
 * Created by in-qu on 24/10/2017.
 */
class OptionsFragment: Fragment() {
    val listener by lazy{
        activity as? Interfaces.OptionsFragmentCallbacks
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fragment_options, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init(){
        switchFlash.setOnCheckedChangeListener { buttonView, isChecked ->
            listener?.onFlashChanged(isChecked)
        }

        switchAutoFocus.setOnCheckedChangeListener { buttonView, isChecked ->
            listener?.onAutoFocusChanged(isChecked)
        }

        lblManualInput.setOnClickListener{
            listener?.onManualInput()
        }
    }
}