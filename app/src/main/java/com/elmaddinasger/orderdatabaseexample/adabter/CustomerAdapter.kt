package com.elmaddinasger.orderdatabaseexample.adabter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmaddinasger.orderdatabaseexample.databinding.ItemCustomerBinding

class CustomerAdapter (val customerList: List<CustomerModel>) : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return customerList.size
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = customerList[position]
        holder.bind(customer)
    }

    inner class CustomerViewHolder(private val binding: ItemCustomerBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (customer: CustomerModel){
            binding.txtvwCustomerName.text = customer.customerName
        }
    }
}