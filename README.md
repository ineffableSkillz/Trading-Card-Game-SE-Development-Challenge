# Trading Card Game SE Development Challenge

## What's this repo all about?
This repository will hold the code for my Trading Card Market game which will consist of a central cerver and many clients connecting and trading cards on a **live** stock exchange market.

The challenge is to create **everything** (including planning) within 24hours, whilst implementing as many good Software Engineering practices, design patterns, javadocs (and so on) as possible to push my Software Engineering abilities to the limit!

## What to expect at the end of the 24 hours?
This page will contain:
- A detailed explanation of the project (structure, UML, other diagrams etc.)
- A demo running via video
- Setup Instructions
- A summary of:
  - Newly learnt Software Engineering techniques
  - Implemented Software Engineering practices
  - Un-implemented Software ENgineering Practices 
  - Security Practices

## Development Timeline
### Hour 1 - Setting Up
Project Setup: 
- Initialised initial classes
- Drew a brief design on paper
- Create Appropriate Text Files 

### Hour 2 - Who's the XML Document?
- Converted the previously created text files into .XML files
- Learnt about XML parsers and created one for the card class

### Hour 4.5 - Reading in the Players
- Altered playerInfo.XML to hold all relevant values
- XML parser for players has been implemented
- Removed Bad Code Smell (duplication) in the reader class

### Hour 5 - Saving the Players
- Creted a packAllPlayers() method in the FileParser class
- Created appropriate toString-esque methods within Player.class and Card.class
- Format testing done via command line input
- Javadocs comments added to all *used* methods in FileParser

### Hour 5:35 - Saving the Cards
- Writer for cards has been implemented and tested
- CardRarity enum updated to aid writing process

### Hour 6:05 - Setting up connection
- Created SSLServer socket
- Created Accept Thread \w Validation methods (need to setup client side before I can progress)
- Accept Thread can check to see if a user has an account, given the correct credentials

