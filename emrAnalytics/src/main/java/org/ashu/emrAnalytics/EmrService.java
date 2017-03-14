package org.ashu.emrAnalytics;

import java.net.URISyntaxException;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.Tuple2;

/**
 * @author ashu
 *
 */

public class EmrService {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
	private SparkSession session;
	private JavaSparkContext context;
	private SQLContext sqlContext;
	private Dataset<Row> dataSet;

	public EmrService(String inputCsv) {
		String sourceCsvURI = null;
		try {
			sourceCsvURI = ClassLoader.getSystemResource(inputCsv).toURI().toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session = SparkSession.builder().appName(EmrService.class.getName()).master("local[*]").getOrCreate();
		context = new JavaSparkContext(session.sparkContext());
		sqlContext = new SQLContext(session);
		dataSet = session.read().option("header", "true").option("inferSchema", "true").csv(sourceCsvURI);

		// showMonthWiseCount("2016");
	}

	/**
	 * @param number
	 * @return
	 */
	public List<Emergency> showTopN(String number) {
		Dataset<Emergency> emergencyDs = dataSet.as(Encoders.bean(Emergency.class));
		Integer num = Integer.valueOf(number);
		return emergencyDs.takeAsList(num);
	}

	/**
	 * @return
	 */
	public List<Result> showYearWiseCount() {
		Dataset<Emergency> emergencyDs = dataSet.as(Encoders.bean(Emergency.class));
		emergencyDs.createOrReplaceTempView("emergency");
		Dataset<Row> countYear = sqlContext
				.sql("select  from_unixtime(unix_timestamp(timeStamp),'Y') as column1, count(*) as column2  from emergency group by from_unixtime(unix_timestamp(timeStamp),'Y')")
				.cache();

		Dataset<Result> result = countYear.as(Encoders.bean(Result.class));
		return result.collectAsList();
	}

	/**
	 * @param year
	 * @return
	 */
	public List<Result> showMonthWiseCount(String year) {
		Dataset<Emergency> emergencyDs = dataSet.as(Encoders.bean(Emergency.class));
		Dataset<Emergency> filterDS = emergencyDs.filter(f -> f.getYear() == Integer.parseInt(year));
		filterDS.createOrReplaceTempView("emergency");
		Dataset<Row> countMonth = sqlContext
				.sql("select  from_unixtime(unix_timestamp(timeStamp),'MMM') as column1, count(*) as column2  from emergency group by from_unixtime(unix_timestamp(timeStamp),'MMM')")
				.cache();

		Dataset<Result> result = countMonth.as(Encoders.bean(Result.class));
		result.show();
		return result.collectAsList();
	}

	/**
	 * @param year
	 * @return
	 */
	public List<Result> showWeekdayCount(String year) {
		Dataset<Emergency> emergencyDs = dataSet.as(Encoders.bean(Emergency.class));
		emergencyDs.createOrReplaceTempView("emergency");
		Dataset<Row> dayOfWeek = sqlContext
				.sql("select  timeStamp, from_unixtime(unix_timestamp(timeStamp), 'EEEEE') AS dow  from emergency where from_unixtime(unix_timestamp(timeStamp), 'Y') = '"
						+ year + "'");
		dayOfWeek.createOrReplaceTempView("dayOfWeek");
		Dataset<Result> doweek = sqlContext
				.sql("select dow as column1, count(*) as column2 from dayOfWeek group by dow")
				.as(Encoders.bean(Result.class));
		return doweek.collectAsList();
	}

	/**
	 * @param year
	 * @return
	 */
	public List<Result> showCityWiseIncidents(String year) {
		Dataset<Emergency> emergencyDs = dataSet.as(Encoders.bean(Emergency.class));

		JavaRDD<Emergency> javaRDD = emergencyDs.filter(e -> e.getYear() == Integer.parseInt(year)).toJavaRDD();
		JavaPairRDD<String, Long> cityPair = javaRDD.mapToPair(e -> new Tuple2<String, Long>(e.getTwp(), (long) 1));
		JavaPairRDD<String, Long> reducedCityPair = cityPair.reduceByKey((num1, num2) -> num1 + num2);
		List<Result> results = reducedCityPair.map(t -> new Result(t._1, t._2)).collect();
		return results;
	}

	/**
	 * @param cityName
	 * @return
	 */
	public List<Result> showCityRecordsPerYear(String cityName) {
		Dataset<Emergency> emergencyDs = dataSet.as(Encoders.bean(Emergency.class));
		JavaRDD<Emergency> javaRDD = emergencyDs.filter(e -> cityName.equalsIgnoreCase(e.getTwp())).toJavaRDD();
		javaRDD.take(10).forEach(e -> System.out.println(e));
		JavaPairRDD<String, Long> yearPair = javaRDD
				.mapToPair(e -> new Tuple2<String, Long>(String.valueOf(e.getYear()), (long) 1));
		JavaPairRDD<String, Long> reducedYearPair = yearPair.reduceByKey((num1, num2) -> num1 + num2);
		List<Result> results = reducedYearPair.map(t -> new Result(t._1, t._2)).collect();
		return results;
	}

	/**
	 * @return
	 */
	public List<Result> showDayTimeCount() {
		Dataset<Emergency> emergencyDs = dataSet.as(Encoders.bean(Emergency.class));
		emergencyDs.createOrReplaceTempView("emergency");
		Dataset<Row> countMorning = sqlContext
				.sql("select  'Morning' as column1, count(*) as column2  from emergency where from_unixtime(unix_timeStamp(timestamp),'k') >= 6 and  from_unixtime(unix_timeStamp(timestamp),'k') < 12")
				.cache();
		Dataset<Row> countAfternoon = sqlContext
				.sql("select  'Afternoon' as column1, count(*) as count  from emergency where  from_unixtime(unix_timeStamp(timestamp),'k') >= 12 and  from_unixtime(unix_timeStamp(timestamp),'k') < 16")
				.cache();
		Dataset<Row> countEvening = sqlContext
				.sql("select  'Evening' as column1, count(*) as count  from emergency where  from_unixtime(unix_timeStamp(timestamp),'k') >= 16 and  from_unixtime(unix_timeStamp(timestamp),'k') < 20")
				.cache();
		Dataset<Row> countNight = sqlContext
				.sql("select  'Night' as column1, count(*) as count  from emergency where  from_unixtime(unix_timeStamp(timestamp),'k') >= 20 or  from_unixtime(unix_timeStamp(timestamp),'k') < 6")
				.cache();
		Dataset<Row> count = countMorning.union(countAfternoon).union(countEvening).union(countNight);
		Dataset<Result> result = count.as(Encoders.bean(Result.class));
		return result.collectAsList();
	}

}
