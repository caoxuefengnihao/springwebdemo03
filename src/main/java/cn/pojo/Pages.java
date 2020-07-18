package cn.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("pages")
@Scope(value="prototype")
public class Pages {
    //当前页
    private int currentPage = 0;
     //页面大小
    private int pageSize = 5;
    //当前页的数据集合
    private List<stu> list;
    //总数据量
    private int totalCount = 0;
    //总页数
    private int totalPage = 0;

    public Pages() {
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public List<stu> getList() {
        return list;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setList(List<stu> list) {
        this.list = list;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "Pages{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", list=" + list +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                '}';
    }
}
