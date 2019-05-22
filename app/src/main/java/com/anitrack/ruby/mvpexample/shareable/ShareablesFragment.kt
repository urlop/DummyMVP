package com.anitrack.ruby.mvpexample.shareable

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anitrack.ruby.mvpexample.R

import com.anitrack.ruby.mvpexample.shareable.dummy.DummyContent
import com.anitrack.ruby.mvpexample.shareable.dummy.DummyContent.Result
import net.natura.minegocionatura.view.shareables.ShareablesPresenter
import net.natura.minegocionatura.view.shareables.ShareablesView

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ShareablesFragment.OnListFragmentInteractionListener] interface.
 */
class ShareablesFragment : Fragment(), ShareablesView {
    override fun onShareablesLoaded(shareables: List<Result>, currentPage: Int, lastPage: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFailure(message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // TODO: Customize parameters
    private var columnCount = 1

    private var isLoading : Boolean = false
    private var isLastPage : Boolean = false
    private var gridLayoutManager : GridLayoutManager? = null
    private var presenter: ShareablesPresenter? = null
    private var currentPage = 1
    private var category : String? = null
    //private var storiesAdapter : ShareablesAdapter? = null

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shareables_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyShareablesRecyclerViewAdapter(DummyContent.ITEMS, listener)
            }
        }
        return view
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        initPresenter()
        setUpErrorView()
    }*/

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /*private fun setUpRecyclerView() {
        gridLayoutManager = GridLayoutManager(context, COLUMN_COUNT)
        rvShareables!!.layoutManager = gridLayoutManager
        storiesAdapter= ShareablesAdapter2(context!!)
        rvShareables.adapter=storiesAdapter
        rvShareables!!.addOnScrollListener(object : GridScrollListener(gridLayoutManager!!) {
            override fun loadMoreItems() {
                isLoading = true
                showProgress()
                currentPage += 1
                presenter!!.getShareables(category,currentPage)
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
    }*/

    abstract class GridScrollListener(internal var layoutManager: GridLayoutManager) :
        RecyclerView.OnScrollListener() {

        abstract fun isLastPage() : Boolean

        abstract fun isLoading() : Boolean

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            if (!isLoading() && !isLastPage()) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loadMoreItems()
                }
            }

        }
        protected abstract fun loadMoreItems()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Result?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ShareablesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
