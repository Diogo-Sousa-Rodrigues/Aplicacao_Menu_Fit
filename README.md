# MenuFit



## Team

- Tiago Filipe da Cunha Fernandes - 2022156526 - a2022156526@isec.pt
- Juliano Mak Babaroca - 2022119541 - a2022119541@isec.pt
- Bárbara Sousa Trindade - 2022144651 - a2022144651@isec.pt 
- Júlio Pacheco - 2017018694 - a21270050@isec.pt
- Diogo Rodrigues - 2022148578 - a2022148578@isec.pt

## Vision and Scope

#### Problem Statement

###### Project Background
In today's fast-paced world, many individuals struggle with creating a meal plan due to time constraints and a lack of knowledge. This can lead to poor dietary choices, which may not align with their personal health goals, dietary restrictions, or financial limitations.

###### Stakeholders
- Individual users
- Nutritionists
- Fitness companies
- Health and Fitness enthusiasts

###### Users
- User with a profile

***

#### Vision & Scope of the Solution

###### Vision statement
To empower individuals to take control of their nutrition and wellness by providing a simple, personalized, and time-saving meal planning experience. Whether striving for a healthier lifestyle, managing dietary restrictions, or maintaining a balanced routine, our app adapts to unique needs and schedules, making healthy eating accessible, enjoyable, and effortless for everyone.

###### List of features
- Generate a full week meal plan based on user input
- Create and Configure a user profile
- Set restrictions for the meal plan (food restrictions, cost restrictions, meal complexity, calorie goals, dietary objective)
- Get information on how to prepare the meals
- Notifications for meal prep times or ingredients.

###### Features that will not be developed
- Connection to shopping app
- Set favourite meals to be considered in the generation on the meal plan

###### Assumptions
- Weekly meal plan
- Existence of a data base

***

## Requirements
#### Use Case Diagram
![Use case diagram](imagens/User_Case_Diagram.png)


#### Mockups
#### User Story 1
![User Story 1](imagens/registration.png)

#### User Story 2
![User Story 2](imagens/signin.png)

#### User Story 3
![User Story 3](imagens/healthdata.png)
![User Story 3](imagens/dietconsiderations.png)
![User Story 3](imagens/dietconsiderations2.png)

#### User Story 4
![User Story 4](imagens/timeandbudget.png)

#### User Story 5
![User Story 5](imagens/mealplanreview.png)

#### User Story 7
![User Story 7](imagens/weeklyplan.png)

#### User Story 6, 9
![User Story 6](imagens/mainmenu.png)

#### User Story 8
![User Story 8](imagens/recipe.png)

#### User Story 10
![User Story 10](imagens/recipeslist.png)


#### User Stories
- User Story 1 (#13)
- User Story 2 (#12)
- User Story 3 (#15)
- User Story 4 (#14)
- User Story 5 (#45)
- User Story 6 (#19)
- User Story 7 (#18)
- User Story 8 (#17)
- User Story 9 (#44)
- User Story 10 (#21)


## Definition of Done
- All tasks done:
    - CI – built, tested (Junit), reviewed (SonarCloud)
    - Merge request to qa (code review)
- Acceptance tests passed
- Accepted by the client
- Code merged to main

***

## Architecture and Design

#### Domain Model
![Domain Model](imagens/modelodominio.png)

***

## Risk Plan

##### Threshhold of Success
<!---
The project fails if:
 Essential features such as habit tracking, visual progress insights, task and habit management, reminders, and goal setting are incomplete or not functioning as intended.
Users find it difficult to navigate the app or encounter usability issues, leading to dissatisfaction and disengagement.
Reminders are unreliable, causing users to miss their tasks and habits frequently.
Milestone tracking for goals is confusing or not visually represented clearly, leading to user frustration.

- The "must" user stories are not developed
- The app is not in a working condition upon release.
The team is not satisfied with the app and their work on the project, based on a questionare at the last meeting before final release. -->

The project is successful if:
<!--- By the final release date, all essential features, namely habit tracking, task and habit management and reminders, are fully implemented and pass acceptance tests without critical defects.
Users find the app intuitive, user-friendly, and engaging, leading to positive feedback and a high level of satisfaction.
Reminders are delivered accurately and on time, ensuring users can effectively manage their tasks and habits.
Milestone tracking for goals is visually appealing, easy to understand, and provides users with a sense of achievement and progress. -->

- All "must" user stories are developed and tasks assigned to them finished.
- All group members contibuted to the project equally (rounded up to the nearest hour).
- The team is satisfied with the app and their work on the project, based on a questionare at the last meeting before final release.
<!-- - The interface of the app it appealing to most group members and clients. -->


##### Risk List
<!-- - RSK1 - PxI: 4x5=20; Inaccurate Time and Effort Estimations
  - Risk: The team lacks experience in estimating the time and effort required for tasks, leading to underestimations or        overestimations, resulting in unforeseen project delays.
  - Impact: 5
  - Likelihood: 4 -->

- RSK1 -  PxI: 2x3 = 6; Misalignment on MVP Definition
  - Risk: Confusion over the core features to be delivered, resulting in work overload or lack of focus.
  - Probability: 2
  - Impact: 3

- RSK2 -  PxI: 4x3 = 12; Team Collaboration Issues
  - Risk: Difficulty in coordinating the team, resulting in inefficiency or uneven contributions.
  - Probability: 4
  - Impact: 3


- RSK3 -  PxI: 3x5 = 15; Task Overload or Lack of Team Capacity
  - Risk: The team may not complete the planned work within the allocated capacity.
  - Probability: 3
  - Impact: 5


- RSK4 - PxI: 4x5 = 20; Lack of team AI knowledge
  - Risk: The team may not know how to properly work with AI, which the project is very dependent on, leading to not being able to complete the tasks.
  - Probability: 4
  - Impact: 5



##### Mitigation Actions (threats>=20)
Mitigation actions:
- RSK4 - MS; Allocate time in tasks for the learning of AI. 

***

## Pre-Game
### Sprint 0 Plan

- Goal: description
- Dates: from 1/Oct to 15/Oct, 2 weeks
- Sprint 0 Backlog:
  - Task1 – Write Team
  - Task2 – Write V&S
  - Task3 – Write Requirements
  - Task4 – Write DoD
  - Task5 – Write Architecture&Design
  - Task6 – Write Risk Plan
  - Task7 – Write Pre-Gane
  - Task8 – Write Release Plan
  - Task9 – Write Product Increments
  - Task10 – Create Product Board
  - Task11 – Create Sprint 0 Board
  - Task12 – Write US in PB, estimate (SML), prioritize (MoSCoW), sort
  - Task13 – Create repository with “GPS Git” Workflow
  
***

## Release Plan

### Release 1

- Goal: MVP - We define our MVP as an app that will offer this possibilities and functionalities to the user:
  - Create Profile
  - Generate weekly meal plan
  - Manage weekly meal plan
  - View recipes
  - Get reminders
  - Favorite/Delete meals
  - Daily Goal Tracker

- Dates: 15/Out
- Team capacity: 4 * 4 * 5 = 80 hours
- Release: V1.0

***

## Increments

### Sprint 1
##### Sprint Plan

- Goal: <!-- Create the base architecture of the app and basic interface. -->
  - Register
  - Login
  - Meal plan form
  - Reminders
  - Generate weekly meal plan
  - Daily Goals

- Dates: from 15/Oct to 29/Oct, 2 weeks

- Roles:
  - Product Owner: Diogo Rodrigues
  - Scrum Master: Tiago Fernandes

- To do:
  - US1: As a user, I want to be able to register my basic information on the app, so that I will be able to login next time and it will save my information.
    - Task1: Validate and Save Info
    - Task2: Redirects
    - Task3: Form UI

  - US2: As a user, I want to be able to use my credentials to access the app so that I can access and manage my information and meal plan.
    - Task1: Redirects
    - Task2: Remember Me
    - Task3: Form UI
    - Task4: Access Data

  - US3: As a user, I want to be able to save my health data and my dietary restrictions, So that I can get meals that follow those parameters.
    - Task1: AI Prompt
    - Task2: Profile Data
    - Task3: Form UI
  
  - US4: As a user, I want my available time and budget to be considered in the making of the meal plan so that it will generate recipes that will better meet my preferred time expectations and budget.
    - Task1: Finish (Redirect to US5)
    - Task2: AI Prompt
    - Task3: Form UI
  
  - US5: As a user, I want to review the meal plan I'm about to generate, so that I can make adjustments in my inputs and so that I can see what I'm about to commit to, without commitment.
    - Task1: Redirects
    - Task2: Meals Data
    - Task3: Form UI
  
  - US6: As a user, I want to keep track of my daily goals, so that I can stay on top of my progress and ensure I meet my nutritional objectives consistently.
    - Task1: Calorie Counter
    - Task2: UI
  
  - US9: As a user, I want the app to tell me if there is anything I need to do ahead of time in a meal so that I can prepare for it.
    - Task1: Reminder Data
    - Task2: UI
    - Task3: Manage Reminder
  
- Story Points: 5S+1M+1S(incomplete)

##### Sprint Review
- Analysis: We worked well but were not able to match all the objectives

- Story Points: 1 User Story + 20 Tasks

- Version: 0.1 

- Client analysis: Client was overall happy, however some things were not done

- Conclusions: Need better estimation for next Sprints


##### Sprint Retrospective



***

#### Sprint 2
##### Sprint Plan

- Goal:
  - Integrate AI
  - Database connection
  - Finish data management
  - Improve UI

- Dates: from 29/Out to 12/Nov, 2 weeks

- Roles:
  - Product Owner: Júlio Pacheco
  - Scrum Master: Juliano Babaroca
  - QA: Bárbara Trindade

- To do:
  - US2: As a user, I want to be able to use my credentials to access the app so that I can access and manage my information and meal plan.
    - Task1: Access data
  - US3: As a user, I want to be able to save my health data and my dietary restrictions, So that I can get meals that follow those parameters.
    - Task1: Profile Data
  - US12: As a user, I want the app to have a user profile that keeps track and changes my informations such as user data, restrictions and objectives. And also that allows me to log out of my acount.
    - Manage Data/
  - US7: As a user, I want quick and easy access to the weekly meal plan in the meal prep app, so that I can efficiently plan and organize my meals without hassle.
    - Task 1: Manage meal
    - Task 2: Generate new Weekly Plan (Redirect to US3)
    - Task 3: Form UI
    - Task 4: Meal Plan Data
  - US6: As a user, I want to keep track of my daily goals, so that I can stay on top of my progress and ensure I meet my nutritional objectives consistently.
    - Task 1: Calorie Counter/
  - US8: As a user, I want to be able to see the ingredients and step-by-step of each recipe so that I can follow it and prepare meals easily and accurately without missing any details.
    - Task 1: Meal Plan Data
    - Task 2: Form UI
  - US5: As a user, I want to review the meal plan I'm about to generate, so that I can make adjustments in my inputs and so that I can see what I'm about to commit to, without commitment.
    - Task 1: Meals Data
  - US11: As a user, I want the app to have a interface that shows all the meals for the day.
    - Task 1: Manage Data
  - US13: As a user, I want the app to tell me the next expected meal of the day.
    - Task 1: Manage Data
  - US9: As a user, I want the app to tell me if there is anything I need to do ahead of time in a meal so that I can prepare for it.
    - Task 1: Reminder Data
    - Task 2: Manage Reminder/
  - US10: As a user, I want to be able to save my favorite meals and to delete the ones I dont like, so that I can quickly access the meals I enjoy and keep my meal plan organized
    - Task 1: UI/
    - Task 2: Recipe Data/
    - Task 3: Manage Recipe
    
  
- Story Points: 1S+2M + 8S(only some tasks) + 1M(only some tasks)

##### Sprint Review
- Analysis: Overall very good. Some improvements to do in QA.

- Story Points: 7S + 2M

- Version: 0.1

- Client analysis: pleased with the application. 

- Conclusions: Need better team meetings


##### Sprint Retrospective

### Release 2

- Goal: MVP - We define our MVP as an app that will offer this possibilities and functionalities to the user:
  - Recipe management (favorite/delete)
  - Data persistence accross instances (database)
  - Add extra meal
  - AI Prompts
  - Remember me

- Dates: 12/Nov
- Team capacity: 4 * 4 * 5 = 80 hours
- Release: V1.0

***

## Increments

### Sprint 3
##### Sprint Plan

- Goal: <!-- Create the base architecture of the app and basic interface. -->
  - AI Prompts
  - DataBase creation and connection
  - Remember me

- Dates: from 11/Nov to 26/Nov, 2 weeks

- Roles:
  - Product Owner: Bárbara Trindade
  - Scrum Master: Diogo Rodrigues
  - QA: Juliano Babaroca

- To do:

  - US10:
    - Task1: Recipe data
    - Task2: Manage recipes
    - Task3: Edit Recipe Table

  - US12: 
    - Task1: Manage Data

  - US14:
    - Task1: Database CRUD
    - Task2: Database connection
    - Task3: Create database tables
    - Spike: see database options

  - US15:
    - Task1: Remember me (Login controller) changes
    - Task2: Remember me UI changes
    - Task3: Remember me logic
  
  - US16:
    - Task1: Substitute recipe prompt
    - Task2: Meal plan prompt
    - Task3: API implementation
    - Task4: Custom AI Prompt
    - Spike: AI API
    - Spike: AI Prompt
  
- Story Points: 1S + 3M + 1L

##### Sprint Review
- Analysis: Overall very good.

- Story Points: 1S + 2M

- Version: 0.2

- Client analysis: pleased with the application. 

- Conclusions: Need better independency of User Stories



### Sprint 4
##### Sprint Plan

- Goal:
  - Continue Data Persistency
  - Finish AI Prompt
  - Add extra meal
  - Edit Profile

- Dates: from 26/Nov to 10/Dez, 2 weeks

- Roles:
  - Product Owner: Juliano Babaroca
  - Scrum Master: Bárbara Trindade
  - QA: Júlio Pacheco

- To do:

  - US10:
    - Task1: Recipe data
    - Task2: Manage recipes
    - Task3: Edit Recipe Table

  - US16: 
    - Spike: AI API
    - Task: Custom AI Prompt

  - US17:
    - Task1: Extra Meal UI
    - Task2: Extra Meal data
    - Task3: Adjust Calorie Log

  - US18:
    - Task1: Create Database table
    - Task2: Database CRUD
  
  - US19:
    - Task1: Create Edit UI
    - Task2: Update User's Info in Database
    
- Story Points: 1S, 3M, 1L

##### Sprint Review
- Analysis: Overall very good.

- Story Points: 1S + 3M + 1L

- Version: 0.2

- Client analysis: pleased with the application. 

- Conclusions: Group member impacted the team efficiency.


### Release 3

- Goal: MVP - We define our MVP as an app that will offer this possibilities and functionalities to the user:
  - Simple Nutrotionist
  - Basic Subscription Plan
  - Improve UI

- Dates: 09/Dec
- Team capacity: 4 * 4 * 5 = 80 hours
- Release: V2.0

***

## Increments

### Sprint 5
##### Sprint Plan

- Goal: <!-- Create the base architecture of the app and basic interface. -->
  - AI Prompts
  - DataBase creation and connection
  - Remember me

- Dates: from 11/Nov to 26/Nov, 2 weeks

- Roles:
  - Product Owner: Tiago Fernandes
  - Scrum Master: Júlio Pacheco
  - QA: Diogo Rodrigues

- To do:

  - US20:
    - Spike: Nutritionist logic
    - Task1: Integrate user in DB
    - Task2: Add new register option

  - US21: 
    - Task1: Client Profile Page
    - Task2: Dashboard

  - US22:
    - Task1: Manage Data
    - Task2: Notification UI
    - Task3: Create Notification Logic and Data
    - Task4: Update MealPlanReview UI
    - Task5: Update Profile UI

  - US23:
    - Spike: Detailed UI/UX analyse
    - Task1: UI Changes
  
- Story Points: 4M

##### Sprint Review
- Analysis: 

- Story Points: 

- Version: 

- Client analysis: 

- Conclusions: 
