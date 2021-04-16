# Paying off technical debt

1. We split ListActions into 3 separate classes, previously it was one long and complicated class. Previously we had one class handling most of our logic. This was not the best way to go because it defied Single Responsibility Principle. We therefore broke it into three separate classes. These classes can now be found in our business folder. We classify this as inadvertent and reckless because when we initially implemented we miscalculated how this class would grow.

   These changes can be found here: [Split List Actions](https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/commit/6ed5f402fa8943ddab26f9d8ed82750d25862c9b)

2. Moved from Hard Coded Strings for UI to a fixed class called Strings.xml, we now can access these strings universally and easily add to it. We would classify this as inadvertent and prudent because we were aware that this would grow into tech deb but we initially had very few Strings in our UI but as the functionality grew with adding more features, creating a separate class as a collection for Strings felt natural.

   These changes can be found here: [Make Strings File](https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/commit/4a299a2a48b1872ffa15d5d3f03c9856df1e145d#d2281fbb3027de2722081a53408dd77628bf080e_3_3)

# SOLID

We created an issue for UMPlanner in A03. The group is missing interfaces for their business layers which defies the Interface Segregation Principle and Dependency Inversion Principle.

The issue can be found here: [SOLID Violation: Missing Interfaces](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-10/umplanner-comp3350-a02-group10/-/issues/74)

# Retrospective

We have used this retrospective to reconsider the future of the app and how we want things to be implemented over the next few iterations as well as how we should be estimating our process.

After iteration 1, we felt that the progression of the app required more depth in features rather than width so we moved around some features. We decided that we do not need the following functions:

- [Add recurring item feature](https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/6)
- [Expired date alert](https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/7)

We moved these features to iteration 3. Given that, we are bringing in the features that we consider important for the app in this iteration. Therefore, in the current iteration, we have added the ability to:

- [Buy all items in the Grocery List, with one button](https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/109)
- [Ability to view more information about the item in either lists](https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/108)

We also made sure to regularly commit to branches and merge features more frequently to ```master```. This helps us all because we encounter less merge conflicts when merging files into ```master```.

# Design Patterns

### Adapter

The code is implementing the hsqldb jar file as a library, where the methods in the persistence layer are taking the data from the database and adapting into an item object to be used in the application.

Some examples can be found [here](https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/blob/master/app/src/main/java/com/smartkitchen/persistence/hsqldb/GroceryPersistenceDB.java#L122), where we extract all objects we need from the database into an ArrayList, and [here](https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/blob/master/app/src/main/java/com/smartkitchen/persistence/hsqldb/GroceryPersistenceDB.java#L36), where we construct an Item object to send to and used by other classes.

### Singleton

The code is declaring the static database variables to be used in the entire application.

This can be found [here](https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/blob/master/app/src/main/java/com/smartkitchen/persistence/DBManager.java#L14).

# Iteration 1 Feedback fixes

The issue created by Grader in the feedback can be found [here](https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/103). The issue was that whenever a user adds an item with a very long description (name, quantity and units) the app breaks. This issue was caused by the quantity field as it was taking in integer inputs. The max value that an int data type can store is up to 2,147,483,647. A user can pass this value easily if since they are not limited in the text input field. What we did to solve this issue was to lock the text field input length to a fixed number so that the user cannot input more than they would need. The solved issue can be found [here](https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/commit/f937e48fa5a1f47990df6a493e9b2bee43bb1019#83dc6ba1ebc351f36046f7cba1744f1e972eed71_92_94).