package com.billkainkoom.ogya.quicklist

/**
 * Created by billkainkoom on 06/11/2017.
 */

interface Listable {
    /*
    * Since we are aiming for a flat structure for card type and card subtypes, the instantiation of the card object adapter would have a general type
    * but the individual elements if they are different would have this type set.*/
    fun getListableType(): ListableType?

}
