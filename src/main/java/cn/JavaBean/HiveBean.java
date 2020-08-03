package cn.JavaBean;

public class HiveBean {
    private String dt;
    private String referer;

    public HiveBean() {
    }

    public HiveBean(String dt, String referer) {
        this.dt = dt;
        this.referer = referer;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    @Override
    public String toString() {
        return "HiveBean{" +
                "dt='" + dt + '\'' +
                ", referer='" + referer + '\'' +
                '}';
    }
}
