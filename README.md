# Introduction

This is an Android Application written in Kotlin that acquires properties from the `/search` endpoint as below:

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

      https://github.com/maquadir/Muhammad-Adeemul-Khader.git

# Architecture and Design
The application follows an ```MVVM architecture``` as given below

<img width="449" alt="Screen Shot 2019-12-25 at 8 05 55 AM" src="https://user-images.githubusercontent.com/19331629/71425127-6ca3cc00-26ed-11ea-98b5-a344b54b7050.png">

# Setup
### Manifest File
- Since the app is going to fetch from json url .We have to add the following internet permissions to the manifest file.

        <uses-permission android:name="android.permission.INTERNET" />

### Material Styling
- A ```progress indicator``` is displayed during the async JSON read operation.
- ```Recycler view``` to display data
- ```SwitchCompat``` for toggle button

### Invoke JSON Url using Retrofit.Builder()
We have declared a Properties API interface to invoke the JSON url using ```Retrofit.Builder()```

         return Retrofit.Builder()
                .baseUrl(BASE_URl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(PropertiesApi::class.java)

### Model
A Model contains all the data classes.
```PropertyModel``` data class is created using ```"JSON to Kotlin class"``` plugin to map the JSON data to Kotlin.

### Service
A ```ApiService``` class to handle api requests and a repository takes care of how data will be fetched from the api.

              val apiService = ApiService()
              val repository = Repository(apiService)

### View Model
We set up a ```view model factory``` which is responsible for creating view models with dependencies.It contains the data required in the View and translates the data which is stored in Model which then can be present inside a View. ```ViewModel``` and ```View``` are connected through ```Databinding``` and the observable ```Livedata```.

### Coroutines
```Coroutines``` are a great way to write asynchronous code that is perfectly readable and maintainable. We use it to perform a job of reading data from the ```JSON``` url.We must specify the ```Dispatcher(Main/IO)``` for relevant thread to be used for the operation.

### View
- It is the UI part that represents the current state of information that is visible to the user.A ```Recycler View``` displays the data read from the JSON. - - We setup a recycler view adapter to take care of displaying the data on the view.
- We use ```Glide``` to display profile image using view binding.
- We use ```SwitchCompat``` for switching between buy and rent properties

### Getting the data from Api
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
Constructor dependency injection has been used at multiple instances.It allows for less code overall when trying to get reference to services you share across classes, and decouples components nicely in general. We are using ```manual dependency injection``` in our application, whereas there are other automated libraries like ```Hilt``` and ```Koin```.

### View Binding
The ```View Binding``` Library is an ```Android Jetpack library``` that allows you to create class files for the XML layouts.All the UIView elements in the layout are binded to the class program through view binding.

### Further Architecture & Design Decisions
There are different approaches to going about with the development. Below is the one adopted -
1. Using a single Activity to display the buy and rent properties within the same activity using a recyclerview as both the api's respond with the same model structure. This appraoch would rely on a single activity viewmodel. 
2. Another approach would have been to use 2 separate fragments one for each api call incase more unique features are to be added . This approach would rely on 2 separate view models , one for each fragment.We are not using this for our application.
3. We are not using ```sharedviewmodel``` in case we chose point 2 as there is no sharing of data involved between both the fragments. 
4. We are using a repository pattern to fetch data from a api source. In our case we have a single suspend function as both api's just differ in the ```search_mode= "buy|rent"``` parameter.
5. Tried to adhere to best practices as given in android documentation whereever possible

# Testing
Unit tests are added for the ```ApiService, Repository & VM```.
Automated UI tests using Espresso can be added for the UI

# Libraries Used
HTTP
- ```Sqaure Retrofit2``` - For API calls
- ```Retrofit Coroutines``` 

UI
- ```RecyclerView``` - For displaying as list
- ```Glide``` - For images
- ```ViewModel``` - MVVM Architecture
- ```Progress Indicator``` 
- ```SwitchCompat``` 

Testing 
- ```Mockito``` - Unit Tests
- ```JUnit``` - UI Tests
- ```MockWebServer``` - mocking server requests

# Screenshot
![Screenshot 2023-03-19 at 5 14 50 pm](https://user-images.githubusercontent.com/19331629/226157198-3ce774eb-d65f-4bd1-947d-e530f1119f5f.png)

![Screenshot 2023-03-19 at 5 14 56 pm](https://user-images.githubusercontent.com/19331629/226157202-ef127460-c95c-43fa-b302-fffa0c99a7c7.png)






