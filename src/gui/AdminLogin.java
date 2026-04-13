package gui;

import core.AccountSys;
import core.AdminSys;

import javax.swing.*;

public class AdminLogin {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Admin Login");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(frame, panel);
        // 设置界面可见
        frame.setVisible(true);
    }

    private static void placeComponents(JFrame frame, JPanel panel) {
        panel.setLayout(null);

        // 输入id
        JLabel idLabel = new JLabel("Admin:");
        JTextField idField = new JTextField(20);


        // 输入密码
        JLabel pwdLabel = new JLabel("Password:");
        JPasswordField pwdField = new JPasswordField(20);

        // 登录按钮
        JButton submit = new JButton("login");

        // tip
        JLabel tip = new JLabel("提示");

        submit.addActionListener(e -> {
            String id = idField.getText();
            String pwd = new String(pwdField.getPassword());

            if (id.isEmpty() || pwd.isEmpty()) {
                JOptionPane.showMessageDialog(tip, "请填写所有字段！");
            }
            else if (AccountSys.isCorrectAdmin(id, pwd)) {
                JOptionPane.showMessageDialog(tip, "登录成功，欢迎 管理员" + id);
                frame.dispose();
                new AdminFrame();
            }
            else {
                JOptionPane.showMessageDialog(tip, "账户密码错误");
            }
        });

        idLabel.setBounds(10,20,80,25);
        idField.setBounds(100,20,165,25);
        pwdLabel.setBounds(10,50,80,25);
        pwdField.setBounds(100,50,165,25);
        submit.setBounds(10, 80, 80, 25);

        panel.add(idLabel);
        panel.add(idField);
        panel.add(pwdLabel);
        panel.add(pwdField);
        panel.add(submit);
        panel.add(tip);
    }
}
