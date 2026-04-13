package gui;

import core.Application;
import core.DataBase;
import core.Job;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserFrame extends JFrame {
    private final DataBase DB;
    private final JTable jobTable;
    private final DefaultTableModel jobTableModel;
    private final JTable appTable;
    private final DefaultTableModel appTableModel;

    public UserFrame(int studId) {
        this.DB = new DataBase();
        DB.init(studId);

        // 窗口基础设置
        setTitle("校园兼职平台 - 用户端");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        //  顶部功能按钮栏
        JPanel topPanel = new JPanel();
        JButton btnQueryJob = new JButton("查看兼职岗位");
        JButton btnApply = new JButton("报名兼职申请");
        JButton btnMyApply = new JButton("查看我的申请进度");

        topPanel.add(btnQueryJob);
        topPanel.add(btnApply);
        topPanel.add(btnMyApply);
        add(topPanel, BorderLayout.NORTH);

        // 中间表格面板（展示岗位/申请数据）
        // 兼职岗位表格列名
        String[] jobTitle = {"岗位ID", "兼职名称", "薪资", "公司", "工作时间", "联系电话"};
        jobTableModel = new DefaultTableModel(null, jobTitle);
        jobTable = new JTable(jobTableModel);
        JScrollPane jobScroll = new JScrollPane(jobTable);

        // 我的申请进度表格列名
        String[] applyTitle = {"申请ID", "兼职岗位", "申请时间", "审核状态(待审核/通过/拒绝)"};
        appTableModel = new DefaultTableModel(null, applyTitle);
        appTable = new JTable(appTableModel);
        JScrollPane appScroll = new JScrollPane(appTable);

        // 选项卡切换：岗位列表 / 我的申请
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("兼职岗位信息", jobScroll);
        tabbedPane.add("我的申请进度", appScroll);
        add(tabbedPane, BorderLayout.CENTER);


        // 查看全部兼职信息 按钮事件
        btnQueryJob.addActionListener(e -> {
            // 清空旧表格数据
            jobTableModel.setRowCount(0);
            //从数据库查询全部兼职岗位, 循环填充表格
            for (Job job : DB.getJOBLIST()) {
                Object[] row = {job.getId(), job.getName(), job.getSalary(), job.getCompany(), job.getWorkTime(), job.getContact()};
                jobTableModel.addRow(row);
            }
        });


        // 选中岗位，提交兼职报名申请
        btnApply.addActionListener(e -> {
            // 获取表格选中行
            int row = jobTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "请先选中要报名的兼职岗位！");
                return;
            }
            // 获取选中岗位ID
            int jobId = Integer.parseInt(jobTable.getValueAt(row, 0).toString());
            System.out.println(DB.getLastAppId());
            Application apply = Application.builder()
                    .setId(() -> DB.getLastAppId() + 1)
                    .setStudId(() -> studId)
                    .setJobId(() -> jobId)
                    .setSubmitTime(() -> {
                        java.util.Date date = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                        String timeStr = format.format(date);
                        System.out.println("申请时间: " + timeStr);
                        return timeStr;
                    })
                    .build();
            // 调用数据库提交申请
            DB.insertUserApp(apply);
        });


        // 查询本人申请审核状态
        btnMyApply.addActionListener(e -> {
            // 清空旧列表
            appTableModel.setRowCount(0);

            // 循环填充审核状态
            String status;
            for (Application app : DB.getUSERAPPLIST(studId)) {
                if (app.getApplied() == null) {
                   status = "待审核";
                }
                else if (app.getApplied() == true) {
                    status = "通过";
                }
                else {
                    status = "未通过";
                }
                Object[] row = {app.getId(), app.getJobId(), app.getSubmitTime(), status};
                appTableModel.addRow(row);
            }
        });
    }
}