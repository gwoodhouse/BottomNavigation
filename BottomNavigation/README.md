# BottomNavigation

## How to use

Maven

```
<dependency>
  <groupId>ca.gcastle</groupId>
  <artifactId>BottomNavigation</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

Gradle

```
compile 'ca.gcastle:BottomNavigation:1.0.0'
```

Ivy

```
<dependency org='ca.gcastle' name='BottomNavigation' rev='1.0.0'>
  <artifact name='$AID' ext='pom'></artifact>
</dependency>
```

## Default usage
```
<ca.gcastle.bottomnavigation.view.BottomNavigationView
            android:id="@+id/bottomBar"
            android:layout_width="match_parent"
            android:layout_height="56dp"
            android:layout_gravity="bottom"
            app:layout_behavior="ca.gcastle.bottomnavigation.behaviour.BottomNavigationBehavior"
            app:navGrowTabs="false"
            app:navGrowthModifier="32dp"
            app:navShowCircleReveal="false">

            <ca.gcastle.bottomnavigation.view.BottomNavigationTabView
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:id="@+id/tab1"
                app:tabImage="@drawable/icon1"
                app:tabText="tab1"
                app:tabColor="#FF0000"
                app:alwaysShowText="true"
                app:unselectedAlpha="0.2"/>

            <ca.gcastle.bottomnavigation.view.BottomNavigationTabView
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:id="@+id/tab2"
                app:tabImage="@drawable/icon2"
                app:tabText="tab2"
                app:tabColor="#00FF00"
                app:unselectedAlpha="0.2"/>

            <ca.gcastle.bottomnavigation.view.BottomNavigationTabView
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:id="@+id/tab3"
                app:tabImage="@drawable/icon3"
                app:tabText="tab3"
                app:tabColor="#0000FF
                app:unselectedAlpha="0.2"/>
        </ca.gcastle.bottomnavigation.view.BottomNavigationView>
```

## Optional parameters

### BottomNavigationView

#### [Optional]
`app:navGrowTabs="false"`

If you don't want the tabs to grow when selected use "false". Either "true" or "false, defaults to true. 

`app:navGrowthModifier="32dp"`

The amount a tab grows when selected. Default value is 64dp. Is ignored if navGrowTabs is set to false.

`app:navShowCircleReveal="false"`

Decide if you want a circular reveal effect on the background colour, or whether to simply switch colours.

### BottomNavigationTabView

`app:tabImage="@drawable/icon1"`

The drawable to display for this tab

`app:tabText="@string/tab1"`

The text to display for this tab.

`app:tabColor="#0000FF`

The colour background for this tab - used by BottonNavigationView in it's circular reveal

#### [Optional]

`app:alwaysShowText="true"`

Ensure text is always visible. Defaults to false.

`app:unselectedAlpha="0.2"`

Customise the opacity of unselected items. Between 0 and 1. Defaults to 0.8

## License

My work is distributed under the Apache License.

```
Bottom Navigation library for Android
Copyright (c) 2016 Graeme Castle (https://github.com/gwoodhouse/BottomNavigation).

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

This library uses modified source for the Coordinator Layout Behaviour from Nikola Despotoski

```
BottomNavigationLayout library for Android
Copyright (c) 2016. Nikola Despotoski (http://github.com/NikolaDespotoski).
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