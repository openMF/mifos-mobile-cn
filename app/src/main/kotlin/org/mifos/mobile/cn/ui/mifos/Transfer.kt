package org.mifos.mobile.cn.ui.mifos

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_transfer.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseFragment

class Transfer : MifosBaseFragment(), ZXingScannerView.ResultHandler {

    companion object {
        fun newInstance(): Transfer = Transfer()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        getToolbar().setVisibility(View.GONE)

        return inflater.inflate(R.layout.fragment_transfer, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Dexter.withActivity(requireActivity())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object:PermissionListener{
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        qrCodeScanner.setResultHandler(this@Transfer)
                        qrCodeScanner.setAutoFocus(true)
                        qrCodeScanner.startCamera()
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        TODO("Not yet implemented")
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Toast.makeText(requireActivity(),"You should enable this permission",Toast.LENGTH_SHORT).show()
                    }

                }).check()
    }
    override fun handleResult(rawResult: Result?) {
        Toast.makeText(requireActivity(),rawResult?.text,Toast.LENGTH_SHORT).show()
    }
}