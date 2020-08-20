package com.example.golivewebactivity

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.*
import kotlinx.android.synthetic.main.activity_go_live_web.*
import pub.devrel.easypermissions.EasyPermissions

class GoLiveWebActivity : AppCompatActivity(),EasyPermissions.PermissionCallbacks {

    companion object{
        private const val REQUEST_CAMERA_PERMISSION = 1
        private const val REQUEST_AUDIO_PERMISSION=2
        private const val TAG = "GoLiveWebActivity"
        private const val MAIN_URL="https://live.teach-r.com/#/welcome"
        private val PERM_CAMERA =
            arrayOf<String>(Manifest.permission.CAMERA, Manifest.permission.CAMERA)
        private val PERM_AUDIO= arrayOf(
            Manifest.permission.RECORD_AUDIO, Manifest.permission.CAPTURE_AUDIO_OUTPUT,
            Manifest.permission.MODIFY_AUDIO_SETTINGS)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_live_web)

        web_view_home.apply {
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    Log.d(TAG, "shouldOverrideUrlLoading: ${request?.url}")
                    return super.shouldOverrideUrlLoading(view, request)
                }

            }

            webChromeClient = object : WebChromeClient() {

                override fun onPermissionRequest(request: PermissionRequest?) {
                   // super.onPermissionRequest(request)
                    Log.d(TAG, "onPermissionRequest: ")
                    if(!hasCameraPermission())
                    {

                        EasyPermissions.requestPermissions(
                            this@GoLiveWebActivity ,
                            "This app needs access to your camera so you can take pictures.",
                            REQUEST_CAMERA_PERMISSION,
                            *PERM_CAMERA
                        )

                    }
                    if(!hasAudioPermission())
                    {
                        EasyPermissions.requestPermissions(
                            this@GoLiveWebActivity,
                            "This app needs access to your audio so you can use microphone",
                            REQUEST_AUDIO_PERMISSION,
                            *PERM_AUDIO
                        )
                    }
                    request?.grant(request?.resources)
                }





                override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                    return super.onConsoleMessage(consoleMessage)
                }
                override fun onConsoleMessage(
                    message: String?,
                    lineNumber: Int,
                    sourceID: String?
                ) {
                    super.onConsoleMessage(message, lineNumber, sourceID)

                }

            }
        }


        //WebSettings for WebView
        var webSettings = web_view_home.settings

        webSettings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
            useWideViewPort = true
            setRenderPriority(WebSettings.RenderPriority.HIGH)
            setAppCacheEnabled(true)
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

        }


        web_view_home.loadUrl(MAIN_URL)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(
            this,
            *PERM_CAMERA
        )
    }
    private  fun hasAudioPermission():Boolean{
        return EasyPermissions.hasPermissions(
            this,
            *PERM_AUDIO
        )
    }

    override fun onBackPressed() {
        if(web_view_home.canGoBack())
            web_view_home.goBack()
        else
        super.onBackPressed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

    }
}