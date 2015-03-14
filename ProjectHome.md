The project is developed in a joint collaboration between University POLITEHNICA of Bucharest (http://cipsm.hpc.pub.ro/vanet/) and Rutgers University (http://discolab.rutgers.edu/).

Disclaimer: Before downloading, you must read and agree with this disclaimer:

The sole purpose of this software is to collect data about traffic. The identity and location of the users participating on this project will not be stored at the server, nor will it be used for any other commercial or non-commercial purpose (without explicit permission from the users participating in the experiment). It is the responsibility of the user to fully obbey all the applicable laws, including those related to the driving and the use of mobile phones. By downloading this software, you agree to use it in only a lawful manner. Installing it on  someone's else phone without their knowledge is not permitted. Our team assumes no liability and it is not responsible for any misuse, damage or consequence of using this software.


---


Traffic congestions are realities of urban environments. The road infrastructure capacities cannot cope with the rate of increase in the number of cars. This, coupled with traffic incidents, work zones, weather conditions, make traffic congestions one major concern for municipalities and research organizations.

Traffic Collector is an application that gathers information about daily traffic. The idea is to collect data that can be used to construct a model of the traffic patterns in a large city such as Bucharest. This means getting data about the traffic on a particular road, depending on the hour of the day or time of the week for example. The model can be used to make predictions about traffic, to construct smart navigation application that might tell you to avoid a certain route because it's always congested at this time of the day. Having such data anyone could construct his own mining solution to possible recognize traffic patterns (e.g., is congestion a function of season or just daily weather, are there possible streets congested just because people prefer commodity, straight and large boulevards instead of smaller adjacent routes, etc.). Anyone? Yes, anyone, because we rely on public crowds (meaning you) to collect the data, and give back freely the obtained data to anyone interesting (everything is public).

So how can you help? We developed an Android application (for now only Android, we hope to have alternatives for other OSs) that you could install on your Android-operated smartphone and use it while driving (or, when taking the bus - it doesn't matter if you have your own car or not, if you travel in congestion than your feedback interest us a lot). The application gathers data obtained from the phone's GPS, it continuously samples the data. What data? We promise not to take any intrusive information (the public source is openly available - if you don't trust us, please feel free to check it out). The only thing we collect is:

> timestamp    latitude longitude    speed

This data is sent to the server running within the University POLITEHNICA of Bucharest. In fact, there are two ways you can set the communication (sending the data): real-time (a new sample is immediately sent to the server, over WiFi or 3G) and history (the data is collected on the smartphone until the user explicitly send it to the server - this transmission mode could be used when you don't want to waste any money using the 3G connection).

So why a real-time mode? Traffic Collector actually lets your friend know your location. Or how about you seeing what your friends are doing (or at least their current location). The application lets you specify your Facebook public account. A Web-based application can further show you the information collected (real-time) about your friends.

For more detailed about the application please check out the Wiki.
