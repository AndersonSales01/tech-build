package com.tech.building.features.utils.provider

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.tech.building.R
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.network_error_dialog_content.*

class NetWorkErrorPage : DialogFragment() {

    @Parcelize
    data class Args(
        val onTryAgain: () -> Unit = {},
        val onClosed: () -> Unit = {},
    ) : Parcelable

    private var arg: Args? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.network_error_dialog_content, container, false)
        val data: Args = arguments?.get(NETWORK_ERROR_PAGE) as Args
        arg = data
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        arg?.let { argument ->
            tryAgainButton.setOnClickListener {
                argument.onTryAgain.invoke()
                dismiss()
            }

            closeButton.setOnClickListener {
                argument.onClosed.invoke()
                dismiss()
            }
        }
    }

    companion object {
        const val NETWORK_ERROR_PAGE = "NetWorkErrorPage"
        operator fun invoke(args: Args): NetWorkErrorPage {
            val page = NetWorkErrorPage()
            val bundle = Bundle()
            bundle.putParcelable(NETWORK_ERROR_PAGE, args)
            page.arguments = bundle
            return page
        }
    }
}