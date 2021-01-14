package org.mifos.mobile.cn.ui.mifos.customerProfile

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.github.therajanmaurya.sweeterror.SweetUIErrorHandler
import kotlinx.android.synthetic.main.activity_customer_profile.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.utils.*
import java.io.ByteArrayOutputStream
import java.util.jar.Manifest

class CustomerProfileActivity: MifosBaseActivity(),CustomerProfileContract.View {

    private lateinit var  customerIdentifier: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_profile)

        customerIdentifier = intent.extras.getString(ConstantKeys.CUSTOMER_IDENTIFIER)
        loadCustomerPortrait()

        showBackButton()
        setToolbarTitle(getString(R.string.customer_image))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_customer_profile,menu)
        Utils.setToolbarIconColor(this,menu,R.color.white)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_customer_profile_share) {
            checkWriteExternalStoragePermission()
            return true
        }
        else return super.onOptionsItemSelected(item)
    }
    fun shareImage(){
        val bitmapUri = getImageUri(this, getBitmapFromView(iv_customer_picture))
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/jpg"
        shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
        startActivity(Intent.createChooser(shareIntent,
                getString(R.string.share_customer_profile)))
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver,
                inImage, customerIdentifier, null)
        return Uri.parse(path)
    }

    fun getBitmapFromView(view: ImageView): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width,
                view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap
    }

    override fun checkWriteExternalStoragePermission() {
        if (CheckSelfPermissionAndRequest.checkSelfPermission(this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            shareImage()
        }
        else{
            requestPermission()
        }
    }

    override fun requestPermission() {
        CheckSelfPermissionAndRequest.requestPermission(
                this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ConstantKeys.PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE,
                resources.getString(
                        R.string.dialog_message_write_permission_for_share_denied_prompt),
                resources.getString(
                        R.string.dialog_message_write_permission_for_share_never_ask_again),
                ConstantKeys.PERMISSIONS_WRITE_EXTERNAL_STORAGE_STATUS)
        if (CheckSelfPermissionAndRequest.checkSelfPermission(this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            shareImage()
        }
    }

    override fun loadCustomerPortrait() {
        val imageLoaderUtils: ImageLoaderUtils = ImageLoaderUtils(this)
        imageLoaderUtils.loadImage(imageLoaderUtils.buildCustomerPortraitImageUrl(
                customerIdentifier),iv_customer_picture,R.drawable.mifos_logo_new
        )
    }

   override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                   grantResults: IntArray) {
        when (requestCode) {
            ConstantKeys.PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    shareImage()
                } else {
                    Toaster.show(findViewById(android.R.id.content),
                            getString(R.string.permission_denied_write))
                }
            }
        }
    }


}