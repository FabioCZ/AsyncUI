package com.gottlicher.asyncuidemo


import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.collection.SparseArrayCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.demo_list_item.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.homeRecycler.layoutManager = LinearLayoutManager(context)
        view.homeRecycler.addItemDecoration(DividerItemDecoration(context!!))

        view.homeRecycler.adapter = DestinationsAdapter(findNavController())
        (view.homeRecycler.adapter as DestinationsAdapter).notifyDataSetChanged()
    }

    class DestinationsAdapter(private val navigationController: NavController): RecyclerView.Adapter<DestinationVH>() {

        private val actions: SparseArrayCompat<NavAction>

        init {
            val destination = navigationController.currentDestination
            val actionsField = NavDestination::class.java.getDeclaredField("mActions")
            actionsField.isAccessible = true
            actions = actionsField.get(destination) as SparseArrayCompat<NavAction>
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationVH =
            DestinationVH(LayoutInflater.from(parent.context).inflate(R.layout.demo_list_item, parent, false))

        override fun getItemCount(): Int = actions.size()

        override fun onBindViewHolder(holder: DestinationVH, position: Int) {
            val dest = navigationController.graph.findNode(actions.valueAt(position).destinationId)
            holder.itemView.demoLabel.text = dest?.label
            holder.itemView.setOnClickListener { holder.itemView.findNavController().navigate(actions.keyAt(position)) }
        }
    }

    class DestinationVH(view: View) : RecyclerView.ViewHolder(view)

    class DividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

        private val divider: Drawable = context.resources.getDrawable(R.drawable.divider)

        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + params.bottomMargin
                val bottom: Int = top + divider.intrinsicHeight
                divider.setBounds(left, top, right, bottom)
                divider.draw(c)
            }
        }
    }
}
