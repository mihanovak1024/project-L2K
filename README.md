# Project - Learn to Know (L2K)

Project L2K is short for Learn to know and is my personal learning playground. 
Here I'll be playing with stuff I learn from books and videos and make little projects out of them.
Most mini projects here will be in Java. I might create a different project for Android,... 

The code is meant to be well organized with Javadocs and Unit tests and is meant to present my already achieved skills.
Older "little projects" might not be as up-to-date as the newer ones as I currently don't to update the older projects, but only create newer (more up-to-date) ones. 

## TODO
Topics that are already a known concept in my mind, but didn't yet have the privilege to have their very own project:
- Dependency Injection
- Design patterns
- SPI


## Project status
### Finished
- Java multithreading (synchronised)
- Java multithreading (locks and conditions)
- Futures and ListenableFutures ([Guava](https://github.com/google/guava))
- Java Generics
### In progress
- RxJava2
### Running each project
Each little project will have Unit tests, but can also be run (in Java) via the `public static void main()` method.
Each project can be run separately and independently from one another.

## Project structure
Each topic is in its own project folder. The whole repository uses `git-flow-avh`([link](https://github.com/petervanderdoes/gitflow-avh)) and the default branch on Github is the `develop`.
Each project is created and finalised in its own `feature` branch and is merged to `develop` when everything is done (including Unit tests).
