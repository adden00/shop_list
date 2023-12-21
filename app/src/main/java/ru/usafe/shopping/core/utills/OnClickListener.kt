package ru.usafe.shopping.core.utills

interface OnClickListener<T> {
    fun onClick(item: T)
    fun onRemove(item: T)
    fun onEdit(item: T)
}