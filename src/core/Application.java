package core;

import java.io.Serializable;
import java.util.function.Supplier;

public class Application implements Serializable {
    private int id;                 // 申请单id
    private int studId;             // 学生id
    private int jobId;              // 岗位id
    private String submitTime;      // 申请时间: YYYY/MM/DD
    private Boolean isApplied;      // 是否被录取

    public Application(int id, int studId, int jobId, String submitTime, Boolean isApplied) {
        this.id = id;
        this.studId = studId;
        this.jobId = jobId;
        this.submitTime = submitTime;
        this.isApplied = isApplied;
    }

    public int getId() {
        return id;
    }

    public int getStudId() {
        return studId;
    }

    public int getJobId() {
        return jobId;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public Boolean getApplied() {
        return isApplied;
    }

    public void setStudId(int studId) {
        this.studId = studId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public void setApplied(Supplier<Boolean> supplier) {
        isApplied = supplier.get();
    }

    @Override
    public String toString() {
        return " | 应聘信息:"
                + " | 申请单id:" + id
                + " | 学生id" + studId
                + " | 岗位id" + jobId
                + " | 申请时间" + submitTime
                + " | 是否录用" + isApplied
                + " |";
    }

    public static AppBuilder builder() {
        return new AppBuilder();
    }

    public static class AppBuilder {
        int id;
        int studId;
        int jobId;
        String submitTime;
        Boolean isApplied;

        public AppBuilder setId(Supplier<Integer> supplier) {
            this.id = supplier.get();
            return this;
        }

        public AppBuilder setStudId(Supplier<Integer> supplier) {
            this.studId = supplier.get();
            return this;
        }

        public AppBuilder setJobId(Supplier<Integer> supplier) {
            this.jobId = supplier.get();
            return this;
        }

        public AppBuilder setSubmitTime(Supplier<String> supplier) {
            this.submitTime = supplier.get();
            return this;
        }

        public AppBuilder setApplied(Supplier<Boolean> supplier) {
            isApplied = supplier.get();
            return this;
        }

        public Application build(){
            return new Application(id, studId, jobId, submitTime, isApplied);
        }
    }
}
