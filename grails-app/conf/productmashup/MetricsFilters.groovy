package productmashup
import static Constants.*

class MetricsFilters {

     def filters = {
        all(controller:'productSearch', action:'search') {
            before = {
                //Create a container for the subsequent use by controller/service layer
                //metrics will be dumped into this container which gets logged at the end of the request processing
                request[METRICS_CONTAINER] = new ProductSearchMetricsContainer();
                request["startTime"] = System.currentTimeMillis();
            }
            after = { Map model ->
                ProductSearchMetricsContainer metricsContainer = request[METRICS_CONTAINER];
                metricsContainer.addBrowserMetric( request )
                long endTime = System.currentTimeMillis();
                metricsContainer.addElapsedTimeMetric(endTime-(long)(request['startTime']))
                metricsContainer.metric.insert()
            }
            afterView = { Exception e ->

            }
        }
    }
}
