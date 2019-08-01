package com.verbatoria.ui.schedule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.schedule.ScheduleComponent
import com.verbatoria.domain.schedule.ScheduleDataSource
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.common.adaptivetablelayout.AdaptiveTableLayout

/**
 * @author n.remnev
 */

interface ScheduleView : BaseView {

    fun setSchedule(scheduleDataSource: ScheduleDataSource)

    fun updateScheduleCellAfterClicked(row: Int, column: Int)

    fun close()

    interface Callback {

        fun onNavigationClicked()

    }

}

class ScheduleActivity : BasePresenterActivity<ScheduleView, SchedulePresenter, ScheduleActivity, ScheduleComponent>(), ScheduleView {

    companion object {

        fun createIntent(context: Context): Intent =
            Intent(context, ScheduleActivity::class.java)

    }

    private lateinit var toolbar: Toolbar
    private lateinit var adaptiveTableLayout: AdaptiveTableLayout
    private var scheduleAdapter: ScheduleAdapter? = null

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_schedule

    override fun buildComponent(injector: Injector, savedState: Bundle?): ScheduleComponent =
        injector.plusScheduleComponent()
            .build()

    override fun initViews(savedState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)
        adaptiveTableLayout = findViewById(R.id.schedule_adaptive_table_layout)

        toolbar.setTitle(R.string.schedule_title)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener {
            presenter.onNavigationClicked()
        }
    }

    //endregion

    //region ScheduleView

    override fun setSchedule(scheduleDataSource: ScheduleDataSource) {
        scheduleAdapter = ScheduleAdapter(this, adaptiveTableLayout.width, adaptiveTableLayout.height, scheduleDataSource)
        scheduleAdapter?.onItemClickListener = presenter
        adaptiveTableLayout.setAdapter(scheduleAdapter)
    }

    override fun updateScheduleCellAfterClicked(row: Int, column: Int) {
        scheduleAdapter?.notifyItemChanged(row, column)

    }

    override fun close() {
        finish()
    }

    //endregion

}

//
//    public AdaptiveTableLayout mScheduleLayout;
//
//    ISchedulePresenter mSchedulePresenter;
//
//    private ScheduleAdapter mScheduleAdapter;
//
//    public static Intent newInstance(Context mContext) {
//        Intent intent = new Intent(mContext, ScheduleActivity.class);
//        return intent;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
////        VerbatoriaApplication.getInjector().addModule(new ScheduleModule()).inject(this);
//
//        setContentView(R.layout.activity_schedule);
//
//        mSchedulePresenter.bindView(this);
//        setPresenter((BasePresenter) mSchedulePresenter);
//
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_toolbar_schedule, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                break;
//            case R.id.action_prev_week:
//                mSchedulePresenter.onPreviousWeekClicked();
//                break;
//            case R.id.action_next_week:
//                mSchedulePresenter.onNextWeekClicked();
//                break;
//            case R.id.action_clear:
//                mSchedulePresenter.onClearScheduleClicked();
//                break;
//            case R.id.action_save:
//                mSchedulePresenter.onSaveScheduleClicked();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//
//    @Override
//    public void notifyItemChanged(int row, int column, boolean state) {
//        mScheduleAdapter.notifyItemChanged(row, column, state);
//    }
//
//    @Override
//    public void notifyScheduleCleared() {
//        Helper.showShortHintSnackBar(mScheduleLayout, getString(R.string.schedule_cleared));
//        mScheduleLayout.notifyDataSetChanged();
//    }
//
//    @Override
//    public void showScheduleClearConfirmation() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this)
//                .setTitle(getString(R.string.confirmation))
//                .setMessage(getString(R.string.schedule_clear_confirm_message));
//        builder.setPositiveButton(getString(R.string.clear), (dialog, which) -> {
//            mSchedulePresenter.clearSchedule();
//        });
//        builder.setNegativeButton(getString(R.string.cancel), null);
//        builder.create().show();
//    }
//
//    @Override
//    public void showError(String error) {
//        Helper.showErrorSnackBar(mScheduleLayout, error);
//    }
//
//    @Override
//    public void showScheduleSaved() {
//        Helper.showShortHintSnackBar(mScheduleLayout, getString(R.string.schedule_saved));
//    }
//
//    @Override
//    public void showScheduleLoaded(String period) {
//        Helper.showShortHintSnackBar(mScheduleLayout, String.format(getString(R.string.schedule_loaded_by_period), period));
//    }
//
//    @Override
//    public void confirmSaveSchedule() {
//        View dialogRootView = getLayoutInflater().inflate(R.layout.dialog_save_schedule, null);
//        RadioButton noOption = dialogRootView.findViewById(R.id.no_option);
//        RadioButton firstOption = dialogRootView.findViewById(R.id.first_option);
//        RadioButton secondOption = dialogRootView.findViewById(R.id.second_option);
//        RadioButton thirdOption = dialogRootView.findViewById(R.id.third_option);
//        RadioButton fourthOption = dialogRootView.findViewById(R.id.fourth_option);
//        AlertDialog builder = new AlertDialog.Builder(this)
//                .setView(dialogRootView)
//                .setTitle(getString(R.string.schedule_saving))
//                .setPositiveButton(getString(R.string.save), (dialog, which) -> {
//                    int selectedWeeks = noOption.isChecked() ? 0 :
//                            firstOption.isChecked() ? 1 :
//                                    secondOption.isChecked() ? 2 :
//                                            thirdOption.isChecked() ? 3 :
//                                                    fourthOption.isChecked() ? 4 : 5;
//                    mSchedulePresenter.saveSchedule(selectedWeeks);
//                })
//                .setNegativeButton(getString(R.string.cancel), null)
//                .create();
//        noOption.setChecked(true);
//        builder.show();
//    }
//