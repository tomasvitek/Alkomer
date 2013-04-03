![Alkomer](/img/alkomer.png)

*Alkomer* is an Android app for realtime calculation of *[blood alcohol content (BAC)](http://en.wikipedia.org/wiki/Blood_alcohol_content)* with monthly, weekly and daily statistics and graphs. (It speaks only Czech, but the upcoming *Alkomer 2* will be in English).

The app was available in the [Google Play](https://play.google.com/store/apps/details?id=cz.jmx.tomik.alkomer.android), with almost 20.000 downloads.

In anticipation of *Alkomer 2* release, I am open sourcing original *Alkomer* app. The app is dependant on backend, which is no longer running and therefore if you compile this app, it will not work. However this code may be helpful to somebody.

## Screenshots
![Dashboard](/img/screenshots/small/dashboard.png)

![List of drinks](/img/screenshots/small/drinklist.png)

![List of drunk drinks](/img/screenshots/small/glasslist.png)

![Month statistics](/img/screenshots/small/month_stats.png)

![Statistics](/img/screenshots/small/statistics.png)

## Requirements
*Android 1.6* or higher

## Dependencies:
- *View Flow for Android* ~ [https://github.com/pakerfeldt/android-viewflow](https://github.com/pakerfeldt/android-viewflow)

## How to build and run the project:
1. Download *[View Flow for Android](https://github.com/pakerfeldt/android-viewflow)*, import the library project to *Eclipse*. In *CloudApp for Android* project go to *Properties → Android → Library*, add a reference to the library project.
2. Build and run! :)

## License
See `LICENSE.md`.