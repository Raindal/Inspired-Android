# Inspired-Android

An Android app offering a new inspiring video everyday.

Download it here: [Inspired](https://play.google.com/store/apps/details?id=com.neilrosenstech.inspired&hl=en)  
Check out the API application providing the videos: [Inspired API](https://github.com/Raindal/Inspired-API)

## How to install locally

* Clone the repo as the `app` folder of a new Android application (created with Android Studio in my case)

* This app fetches the daily video from the Inspired API application and displays it using the Youtube Android
Player library. This library requires an Android Youtube Data API key to work. Use the Google Developer Console 
tools to generate one.

* Create a configuration file `src/main/assets/config/config.json` with the following content:

``` json
{
  "youtubeDataApiKey": "[your-key-here]"
}
```

* Don't forget to reference `libs/YoutubeAndroidPlayerApi.jar` in your gradle file

## How to contribute

* Create a feature branch

* Make a pull request
