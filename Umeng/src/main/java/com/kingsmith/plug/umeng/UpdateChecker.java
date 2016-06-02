package com.kingsmith.plug.umeng;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.umeng.update.*;

/**
 * Update Checker powered by umeng auto update
 * TODO replace the dialog and force update
 *
 * @author chgocn(chgocn@gmail.com)
 */
public class UpdateChecker {

    private static ProgressDialog progressDialog;
    private final static String KEYWORD_FORCE_UPDATE = "forceUpdate";

    /**
     * http://dev.umeng.com/auto-update/android/
     * https://github.com/nxzhou91/umeng-android-sdk-theme/blob/master/blogs/articles/force_update.md
     *
     * @param ctx
     * @param manualCheckUpdate
     * 	手动强制更新
     */
    public static void doUmengCheckUpdateAction(final Context ctx, final boolean manualCheckUpdate) {
        // check update even in 3g network
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        // disable umeng dialog and notification
        UmengUpdateAgent.setUpdateCheckConfig(false);
        // disable auto popup dialog
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, final UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case UpdateStatus.Yes: // has update
                        // 由于Umeng没有强制更新选项，因此用updateLog是否含有KEYWORD_FORCE_UPDATE
                        if (manualCheckUpdate || updateInfo.updateLog.contains(KEYWORD_FORCE_UPDATE) || !UmengUpdateAgent.isIgnore(ctx, updateInfo)) {
                            double targetSize = 0;
                            try {
                                targetSize = Long.parseLong(updateInfo.target_size) / 1024 / 1024;
                            } catch (NumberFormatException nfe) {
                            }
                            CharSequence dialogMsg = ctx.getString(R.string.UMNewVersion) + updateInfo.version + "\n" + ctx.getString(R.string.UMTargetSize) + targetSize + "M"
                                    + "\n\n" + ctx.getString(R.string.UMUpdateContent) + "\n" + updateInfo.updateLog;

                            new MaterialDialog.Builder(ctx)
                                    .title(R.string.UMUpdateTitle)
                                    .content(dialogMsg)
                                    .theme(Theme.LIGHT)
                                    .positiveText(R.string.UMUpdateNow)
                                    .negativeText(R.string.UMNotNow)
                                    .neutralText(R.string.UMIgnore)
                                    .callback(new MaterialDialog.ButtonCallback() {
                                        @Override
                                        public void onPositive(MaterialDialog dialog) {
                                            UmengUpdateAgent.startDownload(ctx, updateInfo);
                                        }

                                        @Override
                                        public void onNegative(MaterialDialog dialog) {
                                            dialog.dismiss();
                                        }

                                        @Override
                                        public void onNeutral(MaterialDialog dialog) {
                                            UmengUpdateAgent.ignoreUpdate(ctx, updateInfo);
                                        }
                                    })
                                    .show();

                            /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
                            alertDialogBuilder.setTitle(R.string.UMUpdateTitle).setMessage(dialogMsg).setCancelable(false)
                                    .setPositiveButton(R.string.UMUpdateNow, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            UmengUpdateAgent.startDownload(ctx, updateInfo);
                                        }
                                    });


                            if (!updateInfo.updateLog.contains(KEYWORD_FORCE_UPDATE)) {
                                alertDialogBuilder.setCancelable(true).setNegativeButton(R.string.UMNotNow, new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setNeutralButton(R.string.UMIgnore, new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        UmengUpdateAgent.ignoreUpdate(ctx, updateInfo);
                                    }
                                });
                            }
                            alertDialogBuilder.create().show();*/
                        }
                        break;
                    case UpdateStatus.No: // has no update
                        if (manualCheckUpdate) {
                            Toast.makeText(ctx, "您使用的已经是最新版本", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case UpdateStatus.NoneWifi: // none wifi
                        if (manualCheckUpdate) {
                            Toast.makeText(ctx, ctx.getString(R.string.UMGprsCondition), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case UpdateStatus.Timeout: // time out
                        if (manualCheckUpdate) {
                            Toast.makeText(ctx, "连接超时,请检查网络后重试", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });
        // 开始检查更新
        UmengUpdateAgent.update(ctx);

        // 下载监听
        UmengUpdateAgent.setDownloadListener(new UmengDownloadListener() {

            @Override
            public void OnDownloadStart() {
                progressDialog = new ProgressDialog(ctx, 0);
                progressDialog.setIndeterminate(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMax(100);
                progressDialog.setMessage(ctx.getString(R.string.umeng_common_download_notification_prefix));
                progressDialog.setProgressNumberFormat("");
                progressDialog.show();
            }

            @Override
            public void OnDownloadUpdate(int progress) {
                if (null != progressDialog) {
                    progressDialog.setProgress(progress);
                }
            }

            @Override
            public void OnDownloadEnd(int result, String file) {
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
            }
        });
    }

}
