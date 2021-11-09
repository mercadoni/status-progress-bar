package io.instaleap.statusprogressbar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _dataModelView = MutableLiveData<DataModelView>()
    val dataModelView: LiveData<DataModelView> = _dataModelView

    private val _dataList = MutableLiveData<List<DataModelView>>()
    val dataList: LiveData<List<DataModelView>> = _dataList

    private val _pendingQuantity = MutableLiveData(TOTAL_ITEMS_EXAMPLE)
    private val _addedQuantity = MutableLiveData(ZERO_ITEMS)
    private val _removedQuantity = MutableLiveData(ZERO_ITEMS)

    private val dataModelStateList: MutableList<DataModelState> = mutableListOf()

    init {
        updateDataList()
        setListDataModelView()
    }

    fun addItem() {
        _addedQuantity.value = increaseOneItem(_addedQuantity.value)
        _pendingQuantity.value = decreaseOneItem(_pendingQuantity.value)
        updateDataList()
    }

    fun removeItem() {
        _removedQuantity.value = increaseOneItem(_removedQuantity.value)
        _pendingQuantity.value = decreaseOneItem (_pendingQuantity.value)
        updateDataList()
    }

    fun pendingItem() {
        _pendingQuantity.value = increaseOneItem(_pendingQuantity.value)
        _removedQuantity.value = decreaseOneItem(_removedQuantity.value)
        updateDataList()
    }

    private fun increaseOneItem(value: Int?) : Int {
        return (value ?: ZERO_ITEMS) + ONE_UNIT
    }

    private fun decreaseOneItem(value: Int?) : Int {
        return (value ?: ZERO_ITEMS) - ONE_UNIT
    }

    private fun updateDataList() {
        dataModelStateList.clear()
        dataModelStateList.add(
            DataModelState(
                "added",
                (_addedQuantity.value ?: ZERO_ITEMS),
                StateColor.Added.color
            )
        )
        dataModelStateList.add(
            DataModelState(
                "pendidng",
                (_pendingQuantity.value ?: ZERO_ITEMS),
                StateColor.Pending.color
            )
        )
        dataModelStateList.add(
            DataModelState(
                "removed",
                (_removedQuantity.value ?: ZERO_ITEMS),
                StateColor.Removed.color
            )
        )

        _dataModelView.postValue(
            DataModelView(
                dataList = dataModelStateList,
                totalValue = getAllItemsQuantity()
            )
        )
    }

    private fun getAllItemsQuantity(): Int {
        val totalItems = (_addedQuantity.value ?: ZERO_ITEMS) + (_removedQuantity.value ?: ZERO_ITEMS) + (_pendingQuantity.value ?: ZERO_ITEMS)
        return if(totalItems > 0) totalItems else DEFAULT_ITEMS
    }

    private fun setListDataModelView() {
        val dataModelList = listOf(
            DataModelView(
                dataList = listOf(
                    DataModelState("added", 5, StateColor.Added.color),
                    DataModelState("pendidng", 15, StateColor.Pending.color),
                    DataModelState("removed", 5, StateColor.Removed.color),
                ),
                totalValue = 20
            ),
            DataModelView(
                dataList = listOf(
                    DataModelState("added", 5, StateColor.Added.color),
                    DataModelState("pendidng", 5, StateColor.Pending.color),
                    DataModelState("removed", 5, StateColor.Removed.color),
                ),
                totalValue = 15
            ),
            DataModelView(
                dataList = listOf(
                    DataModelState("added", 0, StateColor.Added.color),
                    DataModelState("pendidng", 28, StateColor.Pending.color),
                    DataModelState("removed", 2, StateColor.Removed.color),
                ),
                totalValue = 30
            ),
            DataModelView(
                dataList = listOf(
                    DataModelState("added", 2, StateColor.Added.color),
                    DataModelState("pendidng", 6, StateColor.Pending.color),
                    DataModelState("removed", 2, StateColor.Removed.color),
                ),
                totalValue = 10
            ),
            DataModelView(
                dataList = listOf(
                    DataModelState("added", 0, StateColor.Added.color),
                    DataModelState("pendidng", 3, StateColor.Pending.color),
                    DataModelState("removed", 0, StateColor.Removed.color),
                ),
                totalValue = 3
            )
        )

        _dataList.postValue(dataModelList)
    }
}

