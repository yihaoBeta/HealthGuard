package com.yihaobeta.healthguard.ui.profile;

import android.widget.TextView;

import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.base.BaseActivity;
import com.yihaobeta.healthguard.utils.SystemUtils;

import butterknife.BindView;

/**
 * Created by yihaobeta on 2017/4/9.
 */

public class AboutActivity extends BaseActivity {
    @BindView(R.id.version)
    TextView tvVersion;

    @Override
    protected int attachLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void initDagger() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void handleLogic() {
        String version = SystemUtils.getVersion(this);
        if (version != null) {
            tvVersion.setText(version);
        }
    }
}
