/*
 * Copyright (c) 2020.  dev-eth0.de All rights reserved.
 */

package de.dev.eth0.prun.impl.util

import kotlin.math.pow
import kotlin.math.roundToInt

class MathUtil {
  companion object {

    fun round(value: Double, digits: Int): Double {
      val factor = 10.0.pow(digits)
      return (value * factor).roundToInt() / factor
    }
  }
}