package io.instaleap.statusprogressbar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _dataModelView = MutableLiveData<DataModelView>()
    val dataModelView: LiveData<DataModelView> = _dataModelView

    private val _dataList = MutableLiveData<List<DataModelView>>()
    val dataList: LiveData<List<DataModelView>> = _dataList

    private val _pendingQuantity = MutableLiveData(10)
    private val _addedQuantity = MutableLiveData(0)
    private val _removedQuantity = MutableLiveData(0)

    private val dataModelStateList: MutableList<DataModelState> = mutableListOf()

    init {
        updateDataList()
        setListDataModelView()
    }

    fun addItem() {
        _addedQuantity.value = (_addedQuantity.value ?: 0) + 1
        _pendingQuantity.value = (_pendingQuantity.value ?: 0) - 1
        updateDataList()
    }

    fun removeItem() {
        _removedQuantity.value = (_removedQuantity.value ?: 0) + 1
        _pendingQuantity.value = (_pendingQuantity.value ?: 0) - 1
        updateDataList()
    }

    fun pendingItem() {
        _pendingQuantity.value = (_pendingQuantity.value ?: 0) + 1
        _removedQuantity.value = (_removedQuantity.value ?: 0) - 1
        updateDataList()
    }

    private fun updateDataList() {
        dataModelStateList.clear()
        dataModelStateList.add(
            DataModelState(
                "added",
                (_addedQuantity.value ?: 0),
                StateColor.ADDED.color
            )
        )
        dataModelStateList.add(
            DataModelState(
                "pendidng",
                (_pendingQuantity.value ?: 0),
                StateColor.PENDING.color
            )
        )
        dataModelStateList.add(
            DataModelState(
                "removed",
                (_removedQuantity.value ?: 0),
                StateColor.REMOVED.color
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
        return (_addedQuantity.value ?: 0) + (_removedQuantity.value ?: 0) + (_pendingQuantity.value
            ?: 0)
    }

    private fun setListDataModelView() {
        val dataModelList = listOf(
            DataModelView(
                dataList = listOf(
                    DataModelState("added", 5, StateColor.ADDED.color),
                    DataModelState("added", 15, StateColor.PENDING.color),
                    DataModelState("added", 5, StateColor.REMOVED.color),
                ),
                totalValue = 20
            ),
            DataModelView(
                dataList = listOf(
                    DataModelState("added", 5, StateColor.ADDED.color),
                    DataModelState("added", 5, StateColor.PENDING.color),
                    DataModelState("added", 5, StateColor.REMOVED.color),
                ),
                totalValue = 15
            ),
            DataModelView(
                dataList = listOf(
                    DataModelState("added", 0, StateColor.ADDED.color),
                    DataModelState("added", 28, StateColor.PENDING.color),
                    DataModelState("added", 2, StateColor.REMOVED.color),
                ),
                totalValue = 30
            ),
            DataModelView(
                dataList = listOf(
                    DataModelState("added", 2, StateColor.ADDED.color),
                    DataModelState("added", 6, StateColor.PENDING.color),
                    DataModelState("added", 2, StateColor.REMOVED.color),
                ),
                totalValue = 10
            ),
            DataModelView(
                dataList = listOf(
                    DataModelState("added", 0, StateColor.ADDED.color),
                    DataModelState("added", 3, StateColor.PENDING.color),
                    DataModelState("added", 0, StateColor.REMOVED.color),
                ),
                totalValue = 3
            )
        )

        _dataList.postValue(dataModelList)
    }
}

