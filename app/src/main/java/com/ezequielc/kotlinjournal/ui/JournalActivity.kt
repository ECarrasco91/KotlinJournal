package com.ezequielc.kotlinjournal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.get
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.ezequielc.kotlinjournal.R
import com.ezequielc.kotlinjournal.databinding.ActivityJournalBinding
import com.ezequielc.kotlinjournal.ui.dialog.DeleteAllDialogFragment
import com.ezequielc.kotlinjournal.ui.journal.JournalFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JournalActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityJournalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_container)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener(this)

        binding.fab.setOnClickListener {
            val title = it.context.resources.getString(R.string.new_journal_post)
            val direction = JournalFragmentDirections
                .actionJournalFragmentToAddEditJournalFragment(title = title)
            navController.navigate(direction)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                val dialog = DeleteAllDialogFragment()
                dialog.show(supportFragmentManager, "DeleteAllDialogFragment")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_container)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?,
    ) {
        val journalDestination = controller.graph[R.id.JournalFragment]
        val addEditJournalDestination = controller.graph[R.id.AddEditJournalFragment]
        when (destination) {
            journalDestination -> binding.fab.visibility = View.VISIBLE
            addEditJournalDestination -> binding.fab.visibility = View.INVISIBLE
        }
    }
}