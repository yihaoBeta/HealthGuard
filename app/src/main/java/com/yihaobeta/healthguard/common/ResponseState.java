package com.yihaobeta.healthguard.common;

import com.orhanobut.logger.Logger;
import com.yihaobeta.healthguard.R;
import com.yihaobeta.healthguard.base.BaseApplication;

/**
 * Created by yihaobeta on 2017/4/1.
 * 返回状态类
 */

public class ResponseState {
    private State mState;

    public ResponseState(State state) {
        Logger.d(state.getDescription());
        this.mState = state;
    }

    public ResponseState() {
    }

    public State getState() {
        return this.mState;
    }

    public void setState(State state) {
        this.mState = state;
        Logger.d(state.getDescription());
    }

    public void handleState() {
    }

    public String getStateDescription() {
        return mState.getDescription();
    }

    public enum State {

        NT_STATE_CONNECTION_TIME_OUT(BaseApplication.getContext().getString(R.string.timeout), 0x01),
        NT_STATE_NETWORK_DISCONNECTED(BaseApplication.getContext().getString(R.string.net_disconnected), 0x02),
        NT_STATE_SERVER_ERROR(BaseApplication.getContext().getString(R.string.server_err), 0x03),
        NT_STATE_NO_SUCH_DATA(BaseApplication.getContext().getString(R.string.no_such_data), 0x04),
        NT_STATE_NO_MORE_DATA(BaseApplication.getContext().getString(R.string.no_more_data), 0x05),
        DB_STATE_DELETE_FAIL(BaseApplication.getContext().getString(R.string.delete_fail), 0x06),
        DB_STATE_UPDATE_FAIL(BaseApplication.getContext().getString(R.string.update_fail), 0x07),
        DB_STATE_NO_SUCH_DATA(BaseApplication.getContext().getString(R.string.no_such_data), 0x08),
        DB_STATE_QUERY_FAIL(BaseApplication.getContext().getString(R.string.query_fail), 0x09),
        STATE_COMMON_FAIL(BaseApplication.getContext().getString(R.string.load_fail), 0x0A),
        STATE_UNKNOWN_ERROR(BaseApplication.getContext().getString(R.string.unknown_error), 0x0B);

        private final String description;
        private final int mId;

        State(String description, int id) {
            this.description = description;
            mId = id;
        }

        public String getDescription() {
            return description;
        }

        public int getId() {
            return mId;
        }
    }

}
