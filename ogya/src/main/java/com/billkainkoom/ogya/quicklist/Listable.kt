package com.billkainkoom.ogya.quicklist

/**
 * Created by billkainkoom on 06/11/2017.
 */

abstract class Listable(@Transient val identifier: String = "", @Transient val span: Int = 1) {
    abstract fun getListableType(): ListableType?
}


