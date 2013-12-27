package productmashup

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

import javax.servlet.http.HttpServletRequest

import static productmashup.Constants.METRICS_CONTAINER

/**
 * Created by IntelliJ IDEA.
 * User: suman
 * Date: 12/26/13.
 * Time: 2:58 PM
 */
class Util {

    static ProductSearchMetricsContainer metricsContainer() {
        //grab the current http request to access the metrics container
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request[METRICS_CONTAINER]
    }
}