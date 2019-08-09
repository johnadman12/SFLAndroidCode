package stock.com.ui.rules_and_winning

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_rules_and_winning.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.rules_and_winning.adapter.RankingAdapter
import stock.com.ui.rules_and_winning.adapter.RulesWinningAdapter

class RulesAndWinningActivity : BaseActivity() {


    private var rulesAdapter:RulesWinningAdapter?=null;
    private var rankingAdapter:RankingAdapter?=null;

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules_and_winning)

        imgBtnClose.setOnClickListener {
            onBackPressed()
        }

        rulesAdapter= RulesWinningAdapter(applicationContext);
        rankingAdapter= RankingAdapter(applicationContext);

        val llm = LinearLayoutManager(applicationContext)
        val llm1 = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recylerViewRule!!.layoutManager = llm
        recylerViewRule!!.adapter = rulesAdapter;


        recylerViewRanking!!.layoutManager=llm1;
        recylerViewRanking!!.adapter=rankingAdapter;


    }


}
