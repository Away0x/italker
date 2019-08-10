package net.away0x.italker.push.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import net.away0x.italker.common.app.Activity;
import net.away0x.italker.common.app.Fragment;
import net.away0x.italker.push.R;
import net.away0x.italker.push.frags.account.UpdateInfoFragment;

public class AccountActivity extends Activity {

    private Fragment mCurFragment;

    /**
     * 账户 activity 显示的入口
     * @param context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mCurFragment = new UpdateInfoFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCurFragment.onActivityResult(requestCode, resultCode, data);
    }
}
