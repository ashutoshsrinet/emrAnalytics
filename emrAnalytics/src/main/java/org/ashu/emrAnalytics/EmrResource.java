package org.ashu.emrAnalytics;

import static spark.Spark.get;

/**
 * @author ashu
 *
 */
public class EmrResource {

	private static final String API_CONTEXT = "/api/v1";

	private final EmrService emrService;

	public EmrResource(EmrService emrService) {
		this.emrService = emrService;
		setupEndpoints();
	}

	private void setupEndpoints() {
		get(API_CONTEXT + "/showTopN/:n", "application/json", (request, response)

		-> emrService.showTopN(request.params(":n")), new JsonTransformer());
		
		get(API_CONTEXT + "/getyearwise", "application/json", (request, response)

				-> emrService.showYearWiseCount(), new JsonTransformer());
		
		get(API_CONTEXT + "/getmonthwise/:n", "application/json", (request, response)

				-> emrService.showMonthWiseCount(request.params(":n")), new JsonTransformer());
		
		get(API_CONTEXT + "/wdcount/:n", "application/json", (request, response)

				-> emrService.showWeekdayCount(request.params(":n")), new JsonTransformer());
	
		get(API_CONTEXT + "/citycount/:n", "application/json", (request, response)

				-> emrService.showCityWiseIncidents(request.params(":n")), new JsonTransformer());
		
		get(API_CONTEXT + "/yearcount/:n", "application/json", (request, response)

				-> emrService.showCityRecordsPerYear(request.params(":n")), new JsonTransformer());
		
		get(API_CONTEXT + "/gettodcount", "application/json", (request, response)

				-> emrService.showDayTimeCount(), new JsonTransformer());
		
	}
	
	

}
