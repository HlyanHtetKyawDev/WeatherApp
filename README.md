# Weather & Sports Android App Assignment

### Overview
This is an Android application development assignment that integrates WeatherAPI.com services to create a multi-functional app with weather search, astronomy data, and sports information features.

### Used Technologies
- UI Design
    - Jetpack Compose

- Architecture
    - MVVM (Model-View-ViewModel) clean architecture pattern

- Technologies
    - Kotlin programming language
    - Retrofit for API integration
    - Hilt for dependency injection
    - Firebase Authentication for google account login and manual login
    - Compose Navigation
    - MockK for testing
    - Additional third-party libraries as needed

### Features
The application consists of four screens:

1. **Login Screen**
    - Google account login integration
    - Email and password login integration (email = test@gmail.com, password = abc123 for testing)

2. **Search Screen**
    - Integration with WeatherAPI Search endpoint
    - Search functionality for locations (set Yangon as initial location). Users can tap each search item to go to Astronomy Screen
    - Log out function

3. **Astronomy Screen**
    - Integration with WeatherAPI Astronomy endpoint
    - Search functionality for locations. Click the location to go to Sports Screen
    - Show Distance and Local time
    - Display Sun and Moon data with engaging animations. Users can tap the card to trigger the animation.
    - Log out function
    
4. **Sports Screen**
    - Integration with WeatherAPI Sports endpoint
    - Show 3 sport types (Football, Cricket, Golf)
    - If the Sport type (golf) has no data, show “There is no relevant data” on screen.
    - Log out function