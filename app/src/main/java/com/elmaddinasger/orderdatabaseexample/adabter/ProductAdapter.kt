package com.elmaddinasger.orderdatabaseexample.adabter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elmaddinasger.orderdatabaseexample.databinding.ItemProductBinding

class ProductAdapter(val chanceCount: (product: ProductModel,plusMinus: Boolean) -> Unit): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<ProductModel>(){
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem == newItem
        }

    }

    private val asyncListDiffer = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding  = ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = asyncListDiffer.currentList[position]
        holder.bind(product)
    }


    inner class ProductViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root){
        init {


            binding.fabPlus.setOnClickListener {
                count(true)
                notifyItemChanged(adapterPosition)
                }

            binding.fabMinus.setOnClickListener {
                count(false)
                notifyItemChanged(adapterPosition)
                }
            binding.chbSelected.setOnClickListener {

            }
        }

        fun count (plusMinus: Boolean) {
            chanceCount(asyncListDiffer.currentList[adapterPosition],plusMinus)
            notifyItemChanged(adapterPosition)
        }
        fun bind(product: ProductModel) {
            with(binding) {
                txtvwProductName.text = product.productName
                txtvwQuality.text = product.productQuantity.toString()
                txtvwCount.text = product.productCount.toString()
                chbSelected.isChecked = product.productState
            }
            binding.chbSelected.setOnClickListener {
                product.productState = !product.productState
                notifyItemChanged(adapterPosition)
            }
        }
    }

    fun setList (productList : List<ProductModel>){
        asyncListDiffer.submitList(productList)
    }
}