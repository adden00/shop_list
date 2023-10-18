package com.adden00.shopping_list.core.utills

interface OnClickListener<T> {
        fun onClick(item: T)
        fun onLongClick(item: T)
    }