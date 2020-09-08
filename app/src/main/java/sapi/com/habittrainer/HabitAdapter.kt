package sapi.com.habittrainer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_card.view.*


class HabitAdapter(val habits : List<Habit>) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    /**
     * It is the main part where the performance comes from because view holder is responsible
     * for minimizing the cost to findViewById which can require an expensive operation.
     */
    class HabitViewHolder(val view : View) : RecyclerView.ViewHolder(view)

    /**
     * This method is gonna be called whenever the recycler view has to create a new object of this
     * view holder type. And each of those view holder is gonna contain the layout of one of the
     * cards.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitAdapter.HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_card, parent, false)
        return HabitViewHolder(view = view)
    }

    override fun onBindViewHolder(holder: HabitAdapter.HabitViewHolder, position: Int) {
        holder.view.txv_act_main_title.text = habits.get(position).title
        holder.view.txv_act_main_description.text = habits.get(position).description
        holder.view.img_act_main.setImageBitmap(habits.get(position).image)

    }

    override fun getItemCount(): Int = habits.size

}