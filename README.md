### Description: Simple telegram bot to find food

### Video Demo: https://www.youtube.com/watch?v=9vSGQq71fRY

## Motivation to the project
One pain point for me and people around me is that it was difficult to find food places some times, especially for supper. Although we generally use Google Map, but we can't tell on a glance where is open. More often than not, we end up at food establishment that are closed. Hence, this bot returns result of food establishment that are currently open, helping us to eat (nom nom) now!

## Techical background
This is a simple telegram bot written in Java using the TelegramBots library (https://github.com/rubenlagus/TelegramBots)

The results are fetched from a Places API offered by Google. To read more about the documentation, please refer here: https://developers.google.com/maps/documentation/places/web-service/search-nearby

When the user send over a location to the bot, the program will extract the latitude and longitude of the location and build a request to the API. For example: 
https://maps.googleapis.com/maps/api/place/nearbysearch/json?keyword=cafe&location=1.342887%2C103.723641&radius=1500&key=<YOUR_GOOGLE_PLACE_TOKEN>
_note that it should be through the location function of the telegram bot, and not statements/number such as postal code, or address_

Results returned from the API will be processed to show the Name, rating, and address. A URL based on the address's latitude and longitude will be generated to redirect user to Google Map and navigate themselves to the food establishment.

Up to 20 results will be returned in pages of 5. User can navigate to the next page with the "Next Page" button at the end of each page (of 5 listings).

## Bot.java
This file contains default methods required by the library. One of the most important method is onUpdateReceived. As it is the main method listening to any input by the user.

## botConfig.java
Contains important configuration to allow the bot to use the tokens specified in application.properties

## GetFoodSvc.java
Contains method to call Places API and to return the results as a list

## Listing.java
Define POJO listing - with properties name, rating, vicinity (address), place_id, url, latitute and longtitute

## navButton.java
Define a row of 2 buttons

### Trying the program
To try running this program, clone this repository and add the following lines into application.properties:
telegram.token=<YOUR_TELEGRAM_TOKEN>
google.place.token=<YOUR_GOOGLE_PLACE_TOKEN>

The telegram token can be obtained when you register a bot using @BotFather. See this documentation for a comprehensive guide: https://core.telegram.org/bots/tutorial

Token for google place: You will need to set up a project under Google Cloud Console. Follow the guide here: https://developers.google.com/maps/documentation/places/web-service/get-api-key