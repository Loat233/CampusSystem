package core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;


public class AccountSys {
    private static final String filename = "accounts.txt";

    public static String getStudAcc(String id){
        try {
            Path path = Path.of(filename);
            List<String> list = Files.lines(path)
                    .filter(line -> line.matches("^\\s*" + id + "\\s*:.*"))
                    .toList();
            return list.isEmpty() ? null : list.getFirst();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAdminAcc(String id){
        try {
            Path path = Path.of(filename);
            List<String> list = Files.lines(path)
                    .filter(line -> line.matches("^\\*\\s*" + id + "\\s*:.*"))
                    .toList();
            return list.isEmpty() ? null : list.getFirst();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isCorrectStud(String id, String pwd) {
        String str = getStudAcc(id);
        if (str != null) {
            String[] acc  = str.split(":");
            return Objects.equals(acc[1], pwd);
        }
        return false;
    }

    public static boolean isCorrectAdmin(String id, String pwd) {
        String str = getAdminAcc(id);
        if (str != null) {
            String[] acc  = str.split(":");
            return Objects.equals(acc[1], pwd);
        }
        return false;
    }

    private static void writeAcc(String id, String pwd) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(filename, true);
        writer.write(id + ":" + pwd + "\n");
        writer.flush();
    }
}
