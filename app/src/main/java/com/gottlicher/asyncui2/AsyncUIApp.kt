package com.gottlicher.asyncui2

import android.app.Application
import com.markodevcic.peko.PermissionRequester

class AsyncUIApp: Application() {
    override fun onCreate() {
        super.onCreate()
        PermissionRequester.initialize(this)
    }
}
