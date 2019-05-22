package net.natura.minegocionatura.view.shareables

import com.anitrack.ruby.mvpexample.BaseView
import com.anitrack.ruby.mvpexample.shareable.dummy.DummyContent

interface ShareablesView : BaseView {

    fun onShareablesLoaded(shareables: List<DummyContent.Result>, currentPage : Int, lastPage : Int)

    fun hideProgress ()

    fun showProgress ()

}