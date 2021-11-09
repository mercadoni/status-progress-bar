How to integrate the library in the app?

Step 1. Add it in root build.gradle at the end of repositories:
    
    allprojects {
        repositories {
          TODO: pending
        }
    }

Step 2. Add the dependency
    
    dependencies {
        TODO: pending
    }

Step 3. Add component to Layout file

     <com.shopper.customviews.LinearStateBar
        android:id="@+id/linear_bar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
        
Step 4. Custom the view.

    app:fontSize="12" -> Can modify the fontSize
    app:heightLineStatus="10" -> Can modify thw height of LinearBar


Step 5.  Refer this View in your activity/fragment file,
         set a List<DataModelState> and the total value of items.

            DataModelView(
                val dataList: List<DataModelState>,
                val totalValue: Int
            )

            DataModelState(
                val label: String,
                val value: Int,
                val color: Int
            )

             - The second param on DataModelView is the entire span linear bar view. 
             - And value param in each DataModelState object will occupy a percent length.
             - Width of each item is calculated by
                    model.value / range * viewWidth.

    For example:

        DataModelView(
            dataList = listOf(
                DataModelState("Label 1", 10, color),
                DataModelState("Label 2", 15, color),
                DataModelState("Label 3", 5, color)
            ),
            totalValue = 20
        )

        Getting the id component (linearBar) and set data:

        binding.linearBar.setDataModelView(List<DataModelState>, totalValue)
