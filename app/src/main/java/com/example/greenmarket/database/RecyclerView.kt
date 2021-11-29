package com.example.greenmarket.database

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenmarket.databinding.ActivityRecyclerViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecyclerView : AppCompatActivity() {

    val db by lazy { TodoDatabase(this) }
    lateinit var recyclerViewAdapter : RecyclerViewAdapter

    private lateinit var binding : ActivityRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
        setupRecyclerView()

    }

    override fun onStart() {
        super.onStart()
        loadTodos()
    }

    fun loadTodos(){
        CoroutineScope(Dispatchers.IO).launch {
            val todos = db.todoDao().loadItems()
            Log.d("RecyclerView","dbResponse: $todos")
            withContext(Dispatchers.Main){
                recyclerViewAdapter.setData(todos)
            }
        }
    }

    private fun setupListener() {
        binding.buttonAdd.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(todoID : Int, intentType : Int){
        startActivity(Intent(this@RecyclerView, Database::class.java)
            .putExtra("intent_id", todoID)
            .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = RecyclerViewAdapter(arrayListOf(), object : RecyclerViewAdapter.OnAdapterChangeListener{
            override fun onRead(todo: Todo) {
                intentEdit(todo.id, Constant.TYPE_READ)
            }

            override fun onUpdate(todo: Todo) {
                intentEdit(todo.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(todo: Todo) {
                deleteDialog(todo)
            }
        })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = recyclerViewAdapter
        }
    }

    private fun deleteDialog(todo : Todo){
        val alertDeleteDialog = AlertDialog.Builder(this)
        alertDeleteDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin untuk menghapus ${todo.item}")
            setNegativeButton("Batal") { dialogInterface, i -> dialogInterface.dismiss() }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.todoDao().deleteItem(todo)
                    loadTodos()
                }
            }
        }
        alertDeleteDialog.show()
    }
}