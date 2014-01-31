/**
* Copyright 2014 Costa Zervos
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

CMPUT 301 - Assignment 1: Counter Application
===

Application name: czervos-counter
author: Costa Zervos
apk: as1.apk

---
Description
---
A counter app that allows the user to create multiple counters and
increment them, along with resetting, renaming, and deleting them.
The user can also see count statistics on each counter which display
counts by month, week, day, and hour.

---
Instructions
---
After opening the application, you will be taken to the counter list
screen. This screen allows you to do several actions:

    1. Add counter: you may add a new counter by tapping on the plus
       icon in the action menu. This will take you to a new screen
       where you enter the name of the counter you wish to add. Upon
       clicking add a counter, you will be taken back to the counter
       list where your new counter will be displayed. If you change
       your mind about adding a counter in the add counter screen,
       you may click the back button in the action bar.

    2. Increment a counter: tap on a counter to increment the count.

    3. Counter statistics: tap and hold on a counter to bring up a
       context menu. Select stats to bring up a statistics screen
       where several statistics about the counter will be displayed.
       To leave this screen, hit back on the action bar.

    4. Rename counter: tap and hold on a counter to bring up a
       context menu. Select rename to bring up the coutner rename
       screen. This will take you to a new screen where you enter 
       the name of the counter you wish to add. Upon clicking add a 
       counter, you will be taken back to the counter list where 
       your new counter will be displayed. If you change your mind 
       about adding a counter in the add counter screen, you may 
       click the back button in the action bar.

    5. Reset countner: tap and hold on a counter to bring up a
       context menu. Select reset to reset (zero) a counters count.

    6. Delete coutner: tap and hold on a counter to bring up a
       context menu. Select delte to delete a counter.

---
Code Reuse
---
A few methods were modified from other authors. The original authors 
of these methods are listed below. The authors have also been credited 
directly in the source code above the methods.


author: Mike Plate
URL: http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/
license: No license


author: Android Developer Training
URL: http://developer.android.com/training/basics/actionbar/adding-buttons.html#Respond
license: Creative Commons 2.5 Attribution License (http://creativecommons.org/licenses/by/2.5/)


author: FabiF
URL: http://stackoverflow.com/questions/11106418/how-to-set-adapter-in-case-of-multiple-textviews-per-listview
license: Creative Commons 3.0 Attribution-ShareAlike (http://creativecommons.org/licenses/by-sa/3.0/)


