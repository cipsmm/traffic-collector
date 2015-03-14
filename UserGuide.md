# Introduction #

Traffic Collector is an application that gathers information about daily traffic. The idea is to collect data that can be used to construct a model of the traffic patterns in a large city such as Bucharest. This means getting data about the traffic on a particular road, depending on the hour of the day or time of the week for example. The model can be used to make predictions about traffic, to construct smart navigation application that might tell you to avoid a certain route because it's always congested at this time of the day. Having such data anyone could construct his own mining solution to possible recognize traffic patterns (e.g., is congestion a function of season or just daily weather, are there possible streets congested just because people prefer commodity, straight and large boulevards instead of smaller adjacent routes, etc.). Anyone? Yes, anyone, because we rely on public crowds (meaning you) to collect the data, and give back freely the obtained data to anyone interesting (everything is public).

The project is developed in a joint collaboration between University POLITEHNICA of Bucharest (http://cipsm.hpc.pub.ro/vanet/) and Rutgers University (http://discolab.rutgers.edu/). TrafficCollector can be downloaded and installed from [the download page](http://code.google.com/p/traffic-collector/downloads/list).

# So why should i participate ? #

The Android application provides several advantages to the user.

TrafficCollector lets you **collect** data about your traffic patterns. This means that whenever you're in traffic and use this application, you log important information about your routes and driving style. This can be use **only by you** (your own private account of data is password-protected and all data is stored encrypted for your safety) to see where you've been, how many kilometers you've drove on a daily basis, what was the average speed. You can use such data to understand your usual routes and maybe recognize a possible solution to avoid daily congestions (and minimize the time spent in traffic until you get to work, or even better - back from work). And you can use this data to see the driving utility: each time you fill in the gas tank of your car, you can anaylize how much real-life kilometers you managed to drove on the last fill.

TrafficCollector lets you **share** data with friends. Maybe you want to show to the world (meaning close friends or wife) where you are in traffic, in real life. This is a solution designed to smooth the sharing of data among users, and TrafficCollector allows you to send a private URL to your friends. Your friends will see there in real-time your current driving position.

TrafficCollector lets you **see** your surroundings. The application itself allows you to see where you are, and various statistics regarding your driving performance.

So why don't you give it a try today and let us know your impression?

# User Guide #

After installing the application the main screen is similar to:

![http://traffic-collector.googlecode.com/svn/wiki/images/main_screen.png](http://traffic-collector.googlecode.com/svn/wiki/images/main_screen.png)

You can select to **send data real-time** or not using the _Real-time_ selector. When you want to start using the application (meaning start collecting the data) you can click on the _Start_ button. When you want to stop using the application you can click the _Stop_ button. For the application to run you are asked to also start the GPS sensor (in your phone's Settings the GPS option should be selected). You know the application is running when you can see in the _Status_ the Traffic Collector service, as in this image:

![http://traffic-collector.googlecode.com/svn/wiki/images/service_detail.png](http://traffic-collector.googlecode.com/svn/wiki/images/service_detail.png)

When the application is _not_ in real-time mode, it collects the data in the background. It actually stores the data on your mobile phone (in SQLite), until you are ready to upload it and start using it to see all kinds of statistics. This is an appropriate mode when you don't have a 3G or WiFi connection around, so you can collect the data and upload it later on when an Internet connection becomes available. The **Upload** button can be used to start the explicit upload process.

When the application is _in_ real-time mode, you will see a screen similar to:

![http://traffic-collector.googlecode.com/svn/wiki/images/main_screen_menu.png](http://traffic-collector.googlecode.com/svn/wiki/images/main_screen_menu.png)

In real-time you have a unique page assigned to your real-time data. You have complete control with whom you share your position (if you even want to share it). If you want to send an URL to a Web page where another person can see your position, you can use the **Share Position** button. When you don't want to share your position anymore click on **Stop Sharing**. You can even regenerate the URL of your Web page.

In the **Settings** dialog (click on the Settings within the application) you have the possibility to set parameters, see traffic statistics and see your current position.

You can see your current configuration within Settings. The screen is similar to:

![http://traffic-collector.googlecode.com/svn/wiki/images/settings.png](http://traffic-collector.googlecode.com/svn/wiki/images/settings.png)

The **name** is the string that will be used in the Google Maps attached to your own data (so that your friend would recognize you). The **username** is used by you to access your private data repository (within the application, when clicking on the link below the text **To view your statistics follow:** you are sent to your personal Web page, which requires authentication). You can even change your password (in case you forgot it) from this Settings tab.

The next Settings tab lets you see several traffic statistics. The screen is similar to:

![http://traffic-collector.googlecode.com/svn/wiki/images/statistics.png](http://traffic-collector.googlecode.com/svn/wiki/images/statistics.png)

Here you can see your current speed, top speed, as well as the length of your current route.

You can also see your own position and what's around you in the third tab. The screen is similar to:

![http://traffic-collector.googlecode.com/svn/wiki/images/map_screen.png](http://traffic-collector.googlecode.com/svn/wiki/images/map_screen.png)