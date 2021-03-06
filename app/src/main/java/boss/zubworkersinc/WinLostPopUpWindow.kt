package boss.zubworkersinc

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class WinLostPopUpWindow : DialogFragment() {

    companion object {
        fun newInstance() = WinLostPopUpWindow()
    }

    private lateinit var viewModel: WinLostPopUpWindowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.win_lost_pop_up_window_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WinLostPopUpWindowViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
