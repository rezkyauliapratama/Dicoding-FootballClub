package android.rezkyaulia.com.hellokotlin.di.viewmodel

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by Rezky Aulia Pratama on 15/8/18.
 */

@MapKey
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value : KClass<out ViewModel>)