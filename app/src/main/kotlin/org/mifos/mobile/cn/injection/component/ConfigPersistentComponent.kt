package org.mifos.mobile.cn.injection.component

import dagger.Component
import org.mifos.mobile.cn.injection.ConfigPersistent
import org.mifos.mobile.cn.injection.module.ActivityModule

/**
 * A dagger component that will live during the lifecycle of an Activity but it won't
 * be destroy during configuration changes. Check [@MifosBaseActivity] to see how this components
 * survives configuration changes.
 * Use the [ConfigPersistent] scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface ConfigPersistentComponent {
    fun activityComponent(activityModule: ActivityModule): ActivityComponent
}
