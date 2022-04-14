import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab8.R
import com.example.lab8.model.WordViewModel

class MyListAdapter(private val wordViewModel: WordViewModel): RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
    private var myWordList = wordViewModel.defList.value

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //create an instance of LayoutInflater
        val layoutInflater = LayoutInflater.from(parent.context)
        //inflate the view
        val itemViewHolder = layoutInflater.inflate(R.layout.word_definition_layout, parent, false)
        return ViewHolder(itemViewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //get data at the position
        val definition = myWordList?.get(position)
        //set the text of the textview to the name
        val definitionTitle = "Definition " + (position + 1).toString() + ": "
        holder.titleTextView.text = definitionTitle + definition?.definition ?: ""
    }

    override fun getItemCount(): Int {
        if (myWordList != null) {
            return myWordList!!.size
        } else return 0
    }

    fun update() {
        myWordList = wordViewModel.defList.value
        notifyDataSetChanged()
    }
}