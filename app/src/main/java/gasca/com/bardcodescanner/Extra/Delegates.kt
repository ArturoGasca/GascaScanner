package gasca.com.bardcodescanner.Extra

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by in-qu on 29/10/2017.
 */
object Delegates {

    private class CustomDelegate<T>(initializer: () -> T) : ReadWriteProperty<Any?, T> {

        private var initializer: (() -> T)? = initializer
        private var value: T? = null

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value ?: initializer!!()
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this.value = value
        }

    }

    fun <T> lazyComponent(initilizer: () -> T): ReadWriteProperty<Any?, T> = CustomDelegate(initilizer)

}