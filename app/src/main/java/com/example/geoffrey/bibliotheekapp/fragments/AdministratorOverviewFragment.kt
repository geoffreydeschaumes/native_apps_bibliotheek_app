package com.example.geoffrey.bibliotheekapp.fragments
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.adapter.AdministratorBookAdapter
import com.example.geoffrey.bibliotheekapp.databinding.AdministratorRecyclerviewBinding
import com.example.geoffrey.bibliotheekapp.models.ShopList
import com.example.geoffrey.bibliotheekapp.viewModel.AdministratorOverviewViewModel
import kotlinx.android.synthetic.main.administrator_recyclerview.*

class AdministratorOverviewFragment : Fragment() {
    private lateinit var binding: AdministratorRecyclerviewBinding
    private lateinit var administratorOverviewViewModel: AdministratorOverviewViewModel
    private lateinit var adapter: AdministratorBookAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.administrator_recyclerview, container, false)
        administratorOverviewViewModel = ViewModelProviders.of(this).get(AdministratorOverviewViewModel::class.java)
        administratorOverviewViewModel.getShopList(this)
        binding.setLifecycleOwner(this)
        return binding.root
    }
    fun setList(){
        val application = requireNotNull(this.activity).application
        adapter = AdministratorBookAdapter( administratorOverviewViewModel._shopList.value, application)
        recycler.adapter = adapter
    }

}
