Simple telegram bot to find food

Video Demo:  

Description:
This is a simple telegram bot written in Java using the TelegramBots library (https://github.com/rubenlagus/TelegramBots) to find food establishment near you that are currently open. The results are fetched from a Places API offered by Google. To read more about the documentation, please refer here: https://developers.google.com/maps/documentation/places/web-service/search-nearby

When the user send over a location to the bot, the program will extract the latitude and longitude of the location and build a request to the API: 


When the bot has started, send your location to the bot (note that it should be through the location function of the telegram bot, and not statements/number such as postal code, or address)

When the bot receive the location, it will call a Google API. For example
https://maps.googleapis.com/maps/api/place/nearbysearch/json?keyword=cafe&location=1.342887%2C103.723641&radius=1500&key=<YOUR_GOOGLE_PLACE_TOKEN>

Results returned from the API will be processed to show the Name, rating, and address. A URL based on the address's latitude and longitude will be generated to redirect user to Google Map and navigate themselves to the food establishment.

Up to 20 results will be returned in pages of 5. User can navigate to the next page with the "Next Page" button at the end of each page (of 5 listings).

To try running this program, clone this repository and add the following lines into application.properties:
telegram.token=<YOUR_TELEGRAM_TOKEN>
google.place.token=<YOUR_GOOGLE_PLACE_TOKEN>

The telegram token can be obtained when you register a bot using @BotFather. See this documentation for a comprehensive guide: https://core.telegram.org/bots/tutorial

Token for google place: You will need to set up a project under Google Cloud Console. Follow the guide here: https://developers.google.com/maps/documentation/places/web-service/get-api-key