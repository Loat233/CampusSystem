import java.io.Serializable;
import java.util.function.Supplier;

public class Job implements Serializable {
    private int id;             // id
    private String name;        // 岗位名称
    private String company;     // 单位
    private int salary;         // 薪资
    private String workTime;    // 工作时间: HH/MM_HH/MM
    private String contact;     // 联系人电话

    public Job(int id, String name, String company, int salary, String workTime, String contact) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.salary = salary;
        this.workTime = workTime;
        this.contact = contact;
    }

    /// get方法
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public int getSalary() {
        return salary;
    }

    public String getWorkTime() {
        return workTime;
    }

    public String getContact() {
        return contact;
    }

    /// set方法
    public Job setName(Supplier<String> supplier) {
        this.name = supplier.get();
        return this;
    }

    public Job setCompany(Supplier<String> supplier) {
        this.company = supplier.get();
        return this;
    }

    public Job setSalary(Supplier<Integer> supplier) {
        this.salary = supplier.get();
        return this;
    }

    public Job setWorkTime(Supplier<String> supplier) {
        this.workTime = supplier.get();
        return this;
    }

    public Job setContact(Supplier<String> supplier) {
        this.contact = supplier.get();
        return this;
    }

    @Override
    public String toString() {
        return "| 岗位名称:" + name
                + " | 岗位id" + id
                + " | 单位:" + company
                + " | 薪资:" + salary
                + " | 工作时间:" + workTime
                + " | 联系人:" + contact
                + " |";
    }

    /// 建造者模式
    public static JobBuilder builder() {
        return new JobBuilder();
    }

    public static class JobBuilder {
        private int id;             // id
        private String name;        // 岗位名称
        private String company;     // 单位
        private int salary;      // 薪资
        private String workTime;    // 工作时间
        private String contact;

        public Job build() {
            return new Job(id, name, company, salary, workTime, contact);
        }

        public JobBuilder setId(Supplier<Integer> supplier) {
            this.id = supplier.get();
            return this;
        }

        public JobBuilder setName(Supplier<String> supplier) {
            this.name = supplier.get();
            return this;
        }

        public JobBuilder setCompany(Supplier<String> supplier) {
            this.company = supplier.get();
            return this;
        }

        public JobBuilder setSalary(Supplier<Integer> supplier) {
            this.salary = supplier.get();
            return this;
        }

        public JobBuilder setWorkTime(Supplier<String> supplier) {
            this.workTime = supplier.get();
            return this;
        }

        public JobBuilder setContact(Supplier<String> supplier) {
            this.contact = supplier.get();
            return this;
        }
    }

}
