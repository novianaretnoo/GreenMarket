package com.example.greenmarket.database

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.greenmarket.databinding.ActivityDatabaseBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Database : AppCompatActivity() {

    val db by lazy { TodoDatabase(this) }
    private lateinit var binding: ActivityDatabaseBinding

    private var todoID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatabaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupListener()
        todoID = intent.getIntExtra("intent_id", 0)
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                binding.buttonUpdate.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                binding.buttonSave.visibility = View.GONE
                binding.buttonUpdate.visibility = View.GONE
                getTodo()
            }
            Constant.TYPE_UPDATE -> {
                binding.buttonSave.visibility = View.GONE
                getTodo()
            }
        }
    }

    private fun setupListener() {
        binding.buttonSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.todoDao().addItem(
                    Todo(0, binding.item.text.toString().trim(), binding.price.text.toString().trim())
                )
                finish()
            }
        }
        binding.buttonUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.todoDao().updateItem(
                    Todo(todoID, binding.item.text.toString().trim(), binding.price.text.toString().trim())
                )
                finish()
            }
        }
    }

    fun getTodo() {
        todoID = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val todos = db.todoDao().loadItem(todoID)[0]
            binding.item.setText(todos.item)
            binding.price.setText(todos.price)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}