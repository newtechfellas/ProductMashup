package productmashup

import java.sql.Timestamp

/**
 * Generic metrics logging implementation
 */

class ProductSearchMetric {

    Date timestamp = new Date();
    String searchString;

    // These URIs holds lot of information. manufacturers selected, search string,
    // any other advanced filter options chosen such as price range
    String bestbuyRestUri;
    String amazonRestUri;
    String walmartRestUri;
    boolean isCachedData;
    String browser;
    long elapsedTime;

    static constraints = {
        timestamp  unique: true
        bestbuyRestUri nullable: true, maxSize: 1024
        amazonRestUri nullable: true, maxSize: 1024
        walmartRestUri nullable: true, maxSize: 1024
        browser nullable: true
    }

    @Override
    public String toString() {
        return "ProductSearchMetric{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", searchString='" + searchString + '\'' +
                ", bestbuyRestUri='" + bestbuyRestUri + '\'' +
                ", amazonRestUri='" + amazonRestUri + '\'' +
                ", walmartRestUri='" + walmartRestUri + '\'' +
                ", isCachedData=" + isCachedData +
                ", browser='" + browser + '\'' +
                ", elapsedTime=" + elapsedTime +
                ", version=" + version +
                '}';
    }
}
