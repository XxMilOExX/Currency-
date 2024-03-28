# Currencny Converter MVVM + Clean Architecture 

✨ A Currency Converter with MVVM + Clean Architecture using [Fixer API](https://fixer.io/) ✨
The application is offline first. On Free API. It fetches currency data for first time and then gives users ability to pull new data on demand.


## Usecase:
- [Usecase-1]() : Downloads Currency rates from "latest" endpoints and caches in local Database. Displays Date of last update. In case of No internet, user can continue to do currency conversions
- [Usecase-2](): User can see last X days (currently 3) history of his selections, from "Time-series" endpoint of API in following forms: 
 - In the Form of list (Requires Internet)
 - In the form of Graph (Requires Internet)
- [Usecase-3](): User can see conversion of his current selection into 10 other popular currencies
- [Unit Test](): So Far unit tests are written for the first Usecase only. I will continue adding unit tests. 

## Architecture

<img width="679" alt="Screenshot 2023-02-04 at 7 20 31 PM" src="https://user-images.githubusercontent.com/5016570/216902862-c60eeb23-8088-40ed-acc6-c74504717d83.png">

Uses concepts of the notorious Uncle Bob's architecture called [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).</br>
* Better separation of concerns. Each module has a clear API., Feature related classes life in different modules and can't be referenced without explicit module dependency.
* Every layer is using their own model and a 'LayerObjectMapper' class is used to convert an object to its relative pattern 
* Usecases represents a UI use cases, communication between Repo and UI is done through it and maps repo object back to UI objects. 


## Tech stack - Library:
- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) - Flow is used to pass (send) a stream of data that can be computed asynchronously
- [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - for dependency injection.
 - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - For reactive style programming (from VM to UI). 
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - Used to create room db and store the data.
  - [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) - Used to navigate between fragments
  - [Data Binding](https://developer.android.com/topic/libraries/data-binding) - Used to bind UI components in your XML layouts.
- [Retrofit](https://github.com/square/retrofit) - Used for REST api communication.
- [OkHttp](http://square.github.io/okhttp/) - HTTP client that's efficient by default: HTTP/2 support allows all requests to the same host to share a socket
- [Gson](https://github.com/square/moshi) - Used to convert Java Objects into their JSON representation and vice versa.
- [Ktlint](https://pinterest.github.io/ktlint/) - ktlint aims to capture the official Kotlin coding conventions and Android Kotlin Style Guide.
Testing 
- [Google-truth](https://github.com/google/truth) - Google Truth 
- [Mockito](https://site.mockito.org/) - Mockito
- [JUnit]() JUnit


## Future Improvements: 
- Finish writing test cases 
- Add work Manager to download data on specific intervals


## Screenshots:
![WhatsApp Image 2023-02-10 at 9 11 50 PM](https://user-images.githubusercontent.com/5016570/218141842-7343f755-c4a8-4dc0-8765-b36ac181a9af.jpeg)
![WhatsApp Image 2023-02-10 at 9 11 45 PM](https://user-images.githubusercontent.com/5016570/218142105-278a931a-0a8c-457d-94b4-4701dbe01350.jpeg)
![WhatsApp Image 2023-02-10 at 9 11 44 PM (1)](https://user-images.githubusercontent.com/5016570/218142214-0e79b7c8-e148-4249-9adf-66a76aea13d1.jpeg)



