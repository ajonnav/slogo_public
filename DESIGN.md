##Introduction

This SLOGO project will provide an interactive programming environment that allows users to create code expressions that commands a sample object in a window pane. The primary design goals of the project are to develop a substantial code hierarchy that promotes effective interaction between the front end and back end models, as well as to have enough extensibility to account for potential new features or additional programming language properties. The primary architecture of the project is to have the view/simulation side take in input, pass it to the back end controller, which interprets and makes changes based on that input, and then pass refreshed values that reflect those changes back to the simulation, which will then adjust the current state to the new state. The commands and interpreter will be closed since those features are concrete in behavior, while the display and visualization will be open due to the changing nature of the user edits.  

##Overview

![Slogo] (images/SLOGOpic.png "Slogo Architecture Design")



##User Interface

The User Interface will contain many components that will all interact with each other. There will be a splash screen that prompts the user to begin programming. From there, a new window will open with three panes. The first will be an initially blank history screen that will continually refresh with previous user input. Next, there will be a graphic display to show the turtle and the results of the user's commands. Lastly, there will be a command pane that allows the user to type in new input code to be interpreted and executed. In addition, there will be small features like buttons to exit or navigate back to the main menu. In the event that the user inputs improperly formatted expressions, a error message will appear with details about how to make the appropriate changes. The user will be required to dismiss the message before being allowed to try again.  


##Design Details

##### Class Justifications:


Program Walkthrough:


##Use Cases:
#####Case One


##Design Considerations


##### Design Assumptions 



##Team Responsibilities

The team is split into two parts, front end (Michael and Michael) and back end (Andy and Anirudh). The front end side will work on User Interface and managing display changes and handling user input. The back end will handle the programming interpretation and command changes. In addition, cross collaboration will be essential to making the most effective and best designed project. Testing edge cases and debugging larger bug issues will take place at group meetings.


