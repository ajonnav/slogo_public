##Introduction

This SLOGO project will provide an interactive programming environment that allows users to create code expressions that commands a sample object in a window pane. The primary design goals of the project are to develop a substantial code hierarchy that promotes effective interaction between the front end and back end models, as well as to have enough extensibility to account for potential new features or additional programming language properties. The primary architecture of the project is to have the view/simulation side take in input, pass it to the back end controller, which interprets and makes changes based on that input, and then pass refreshed values that reflect those changes back to the simulation, which will then adjust the current state to the new state. The commands and interpreter will be closed since those features are concrete in behavior, while the display and visualization will be open due to the changing nature of the user edits.  

##Overview

![Slogo] (images/SLOGOpic.png "Slogo Architecture Design")

Our design is back end heavy, with the model and controller handling most of the changes in this project. The view will encompass the display packages which are responsible for initializing and updating the various panes on the screen, including the history, command, and scene features. User input will be sent to the parser, which will then process those commands and make the appropriate changes. The commands package will be responsible for changing values and the binding nature of those variables will be updated throughout the project. This way, the front end and the back end are consistently working with the same states and information. 

##User Interface

The User Interface will contain many components that will all interact with each other. There will be a splash screen that prompts the user to begin programming. From there, a new window will open with three panes. The first will be an initially blank history screen that will continually refresh with previous user input. Next, there will be a graphic display to show the turtle and the results of the user's commands. Lastly, there will be a command pane that allows the user to type in new input code to be interpreted and executed. In addition, there will be small features like buttons to exit or navigate back to the main menu. In the event that the user inputs improperly formatted expressions, a error message will appear with details about how to make the appropriate changes. The user will be required to dismiss the message before being allowed to try again.  


##API Details

Front-end Internal

draw()

Back-end Internal

Front-end External

Back-end External

##API Example Code

##Design Considerations

In our planning process, we discussed updating the display when there are multiple commands in the expression. For example, ‘pu fd 50 pd fd 50’ can be split into two operations and evaluated successively. The issue we debated was to either handle a command, update the display, and handle the next command or to handle all commands at once and then pass the display the states of the turtle after every command and allow the front end to update this. We choose to go with the first option so that the movement of the turtle would be more dynamic and evaluating programming expressions would be more visible to a new programmer.

Another issue we discussed was having an animation timer versus an update method. The display with the turtle only needs to be refreshed after the user inputs more commands that alter its features. So rather than have an animation timer with a step method responsible for refreshing values, we opted to have the back end update the display so that way the changes take place sequentially and the problem of having all of the changes take place within one frame is avoided.



##Team Responsibilities

The team is split into two parts, front end (Michael and Michael) and back end (Andy and Anirudh). The front end side will work on User Interface and managing display changes and handling user input. The back end will handle the programming interpretation and command changes. In addition, cross collaboration will be essential to making the most effective and best designed project. Testing edge cases and debugging larger bug issues will take place at group meetings.


