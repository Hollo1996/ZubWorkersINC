package boss.zubworkersinc.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import boss.zubworkersinc.R
import boss.zubworkersinc.graphics.base.WorkerRepresentation
import kotlinx.android.synthetic.main.worker_column.view.*

class WorkerListAdapter(val context: Context,val items: MutableList<WorkerRepresentation>): RecyclerView.Adapter<WorkerListAdapter.WorkerHolder>(){


    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: WorkerHolder, position: Int) {
        val name = items[holder.adapterPosition].Owner.GetName()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.worker_column , parent, false
        )
        return WorkerHolder(view)
    }



    class WorkerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name:TextView=view.nameTV
        val image:ImageButton=view.pictureIB


    }
}