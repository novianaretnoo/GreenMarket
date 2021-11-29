package com.example.greenmarket.database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenmarket.databinding.AdapterRecyclerViewBinding

class RecyclerViewAdapter(private val todos : ArrayList<Todo>, private val listener : OnAdapterChangeListener) : RecyclerView.Adapter<RecyclerViewAdapter.TodoViewHolder>() {
    class TodoViewHolder(val binding: AdapterRecyclerViewBinding): RecyclerView.ViewHolder(binding.root)
    interface OnAdapterChangeListener {
        fun onRead(todo : Todo)
        fun onUpdate(todo : Todo)
        fun onDelete(todo : Todo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            AdapterRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]
        holder.binding.textTitle.text = todo.item
        holder.binding.textTitle.setOnClickListener {
            listener.onRead(todo)
        }
        holder.binding.iconEdit.setOnClickListener {
            listener.onUpdate(todo)
        }
        holder.binding.iconDelete.setOnClickListener {
            listener.onDelete(todo)
        }
    }

    override fun getItemCount() = todos.size

    fun setData(list: List<Todo>){
        todos.clear()
        todos.addAll(list)
        notifyDataSetChanged()
    }
}
