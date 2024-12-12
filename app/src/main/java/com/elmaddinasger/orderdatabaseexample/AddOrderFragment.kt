package com.elmaddinasger.orderdatabaseexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.elmaddinasger.orderdatabaseexample.databinding.FragmentAddOrderBinding
import com.elmaddinasger.orderdatabaseexample.db.AppDatabase
import com.elmaddinasger.orderdatabaseexample.db.ProductDao
import com.elmaddinasger.orderdatabaseexample.db.ProductEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddOrderFragment : Fragment() {
private lateinit var binding: FragmentAddOrderBinding
private lateinit var db: AppDatabase
private lateinit var productDao: ProductDao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddOrderBinding.inflate(inflater,container,false)
        db = AppDatabase.getDatabase(requireContext())
        productDao = db.productDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            addProduct()
        }
        binding.btnAddOrder.setOnClickListener {
            findNavController().navigate(R.id.action_addOrderFragment_to_customerFragment)
        }
    }

    private fun addProduct () {
        val productName = binding.inpedtProductName.text.toString()
        val productQuantity = binding.inpedtProductQuality.text.toString()
        if (productQuantity.isNotEmpty() && productName.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
               val productEntity = ProductEntity(0,productName,productQuantity.toLong())
                productDao.insert(productEntity)
            }
        }
    }



}