package org.mifos.mobile.cn.ui.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.data.remote.BaseUrl
import org.mifos.mobile.cn.data.remote.EndPoints
import org.mifos.mobile.cn.data.remote.MifosInterceptor
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BitmapImageViewTarget


class ImageLoaderUtils  constructor(var context: Context) {
    var  preferencesHelper : PreferencesHelper = PreferencesHelper(context)

    fun buildCustomerPortraitImageUrl(customerIdentifier:String?) : String{
        return BaseUrl.defaultBaseUrl+
                EndPoints.API_CUSTOMER_PATH+"/customers"+
                 customerIdentifier+"/portrait"
    }
    fun buildIdentificationScanCardImageUrl(customerIdentifier: String?,identificationNumber:String,scanIdentifier:String):String{
        return (BaseUrl.defaultBaseUrl +
                EndPoints.API_CUSTOMER_PATH + "/customers/"
                + customerIdentifier + "/identifications/"
                + identificationNumber + "/scans/" + scanIdentifier + "/image")
    }

    private fun buildGlideUrl(imageUrl : String): GlideUrl{
       return GlideUrl(imageUrl,LazyHeaders.Builder()
                .addHeader(MifosInterceptor.HEADER_ACCESS_TOKEN,preferencesHelper.getAccessToken())
               .build())

    }
    fun loadImage(imageUrl: String, imageView: ImageView, placeholder: Int){


        val requestOptions : RequestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
                .skipMemoryCache(true)
                .placeholder(placeholder)
                .error(placeholder)
                .centerCrop()


        Glide.with(context)
                        .asBitmap()
                        .load(buildGlideUrl(imageUrl))
                        .apply(requestOptions)
                .into(BitmapImageViewTarget(imageView))


}
}
