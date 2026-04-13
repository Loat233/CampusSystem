package core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

@Deprecated
public class UserSys {
    private static final DataBase db = new DataBase();
    private static int studId;

    public static void userTable(int input) {
        System.out.println("系统初始化...");
        studId = input;
        db.init(studId);
        Scanner userInput = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("========= 学生系统 =========");
            System.out.println("1. 浏览兼职信息");
            System.out.println("2. 提交应聘申请");
            System.out.println("3. 查看录用情况");
            System.out.println("4. 退出用户系统");
            System.out.println("========================");
            if (userInput.hasNext()) {
                switch (parseInt(userInput.nextLine())) {
                    case 1 -> db.printJobs();
                    case 2 -> insertApp(userInput);
                    case 3 -> db.printUserApps();
                    case 4 -> {
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



    ////////////////////////
    /// Application类相关 ///
    ///////////////////////

    private static void insertApp(Scanner input){
        Application app = Application.builder()
                .setId(() -> db.getLastAppId() + 1)
                .setStudId(() -> studId)
                .setJobId(() -> {
                    while (true) {
                        System.out.print("岗位id: ");
                        String str = input.nextLine();
                        if (isNum(str) && db.getJob(parseInt(str)) != null) {
                            return parseInt(str);
                        }
                        else {
                            System.out.println("id为 " + str + " 的岗位不存在, 请重新输入");
                        }
                    }
                })
                .setSubmitTime(() -> {
                    Date date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    String timeStr = format.format(date);
                    System.out.println("申请时间: " + timeStr);
                    return timeStr;
                })
                .setApplied(() -> false)
                .build();

        db.insertUserApp(app);
    }
}
