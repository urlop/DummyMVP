package net.natura.minegocionatura.view.shareables

import com.anitrack.ruby.mvpexample.Presenter


class ShareablesPresenter(view: ShareablesView) : Presenter<ShareablesView> {

    private val ALL_TAG = "Todos"

    fun getShareables(category: String?, page: Int) {
        //Get data from WS
    }

}