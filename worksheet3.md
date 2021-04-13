What technical debt has been cleaned up
========================================

One example of technical debt that we covered was package management in each of the layers, as well as overall code management such as getting rid of unused code, including commented out and redundant code. This type of debt could be classified as **Reckless and Inadvertent**, as we just tried to complete the issues at the time and did not think about layering.

Link(s): 
- https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/116
- https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/113


What technical debt did you leave?
==================================

Some technical debt that we left were UI compatibility problems with other devices with varying screen sizes, as we have only had the android tablet in mind when working on the project. The app does not resize long strings to better fit smaller screens, so the spacing will not look ideal for smaller devices. This type of debt could be classified as **Deliberate and Prudent** as we chose to focus on implementing the UI on a tablet at the time as it was mentioned in the initial project summary. We decided to start with that aspect, then deal with the potential issues later on.

Link(s): 
- https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/121

Discuss a Feature or User Story that was cut/re-prioritized
============================================

One of the features that was cut/re-prioritized was one of our features from iteration 2. It was adding expiration date reminders on our app. We spent most of this iteration trying to solve major problems with the database and when the time came to approach this feature, the group was already exhausted from integrating the existing functionality with the new database. We decided to implement the feature in another iteration to relieve the pressure from the upcoming due date and focused on the higher priority implementations. 

Acceptance test/end-to-end
==========================

One of the system tests we wrote was for adding an item to the inventory list. In that test, we made sure that the item was added to the persistent database. This was done by having assertions after the add functions to confirm the item was added properly. To avoid flakiness in the test, we made sure that the items we added were simple enough to pass as well as adding a cleanup portion to remove the added test item so as to not conflict with other tests.

Link(s): 
- (*****TBD after all merge to master is done******)

Acceptance test, untestable
===============

When creating these tests, the challenge was making sure that for each test, it ended in a graceful and clean manner so that it did not conflict with the other tests. The test for adding and making a recipe was difficult because it interacts with multiple layers of our logic (Inventory, Grocery, and Recipe) so it was very important to make sure that they were all cleaned up properly after the test.

Velocity/teamwork
=================

Looking over previous estimates, they were done poorly as the difference between the estimate and actual times were noticeably bigger in the earlier iterations when we were initially trying to give more headroom for possible setbacks. When looking at the estimates in later iterations, we can see that the difference between the estimate and actual times were much closer as we had more experience with estimating how quickly a task could be done or checking which tasks would be difficult.

Link(s): 
- https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/21
- https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/122

