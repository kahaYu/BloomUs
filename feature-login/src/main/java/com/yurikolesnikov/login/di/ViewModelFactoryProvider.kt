package com.yurikolesnikov.login.di

import com.yurikolesnikov.login.presentation.logInScreen.LogInScreenViewModel
import dagger.Binds
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {

    fun logInScreenViewModelFactory(): LogInScreenViewModel.LogInScreenViewModelFactory

}