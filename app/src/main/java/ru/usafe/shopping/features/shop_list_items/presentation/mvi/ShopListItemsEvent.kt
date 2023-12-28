package ru.usafe.shopping.features.shop_list_items.presentation.mvi

sealed class ShopListItemsEvent {
    class LoadAllItems(val listId: Int) : ShopListItemsEvent()
    class AddNewItem(val listId: Int, val name: String) : ShopListItemsEvent()
    class RemoveItem(val listId: Int, val itemId: Int) : ShopListItemsEvent()
    class UpdateItem(val listId: Int, val itemId: Int, val newName: String) : ShopListItemsEvent()

    //    class EditItem(val listId: Int, val itemId: Int, val newName: String) : ShopListItemsEvent()
    class CrossItem(val listId: Int, val itemId: Int) : ShopListItemsEvent()
    class SubscribeOnUpdating(val listId: Int) : ShopListItemsEvent()
    class UnSubscribeOnUpdating(val listId: Int) : ShopListItemsEvent()
    class MoveItem(val startId: Int, val toId: Int, val listId: Int) : ShopListItemsEvent()
    class OnDrag(val startPos: Int, val toPos: Int) : ShopListItemsEvent() {

    }
}
