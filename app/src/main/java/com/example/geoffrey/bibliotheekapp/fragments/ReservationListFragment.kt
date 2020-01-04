package com.example.geoffrey.bibliotheekapp.fragments


import android.graphics.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.adapter.ReservationListAdapter
import com.example.geoffrey.bibliotheekapp.database.BookDatabase
import com.example.geoffrey.bibliotheekapp.databinding.FragmentBookListBinding
import com.example.geoffrey.bibliotheekapp.databinding.FragmentReservationListBinding
import com.example.geoffrey.bibliotheekapp.factory.BookDetailsViewModelFactory
import com.example.geoffrey.bibliotheekapp.factory.ReservationListViewModelFactory
import com.example.geoffrey.bibliotheekapp.viewModel.ReservationListViewModel
import kotlinx.android.synthetic.main.administrator_recyclerview.*
import kotlinx.android.synthetic.main.fragment_reservation_list.*

/**
 * A simple [Fragment] subclass.
 */
class ReservationListFragment : MenuBaseFragment() {
    private lateinit var binding: FragmentReservationListBinding
    private val p = Paint()
    private lateinit var adapter:ReservationListAdapter
    private lateinit var reservationListViewModel: ReservationListViewModel
    /**
     * Called to have the fragent instantiate its user interface view and is called between onCreate(Bundle) and onActivityCreated(Bundle)
     * @property inflater Is sed to inflate any view in the fragment
     * @property container if non-null, this is the parent view that the fragment's UI should be attached to.
     * @property savedInstanceState Bundle: if non-null, this fragment is being re-constructed from a previous saved state as given here
     *
     * @return View? return the View for the fragment's UI, or null
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reservation_list, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = BookDatabase.getInstance(application).bookDatabaseDao
        val viewModelFactory = ReservationListViewModelFactory(dataSource, application)
        reservationListViewModel = ViewModelProviders.of(this, viewModelFactory).get(ReservationListViewModel::class.java)
        binding.reservationListViewModel = reservationListViewModel
        adapter = ReservationListAdapter()
        binding.bookList.adapter = adapter
        reservationListViewModel.bookList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })
        binding.setLifecycleOwner(this)
        return binding.root
    }

    /**
     * calls enableSwipe which makes it possible to swipe to the left and remove a book from the list
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enableSwipe()
    }

    private fun enableSwipe() {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition

                    if (direction == ItemTouchHelper.LEFT) {
                        val workId =  adapter!!.getWorkIdFromItemPosition(position)
                        reservationListViewModel.removeBookById(workId)
                        reservationListViewModel.bookList.observe(viewLifecycleOwner, Observer {
                            it?.let {
                                adapter.data = it
                            }
                        })
                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {

                    val icon: Bitmap
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                        val itemView = viewHolder.itemView
                        val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                        val width = height / 3

                        if (dX < 0) {
                            p.color = Color.parseColor("#D32F2F")
                            val background = RectF(
                                itemView.right.toFloat() + dX,
                                itemView.top.toFloat(),
                                itemView.right.toFloat(),
                                itemView.bottom.toFloat()
                            )
                            c.drawRect(background, p)
                        }
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(book_list)
    }
}
