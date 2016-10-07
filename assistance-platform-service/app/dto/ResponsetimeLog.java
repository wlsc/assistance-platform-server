package dto;

import java.util.List;

public class ResponsetimeLog {
    public ResponsetimeLog() {
    }

    public static class ResponseTimeEntry {
        public ResponseTimeEntry() {
        }

        public long startTime;
        public long responseTime;
        public long processingTime;
        public long bodySize;
        public int eventsNumber;
        public String networkType;
    }

    public long userId;

    public long deviceId;

    public List<ResponseTimeEntry> data;
}
