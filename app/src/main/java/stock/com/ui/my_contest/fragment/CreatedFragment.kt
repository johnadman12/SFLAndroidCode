package stock.com.ui.my_contest.fragment

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_created.*
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.ui.my_contest.adapter.CreatedAdapter

class CreatedFragment : BaseFragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_created, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()

    }

    private fun setAdapter(){
        val llm = GridLayoutManager(context,2)
        //llm.orientation = GridLayoutManager(applicationContext,2)
        recycler_my_contest!!.layoutManager = llm
        recycler_my_contest!!.adapter = CreatedAdapter(context!!);
    }



}