package com.pubsub.internalsubscriber.config;

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
            LogEntry entry =
                    LogEntry.newBuilder(JsonPayload.of(payload))
                        .setSeverity(Severity.INFO)
                        .setLogName(logName)
                        .setResource(MonitoredResource.newBuilder("global").build())
                        .build();
            logging.write(Collections.singleton(entry));
            logging.flush();
        }
        
    }
}