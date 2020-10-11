package com.dw.capstonebnform.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.view.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BnFormAppWidgetProvider extends AppWidgetProvider {

    private static final String TAG = BnFormAppWidgetProvider.class.getSimpleName() ;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Log.i(TAG, "updateAppWidget: ");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bn_form_app_widget_provider);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.appwidget_title_label_text, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            Intent intentService = new Intent(context, BnFormAppWidgetService.class);
            intentService.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intentService.setData(Uri.parse(intentService.toUri(Intent.URI_INTENT_SCHEME)));

            //set remove object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bn_form_app_widget_provider);
            views.setRemoteAdapter(R.id.bnform_widget_stack_view, intentService);
            views.setEmptyView(R.id.bnform_widget_stack_view, R.id.bnform_empty_view_id);

            Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
            resizeWidget(appWidgetOptions, views);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.bnform_collection_widget_list_item );

        resizeWidget(newOptions, views);
    }

    private void resizeWidget(Bundle appWidgetOptions, RemoteViews views) {

        int minWidth = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int maxWidth = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int minHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int maxHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);

        if (maxHeight > 100) {
            views.setViewVisibility(R.id.bnform_widget_item_task_name_label, View.VISIBLE);
        } else {
            views.setViewVisibility(R.id.bnform_widget_item_task_name_label, View.GONE);
        }
    }

    public void updateWidgetBnForm(Context context, AppWidgetManager appWidgetManager,
                                   int[] appWidgetIds){

        Log.i(TAG, "updateWidgetBnForm: ");
        this.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

