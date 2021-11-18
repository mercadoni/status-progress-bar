<h1 align="center">Status Progress Bar</h1></br>

<p align="center">
<img alt="api" src="https://img.shields.io/badge/API-21%2B-green?logo=android"/>
<img alt="license" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/>
</p>

<p align="center">
An Android custom view library to display data with color coded information in a Linear bar format with labels to indentfy each color.
Is use by ShopperApp application (shopper-app-new).
</p><br>

<p align="center">
<img src="https://user-images.githubusercontent.com/56521677/141031669-96416ac0-687b-4a9c-89d4-66ab4ee0a3e4.png" width="30%"/>
</p>
</br>

## How to integrate the library in the app?

**Step 1.** Add it in root `build.gradle` at the end of repositories:
```gradle
    allprojects {
        repositories {
          TODO: pending
        }
    }
```
**Step 2.** Add the dependency
```gradle 
    dependencies {
        TODO: pending
    }
```

## How to usage the library in the app?

**Step 1.** Add component to Layout file
```xml
     <com.shopper.customviews.LinearStateBar
        android:id="@+id/linear_bar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```
**Step 2.** Custom the view.
```xml
    app:fontSize="12" -> Can modify the fontSize
    app:heightLineStatus="10" -> Can modify the height of LinearBar
```

**Step 3.** Set a `List<DataModelState>` and the total value of items.
```kotlin
    DataModelView(
        val dataList: List<DataModelState>,
        val totalValue: Int
    )

    DataModelState(
        val label: String,
        val value: Int,
        val color: Int
    )
    
    DataModelView(
        dataList = listOf(
            DataModelState("Label 1", 10, color),
            DataModelState("Label 2", 15, color),
            DataModelState("Label 3", 5, color)
        ),
        totalValue = 20
    )
```


- The second param on DataModelView is the entire span linear bar view. 
- And value param in each DataModelState object will occupy a percent length.
- Width of each item is calculated by `model.value / range * viewWidth`.



**Step 4.** Refer this View in your activity/fragment file, getting the id component (linearBar) and set data:
``` kotlin
    binding.linearBar.setDataModelView(List<DataModelState>, totalValue)
```     
       

## Find this library useful? :heart:
Be free to use it and enjoy. :star:


# License
```xml
 Copyright 2020

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

