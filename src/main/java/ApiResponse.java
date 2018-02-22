import java.util.List;

public class ApiResponse {

    private String requestId;
    private double took;
    private List<AlertMeta> data;
    private Paging paging;

    public String getRequestId() {
        return requestId;
    }

    public ApiResponse setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public double getTook() {
        return took;
    }

    public ApiResponse setTook(double took) {
        this.took = took;
        return this;
    }

    public List<AlertMeta> getData() {
        return data;
    }

    public ApiResponse setData(List<AlertMeta> data) {
        this.data = data;
        return this;
    }

    public Paging getPaging() {
        return paging;
    }

    public ApiResponse setPaging(Paging paging) {
        this.paging = paging;
        return this;
    }

    public class Paging {

        private String next;
        private String first;
        private String last;

        public String getNext() {
            return next;
        }

        public Paging setNext(String next) {
            this.next = next;
            return this;
        }

        public String getFirst() {
            return first;
        }

        public Paging setFirst(String first) {
            this.first = first;
            return this;
        }

        public String getLast() {
            return last;
        }

        public Paging setLast(String last) {
            this.last = last;
            return this;
        }
    }
}
