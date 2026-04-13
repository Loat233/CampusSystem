package gui;

import core.Application;
import core.DataBase;
import core.Job;

import javax.swing.*;
import java.awt.*;

public class AdminFrame {
    private final DataBase DB;
    JFrame frame;

    public AdminFrame() {
        // 初始化数据库
        this.DB = new DataBase();
        DB.init();

        frame = new JFrame("校园兼职平台 - 管理员端");
        frame.setSize(900, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 选项卡面板
        JTabbedPane JTPane = new JTabbedPane();
        JTPane.addTab("岗位管理", createJobsPane());
        JTPane.addTab("应聘记录管理", createAppsPane());

        frame.add(JTPane);
        // 设置界面可见
        frame.setVisible(true);
    }

    private JPanel createJobsPane() {
        // 创建主面板，使用 BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 左侧岗位列表面板
        JPanel listPanel = new JPanel(new BorderLayout(0, 5));
        listPanel.setBorder(BorderFactory.createTitledBorder("岗位列表"));

        // 创建数据模型和列表
        DefaultListModel<Job> jobModel = new DefaultListModel<>();
        for (Job job : DB.getJOBLIST()) {
            jobModel.addElement(job);
        }

        JList<Job> jobList = getJobList(jobModel);

        // 添加滚动条
        JScrollPane listScrollPane = new JScrollPane(jobList);
        listScrollPane.setPreferredSize(new Dimension(300, 0));
        listPanel.add(listScrollPane, BorderLayout.CENTER);

        // 右侧岗位详情面板
        JPanel detailPanel = new JPanel(new GridBagLayout());
        detailPanel.setBorder(BorderFactory.createTitledBorder("岗位详情"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 详情显示区域
        JLabel nameLabel = new JLabel("岗位名称：");
        JLabel nameValue = new JLabel("");
        JLabel companyLabel = new JLabel("单位：");
        JLabel companyValue = new JLabel("");
        JLabel salaryLabel = new JLabel("薪资：");
        JLabel salaryValue = new JLabel("");
        JLabel timeLabel = new JLabel("工作时间：");
        JLabel timeValue = new JLabel("");
        JLabel contactLabel = new JLabel("联系人：");
        JLabel contactValue = new JLabel("");
        JLabel idLabel = new JLabel("岗位ID：");
        JLabel idValue = new JLabel("");

        // 设置详情标签字体
        Font detailFont = new Font("微软雅黑", Font.BOLD, 12);
        nameLabel.setFont(detailFont);
        companyLabel.setFont(detailFont);
        salaryLabel.setFont(detailFont);
        timeLabel.setFont(detailFont);
        contactLabel.setFont(detailFont);
        idLabel.setFont(detailFont);

        // 详情值标签
        Font valueFont = new Font("微软雅黑", Font.PLAIN, 12);
        nameValue.setFont(valueFont);
        companyValue.setFont(valueFont);
        salaryValue.setFont(valueFont);
        timeValue.setFont(valueFont);
        contactValue.setFont(valueFont);
        idValue.setFont(valueFont);

        // 使用HTML使文本可以换行
        nameValue.setToolTipText("<html>岗位名称将在这里显示</html>");
        companyValue.setToolTipText("<html>单位名称将在这里显示</html>");

        // 布局详情面板
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        detailPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        detailPanel.add(nameValue, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        detailPanel.add(companyLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        detailPanel.add(companyValue, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        detailPanel.add(salaryLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        detailPanel.add(salaryValue, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        detailPanel.add(timeLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        detailPanel.add(timeValue, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        detailPanel.add(contactLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        detailPanel.add(contactValue, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        detailPanel.add(idLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        detailPanel.add(idValue, gbc);

        // 添加一个空的组件来占据剩余空间
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        detailPanel.add(Box.createVerticalGlue(), gbc);

        // 操作按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("操作"));

        JButton addButton = new JButton("新增岗位");
        JButton editButton = new JButton("编辑岗位");
        JButton deleteButton = new JButton("删除岗位");
        JButton refreshButton = new JButton("刷新列表");

        // 设置按钮图标
        addButton.setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
        editButton.setIcon(UIManager.getIcon("FileChooser.editIcon"));
        deleteButton.setIcon(UIManager.getIcon("FileChooser.deleteIcon"));
        refreshButton.setIcon(UIManager.getIcon("FileChooser.refreshIcon"));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(refreshButton);


        // 监听列表选择事件
        jobList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Job selectedJob = jobList.getSelectedValue();
                if (selectedJob != null) {
                    // 更新详情显示
                    nameValue.setText(selectedJob.getName());
                    companyValue.setText(selectedJob.getCompany());
                    salaryValue.setText(selectedJob.getSalary() + " 元/月");
                    timeValue.setText(formatWorkTime(selectedJob.getWorkTime()));
                    contactValue.setText(selectedJob.getContact());
                    idValue.setText(String.valueOf(selectedJob.getId()));

                    // 启用编辑和删除按钮
                    editButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                } else {
                    // 清空详情显示
                    nameValue.setText("");
                    companyValue.setText("");
                    salaryValue.setText("");
                    timeValue.setText("");
                    contactValue.setText("");
                    idValue.setText("");

                    // 禁用编辑和删除按钮
                    editButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                }
            }
        });

        // 新增岗位按钮的事件监听
        addButton.addActionListener(e -> {
             addJob(mainPanel, jobList, jobModel);
        });

        // 编辑岗位按钮的事件监听
        editButton.addActionListener(e -> {
            Job selectedJob = jobList.getSelectedValue();
            if (selectedJob != null) {
                editJob(selectedJob, mainPanel, jobList, jobModel);
            }
        });

        // 删除岗位按钮的事件监听
        deleteButton.addActionListener(e -> {
            Job selectedJob = jobList.getSelectedValue();
            if (selectedJob != null) {
                int confirm = JOptionPane.showConfirmDialog(mainPanel,
                        "确定要删除岗位：" + selectedJob.getName() + " 吗？",
                        "确认删除", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = DB.deleteJob(selectedJob.getId());
                    if (success) {
                        jobModel.removeElement(selectedJob);
                        JOptionPane.showMessageDialog(mainPanel, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "删除失败", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        refreshButton.addActionListener(e -> {
            // 重新加载数据
            jobModel.clear();
            for (Job job : DB.getJOBLIST()) {
                jobModel.addElement(job);
            }
            JOptionPane.showMessageDialog(mainPanel, "列表已刷新", "提示", JOptionPane.INFORMATION_MESSAGE);
        });

        // 初始禁用编辑和删除按钮
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        // 使用分割面板分隔列表和详情
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel, detailPanel);
        splitPane.setDividerLocation(350); // 设置分割位置
        splitPane.setResizeWeight(0.3); // 左侧占30%

        // 组装主面板
        mainPanel.add(splitPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private void addJob(JPanel mainPanel, JList<Job> jobList, DefaultListModel<Job> jobModel) {
        // 创建新增岗位对话框
        JDialog addDialog = new JDialog(frame, "新增岗位", true);
        addDialog.setLayout(new BorderLayout(10, 10));
        addDialog.setSize(400, 350);
        addDialog.setLocationRelativeTo(mainPanel);

        // 创建表单面板
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 岗位名称输入
        JLabel nameLabel = new JLabel("岗位名称*：");
        JTextField nameField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(nameField, gbc);

        // 单位名称输入
        JLabel companyLabel = new JLabel("单位名称*：");
        JTextField companyField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(companyLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(companyField, gbc);

        // 薪资输入
        JLabel salaryLabel = new JLabel("薪资（元/月）*：");
        JTextField salaryField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(salaryLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(salaryField, gbc);

        // 工作时间输入
        JLabel timeLabel = new JLabel("工作时间：");
        JTextField timeField = new JTextField(20);
        timeField.setText("09:00-18:00"); // 默认值
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(timeLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(timeField, gbc);

        // 联系人输入
        JLabel contactLabel = new JLabel("联系人电话*：");
        JTextField contactField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        formPanel.add(contactLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(contactField, gbc);

        // 底部按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton submitButton = new JButton("提交");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);

        // 提交按钮事件监听
        submitButton.addActionListener(submitEvent -> {
            // 获取输入值
            String name = nameField.getText().trim();
            String company = companyField.getText().trim();
            String salaryStr = salaryField.getText().trim();
            String workTime = timeField.getText().trim();
            String contact = contactField.getText().trim();

            // 验证必填字段
            if (name.isEmpty() || company.isEmpty() || salaryStr.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(addDialog,
                        "请填写所有带*号的必填字段！",
                        "输入错误", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 验证薪资是否为数字
            int salary;
            try {
                salary = Integer.parseInt(salaryStr);
                if (salary <= 0) {
                    JOptionPane.showMessageDialog(addDialog,
                            "薪资必须为正数！",
                            "输入错误", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addDialog,
                        "薪资必须是有效的数字！",
                        "输入错误", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 生成新的岗位ID（基于当前最大ID+1）
            int newJobId = DB.getLastJobId() + 1;

            // 创建Job对象
            Job newJob = Job.builder()
                    .setId(() -> newJobId)
                    .setName(() -> name)
                    .setCompany(() -> company)
                    .setSalary(() -> salary)
                    .setWorkTime(() -> workTime)
                    .setContact(() -> contact)
                    .build();

            // 保存到数据库
            DB.insertJob(newJob);

            // 更新JList的数据模型
            jobModel.addElement(newJob);

            // 自动选中新增的岗位
            jobList.setSelectedValue(newJob, true);

            // 关闭对话框
            addDialog.dispose();

            // 显示成功消息
            JOptionPane.showMessageDialog(mainPanel,
                    "新增岗位成功！\n岗位ID: " + newJobId + "\n岗位名称: " + name,
                    "操作成功", JOptionPane.INFORMATION_MESSAGE);
        });

        // 取消按钮事件监听
        cancelButton.addActionListener(cancelEvent -> {
            addDialog.dispose();
        });

        // 按回车键触发提交
        addDialog.getRootPane().setDefaultButton(submitButton);

        // 显示对话框
        addDialog.setVisible(true);
    }

    private void editJob(Job selectedJob, JPanel mainPanel, JList<Job> jobList, DefaultListModel<Job> jobModel) {
        JDialog editDialog = new JDialog(frame, "编辑岗位", true);
        editDialog.setLayout(new BorderLayout(10, 10));
        editDialog.setSize(400, 350);
        editDialog.setLocationRelativeTo(mainPanel);

        // 创建表单面板
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 显示当前岗位ID（不可编辑）
        JLabel idLabel = new JLabel("岗位ID：");
        JLabel idValue = new JLabel(String.valueOf(selectedJob.getId()));
        idValue.setForeground(Color.GRAY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(idLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(idValue, gbc);

        // 岗位名称输入
        JLabel nameLabel = new JLabel("岗位名称*：");
        JTextField nameField = new JTextField(selectedJob.getName(), 20);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(nameField, gbc);

        // 单位名称输入
        JLabel companyLabel = new JLabel("单位名称*：");
        JTextField companyField = new JTextField(selectedJob.getCompany(), 20);
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(companyLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(companyField, gbc);

        // 薪资输入
        JLabel salaryLabel = new JLabel("薪资（元/月）*：");
        JTextField salaryField = new JTextField(String.valueOf(selectedJob.getSalary()), 20);
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(salaryLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(salaryField, gbc);

        // 工作时间输入
        JLabel timeLabel = new JLabel("工作时间：");
        String workTime = selectedJob.getWorkTime() == null ? "" : selectedJob.getWorkTime();
        JTextField timeField = new JTextField(workTime, 20);
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        formPanel.add(timeLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(timeField, gbc);

        // 联系人输入
        JLabel contactLabel = new JLabel("联系人电话*：");
        JTextField contactField = new JTextField(selectedJob.getContact(), 20);
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        formPanel.add(contactLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(contactField, gbc);

        // 底部按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton submitButton = new JButton("保存修改");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        editDialog.add(formPanel, BorderLayout.CENTER);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);

        // 保存修改按钮事件监听
        submitButton.addActionListener(submitEvent -> {
            // 获取输入值
            String name = nameField.getText().trim();
            String company = companyField.getText().trim();
            String salaryStr = salaryField.getText().trim();
            String workTimeStr = timeField.getText().trim();
            String contact = contactField.getText().trim();

            // 验证必填字段
            if (name.isEmpty() || company.isEmpty() || salaryStr.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(editDialog,
                        "请填写所有带*号的必填字段！",
                        "输入错误", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 验证薪资是否为数字
            int salary;
            try {
                salary = Integer.parseInt(salaryStr);
                if (salary <= 0) {
                    JOptionPane.showMessageDialog(editDialog,
                            "薪资必须为正数！",
                            "输入错误", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editDialog,
                        "薪资必须是有效的数字！",
                        "输入错误", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 验证电话号码格式（简单的验证）
            if (!contact.matches("\\d{7,11}")) {
                int confirm = JOptionPane.showConfirmDialog(editDialog,
                        "联系人电话格式可能不正确（建议7-11位数字），是否继续保存？",
                        "电话格式警告", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // 使用Job的建造者模式更新岗位信息
            // 注意：这里需要调用setter方法，但根据【文档3：AdminSys.java】中的modifyJob方法，
            // Job类应该有一系列的setter方法
            try {
                // 更新选中岗位的字段
                selectedJob.setName(() -> name)
                        .setCompany(() -> company)
                        .setSalary(() -> salary)
                        .setWorkTime(() -> workTimeStr)
                        .setContact(() -> contact);

                // 保存到数据库
                DB.saveJobs();  // 保存整个岗位列表

                // 更新JList的数据模型显示
                int index = jobList.getSelectedIndex();
                jobModel.setElementAt(selectedJob, index);

                // 自动重新选中当前岗位，刷新右侧详情显示
                jobList.setSelectedIndex(index);

                // 关闭对话框
                editDialog.dispose();

                // 显示成功消息
                JOptionPane.showMessageDialog(mainPanel,
                        "岗位信息修改成功！\n岗位ID: " + selectedJob.getId() + "\n岗位名称: " + name,
                        "操作成功", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPanel,
                        "保存失败：" + ex.getMessage(),
                        "操作失败", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // 取消按钮事件监听
        cancelButton.addActionListener(cancelEvent -> {
            editDialog.dispose();
        });

        // 按回车键触发提交
        editDialog.getRootPane().setDefaultButton(submitButton);

        // 显示对话框
        editDialog.setVisible(true);
    }

    private static JList<Job> getJobList(DefaultListModel<Job> jobModel) {
        JList<Job> jobList = new JList<>(jobModel);
        jobList.setVisibleRowCount(15);

        // 自定义单元格渲染器
        jobList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                if (value instanceof Job job) {
                    // 使用简洁的显示格式
                    label.setText(String.format("岗位: %s (单位: %s)", job.getName(), job.getCompany()));
                }

                return label;
            }
        });
        return jobList;
    }

    private String formatWorkTime(String workTime) {
        if (workTime == null || workTime.trim().isEmpty()) {
            return "未设置";
        }

        // 假设 workTime 格式为 "HH/MM_HH/MM"，例如 "09:00_18:00"
        try {
            String[] times = workTime.split("-");
            if (times.length == 2) {
                return times[0] + " 至 " + times[1];
            }
        } catch (Exception e) {
            // 格式错误，返回原值
        }
        return workTime;
    }



    private JPanel createAppsPane() {
        // 创建主面板，使用 BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === 左侧：应聘记录列表面板 ===
        JPanel listPanel = new JPanel(new BorderLayout(0, 5));
        listPanel.setBorder(BorderFactory.createTitledBorder("应聘记录列表"));

        // 创建数据模型和列表
        DefaultListModel<Application> appModel = new DefaultListModel<>();
        for (Application app : DB.getAPPLIST()) {
            appModel.addElement(app);
        }

        JList<Application> appList = getAppJList(appModel);

        // 添加滚动条
        JScrollPane listScrollPane = new JScrollPane(appList);
        listScrollPane.setPreferredSize(new Dimension(350, 0));
        listPanel.add(listScrollPane, BorderLayout.CENTER);

        // 右侧应聘记录详情面板
        JPanel detailPanel = new JPanel(new GridBagLayout());
        detailPanel.setBorder(BorderFactory.createTitledBorder("应聘记录详情"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 详情显示区域
        JLabel idLabel = new JLabel("申请单ID：");
        JLabel idValue = new JLabel("");
        JLabel studIdLabel = new JLabel("学生ID：");
        JLabel studIdValue = new JLabel("");
        JLabel jobIdLabel = new JLabel("岗位ID：");
        JLabel jobIdValue = new JLabel("");
        JLabel submitTimeLabel = new JLabel("申请时间：");
        JLabel submitTimeValue = new JLabel("");
        JLabel statusLabel = new JLabel("录用状态：");
        JLabel statusValue = new JLabel("");

        // 状态样式设置
        Font statusFont = new Font("微软雅黑", Font.BOLD, 12);
        statusLabel.setFont(statusFont);
        statusValue.setFont(statusFont);

        // 其他标签字体
        Font detailFont = new Font("微软雅黑", Font.PLAIN, 12);
        idLabel.setFont(detailFont);
        studIdLabel.setFont(detailFont);
        jobIdLabel.setFont(detailFont);
        submitTimeLabel.setFont(detailFont);

        // 详情值标签
        Font valueFont = new Font("微软雅黑", Font.PLAIN, 12);
        idValue.setFont(valueFont);
        studIdValue.setFont(valueFont);
        jobIdValue.setFont(valueFont);
        submitTimeValue.setFont(valueFont);
        statusValue.setFont(valueFont);

        // 布局详情面板
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        detailPanel.add(idLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        detailPanel.add(idValue, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        detailPanel.add(studIdLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        detailPanel.add(studIdValue, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        detailPanel.add(jobIdLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        detailPanel.add(jobIdValue, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        detailPanel.add(submitTimeLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        detailPanel.add(submitTimeValue, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        detailPanel.add(statusLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        detailPanel.add(statusValue, gbc);

        // 添加操作区域
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actionPanel.setBorder(BorderFactory.createTitledBorder("录用操作"));

        JButton acceptButton = new JButton("录用");
        JButton rejectButton = new JButton("不录用");
        acceptButton.setEnabled(false);
        rejectButton.setEnabled(false);

        actionPanel.add(acceptButton);
        actionPanel.add(rejectButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.weighty = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        detailPanel.add(actionPanel, gbc);

        // 添加一个空的组件来占据剩余空间
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        detailPanel.add(Box.createVerticalGlue(), gbc);

        // 底部操作按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("操作"));

        JButton viewJobButton = new JButton("查看岗位信息");
        JButton deleteAppButton = new JButton("删除应聘记录");
        JButton refreshButton = new JButton("刷新列表");

        // 设置按钮图标
        try {
            viewJobButton.setIcon(UIManager.getIcon("FileChooser.detailsViewIcon"));
            deleteAppButton.setIcon(UIManager.getIcon("FileChooser.deleteIcon"));
            refreshButton.setIcon(UIManager.getIcon("FileChooser.refreshIcon"));
        } catch (Exception e) {
            // 如果图标不存在，忽略
        }

        buttonPanel.add(viewJobButton);
        buttonPanel.add(deleteAppButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(refreshButton);

        // 监听列表选择事件
        appList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Application selectedApp = appList.getSelectedValue();
                if (selectedApp != null) {
                    // 更新详情显示
                    idValue.setText(String.valueOf(selectedApp.getId()));
                    studIdValue.setText(String.valueOf(selectedApp.getStudId()));
                    jobIdValue.setText(String.valueOf(selectedApp.getJobId()));
                    submitTimeValue.setText(selectedApp.getSubmitTime());

                    // 根据录用状态设置显示文本和颜色
                    Boolean isApplied = selectedApp.getApplied();
                    if (isApplied == null) {
                        statusValue.setText("待处理");
                        statusValue.setForeground(Color.BLUE);
                    } else if (isApplied) {
                        statusValue.setText("已录用");
                        statusValue.setForeground(Color.GREEN.darker());
                    } else {
                        statusValue.setText("未录用");
                        statusValue.setForeground(Color.RED);
                    }

                    // 根据启用 接受/拒绝录用按钮
                    acceptButton.setEnabled(true);
                    rejectButton.setEnabled(true);


                    // 启用查看按钮
                    viewJobButton.setEnabled(true);
                    deleteAppButton.setEnabled(true);
                } else {
                    // 清空详情显示
                    idValue.setText("");
                    studIdValue.setText("");
                    jobIdValue.setText("");
                    submitTimeValue.setText("");
                    statusValue.setText("");

                    // 禁用所有按钮
                    acceptButton.setEnabled(false);
                    rejectButton.setEnabled(false);
                    viewJobButton.setEnabled(false);
                    deleteAppButton.setEnabled(false);
                }
            }
        });

        // 删除应聘记录按钮的事件监听
        deleteAppButton.addActionListener(e -> {
            Application selectedApp = appList.getSelectedValue();
            if (selectedApp != null) {
                int confirm = JOptionPane.showConfirmDialog(mainPanel,
                        "确定要删除应聘记录 #" + selectedApp.getId() + " 吗？\n" +
                                "学生ID: " + selectedApp.getStudId() + "\n" +
                                "岗位ID: " + selectedApp.getJobId() + "\n" +
                                "申请时间: " + selectedApp.getSubmitTime(),
                        "确认删除应聘记录", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // 调用数据库删除方法
                    boolean success = DB.deleteApp(selectedApp.getId());
                    if (success) {
                        // 从数据模型中删除
                        appModel.removeElement(selectedApp);

                        // 清空详情显示
                        idValue.setText("");
                        studIdValue.setText("");
                        jobIdValue.setText("");
                        submitTimeValue.setText("");
                        statusValue.setText("");

                        // 禁用所有操作按钮
                        acceptButton.setEnabled(false);
                        rejectButton.setEnabled(false);
                        viewJobButton.setEnabled(false);
                        deleteAppButton.setEnabled(false);

                        JOptionPane.showMessageDialog(mainPanel,
                                "应聘记录删除成功！\n申请单ID: " + selectedApp.getId(),
                                "操作成功", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(mainPanel,
                                "删除失败，请重试！",
                                "操作失败", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // 按钮事件监听
        acceptButton.addActionListener(e -> {
            Application selectedApp = appList.getSelectedValue();
            if (selectedApp != null) {
                int confirm = JOptionPane.showConfirmDialog(mainPanel,
                        "确定要录用申请单 #" + selectedApp.getId() + " 吗？\n" +
                                "学生ID: " + selectedApp.getStudId() + "\n" +
                                "岗位ID: " + selectedApp.getJobId(),
                        "确认录用", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // 更新录用状态
                    selectedApp.setApplied(() -> true);
                    DB.saveApps();

                    // 更新数据模型（触发UI刷新）
                    int index = appList.getSelectedIndex();
                    appModel.setElementAt(selectedApp, index);

                    // 更新详情显示
                    statusValue.setText("已录用");
                    statusValue.setForeground(Color.GREEN.darker());


                    JOptionPane.showMessageDialog(mainPanel, "已成功录用", "操作成功", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        rejectButton.addActionListener(e -> {
            Application selectedApp = appList.getSelectedValue();
            if (selectedApp != null) {
                int confirm = JOptionPane.showConfirmDialog(mainPanel,
                        "确定不录用申请单 #" + selectedApp.getId() + " 吗？\n" +
                                "学生ID: " + selectedApp.getStudId() + "\n" +
                                "岗位ID: " + selectedApp.getJobId(),
                        "确认不录用", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // 更新录用状态
                    selectedApp.setApplied(() -> false);
                    DB.saveApps();

                    // 更新数据模型（触发UI刷新）
                    int index = appList.getSelectedIndex();
                    appModel.setElementAt(selectedApp, index);

                    // 更新详情显示
                    statusValue.setText("未录用");
                    statusValue.setForeground(Color.RED);

                    JOptionPane.showMessageDialog(mainPanel, "已标记为不录用", "操作成功", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        viewJobButton.addActionListener(e -> {
            Application selectedApp = appList.getSelectedValue();
            if (selectedApp != null) {
                int jobId = selectedApp.getJobId();
                Job job = DB.getJob(jobId);
                if (job != null) {
                    JOptionPane.showMessageDialog(mainPanel,
                            "岗位ID: " + jobId + "\n"
                                    + "岗位名称: " + job.getName() + "\n"
                                    + "岗位单位: " + job.getCompany() + "\n"
                                    + "薪资: " + job.getSalary() + "\n"
                                    + "工作时间: " + formatWorkTime(job.getWorkTime()) + "\n"
                                    + "联系人电话: " + job.getContact(),
                            "查看岗位信息", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(mainPanel,
                            "ID所对应的岗位被删除或不存在",
                            "查看岗位信息", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        refreshButton.addActionListener(e -> {
            // 重新加载数据
            appModel.clear();
            for (Application app : DB.getAPPLIST()) {
                appModel.addElement(app);
            }
            JOptionPane.showMessageDialog(mainPanel, "应聘记录列表已刷新", "提示", JOptionPane.INFORMATION_MESSAGE);
        });

        // 初始禁用查看按钮
        viewJobButton.setEnabled(false);
        deleteAppButton.setEnabled(false);

        // 使用分割面板分隔列表和详情
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel, detailPanel);
        splitPane.setDividerLocation(400); // 设置分割位置
        splitPane.setResizeWeight(0.4); // 左侧占40%

        // 组装主面板
        mainPanel.add(splitPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private static JList<Application> getAppJList(DefaultListModel<Application> appModel) {
        JList<Application> appList = new JList<>(appModel);
        appList.setVisibleRowCount(15);

        // 自定义单元格渲染器
        appList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                if (value instanceof Application app) {
                    String status = app.getApplied() == null ? "待处理" :
                            (app.getApplied() ? "已录用" : "未录用");

                    // 使用简洁的显示格式
                    String displayText = String.format("申请单#%d | 学生ID:%d | 岗位ID:%d | 状态:%s",
                            app.getId(), app.getStudId(), app.getJobId(), status);
                    label.setText(displayText);

                    // 根据状态设置颜色
                    if (app.getApplied() == null) {
                        label.setForeground(Color.BLUE); // 待处理
                    } else if (app.getApplied()) {
                        label.setForeground(Color.GREEN.darker()); // 已录用
                    } else {
                        label.setForeground(Color.RED); // 未录用
                    }
                }

                return label;
            }
        });
        return appList;
    }
}
