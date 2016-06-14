/*
 * Copyright 2015 Eduard Ereza Martínez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.chgocn.plug.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.chgocn.plug.BaseApplication;
import io.chgocn.plug.R;
import io.chgocn.plug.utils.CrashHandler;
import io.chgocn.plug.utils.PLog;


public final class DefaultErrorActivity extends BaseActivity{

    private Button restartBtn;
    private Button moreInfoBtn;
    private ImageView errorImageView;

    @Override
    protected int getContentView() {
        return R.layout.default_error_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        handleCrashMessage();
    }

    private void handleCrashMessage() {
        final Class<? extends Activity> restartActivityClass = CrashHandler.getRestartActivityClassFromIntent(getIntent());
        final CrashHandler.EventListener eventListener = CrashHandler.getEventListenerFromIntent(getIntent());

        if (restartActivityClass != null) {
            restartBtn.setText(R.string.restart_app);
            restartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PLog.e(CrashHandler.getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent()));
                    Intent intent = new Intent(DefaultErrorActivity.this, restartActivityClass);
                    CrashHandler.restartApplicationWithIntent(DefaultErrorActivity.this, intent, eventListener);
                }
            });
        } else {
            restartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PLog.e(CrashHandler.getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent()));
                    CrashHandler.closeApplication(DefaultErrorActivity.this, eventListener);
                }
            });
        }

        if (CrashHandler.isShowErrorDetailsFromIntent(getIntent())) {

            moreInfoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //We retrieve all the error data and show it
                    AlertDialog dialog = new AlertDialog.Builder(DefaultErrorActivity.this)
                            .setTitle(R.string.error_details)
                            .setMessage(CrashHandler.getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent()))
                            .setPositiveButton(R.string.close, null)
                            .setNeutralButton(R.string.copy,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            copyErrorToClipboard();
                                            //Toast.makeText(DefaultErrorActivity.this, R.string.copied, Toast.LENGTH_SHORT).show();
                                            BaseApplication.showToast(R.string.copied);
                                        }
                                    })
                            .show();
                    TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.customactivityoncrash_error_activity_error_details_text_size));
                }
            });
        } else {
            moreInfoBtn.setVisibility(View.GONE);
        }

        int defaultErrorActivityDrawableId = CrashHandler.getDefaultErrorActivityDrawableIdFromIntent(getIntent());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            errorImageView.setImageDrawable(getResources().getDrawable(defaultErrorActivityDrawableId, getTheme()));
        } else {
            //no inspection deprecation
            errorImageView.setImageDrawable(getResources().getDrawable(defaultErrorActivityDrawableId));
        }
    }

    private void initView() {
        restartBtn = (Button) findViewById(R.id.restart);
        moreInfoBtn = (Button) findViewById(R.id.more_info);
        errorImageView = ((ImageView) findViewById(R.id.error_image));
    }

    private void copyErrorToClipboard() {
        String errorInformation = CrashHandler.getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(getString(R.string.clipboard_label), errorInformation);
            clipboard.setPrimaryClip(clip);
        } else {
            //no inspection deprecation
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(errorInformation);
        }
    }
}
