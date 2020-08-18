package br.com.roomwordsample.view.activitys

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.roomwordsample.R
import br.com.roomwordsample.entity.Word
import br.com.roomwordsample.view.adapters.WordListAdapter
import br.com.roomwordsample.viewModel.WordViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: WordViewModel

    private val newWordActivityRequestCode = 1
    lateinit var wordAdapter: WordListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        wordAdapter = WordListAdapter(this)
        recyclerview?.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }

        handleObservables()
        handleListeners()
    }

    private fun handleObservables() {
        viewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { wordAdapter.setWords(it) }
        })
    }

    private fun handleListeners() {
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

        wordAdapter.onClick = {
            openDeleteDialog(it, this)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                val word = Word(it)
                viewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }

    private fun openDeleteDialog(
        word: Word,
        context: Context
    ) {
        val dialog = AlertDialog.Builder(context)

        dialog.setIcon(R.drawable.ic_baseline_warning_24)
        dialog.setTitle("Você deseja apagar essa palavra?")

        dialog.setPositiveButton("Sim") { _, _ ->
            viewModel.delete(word)
        }

        dialog.setNegativeButton("Não") { dialog, _ ->
            dialog.dismiss()
        }

        dialog.create()
        dialog.show()
    }
}