package com.adden00.testtaskunisafe.core.utills

interface OnClickListener<T> {
        fun onClick(item: T)
        fun onLongClick(item: T)
    }