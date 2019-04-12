package com.billkainkoom.ogya.quicklist

/**
 * Created by billkainkoom on 06/11/2017.
 */

abstract class Listable(@Transient val identifier: String = "", @Transient val span: Double = 1.0) {
    abstract fun getListableType(): ListableType?
}


