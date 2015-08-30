# Mandelbrott
An application that calculates the Mandelbrot in distributed manner. The system
consists of a SWING-based client and an arbitrary number of workers. The client divides the
task and provides job objects in the Tuple Space. The workers take these job objects from
the Tuple Space, perform the calculation and put the result back in the Tuple Space.
Thereafter the SWING-based client takes the results out of the Tuple Space and presents
them. The application implements the following features / internal behavior:
 The client implements a listener. This listener responds if a worker puts a result
object in the Tuple Space.
 A colored bar on the left side indicates which worker calculates the corresponding
line of the image (cf. illustration 1).
 Multiple simultaneously active clients should be possible.
 A working Ant script starts the SWING-based client and two workers.

The instructions to run the application are provided in the Readme.txt
