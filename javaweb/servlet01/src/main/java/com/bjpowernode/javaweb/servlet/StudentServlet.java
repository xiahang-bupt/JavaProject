package com.bjpowernode.javaweb.servlet;


import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class StudentServlet implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        //设置响应的内容类型
        servletResponse.setContentType("text/html;charset=utf-8");
        //向浏览器输出内容
        PrintWriter out = servletResponse.getWriter();
        //连接数据库，查询学生信息
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs= null;
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取连接
            String url = "jdbc:mysql://localhost:3306/powernode";
            String user = "root";
            String password = "xh20000513";
            conn = DriverManager.getConnection(url, user, password);
            //获取预编译的数据库操作对象
            String sql = "select * from t_student";
            ps = conn.prepareStatement(sql);
            //执行SQL语句
            rs = ps.executeQuery();
            //遍历查询结果集
            while (rs.next()) {
                String no = rs.getString("no");
                String name = rs.getString("name");
                out.println("学号：" + no + "，姓名：" + name + "<br/>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
