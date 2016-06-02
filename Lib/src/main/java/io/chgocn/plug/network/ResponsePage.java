package io.chgocn.plug.network;

/**
 * Response Page
 *
 * @author chgocn
 */
public class ResponsePage {
    private long totalCount;
    private int pageSize;
    private int pageNo;

    public ResponsePage() {
    }

    public ResponsePage(long totalCount, int pageSize, int pageNo) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public boolean hasNextPage() {
        return totalCount > pageSize * pageNo;
    }
}
