package com.pubsub.externalsubscriber.config;

import java.util.Collections;
import java.util.Map;

import com.google.cloud.MonitoredResource;
import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.Logging;
import com.google.cloud.logging.LoggingOptions;
import com.google.cloud.logging.Payload.JsonPayload;
import com.google.cloud.logging.Severity;


public class DemoLogEntry {
   
    public static void writeLogs(String logName, Map<String,String> payload) throws Exception {
        LoggingOptions options = LoggingOptions.getDefaultInstance();
        try(Logging logging = options.getService()) {
//            MetricInfo metricInfo = MetricInfo.newBuilder("test-metric", "severity >= ERROR")
//                    .setDescription("Log entries with severity higher or equal to ERROR")
//                    .build();
//                logging.create(metricInfo);
            LogEntry entry =
                    LogEntry.newBuilder(JsonPayload.of(payload))
                        .setSeverity(Severity.INFO)
                        .setLogName(logName)
                        .setResource(MonitoredResource.newBuilder("global").build())
                        .build();
            // Writes the log entry asynchronously
            logging.write(Collections.singleton(entry));

            // Optional - flush any pending log entries just before Logging is closed
            logging.flush();
        }
        
    }
}