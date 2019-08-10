package net.away0x.italker.push.frags.main;

import android.Manifest;

import net.away0x.italker.common.app.Fragment;
import net.away0x.italker.common.app.widget.GalleryView;
import net.away0x.italker.push.R;

import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

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

        String[] perms = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };

        if (EasyPermissions.hasPermissions(getContext(), perms)) {
        } else {
            EasyPermissions.requestPermissions(this, "授权权限",
                    0x0100, perms);
        }

    }
}
