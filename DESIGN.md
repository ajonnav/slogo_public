##Introduction

This SLOGO project will provide an interactive programming environment that allows users to create code expressions that commands a sample object in a window pane. The primary design goals of the project are to develop a substantial code hierarchy that promotes effective interaction between the front end and back end models, as well as to have enough extensibility to account for potential new features or additional programming language properties. The primary architecture of the project is to have the view/simulation side take in input, pass it to the back end controller, which interprets and makes changes based on that input, and then pass refreshed values that reflect those changes back to the simulation, which will then adjust the current state to the new state. The commands and interpreter will be closed since those features are concrete in behavior, while the display and visualization will be open due to the changing nature of the user edits.  

##Design Overview

![Slogo] (SLOGOpic.png "Slogo Architecture Design")

Our design is back end heavy with a command design pattern, with the model and controller handling most of the changes in this project. The view will encompass the display packages which are responsible for initializing and updating the various panes on the screen, including the history, command, and scene features. User input will be sent to the internal back end parser, which will then process those commands and make the appropriate changes in the respective model (turtle, line, commandList, canvas, etc.). The commands package will be responsible for changing values and the binding nature of those variables will be updated throughout the project. This way, the front end and the back end are consistently working with the same states and information. 

Looking more in depth at each of our API, we obtain a better view of the design model. In the front end (internal), methods exist to draw() the turtle, lines, and scene per input; showHistory(); and showCommands(). From these, when a command is input, the back end will parse() the input information and convert it to the language of the program. Once the input string is parsed, the commands will be passed to the external back end for execution towards the UI. By some method, this parsed line will execute() changes to the respective values of the scene so long as the exceptionCheck() does not catch any errors on the deciphered syntax. With each execute(), the appropriate target (observable turtle, line, scene (and such)) will be signalled to update and the external front end will update() to obtain those values to draw() once more. This will all be repeated for each input line until each instruction is complete, and then the program will wait for the next input from the user. 


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
draw() - abstract method from Drawable interface
Each view implements the Drawable interface
Each view has a set of properties and a draw method that draws the object using those properties

Front-end External

update() - abstract method from Updateable interface
Each view implements the Updateable interface
After a command updates backend values, each view is updated by calling the update
method (which draws all the updated elements)

Back-end Internal

execute() - abstract method from Command interface
Each command implements the Command interface
Updates model by calling model method with specific arguments

Back-end External
beginCommands(String s)
Takes the command input and parses it into a queue of commands
Execute each command, which updates the view 

##API Example Code


### Use Case: Move forward 1 unit 100 times
input: ‘dotimes 100 fd 1’
description: move forward by 1 unit 100 times
precondition: turtle beings at (x,y)
postcondition: turtle ends at (x + 100*cos(heading), y+100*sin(heading))

course of action:
backExternal:  begincommands(“textField.getString()”);
exceptionChecker(for (n=0; n<100; n++){fd(1)});     (some parsedInput)
backInternal:   parser(“dotimes 100 fd 1”);
		execute(fd(1)) - updates turtle model
		execute(fd(1))	- updates turtle model
		…
		execute(fd(1)) - updates turtle model
		execute(fd(1)) - updates turtle model
Loops through all views and calls update and draw
frontExternal:  update();
frontInternal:   draw(); 

### Use Case: Move forward 50 units with pen down
input: ‘pd fd 50’
description: move forward by 50 units 
precondition: turtle beings at (x,y)
postcondition: turtle ends at (x + 50*cos(heading), y+50*sin(heading)) with a line behind it

course of action:
backExternal:  begincommands(textField.getString());
exceptionChecker(for (n=0; n<100; n++){fd(1)});     (some parsedInput)
backInternal:   parser(‘pd fd 50’);
	execute(‘pd’); - updates line model
	execute(‘fd 50’) - updates turtle model
Loops through all views and calls update and draw
frontExternal:  update();
frontInternal:   draw(); 




### Use Case: Change the background color of the display
input: ‘bg blue’
description: changes the background of the display to blue
precondition: background has a color other than blue
postconiditon: background is blue

course of action:
backExternal: begincommands(textField.getString());
		exceptionChecker(isColor()): (some parsedInput)
backInternal: parser(‘bg blue’);
	          execute(bg.setColor(Colors.BLUE));
Loops through all views and calls update and draw
frontExternal:  update();
frontInternal:   draw(); 


### Use Case: Erroneous input
input: ‘   fksdflds’
description: input read, found to be unrecognizable and error returned in command pane to user
precondition: some state
postcondition: same state but with user friendly error printed

course of action:
backExternal: beginCommands(textField.getString());
	           exceptionChecker(“not a real input”);
(will print straight to command pane from exceptionChecker() to give the appropriate error)

### Use Case: Add Two Numbers
input: ‘sum 1 2’
description: adds the numbers 1 and 2 and displays the result in the command window
precondition: some state
postcondition: same state with the result in the command window

course of action:
backExternal: 	beginCommands(textField.getString());
		exceptionChecker();
backInternal:	parser(‘sum 1 2’); 
		execute(sum(1,2)); - adds the two numbers and returns result
frontExternal:	update()
frontInternal:	draw()

## Design Considerations

In our planning process, we discussed updating the display when there are multiple commands in the expression. For example, ‘pu fd 50 pd fd 50’ can be split into two operations and evaluated successively. The issue we debated was to either handle a command, update the display, and handle the next command or to handle all commands at once and then pass the display the states of the turtle after every command and allow the front end to update this. We choose to go with the first option so that the movement of the turtle would be more dynamic and evaluating programming expressions would be more visible to a new programmer.

Another issue we discussed was having an animation timer versus an update method. The display with the turtle only needs to be refreshed after the user inputs more commands that alter its features. So rather than have an animation timer with a step method responsible for refreshing values, we opted to have the back end update the display so that way the changes take place sequentially and the problem of having all of the changes take place within one frame is avoided.

## Team Responsibilities

The team is split into two parts, front end (Michael and Michael) and back end (Andy and Anirudh). The front end side will work on User Interface and managing display changes and handling user input. The back end will handle the programming interpretation and command changes. In addition, cross collaboration will be essential to making the most effective and best designed project. Testing edge cases and debugging larger bug issues will take place at group meetings.
