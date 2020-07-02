Android Engineer Task
=====================

Here at Plum, we work in "[squads](https://labs.spotify.com/2014/03/27/spotify-engineering-culture-part-1/)" – a more startup-appropriate name for saying small, cross-functional teams that are focussed on a mission and KPI.

The fundamental basis of any squad, is a team of kick-ass superheroes. Hence this task,
**The Superhero Squad Maker.** Your mission is to build the ultimate superhero squad using
the [Marvel API](https://developer.marvel.com) for data.

The app is simple. When opened, it shows your current squad at the top (if any) and a list of all characters 
from the Marvel API. This is the "Root" screen in the Abstract project.

Tapping on a character shows details and gives you theoption to recruit or fire the character. 
("Hero Details" Abstract screens)

You're expected to use some sort of in-app data persistence mechanism so the squad persists
between app restarts. ("Root screen - Has Squad" Abstract screen)

We've included a design spec describing the user experience and interactions.


To use the Marvel API you need to sign up for a developer account. Once you sign up you
will find your API keys in the Account section. If you don't want to sign up, we can
provide you with our own API key.

**Note**
For your app to work with the API, it's important to add "*" as an Authorized Referrer
in the Account section.

Deliverable
-----------

You should write your solution (in Kotlin most preferably) and deliver it as a Pull Request in your repo.
Document your design choices and anything else you think we need to know in the PR description.

What we look for
----------------

In a nutshell, we're looking for a functioning app and correct, tidy, production-quality
code. Imagine that your code will be read by other developers in your team – keep them happy :-)

Take initiative if needed to cover any “black” spots on the UX flow of the test app, and be as creative as you want!

We don't want you to reinvent the wheel so feel free to use whatever libraries you deem
appropriate, but please explain your decisions in the PR.


Resources
---------
* Plum Android Task Design: https://tinyurl.com/PlumAndroidTask
* Marvel API Reference: https://developer.marvel.com/docs
* Marvel API Auth Guide: https://developer.marvel.com/documentation/authorization

Disclaimer
----------

We will not use any of this code for any of Plum's applications.
