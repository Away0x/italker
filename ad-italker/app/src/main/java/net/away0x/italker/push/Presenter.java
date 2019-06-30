package net.away0x.italker.push;

import android.text.TextUtils;

public class Presenter implements IPresenter {

    private IView mView;

    public Presenter(IView view) {
        mView = view;
    }

    @Override
    public void search() {
        String inputString = mView.getInputString();
        if (TextUtils.isEmpty(inputString)) {
            return;
        }

        String result = "Result: " + inputString.hashCode();
        mView.setResultString(result);
    }
}
