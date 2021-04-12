What technical debt has been cleaned up
========================================

A technical debt that we have covered are package management in each layers, and overall code management such as getting rid of unused codes, including commented codes. This type of debt could be classified as **Reckless and Inadvertent** as we just tried to complete the issues at the time and did not think about layering.

Link(s): 
- https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/116
- https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/113


What technical debt did you leave?
==================================

Some technical debts that we have left are UI compatibility problems with other devices as we have only had the android tablet in mind when working on the project. We do not resize long strings to better fit smaller screens so it will not look ideal for smaller devices. This type of debt could be classified as **Deliberate and Prudent** as we chose to do the UI on a tablet at the time because it was mentioned in the initial project summary and we were just following instructions and deal with the consequences later on.

Link(s): 
- https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/121

Discuss a Feature or User Story that was cut/re-prioritized
============================================

One of the features that was cut/re-prioritized was a feature from iteration 2. It was about adding expiration date reminders on our app. This was the iteration that we had major problems with the database and when approaching this feature, we just had the database working so the group was exhausted from integrating the existing functions from the stub databases. We decided to implement the feature in another iteration to relieve the pressure from the upcoming due date and focused on the higher priority implementations. 

Acceptance test/end-to-end
==========================

One of the system test we've written was adding an item to the inventory list. On that test, we made sure that the item was added to the live database. This was done by having assertions after each confirmation of adding an inventory item. To make the test not flaky, we had to make sure that the items we add were simple enough to pass and we also made a cleanup portion to remove the added test item so as to not make conflicts with other tests.

Link(s): 
- (*****TBD after all merge to master is done******)

Acceptance test, untestable
===============

When creating these tests, the challenge was making sure that for each test, it ends in a graceful and clean matter so that it does not conflict with other tests. The test for adding and making a recipe was a bit difficult because it interacts with multiple layers of our logic (Inventory, Grocery, and Recipe) so I had to make sure that they are all cleaned up properly after the test.

Velocity/teamwork
=================

Looking over previous estimates, they were done poorly as the difference between the estimate and actual times were noticeably big in the earlier iterations as we were initially trying to give more headroom for possible setbacks. When looking at the estimates in later iterations, we can see that the difference between the estimate and actual times were closer as we had experience as to how quickly a task could be done or check which tasks would be difficult, and project estimates with more headroom for those tasks.

Link(s): 
- https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/21
- https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/122

