/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.administrator.archdemo

import android.app.Activity
import android.app.Application
import com.example.administrator.archdemo.base.ActivityLifecycle
import com.example.administrator.archdemo.base.AppManager
import com.example.administrator.archdemo.di.component.DaggerArchComponent
import com.example.administrator.archdemo.di.module.TestModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class ArchApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var aLifecycle: ActivityLifecycle

    @Inject
    lateinit var mAppManager: AppManager

    override fun onCreate() {
        super.onCreate()

        initAppComponent()

        this.registerActivityLifecycleCallbacks(aLifecycle)
    }

    private fun initAppComponent() {
        DaggerArchComponent.builder()
                .testModule(TestModule(this))
                .create(this)
                .inject(this)
    }


    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

    /**
     * 程序终止的时候执行
     */
    override fun onTerminate() {
        super.onTerminate()

        if (mAppManager != null) {//释放资源
            this.mAppManager?.release()
        }
    }
}
