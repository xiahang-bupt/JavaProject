package demo;

import javax.swing.*;

public class MySnake {
    //输入psvm直接生成main方法
    public static void main(String[] args) {
        //创建一个窗口
        JFrame frame = new JFrame();
        //指定窗口x和y的位置以及窗口的宽度和高度值
        frame.setBounds(500,50,700,700);
        //不允许拖拽改变窗口大小
        frame.setResizable(false);
        //当点击窗口关闭按钮时，执行操作是退出
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //添加画布
        frame.add(new MyPanel());
        //显示窗口
        frame.setVisible(true);
    }
}
