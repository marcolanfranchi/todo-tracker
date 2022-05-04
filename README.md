# My Personal Project

## To-Do List Application


My to-do list application will provide the user with a comprehensible
and clean interface that allows them to add specific tasks to their 
to-do list and view their tasks in different ways. A task can be 
categorized under either *school work*, *household chores*, or *other*. 
The user can either view all of their tasks, or the tasks in any 1 
of the 3 categories. In these different viewing options, 
the user can also choose to include tasks they have already completed
as these will be removed after being marked as completed.

This application is intended for students or anyone that needs to 
keep track of their many tasks in an organized manner. It is an important 
application to me because as a student it is nearly impossible to mentally
keep track of all of my school work while meeting deadlines. The categories
are very useful to me as well because when I am busy with school work, it is easy to 
overlook any household chores I need to get done. Furthermore, managing tasks
allows for many possible ways of further organizing the data such as breaking
the school work tasks down to their specific courses.

## User Stories

- As a user, I want to be able to add a task to my todo list
- As a user, I want to be able to view the list of tasks on my to-do list
- As a user, I want to be able to mark a task as complete on my to-do list
- As a user, I want to be able to delete a task from my to-do-list

- As a user, I want to be able to save my to-do list to file
- As a user, I want to be able to load my to-do list from file

## Phase 4: Task 2

Thu Nov 25 21:17:33 PST 2021
Loaded a 6 task long todolist from file


Thu Nov 25 21:19:59 PST 2021
Added Task 'Math assignment' to the to-do list


Thu Nov 25 21:20:46 PST 2021
Added Task 'Text mom' to the to-do list


Thu Nov 25 21:20:51 PST 2021
Deleted Task 'sweep' from the to-do list


Thu Nov 25 21:21:02 PST 2021
Deleted Task 'Anth 202 Quiz 11' from the to-do list


Thu Nov 25 21:21:09 PST 2021
Saved a 6 task long todolist to file

## Phase 4: Task 3
 
Refactoring that I would make to my design:

 - Instead of implementing the JInternalFrame class ListUI in the JDesktopPane class ToDoAppUi, I would make ListUI a JFrame itself as it is within this frame that most of the user stories come to life.
 - Continuing on the redesign approach from above, ListUI would now be the main and only window for the application 
 
