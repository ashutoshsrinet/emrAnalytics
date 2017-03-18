# emrAnalytics

Usecase:

This application deals with analysis of 911 Emergency Response 
collected over the year of 2015 through 2016.

Problem Statements:

	1. Show n records from the csv.
	2. Show number of incidents group by year.
	3. Show monthly incidents for a given year.
	4. Show number of incidents per Day Of Week for a given year.
	5. Show city wise count for a given year.(Done using RDD)
	6. Show year wise count for a city.(Done using RDD)
	7. Show calls split across time of the day.
	
Tech stack:

	1. Spark 2.0
	2. Java 8 
	3. SparkJava 2.0 (mini web framework)
	4. Angular JS
	5. Bootstrap CSS

CSV Format:

	lat: String variable, Latitude
	lng: String variable, Longitude
	desc: String variable, Description of the Emergency Call
	zip: String variable, Zip code
	title: String variable, Title
	timeStamp: String variable, YYYY-MM-DD HH:MM:SS
	twp: String variable, Township
	addr: String variable, Address
	e: String variable, Dummy variable (always 1)
