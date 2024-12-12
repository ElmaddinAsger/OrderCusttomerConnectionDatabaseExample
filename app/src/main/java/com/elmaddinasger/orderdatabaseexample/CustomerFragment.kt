package com.elmaddinasger.orderdatabaseexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.R
import com.elmaddinasger.orderdatabaseexample.adabter.ProductAdapter
import com.elmaddinasger.orderdatabaseexample.adabter.ProductModel
import com.elmaddinasger.orderdatabaseexample.databinding.FragmentCustomerBinding
import com.elmaddinasger.orderdatabaseexample.db.AppDatabase
import com.elmaddinasger.orderdatabaseexample.db.CustomerDao
import com.elmaddinasger.orderdatabaseexample.db.CustomerEntity
import com.elmaddinasger.orderdatabaseexample.db.OrderDao
import com.elmaddinasger.orderdatabaseexample.db.OrderEntity
import com.elmaddinasger.orderdatabaseexample.db.OrderProductDao
import com.elmaddinasger.orderdatabaseexample.db.OrderProductEntity
import com.elmaddinasger.orderdatabaseexample.db.ProductDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CustomerFragment : Fragment() {
    private lateinit var db: AppDatabase
    private lateinit var customerDao: CustomerDao
    private lateinit var productDao: ProductDao
    private lateinit var orderProductDao: OrderProductDao
    private lateinit var orderDao: OrderDao
    private lateinit var binding: FragmentCustomerBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: List<ProductModel>
    private lateinit var spinnerAdapter :  ArrayAdapter<CustomerEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerBinding.inflate(inflater,container,false)
        db = AppDatabase.getDatabase(requireContext())
        customerDao = db.customerDao()
        productDao = db.productDao()
        orderDao = db.orderDao()
        orderProductDao = db.orderProductDao()
        productAdapter = ProductAdapter{product, plusMinus -> setAdapter(product,plusMinus) }
        return binding.root
         }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpinner()
        getRecyclerView()

        binding.btnAddOrder.setOnClickListener {
            addOrder()
        }
    }

    private fun setAdapter(product: ProductModel, plusMinus: Boolean){
        if (plusMinus && product.productQuantity > product.productCount) {
            product.productCount += 1
            if(product.productCount == 1L){
                product.productState = true
            }
        }
        else if (!plusMinus && product.productCount > 0) {
            product.productCount -= 1
        }
    }

    private fun setSpinner () {
        CoroutineScope(Dispatchers.IO).launch {
            val customerList = customerDao.getAll()
            withContext(Dispatchers.Main){
               spinnerAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item,customerList)
                spinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
                binding.spnCustomer.adapter = spinnerAdapter
            }
        }
    }

    private fun getRecyclerView () {
        CoroutineScope(Dispatchers.IO).launch {
           val dbProductList = productDao.getAll()
            withContext(Dispatchers.Main){
                productList = dbProductList.map {
                    ProductModel(
                        it.productId,
                        it.productName,
                        it.quantity,
                        false
                    )
                }
                productAdapter.setList(productList)
                binding.rvProducts.adapter = productAdapter
            }
        }
    }

    private fun addOrder() {
        val customer = binding.spnCustomer.selectedItem as CustomerEntity
        CoroutineScope(Dispatchers.IO).launch {
            val orderEntity = OrderEntity(0,customer.customerId)
            val orderId = orderDao.insert(orderEntity)
            productList.forEach {
                if (it.productState){
                    orderProductDao.insert(
                        OrderProductEntity(0,orderId,it.productId,)
                    )
                }
            }
        }
    }

}