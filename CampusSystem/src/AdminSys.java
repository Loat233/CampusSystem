import java.util.Scanner;
import static java.lang.Integer.parseInt;

public class AdminSys {
    private static final DataBase db = new DataBase();

    public static void adminTable() {
        System.out.println("系统初始化...");
        db.init();
        Scanner userInput = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("========= 管理员系统 =========");
            System.out.println("1. 管理兼职岗位");
            System.out.println("2. 管理应聘记录");
            System.out.println("5. 退出管理员系统");
            System.out.println("========================");
            if (userInput.hasNext()) {
                switch (parseInt(userInput.nextLine())) {
                    case 1 -> {
                        jobTable(userInput);
                    }
                    case 2 -> {
                        appTable(userInput);
                    }
                    case 5 -> {
                        System.out.println("系统即将关闭，再见");
                        return;
                    }
                }
            }
        }
    }

    private static boolean isNum(String str) {
        return str.matches("[0-9]+");
    }



    ////////////////
    /// Job类相关 ///
    ////////////////

    private static void jobTable(Scanner userInput) {
        while (true) {
            System.out.println();
            System.out.println("========= 管理员系统 =========");
            System.out.println("1. 查看岗位信息");
            System.out.println("2. 添加岗位");
            System.out.println("3. 删除岗位");
            System.out.println("4. 修改岗位");
            System.out.println("5. 返回");
            System.out.println("========================");
            if (userInput.hasNext()) {
                switch (parseInt(userInput.nextLine())) {
                    case 1 -> {
                        checkJob();
                    }
                    case 2 -> {
                        insertJob(userInput);
                    }
                    case 3 -> {
                        while (true) {
                            System.out.println("请输入被删除岗位的id: ");
                            String input = userInput.nextLine();
                            if (isNum(input)) {
                                deleteJob(parseInt(input));
                                break;
                            }
                            else {
                                System.out.println("输入违法, 请重新输入");
                            }
                        }
                    }
                    case 4 -> {
                        while (true) {
                            System.out.println("请输入被修改岗位的id: ");
                            String input = userInput.nextLine();
                            if (isNum(input)) {
                                modifyJob(parseInt(input), userInput);
                                break;
                            }
                            else {
                                System.out.println("输入违法, 请重新输入");
                            }
                        }
                    }
                    case 5 -> {
                        return;
                    }
                }
            }
        }
    }

    private static void checkJob() {
         db.printJobs();
    }

    private static void insertJob(Scanner input) {
        Job job = Job.builder()
                .setId(() -> db.getLastJobId() + 1)
                .setName(() -> {
                    System.out.print("岗位名称: ");
                    return input.nextLine();
                })
                .setCompany(() -> {
                    System.out.print("单位名称: ");
                    return input.nextLine();
                })
                .setSalary(() -> {
                    while (true) {
                        System.out.print("薪资数量: ");
                        String str = input.nextLine();
                        if (isNum(str)) {
                            return parseInt(str);
                        }
                        else {
                            System.out.println("输入违法, 请重新输入");
                        }
                    }
                })
                .setWorkTime(() -> {
                    System.out.print("工作时间: ");
                    return input.nextLine();
                })
                .setContact(() -> {
                    System.out.print("联系人电话: ");
                    return input.nextLine();
                })
                .build();
        db.insertJob(job);
    }

    private static void deleteJob(int id) {
        boolean isRemoved = db.deleteJob(id);

        if (isRemoved) {
            System.out.println("id为" + id + "的岗位删除成功");
            db.saveJobs();
        }
        else {
            System.out.println("不存在id为" + id + "的岗位");
        }
    }

    private static void modifyJob(int id, Scanner input) {
        Job job = db.getJob(id);
        if (job == null) {
            System.out.println("不存在id为" + id + "的岗位");
            return;
        }

        job.setName(() -> {
                System.out.print("岗位名称: ");
                return input.nextLine();
            })
            .setCompany(() -> {
                System.out.print("单位名称: ");
                return input.nextLine();
            })
            .setSalary(() -> {
                while (true) {
                    System.out.print("薪资数量: ");
                    String str = input.nextLine();
                    if (isNum(str)) {
                        return parseInt(str);
                    }
                    else {
                        System.out.println("输入违法, 请重新输入");
                    }
                }
            })
            .setWorkTime(() -> {
                System.out.print("工作时间: ");
                return input.nextLine();
            })
            .setContact(() -> {
                System.out.print("联系人电话: ");
                return input.nextLine();
            });

        System.out.println("id为 " + id + " 的岗位修改成功");
        db.saveJobs();
    }



    ////////////////////////
    /// Application类相关 ///
    ///////////////////////

    private static void appTable(Scanner userInput) {
        while (true) {
            System.out.println();
            System.out.println("========= 管理员系统 =========");
            System.out.println("1. 查看应聘申请");
            System.out.println("2. 添加应聘申请");
            System.out.println("3. 删除应聘申请");
            System.out.println("4. 处理应聘申请");
            System.out.println("5. 返回");
            System.out.println("========================");
            if (userInput.hasNext()) {
                switch (parseInt(userInput.nextLine())) {
                    case 1 -> {
                        checkApp();
                    }
                    case 2 -> {
                        insertApp(userInput);
                    }
                    case 3 -> {
                        while (true) {
                            System.out.println("请输入被删除申请单的id: ");
                            String input = userInput.nextLine();
                            if (isNum(input)) {
                                deleteApp(parseInt(input));
                                break;
                            }
                            else {
                                System.out.println("输入违法, 请重新输入");
                            }
                        }
                    }
                    case 4 -> {
                        while (true) {
                            System.out.println("请输入被修改申请单的id: ");
                            String input = userInput.nextLine();
                            if (isNum(input)) {
                                modifyApp(parseInt(input), userInput);
                                break;
                            }
                            else {
                                System.out.println("输入违法, 请重新输入");
                            }
                        }
                    }
                    case 5 -> {
                        return;
                    }
                }
            }
        }
    }

    private static void checkApp(){
        db.printApps();
    }

    private static void insertApp(Scanner input) {
        Application app = Application.builder()
                .setId(() -> db.getLastAppId() + 1)
                .setStudId(() -> {
                    while (true) {
                        System.out.print("学生id: ");
                        String str = input.nextLine();
                        if (isNum(str)) {
                            return parseInt(str);
                        }
                        else {
                            System.out.println("输入违法, 请重新输入");
                        }
                    }
                })
                .setJobId(() -> {
                    while (true) {
                        System.out.print("岗位id: ");
                        String str = input.nextLine();
                        if (isNum(str) && db.getJob(parseInt(str)) != null) {
                            return parseInt(str);
                        }
                        else {
                            System.out.println("id为" + str + "的岗位不存在, 请重新输入");
                        }
                    }
                })
                .setSubmitTime(() -> {
                    System.out.print("申请时间(YYYY/MM/DD): ");
                    return input.nextLine();
                })
                .setApplied(() -> {
                    System.out.print("是否被录用(Y/n): ");
                    String in = input.nextLine();
                    return in.equalsIgnoreCase("y");
                })
                .build();

        db.insertApp(app);
    }

    private static void deleteApp(int id) {
        boolean isRemoved = db.deleteApp(id);
        if (isRemoved) {
            System.out.println("id为" + id + "的申请单删除成功");
            db.saveApps();
        }
        else {
            System.out.println("不存在id为" + id + "的申请单");
        }
    }

    private static void modifyApp(int id, Scanner input) {
        Application app = db.getApp(id);
        if (app == null) {
            System.out.println("不存在id为" + id + "的申请单");
            return;
        }

        app.setApplied(() -> {
            System.out.print("是否被录用(Y/n): ");
            String in = input.nextLine();
            return in.equalsIgnoreCase("y");
        });
        System.out.println("id为" + id + "的申请单修改成功");
        db.saveApps();
    }
}
