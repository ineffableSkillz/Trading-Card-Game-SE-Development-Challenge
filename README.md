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

### Hour 7:13 - Client 0
- Client program started
- Client can connect to server and authenticate
- **need to find a way to transfer player object**

### Hour 8:07 - Beam me up (over), Scotty!
- Player object transferred successfully
- Info is written to a file and re-opened.
- Spent an hour trying to solve a problem due to a missing 'return'. If time, will try without writing to file

### Hour 8:52 - Managing Individual Connections
New Threads:
- CommunicationThread
- CheckVoide ConnectionThread

New Data Structure:
- ArrayList<Player> onlinePlayers

New network and thread variables in Player

### Hour 10:06 - C&C Server, do you copy?
- Creating text-based menu for users to choose which action (Will do some cline-side logic before approaching socket communication)
- Methods fully implemented client-side
- ParseCards method implemented (view javadocs for more info)
- Fixed issue with multiple scanners, even after closed with try with resources
- Made template in thread within Server.

