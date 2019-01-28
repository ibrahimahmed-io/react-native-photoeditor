package co.stackbytes.photoeditor;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import java.io.File;

public class PhotoEditorModule extends ReactContextBaseJavaModule {
    private static final int PHOTO_EDITOR_REQUEST = 255;
    private static final String E_PHOTO_EDITOR_CANCELLED = "E_PHOTO_EDITOR_CANCELLED";

    private Callback mDoneCallback;
    private Callback mCancelCallback;

    public PhotoEditorModule(ReactApplicationContext reactContext) {
        super(reactContext);

        ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
            @Override
            public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
                if (requestCode == PHOTO_EDITOR_REQUEST) {
                    if (mDoneCallback != null) {
                        if (resultCode == Activity.RESULT_CANCELED) {
                            mCancelCallback.invoke();
                        } else {
                            mDoneCallback.invoke(intent.getData().getPath());
                        }

                    }

                    mCancelCallback = null;
                    mDoneCallback = null;
                }
            }
        };
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    public String getName() {
        return "RNPhotoEditor";
    }

    @ReactMethod
    public void Edit(final ReadableMap props, final Callback onDone, final Callback onCancel) {
        Uri path = Uri.fromFile(new File(props.getString("path")));

        Context context = getReactApplicationContext();

        ApplicationInfo app = null;
        String key = null;

        try {
            app = context.getPackageManager().getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (app != null) {
            Bundle bundle = app.metaData;

            key = bundle.getString("photoeditor-key");
        }

        Intent intent = new Intent(getCurrentActivity(), DsPhotoEditorActivity.class);
        intent.setData(path);
        intent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_API_KEY, key);

        mCancelCallback = onCancel;
        mDoneCallback = onDone;

        getCurrentActivity().startActivityForResult(intent, PHOTO_EDITOR_REQUEST);
    }
}
