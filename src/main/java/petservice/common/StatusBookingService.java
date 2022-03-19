package petservice.common;

public  class StatusBookingService {

    public enum StatusBooking {
        WAITING("waiting"), PROCESSING("processing"), FINISH("finish");
        private String status;
        StatusBooking(String status){
            this.status = status;
        }

        @Override
        public String toString() {
            return status;
        }
    }

    public static String handleUpperCaseString(String status) {
        StatusBooking[] statuses = StatusBooking.values();
        for(StatusBooking item : statuses){
            if (status.trim().toLowerCase().equals(item.toString())){
                return item.toString();
            }
        }
        return  "";
    }

}
