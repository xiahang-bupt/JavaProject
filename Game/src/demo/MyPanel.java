package demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MyPanel extends JPanel implements KeyListener, ActionListener {
    //声明右侧蛇头和身体图片
    ImageIcon right = new ImageIcon("images/right.png");
    ImageIcon body = new ImageIcon("images/body.png");

    //声明上下左其他三个方向蛇头图片
    ImageIcon top = new ImageIcon("images/top.png");
    ImageIcon bottom = new ImageIcon("images/bottom.png");
    ImageIcon left = new ImageIcon("images/left.png");

    //声明初始值，表示蛇的长度为3
    int length = 3;
    //声明两个数组，分别存储蛇的x和y坐标，最大值为宽度格子*高度格子
    int[] snakeX = new int[784];
    int[] snakeY = new int[784];

    //声明一个枚举类型变量，标识蛇头的方向
    Direction direction = Direction.right;

    //声明一个变量，标识游戏是否开始，当值为true时，游戏开始，否则没有开始
    boolean isStart = false;

    //声明一个定时器
    Timer timer=new Timer(100,this);

    //声明两个变量表示食物的坐标位置
    int foodX;
    int foodY;
    //声明一个变量表示食物的图片
    ImageIcon food=new ImageIcon("images/food.png");
    //声明一个随机变量random
    Random random=new Random();

    //无参数构造函数
    public MyPanel() {
        //设定蛇头和身体的初始位置，长度为3（蛇头+两个身体）
        snakeX[0] = 100;
        snakeY[0] = 100;
        snakeX[1] = 75;
        snakeY[1] = 100;
        snakeX[2] = 50;
        snakeY[2] = 100;

        //获取键盘监听事件
        this.setFocusable(true);
        //添加监听事件（键盘监听事件）
        this.addKeyListener(this);

        //启动定时器
        timer.start();

        //随机生成食物的坐标
        foodX=25+random.nextInt(25)*25;
        foodY=25+random.nextInt(25)*25;
    }

    //重写画组件的方法ctlr+o,形参是画笔
    @Override
    protected void paintComponent(Graphics g) {

        //调用父类的方法做一些基本的工作
        super.paintComponent(g);

        //设置背景颜色
        this.setBackground(Color.green);
        //在画布上添加游戏区（全覆盖），颜色为红色
        //g.setColor(Color.green);
        g.fillRect(0, 0, 700, 700);

        //根据枚举变量的值画出蛇头
        switch (direction) {
            case top:
                top.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case bottom:
                bottom.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case left:
                left.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case right:
                right.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
        }

        //画出蛇的长度(循环画出蛇的身体)
        for (int i = 1; i < length; i++) {
            body.paintIcon(this, g, snakeX[i], snakeY[i]);
        }

        //判断当前游戏是否开始，如果值为false则显示提示语
        if (!isStart) {
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("Press Space to Start/Pause", 100, 350);
        }

        //画出食物
        food.paintIcon(this,g,foodX,foodY);
    }

    @Override
    public void keyTyped(KeyEvent e) {//按键按下时调用

    }

    //重写键盘按下事件,形参是键盘事件
    @Override
    public void keyPressed(KeyEvent e) {//按键按下时调用
        int keyCode = e.getKeyCode();
        //按空格键时游戏开始
        if (keyCode == KeyEvent.VK_SPACE) {
            //取反开始游戏
            isStart = !isStart;
            //刷新画布
            repaint();
        }
        //按下上键时，蛇头方向为上
        else if (keyCode == KeyEvent.VK_UP) {
            direction = Direction.top;
        }
        //按下下键时，蛇头方向为下
        else if (keyCode == KeyEvent.VK_DOWN) {
            direction = Direction.bottom;
        }
        //按下左键时，蛇头方向为左
        else if (keyCode == KeyEvent.VK_LEFT) {
            direction = Direction.left;
        }
        //按下右键时，蛇头方向为右
        else if (keyCode == KeyEvent.VK_RIGHT) {
            direction = Direction.right;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {//按键释放时调用

    }

    @Override
    public void actionPerformed(ActionEvent e) {//定时器事件
        //如果游戏开始，蛇移动,否则不移动,游戏暂停
        if (isStart) {
            //移动身体
            for (int i = length - 1; i > 0; i--) {
                //后一个身体的坐标等于前一个身体的坐标
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            //移动头,蛇头的值根据方向变化25
            switch (direction) {
                case top:
                    snakeY[0] -= 25;
                    //判断蛇头是否超出边界，如果超出边界，游戏结束，游戏蛇头位置重新初始化
                    if (snakeY[0] < 0) {
                        isStart = false;
                        snakeX[0] = 100;
                        snakeY[0] = 100;
                        snakeX[1] = 75;
                        snakeY[1] = 100;
                        snakeX[2] = 50;
                        snakeY[2] = 100;
                        direction = Direction.right;
                        length=3;
                    }
                    break;
                case bottom:
                    snakeY[0] += 25;
                    //判断蛇头是否超出边界，如果超出边界，游戏结束
                    if (snakeY[0] > 700) {
                        isStart = false;
                        snakeX[0] = 100;
                        snakeY[0] = 100;
                        snakeX[1] = 75;
                        snakeY[1] = 100;
                        snakeX[2] = 50;
                        snakeY[2] = 100;
                        direction = Direction.right;
                        length=3;
                    }
                    break;
                case left:
                    snakeX[0] -= 25;
                    //判断蛇头是否超出边界，如果超出边界，游戏结束
                    if (snakeX[0] < 0) {
                        isStart = false;
                        snakeX[0] = 100;
                        snakeY[0] = 100;
                        snakeX[1] = 75;
                        snakeY[1] = 100;
                        snakeX[2] = 50;
                        snakeY[2] = 100;
                        direction = Direction.right;
                        length=3;
                    }
                    break;
                case right:
                    snakeX[0] += 25;
                    //判断蛇头是否超出边界，如果超出边界，游戏结束
                    if (snakeX[0] > 700) {
                        isStart = false;
                        snakeX[0] = 100;
                        snakeY[0] = 100;
                        snakeX[1] = 75;
                        snakeY[1] = 100;
                        snakeX[2] = 50;
                        snakeY[2] = 100;
                        direction = Direction.right;
                        length=3;
                    }
                    break;
            }

            //判断蛇头是否和食物重合
            if(snakeX[0]==foodX&&snakeY[0]==foodY){
                //蛇的长度加1
                length++;
                //重新生成食物
                foodX=25+random.nextInt(25)*25;
                foodY=25+random.nextInt(25)*25;
            }

            //判断蛇头是否和身体重合
            for(int i=1;i<length;i++){
                if(snakeX[0]==snakeX[i]&&snakeY[0]==snakeY[i]){
                    isStart = false;
                    snakeX[0] = 100;
                    snakeY[0] = 100;
                    snakeX[1] = 75;
                    snakeY[1] = 100;
                    snakeX[2] = 50;
                    snakeY[2] = 100;
                    direction = Direction.right;
                    length=3;
                }
            }

            //刷新画布
            repaint();
            //重新启动定时器
            timer.start();
        }
    }
}
