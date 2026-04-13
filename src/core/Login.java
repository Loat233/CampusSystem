package core;

import java.util.Scanner;
import static java.lang.Integer.parseInt;

@Deprecated
public class Login {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("========= 登录界面 =========");
            System.out.println("1. 登录学生账号");
            System.out.println("2. 登录管理员账号");
            System.out.println("5. 退出用户系统");
            System.out.println("========================");
            if (userInput.hasNext()) {
                switch (parseInt(userInput.nextLine())) {
                    case 1 -> {
                        System.out.print("账号id: ");
                        if (userInput.hasNext()) {
                            String acc = userInput.nextLine();
                            System.out.print("密码: ");
                            String pwd = userInput.nextLine();
                            if (AccountSys.getStudAcc(acc) != null) {
                                if (AccountSys.isCorrectStud(acc, pwd)) {
                                    UserSys.userTable(parseInt(acc));
                                }
                                else {
                                    System.out.println("密码错误");
                                }
                            }
                            else {
                                System.out.println("账号不存在");
                            }
                        }
                    }
                    case 2 -> {
                        System.out.print("账号id: ");
                        if (userInput.hasNext()) {
                            String acc = userInput.nextLine();
                            System.out.print("密码: ");
                            String pwd = userInput.nextLine();
                            if (AccountSys.getAdminAcc(acc) != null) {
                                if (AccountSys.isCorrectAdmin(acc, pwd)) {
                                    AdminSys.adminTable();
                                }
                                else {
                                    System.out.println("密码错误");
                                }
                            }
                            else {
                                System.out.println("账号不存在");
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

    // TODO
    private static boolean isStudAcc(String str) {
        return str.matches("[0-9]+");
    }

    // TODO
    private static boolean isAdminAcc(String str) {
        return true;
    }

    // TODO
    private static boolean isAvailPwd(String id, String str) {
        return true;
    }
}
