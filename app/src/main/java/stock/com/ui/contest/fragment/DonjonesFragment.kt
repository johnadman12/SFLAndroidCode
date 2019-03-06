package stock.com.ui.contest.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.content_all_contest.*
import stock.com.R
import stock.com.ui.contest.activity.AllContestActivity
import stock.com.ui.createTeam.activity.myTeam.MyTeamActivity
import stock.com.ui.joinedContest.activity.FixtureJoinedContestActivity

class DonjonesFragment : Fragment(), View.OnClickListener {

    private lateinit var viewThreeFragment: View
    private lateinit var  ctx: AllContestActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewThreeFragment = inflater.inflate(R.layout.one_fragment, container, false)
        ctx = activity as AllContestActivity

        return viewThreeFragment
    }

    private fun initViews() {
        //setAdapter()
        ll_myteam.setOnClickListener(this)
        ll_joinedContests.setOnClickListener(this)
        txt_winning.setOnClickListener(this)
        txt_Winners.setOnClickListener(this)
        txt_Team.setOnClickListener(this)
        txt_EntryFee.setOnClickListener(this)
        sortBySelector(WINNING)
//        txt_Signup.setOnClickListener(this)
    }


   /* @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(ctx)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Contest!!.layoutManager = llm
        rv_Contest!!.adapter = AllContestAdapter(ctx)
    }*/

    private var WINNERS = 1
    private var WINNING = 2
    private var ENTRY_FEE = 3
    private var TEAMS = 4
    fun sortBySelector(value: Int) {
        txt_winning.isSelected = false
        txt_Winners.isSelected = false
        txt_Team.isSelected = false
        txt_EntryFee.isSelected = false
        when (value) {
            WINNERS -> {
                txt_Winners.isSelected = true
            }
            WINNING -> {
                txt_winning.isSelected = true
            }
            ENTRY_FEE -> {
                txt_EntryFee.isSelected = true
            }
            TEAMS -> {
                txt_Team.isSelected = true
            }
        }

    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.txt_winning -> {
                sortBySelector(WINNING)
            }
            R.id.txt_Winners -> {
                sortBySelector(WINNERS)
            }
            R.id.txt_Team -> {
                sortBySelector(TEAMS)
            }
            R.id.txt_EntryFee -> {
                sortBySelector(ENTRY_FEE)
            }
            R.id.ll_myteam -> {
                ctx.startActivity(Intent(ctx, MyTeamActivity::class.java))
            }
            R.id.ll_joinedContests -> {
                ctx.startActivity(Intent(ctx, FixtureJoinedContestActivity::class.java))
            }
//            R.id.txt_Signup -> {
//                startActivity(Intent(this, SignUpActivity::class.java))
//            }
        }
    }

}