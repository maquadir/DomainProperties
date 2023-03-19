# Introduction

This is an Android Application written in Kotlin that acquires property models from the `/search` endpoint as below:

        curl -X POST https://domain-adapter-api.domain.com.au/v1/search -H 'contenttype: application/json' -d '{
         "dwelling_types": ["Apartment / Unit / Flat"],
         "search_mode": "buy"
        }'

        curl -X POST https://domain-adapter-api.domain.com.au/v1/search -H 'contenttype: application/json' -d '{
         "dwelling_types": ["Apartment / Unit / Flat"],
         "search_mode": "rent"
        }'

# Requirements
Displays the properties in a list
 - Display a control on the top of the screen to switch between buy and rent.
 - The properties must be displayed in a list.
 - Display properties on screen with the following UI elements:
      - Image
      - Price
      - Bed, bath, car count
      - Address
      - Agency Logo


# Installation
Clone the repo and install the dependencies.

      https://github.com/maquadir/DomainProperties.git

# Architecture and Design
The application follows an MVVM architecture as given below

<img width="449" alt="Screen Shot 2019-12-25 at 8 05 55 AM" src="https://user-images.githubusercontent.com/19331629/71425127-6ca3cc00-26ed-11ea-98b5-a344b54b7050.png">

# Setup
### Manifest File
- Since the app is going to fetch from json url .We have to add the following internet permissions to the manifest file.

        <uses-permission android:name="android.permission.INTERNET" />

### Material Styling
- A progress bar is displayed during the async JSON read operation.

### Invoke JSON Url using Retrofit.Builder()
We have declared a Properties API interface to invoke the JSON url using Retrofit.Builder()

         return Retrofit.Builder()
                .baseUrl(BASE_URl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(PropertiesApi::class.java)

### Model
A Model contains all the data classes.
PopularMovies and MovieDetails data classes are created using "JSON to Kotlin class" plugin to map the JSON data to Kotlin. A ApiService class to handle api requests and a repository takes care of how data will be fetched from the api.

              val apiService = ApiService()
              val repository = Repository(apiService)

### View Model
We set up a view model factory which is responsible for creating view models.It contains the data required in the View and translates the data which is stored in Model which then can be present inside a View. ViewModel and View are connected through Databinding and the observable Livedata.

### Coroutines
Coroutines are a great way to write asynchronous code that is perfectly readable and maintainable. We use it to perform a job of reading data from the JSON url.

### View
It is the UI part that represents the current state of information that is visible to the user.A Recycler View displays the data read from the JSON. We setup a recycler view adapter to take care of displaying the data on the view.
We use Glide to display profile image using view binding.

### Getting the data from Github
- To get the buy properties, you will need to call the following endpoint:

       curl -X POST https://domain-adapter-api.domain.com.au/v1/search -H 'contenttype: application/json' -d '{
         "dwelling_types": ["Apartment / Unit / Flat"],
         "search_mode": "buy"
           }'

- To get the rent properties:
      
      curl -X POST https://domain-adapter-api.domain.com.au/v1/search -H 'contenttype: application/json' -d '{
        "dwelling_types": ["Apartment / Unit / Flat"],
        "search_mode": "rent"
        }'
      

### Dependency Injection
Constructor dependency injection has been used at multiple instances.It allows for less code overall when trying to get reference to services you share across classes, and decouples components nicely in general

### View Binding
The View Binding Library is an Android Jetpack library that allows you to create class files for the XML layouts.All the UIView elements in the layout are binded to the class program through view binding.

# Testing
Unit tests for the ApiService, Repository & VM.
Automated UI tests using Espresso

# Libraries Used
HTTP
- Sqaure Retrofit2 - For API calls
- Retrofit Coroutines

UI
- RecyclerView - For displaying as list
- Glide - For images
- ViewModel - MVVM Architecture
- Coroutines - Async Calls

Testing
- Mockito - Unit Tests
- JUnit - UI Tests
- MockWebServer

# Screenshot




