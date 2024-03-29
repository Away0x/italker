package net.away0x.italker.push.frags.media;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import net.away0x.italker.common.app.tools.UiTool;
import net.away0x.italker.common.app.widget.GalleryView;
import net.away0x.italker.push.R;

/**
 * 图片选择
 */
public class GalleryFragment extends BottomSheetDialogFragment implements GalleryView.SelectedChangeListener {

    private GalleryView mGallery;
    private OnSelectedListener mListener;


    public GalleryFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TransStatusBottomSheetDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        mGallery = (GalleryView) root.findViewById(R.id.galleryView);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGallery.setup(getLoaderManager(),this);
    }

    @Override
    public void onSelectedCountChanged(int count) {
        // 如果选中了一张图片
        if (count > 0) {
            dismiss(); // 隐藏自己
            if (mListener != null) {
                // 得到所有的选中的图片的路径
                String[] paths = mGallery.getSelectedPath();
                // 返回第一张
                mListener.onSelectedImage(paths[0]);
                // 取消和唤起者之间的引用，加快内存回收
                mListener = null;
            }
        }
    }

    /**
     * 设置事件监听
     * @param listener
     * @return
     */
    public GalleryFragment setListener(OnSelectedListener listener) {
        mListener = listener;
        return this;
    }

    /**
     * 选中图片的监听器
     */
    public interface OnSelectedListener {
        void onSelectedImage(String path);
    }

    /**
     * 为了解决顶部状态栏变黑而写的TransStatusBottomSheetDialog
     */
    public static class TransStatusBottomSheetDialog extends BottomSheetDialog {

        public TransStatusBottomSheetDialog(@NonNull Context context) {
            super(context);
        }

        public TransStatusBottomSheetDialog(@NonNull Context context, int theme) {
            super(context, theme);
        }

        protected TransStatusBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            final Window window = getWindow();
            if (window == null)
                return;


            // 得到屏幕高度
            int screenHeight = UiTool.getScreenHeight(getOwnerActivity());
            // 得到状态栏的高度
            int statusHeight = UiTool.getStatusBarHeight(getOwnerActivity());

            // 计算dialog的高度并设置
            int dialogHeight = screenHeight - statusHeight;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    dialogHeight <= 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);

        }
    }
}
