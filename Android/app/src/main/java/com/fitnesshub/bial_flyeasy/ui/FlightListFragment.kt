package com.fitnesshub.bial_flyeasy.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitnesshub.bial_flyeasy.adapters.FlightsAdapter
import com.fitnesshub.bial_flyeasy.databinding.FragmentFlightListBinding
import com.fitnesshub.bial_flyeasy.models.FlightModel
import com.fitnesshub.bial_flyeasy.models.SearchFlightModel
import com.fitnesshub.bial_flyeasy.models.UIUpdatesModel
import com.fitnesshub.bial_flyeasy.utils.Constants
import com.fitnesshub.bial_flyeasy.viewModels.FlightListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FlightListFragment @Inject constructor() : Fragment(), FlightsAdapter.ItemClickListener {

    private lateinit var binding: FragmentFlightListBinding
    lateinit var viewModel: FlightListViewModel
    private var flights = ArrayList<FlightModel>()
    private lateinit var adapter: FlightsAdapter
    private lateinit var searchFlightModel: SearchFlightModel
    private var uiUpdates = UIUpdatesModel(status = Constants.IN_PROGRESS)
    private lateinit var frag_tag: String

    companion object {
        fun newInstance(searchFlightModel: SearchFlightModel, tag: String): FlightListFragment {
            val args = Bundle()
            args.putSerializable("SearchFlightModel", searchFlightModel)
            args.putString("frag_tag", tag)
            val fragment = FlightListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        searchFlightModel = arguments?.getSerializable("SearchFlightModel") as SearchFlightModel
        frag_tag = arguments?.getString("frag_tag", "TAG_ONE_WAY_FRAG")!!
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentFlightListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(FlightListViewModel::class.java)
        binding.updates = uiUpdates

        activity?.let { activity ->
            viewModel.getFlights(searchFlightModel).observe(activity, {
                uiUpdates = if (it == null) UIUpdatesModel(Constants.ERROR, "API Response is null")
                else if (it.status == Constants.OKAY) {
                    val list = it.data as ArrayList<FlightModel>
                    flights.clear()
                    flights.addAll(list)
                    adapter.notifyDataSetChanged()
                    if (list.isEmpty()) UIUpdatesModel(status = Constants.NO_DATA_FOUND)
                    else UIUpdatesModel(status = Constants.OKAY)
                } else UIUpdatesModel(it.status, it.message)
                binding.updates = uiUpdates
            })
        }

        initRV()
        return binding.root
    }

    private fun initRV() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FlightsAdapter(activity as Context, flights, this)
        binding.recyclerView.adapter = adapter
    }

    override fun onFlightClick(newSelectedFlightPosition: Int, lastSelectedFlightPosition: Int) {
        if (lastSelectedFlightPosition != -1) flights[lastSelectedFlightPosition].selected = false
        flights[newSelectedFlightPosition].selected = true
        val activity = activity as SearchFlightActivity
        activity.updateSelectedFlightDetails(flights[newSelectedFlightPosition], frag_tag)
        adapter.notifyItemChanged(newSelectedFlightPosition)
        adapter.notifyItemChanged(lastSelectedFlightPosition)
    }
}