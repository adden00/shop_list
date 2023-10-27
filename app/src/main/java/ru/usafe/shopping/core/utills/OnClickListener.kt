package ru.usafe.shopping.core.utills

interface OnClickListener<T> {
        fun onClick(item: T)
        fun onLongClick(item: T)
    }