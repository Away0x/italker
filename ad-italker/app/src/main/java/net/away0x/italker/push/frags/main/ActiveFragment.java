package net.away0x.italker.push.frags.main;

import net.away0x.italker.common.app.Fragment;
import net.away0x.italker.common.app.widget.GalleryView;
import net.away0x.italker.push.R;

import butterknife.BindView;

public class ActiveFragment extends Fragment {

    @BindView(R.id.gallery)
    GalleryView mGallery;


    public ActiveFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initData() {
        super.initData();

        mGallery.setup(getLoaderManager(), new GalleryView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {

            }
        });
    }
}
