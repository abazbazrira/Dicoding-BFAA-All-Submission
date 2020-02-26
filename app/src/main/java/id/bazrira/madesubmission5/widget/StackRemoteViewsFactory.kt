package id.bazrira.madesubmission5.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.bazrira.madesubmission5.R
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.CONTENT_URI
import java.util.concurrent.ExecutionException


internal class StackRemoteViewsFactory(
    private val mContext: Context
) :
    RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Bitmap>()

    override fun onCreate() {
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        val identityToken: Long = Binder.clearCallingIdentity()

        val cursor = mContext.contentResolver?.query(CONTENT_URI, null, null, null, null)
        cursor?.apply {
            while (moveToNext()) {
                try {
                    val preview: Bitmap = Glide.with(mContext)
                        .asBitmap()
                        .load(this.getString(this.getColumnIndex("poster")).toString())
                        .apply(RequestOptions().fitCenter())
                        .submit()
                        .get()

                    mWidgetItems.add(
                        preview
                    )
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } catch (e: ExecutionException) {
                    e.printStackTrace()
                }
            }
        }

//        cursor?.close()

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])
        val extras = bundleOf(
            FavoriteWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
    }
}