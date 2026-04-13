package core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class DataBase {
    private static List<Job> JOBLIST = new LinkedList<>();
    private static List<Application> APPLIST = new LinkedList<>();
    private static List<Application> USERAPPLIST = new LinkedList<>();
    private int lastJobId = 0;
    private int lastAppId = 0;

    public void init() {
        try {
            loadJobs();
            loadApps();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLastJobId() {
        return lastJobId;
    }

    public int getLastAppId() {
        return lastAppId;
    }

    private void loadJobs() throws IOException {
        Path path = Path.of("jobs");
        // 如果文件不存在，创建空文件
        if (!Files.exists(path)) {
            Files.createFile(path);
            JOBLIST = new LinkedList<>();
            return;
        }

        // 如果文件为空
        if (Files.size(path) == 0) {
            System.out.println("岗位信息文件为空");
            JOBLIST = new LinkedList<>();
            return;
        }

        // 尝试读取文件
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("jobs"))) {
            JOBLIST = (List<Job>) in.readObject();
            updateJobId();
            System.out.println("岗位信息文件读取完成");
        } catch (ClassNotFoundException e) {
            System.out.println("岗位信息文件格式错误，重置为空列表");
            JOBLIST = new LinkedList<>();
            saveJobs();
        }
    }

    private void loadApps() throws IOException {
        Path path = Path.of("applications");

        // 如果文件不存在，创建空文件
        if (!Files.exists(path)) {
            Files.createFile(path);
            APPLIST = new LinkedList<>();
            return;
        }

        // 如果文件为空
        if (Files.size(path) == 0) {
            System.out.println("应聘记录文件为空");
            APPLIST = new LinkedList<>();
            return;
        }

        // 尝试读取文件
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("applications"))) {
            APPLIST = (List<Application>) in.readObject();
            updateAppId();
            System.out.println("应聘记录文件读取完成");
        } catch (ClassNotFoundException | EOFException e) {
            System.out.println("应聘记录文件格式错误，重置为空列表");
            APPLIST = new LinkedList<>();
            saveApps();
        }
    }

    public void updateJobId() {
        lastJobId = JOBLIST.isEmpty() ? 0 : JOBLIST.getLast().getId();
    }

    public void updateAppId() {
        lastAppId = APPLIST.isEmpty() ? 0 : APPLIST.getLast().getId();
    }

    public void saveJobs() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("jobs"))) {
            updateJobId();
            out.writeObject(JOBLIST);
            out.flush();
            System.out.println("岗位信息文件保存完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveApps() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("applications"))) {
            updateAppId();
            out.writeObject(APPLIST);
            out.flush();
            System.out.println("应聘记录文件保存完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printJobs() {
        JOBLIST.forEach(System.out::println);
    }

    public void printApps() {
        APPLIST.forEach(System.out::println);
    }

    public List<Job> getJOBLIST() {
        return JOBLIST;
    }

    public List<Application> getAPPLIST() {
        return APPLIST;
    }

    public List<Application> getUSERAPPLIST(int studId) {
        return USERAPPLIST;
    }

    public Job getJob(int id) {
        for (Job job : JOBLIST) {
            if (job.getId() == id) {
                return job;
            }
        }
        return null;
    }

    public Application getApp(int id) {
        for (Application app : APPLIST) {
            if (app.getId() == id) {
                return app;
            }
        }
        return null;
    }

    public void insertJob(Job job) {
        JOBLIST.add(job);
        saveJobs();
    }

    public void insertApp(Application app) {
        APPLIST.add(app);
        saveApps();
    }

    public boolean deleteJob(int id) {
        boolean isRemove = JOBLIST.removeIf(job -> job.getId() == id);
        if (isRemove) {
            saveJobs();
        }
        return isRemove;
    }

    public boolean deleteApp(int id) {
        boolean isRemove = APPLIST.removeIf(app -> app.getId() == id);
        if (isRemove) {
            saveApps();
        }
        return isRemove;
    }

    /// studId方法

    public void init(int studId) {
        try {
            loadJobs();
            loadUSERApps(studId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUSERApps(int studId) throws IOException {
        Path path = Path.of("applications");
        try {
            if (Files.size(path) == 0) {
                System.out.println("应聘记录文件为空");
            }
            else {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("applications"));
                List<Application> list = (List<Application>) in.readObject();
                Application app;
                for (Application application : list) {
                    app = application;
                    if (app.getStudId() == studId) {
                        USERAPPLIST.add(app);
                    }
                }
                lastAppId = list.isEmpty() ? 0 : list.getLast().getId();
                System.out.println("应聘记录文件读取完成");
            }
        } catch (NoSuchFileException | ClassNotFoundException e) {
            Files.createFile(path);
        }
    }

    public void printUserApps() {
        USERAPPLIST.forEach(System.out::println);
    }

    public void insertUserApp(Application app) {
        USERAPPLIST.add(app);
        try {
            loadApps();
            insertApp(app);
            APPLIST = new LinkedList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
