package com.elmaddinasger.orderdatabaseexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.elmaddinasger.orderdatabaseexample.adabter.CustomerAdapter
import com.elmaddinasger.orderdatabaseexample.adabter.CustomerModel
import com.elmaddinasger.orderdatabaseexample.databinding.FragmentOrderBinding
import com.elmaddinasger.orderdatabaseexample.db.AppDatabase
import com.elmaddinasger.orderdatabaseexample.db.CustomerDao
import com.elmaddinasger.orderdatabaseexample.db.CustomerEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class OrderFragment : Fragment() {
    private lateinit var binding: FragmentOrderBinding
    private lateinit var db: AppDatabase
    private lateinit var customerDao: CustomerDao
    private lateinit var customerAdapter: CustomerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater,container,false)
        db = AppDatabase.getDatabase(requireContext())
        customerDao = db.customerDao()
        getCustomers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            addCustomer()
        }
        binding.btnAddOrder.setOnClickListener {
            findNavController().navigate(R.id.action_orderFragment_to_addOrderFragment)
        }
    }

    private fun addCustomer(){
     val customerName = binding.intedtCustomerName.text.toString()

     if (customerName.isNotEmpty()){
         CoroutineScope(Dispatchers.IO).launch {
             val customerEntity = CustomerEntity(0,customerName)
             customerDao.insert(customerEntity)
             withContext(Dispatchers.Main){
                 binding.intedtCustomerName.setText("")
                 Toast.makeText(requireContext(),"Customer saved.",Toast.LENGTH_SHORT).show()
             }
         }
     }
    }



    private fun getCustomers(){
        CoroutineScope(Dispatchers.IO).launch {
           val customers = customerDao.getAll()
                withContext(Dispatchers.Main){
                    val customerList = customers.map {
                        CustomerModel(it.customerName)
                    }
                    customerAdapter = CustomerAdapter(customerList)
                    binding.rvCustomers.adapter = customerAdapter
                }

        }
    }
}