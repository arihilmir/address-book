package dev.rlevkovych.addressbook.groupslistactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import dev.rlevkovych.addressbook.R
import kotlinx.android.synthetic.main.activity_groups_list.*

class GroupsListActivity : AppCompatActivity() {

    private lateinit var viewModel: GroupsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groups_list)
        viewModel = ViewModelProviders.of(this).get(GroupsListViewModel::class.java)

        groups_list.apply {
            layoutManager = LinearLayoutManager(this@GroupsListActivity)
            adapter = GroupsListAdapter(viewModel.availableGroups)
        }
    }


}
