# SmartKitchen Group 10

## Purpose
The Smart Kitchen application is a list management system for your food, grocery list, and recipes. It allows you to easily input and view all of the food you currently own, as well as add food to your grocery list or use it when making a recipe.

## How To Run
The user needs Android Studio and the Emulator AVD Nexus 7 API 30. 
- Open Android Studio
- Import the project
- Let Android Studio index all the files
- Set the device to the Nexus 7 API 30
- Build and run the application

The user can also just use the apk linked in the release. Just drag and drop the apk into the emulator to install the app.

## How To Use
There are three main screens that can be accessed via the menu in the top right:
- Current Inventory
- Grocery List
- Recipes

All of these screens have very similar functionality. Press the ```+``` button in the bottom right of the screen to add an item to the list. Fill in the required fields then press submit, and the item will be added to the list. Items can then be edited or removed from the list screen. In the inventory screen, if the quantity is set less than the threshold, the app will prompt the user to add the item to the grocery list upon submitting.

When adding a recipe, the app will try to match ingredients to current inventory items with matching names. This can then let the user know if enough of the ingredient is in the current inventory. The user can then add all ingredients to the grocery list, or deduct the ingredients from the inventory by clicking either the ```Add To Grocery``` or ```Make``` buttons.

## Website
Our website is hosted as an Heroku app here: https://smartkitchen-g10.herokuapp.com/. We suggest viewing it there for the beautiful CSS and Dynamic Routing experience. However, we have added all the relevant files in the website folder. To view the Homepage, navigate to ```website/out/index.html```.

## Branching Strategy

The branching strategy for the iteration can be found [here](Documents/branching_strategy.md).

## Architecture

The architecture diagram for the project can be found [here](ARCHITECTURE.md).

### Worksheet
The worksheet to iteration 1 can be found [here](worksheet.md).\
The worksheet to iteration 2 can be found [here](Worksheet2.md).\
The worksheet to iteration 3 can be found [here](worksheet3.md).
