CompSci 308: SLogo Addition
===================

Michael Kuryshev, slogo_team15

##Estimation: 
#####Before looking at the old code:
* How long do you think it will take you to complete this new feature?
	* All I really remember at this point about SLogo off the top of my head is that it was worse than the other two big projects that we did. The specifics have faded, although I'm sure it won't be too hard to maneuver around the classes. The task itself is fairly simple, so maybe an hour, maybe two.
* How many files will you need to add or update? Why?
	* One class update for creating the view pane, another class for updating the turtle image and a turtle control file to maybe change the update call? So let's guess three, but be safe and say 5 files will have to change.
	
##Review: 
#####After completing the feature:
* How long did it take you to complete this new feature?
	* 2 hours to almost complete, it was a fairly simple to call the parser from the display to change the image and create the new boxes. If I was to make the change without calling the parser and made the image viewable in the box, then this would have been considerably more difficult.
* How many files did you need to add or update? Why?
	* 4 or 5. The main updates were the turtle view pane (didn't make a new pane as there was no room). All else was a few lines of additional method calls or added strings to bundles. The view was where the turtle states and parser calls for the boxes were created and it's where we handled pen color changes, so I just modeled it off of that.
* Did you get it completely right on the first try?
	* Close. I'm not going to implement the image states in the view for people to see the active image, but conceptual code showing the idea is in the TurtleIDView class. Beyond that though, the edit was straightforward and really just made a new Hbox and parser call so not much room for mistakes.
##Analysis: 
####What do you feel this exercise reveals about your project's design and documentation?
* Was it as good (or bad) as you remembered?
	* Oh my gosh the code is bad. About as bad as I remember sure, but there's no consistency in anything but naming conventions and each method is this densely packed call after call that just is too much effort to really try to read. Honestly, if we haven't done a similar piece of work with the pen color, then this could have gone very poorly...
* What could be improved?
	* Commenting is a must for methods. Also, keeping variables more local to individual methods rather than the whole class would lower dependencies. If that doesn't work, then just breaking up the classes to be clearer would be ideal.
* What would it have been like if you were not familiar with the code at all?
	* Again, we just really need comments to muddle our way through. With comments, this would have been more doable even without the pen model to work off of. But yeah, some of the work in the methods is redundant, hope it wasn't me, but who knows, we'll be good eventually.

Thanks.
