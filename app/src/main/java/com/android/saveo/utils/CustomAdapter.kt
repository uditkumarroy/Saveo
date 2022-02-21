package com.android.saveo.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.android.saveo.BR

class CustomAdapter<E : ViewType<*>>(
    private var dataSet: MutableList<E> = mutableListOf(),
    private val onItemActionListener: OnItemActionListener? = null,
    private val viewModel: ViewModel? = null,
) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>(), BindableAdapter<List<E>> {

    private var dataSetAll: MutableList<ViewDataBinding> = mutableListOf()
    private var dataSetIds: MutableList<Int> = mutableListOf()


    class ViewHolder(
        private val viewBinding: ViewDataBinding,
        private val onItemActionListener: OnItemActionListener?,
        private val viewModel: ViewModel?
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bindItem(item: ViewType<*>) {
            viewBinding.setVariable(BR.model, item.data())
            if (item.isUserInteractionEnabled()) {
                viewBinding.setVariable(BR.position, adapterPosition)
                viewBinding.setVariable(BR.actionItemListener, onItemActionListener)
            }
            if (item.isViewModelEnabled()) {
              //  viewBinding.setVariable(BR.viewModel, viewModel)
            }
            viewBinding.executePendingBindings()
        }

        fun getViewBinding(): ViewDataBinding = viewBinding
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                viewType,
                viewGroup,
                false
            )
        return ViewHolder(binding, onItemActionListener, viewModel)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindItem(dataSet[position])
        if (!dataSetIds.contains(position)){
            dataSetAll.add(viewHolder.getViewBinding())
            dataSetIds.add(position)
        }else{
            dataSetAll[position]=(viewHolder.getViewBinding())
        }
    }

    override fun getItemViewType(position: Int): Int = dataSet[position].layoutId()

    override fun getItemCount() = dataSet.size

    override fun setList(list: List<E>) {
        this.dataSet.clear()
        this.dataSet.addAll(list)
        notifyDataSetChanged()
    }

    fun insertElement(data: E, position: Int? = null) {
        if (position != null) {
            this.dataSet.add(position, data)
            notifyItemInserted(position)
        } else {
            this.dataSet.add(data)
            notifyItemInserted(this.dataSet.size - 1)
        }
    }

    fun updateElement(data: E, position: Int) {
        this.dataSet[position] = data
        notifyItemChanged(position)
    }

    fun updateElements(data: List<E>, startIndex: Int) {
        var start = startIndex
        for (i in 0 until data.size) {
            if (start >= this.dataSet.size) {
                this.dataSet.add(data[i])
            } else {
                this.dataSet[start] = data[i]
            }
            start++
        }
        notifyItemRangeChanged(startIndex, data.size)
    }

    fun insertElements(data: List<E>, position: Int? = null) {
        if (position != null) {
            this.dataSet.addAll(position, data)
            notifyItemRangeInserted(position, data.size)
        } else {
            val index = this.dataSet.size - 1
            this.dataSet.addAll(data)
            notifyItemRangeInserted(index, this.dataSet.size - 1)
        }
    }

    fun removeElements(startIndex: Int, endIndex: Int = this.dataSet.size - 1) {
        val iterator = this.dataSet.listIterator(startIndex)
        var end = endIndex
        while (iterator.hasNext()) {
            iterator.next()
            if (startIndex <= end) {
                iterator.remove()
                end--
            } else {
                break
            }
        }


        notifyItemRangeRemoved(startIndex, endIndex - startIndex)
    }

    fun removeElement(index: Int) {
        if (index < dataSet.size) {
            dataSet.removeAt(index)
            notifyItemRemoved(index)
            dataSetIds.removeAt(index)
            dataSetAll.removeAt(index)
        }
    }

    fun updateLastElement(data: E) {
        this.dataSet[this.dataSet.size - 1] = data
        notifyItemChanged(this.dataSet.size - 1)
    }

    fun getViewDataBindingAll(): MutableList<ViewDataBinding> {
        return dataSetAll
    }

    fun getDataAll(): MutableList<E> {
        return dataSet
    }

}


interface OnItemActionListener {
    fun onItemClicked(position: Int)
}

interface OnItemClickListener<T> : OnItemActionListener {
    fun onItemClicked(position: Int, item: T)
}
