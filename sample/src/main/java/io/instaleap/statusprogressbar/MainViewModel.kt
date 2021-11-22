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
                ProductState.Added.rawType,
                (_addedQuantity.value ?: ZERO_ITEMS),
                StateColor.Added.color
            )
        )
        dataModelStateList.add(
            DataModelState(
                ProductState.Pending.rawType,
                (_pendingQuantity.value ?: ZERO_ITEMS),
                StateColor.Pending.color
            )
        )
        dataModelStateList.add(
            DataModelState(
                ProductState.Removed.rawType,
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

    private fun getTotalValue(dataList: List<DataModelState>) : Int {
        var totalValue = ZERO_ITEMS
        for (model in dataList) {
            totalValue += model.value
        }
        return totalValue
    }

    private fun getRandomValue(): Int {
        return (INIT_RANDOM_VALUE..END_RANDOM_VALUE).random()
    }

    private fun createDataModelStateList(): Pair<List<DataModelState>, Int> {
        val dataList = listOf(
            DataModelState(ProductState.Added.rawType, getRandomValue(), StateColor.Added.color),
            DataModelState(ProductState.Pending.rawType, getRandomValue(), StateColor.Pending.color),
            DataModelState(ProductState.Removed.rawType, getRandomValue(), StateColor.Removed.color),
        )
        return Pair(dataList, getTotalValue(dataList))
    }

    private fun setListDataModelView() {
        val dataModelList: MutableList<DataModelView> = mutableListOf()

        for (i in ZERO_RANGE..TEN_RANGE) {
            val dataModelState = createDataModelStateList()
            dataModelList.add(DataModelView(dataModelState.first, dataModelState.second))
        }

        _dataList.postValue(dataModelList)
    }
}

