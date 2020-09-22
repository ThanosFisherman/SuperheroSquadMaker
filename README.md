Super Hero Squad Maker
=====================

An app that allows you to recruit a team of super heroes. When opened, it shows your current squad at the top (if any) and a list of all characters 
from the Marvel API. This is the "Root" screen.

Tapping on a character shows details and gives you the option to recruit or fire the character. 

### Basic functionality.

Initially the app fetches the Characters list from Marvel's API over the network. Once you click on a list entry you are redirected to a second activity where you can view further details about this character and recruit or fire them (if already recruited before). When you navigate back to the root activity you shall be able to view the recruited characters by their avatar on a horizontal scrollable view at the top.

### Tech stack used.

This app features the following technologies and coding practices.

- Kotlin
- Gradle Kotlin DSL scripts
- Clean architecture principles
- Module separation for each layer (domain, presentation, data). I decided to use modules for better separation of concerns.
- Coroutines for asynchronous calls. Suspending functions, Flow and Channels.
- A Hybrid of MVVM-MVI approach. I use ViewModels with LiveData that emit `sealed` classes as states. The UI decides what to do based on the state received.
- RecyclerView Pagination. New Characters will keep showing up on the list as you keep scrolling. 
- Firebase Crashlytics for crash reporting
- Room for data persistence
- Material Components library
- Splash screen
- And more... (I may keep updating this list if something important pops up to my mind)

### Future goals and improvements

- Improve error handling
- Migrate to paging 3
- Provide more meaningful error messages e.g. for network failures
- Add search capabilities. User should be able to search for his favorite character instead of endlessly scrolling through the list
- Ditch `LiveData` for `StateFlow`
- Tests? Ain't nobody got time for tests. Production is the REAL TEST! (just kidding)
- UI improvements. Make the UI more faithful to the mockups provided.
- Cache EVERYTHING. Including the list of characters fetched from network
- Maybe add some fancy animations along the way
- And More I guess

### Screenshots

![superHeroSquadMain](https://user-images.githubusercontent.com/4888330/89290818-1d9a1e00-d662-11ea-8730-5e9c8c27d5aa.png)
![Thanos](https://user-images.githubusercontent.com/4888330/87173782-ed6b9380-c2de-11ea-82f9-113c8c90ab38.png)



