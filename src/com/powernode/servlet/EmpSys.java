package com.powernode.servlet;

import com.powernode.pojo.Dept;
import com.powernode.pojo.Emp;
import com.powernode.service.*;
import com.powernode.service.impl.DeptServiceImpl;
import com.powernode.service.impl.EmpServiceImpl;
import com.powernode.service.impl.UserServiceImpl;
import com.powernode.utils.FileHASH;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "EmpSys", urlPatterns = "/main.do")
public class EmpSys extends HttpServlet {
    private final UserService userService = new UserServiceImpl();
    private final EmpService empService = new EmpServiceImpl();
    private final DeptService deptService = new DeptServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置字符集避免乱码
        req.setCharacterEncoding("UTF-8");
        //获取动作类型
        String action = req.getParameter("action");

        if (action == null || "".equals(action)) {
            resp.sendRedirect(req.getContextPath() + "/");
        } else if ("login".equals(action)) {
            login(req, resp);
        } else if ("add".equals(action)) {
            add(req, resp);
        } else if ("edit".equals(action)) {
            edit(req, resp);
        } else if ("remove".equals(action)) {
            remove(req, resp);
        } else if ("getData".equals(action)) {
            getData(req, resp, req.getSession());
        } else if ("upload".equals(action)) {
            upload(req, resp);
        }
    }

    private void upload(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 上传文件存储目录
        String UPLOAD_DIRECTORY = "upload";

        // 上传配置
        int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
        int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
        int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB


        if (!ServletFileUpload.isMultipartContent(req)) {
            // 如果不是则停止
            PrintWriter writer = resp.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        String uploadPath = getServletContext().getRealPath("/") + File.separator + UPLOAD_DIRECTORY;


        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            // 解析请求的内容提取文件数据
            List<FileItem> formItems = upload.parseRequest(req);

            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        String fileName = UUID.randomUUID().toString() + ".jpg";
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        // 保存文件到硬盘
                        item.write(storeFile);
                        InputStream is = new FileInputStream(filePath);
                        String fileHash = FileHASH.md5HashCode(is);
                        resp.setHeader("Content-Type", "application/json;charset=utf-8");
                        resp.getWriter().write("{\"message\":\"success\",\"fileHash\":\"" + fileHash + "\",\"filePath\":\"" + fileName + "\"}");
                    }
                }
            }
        } catch (Exception ex) {
            resp.setHeader("Content-Type", "application/json;charset=utf-8");
            resp.getWriter().write("{\"message\":\"failed\"}");
        }
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String birthday = req.getParameter("birthday");
        String deptID = req.getParameter("deptID");
        String sex = req.getParameter("sex");
        String filePath = req.getParameter("filePath");
        String fileHash = req.getParameter("fileHash");
        if (id == null || "".equals(id) || age == null || "".equals(age) || deptID == null || "".equals(deptID) || sex == null || "".equals(sex)) {
            resp.sendRedirect(req.getContextPath() + "/pages/edit.jsp?id=" + id);
        } else {
            Emp emp = new Emp();
            emp.setId(Integer.parseInt(id));
            emp.setName(name);
            emp.setAge(Integer.parseInt(age));
            emp.setDate(birthday);
            emp.setDeptID(Integer.parseInt(deptID));
            if ("男".equals(sex)) {
                emp.setSex(1);
            } else if ("女".equals(sex)) {
                emp.setSex(0);
            }
            emp.setFilePath(filePath);
            emp.setFileHash(fileHash);
            empService.editEmp(emp);
            getData(req, resp, req.getSession());
            resp.sendRedirect(req.getContextPath() + "/pages/show.jsp");
        }
    }

    private void remove(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id = req.getParameter("id");
        if (id == null || "".equals(id)) {
            resp.sendRedirect(req.getContextPath() + "/pages/show.jsp");
        } else {
            empService.remove(Integer.parseInt(id));
            getData(req, resp, req.getSession());
            //showEmp(req, resp, req.getSession());
            resp.sendRedirect(req.getContextPath() + "/pages/show.jsp");
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String birthday = req.getParameter("birthday");
        String deptID = req.getParameter("deptID");
        String sex = req.getParameter("sex");
        String filePath = req.getParameter("filePath");
        String fileHash = req.getParameter("fileHash");
        if (age == null || "".equals(age) || deptID == null || "".equals(deptID) || sex == null || "".equals(sex)) {
            req.getRequestDispatcher(req.getContextPath() + "/pages/add.jsp");
        } else {
            Emp emp = new Emp();
            emp.setName(name);
            emp.setAge(Integer.parseInt(age));
            emp.setDate(birthday);
            emp.setDeptID(Integer.parseInt(deptID));
            if ("男".equals(sex)) {
                emp.setSex(1);
            } else if ("女".equals(sex)) {
                emp.setSex(0);
            }
            emp.setFilePath(filePath);
            emp.setFileHash(fileHash);
            empService.addEmp(emp);
            getData(req, resp, req.getSession());
            resp.sendRedirect(req.getContextPath() + "/pages/show.jsp");
        }
    }

    private void showDept(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        List<Dept> dept = deptService.findAllDept();
        session.setAttribute("dept", dept);
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || "".equals(username) || password == null || "".equals(password)) {
            req.getRequestDispatcher(req.getContextPath() + "/").forward(req, resp);
        } else {
            boolean flag = userService.login(username, password);
            if (flag) {
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                getData(req, resp, session);
                showDept(req, resp, session);
                resp.sendRedirect(req.getContextPath() + "/pages/show.jsp");
            } else {
                req.getRequestDispatcher(req.getContextPath() + "/").forward(req, resp);
            }
        }
    }

    private void getData(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException, ServletException {
        String current_page = req.getParameter("current_page");
        String limit_page = req.getParameter("limit_page");
        String name = req.getParameter("name");
        String eage = req.getParameter("age");
        if (current_page == null || limit_page == null || "".equals(current_page) || "".equals(limit_page)) {
            current_page = "1";
            limit_page = "5";
            int current = Integer.parseInt(current_page);
            int limit = Integer.parseInt(limit_page);
            List<Emp> list = empService.getData(current, limit);
            int total = empService.getCount();
            session = req.getSession();
            session.setAttribute("list", list);
            session.setAttribute("current", current);
            session.setAttribute("total", total);
            session.setAttribute("limit", limit);
        } else {
            int current = Integer.parseInt(current_page);
            int limit = Integer.parseInt(limit_page);
            List<Emp> list = null;
            int total = 0;
            if ("".equals(eage) || eage == null) {
                int age = -1;
                list = empService.getDataLike(current, limit, name, age);
                total = empService.getCountLike(name, age);
            } else {
                int age = Integer.parseInt(eage);
                list = empService.getDataLike(current, limit, name, age);
                total = empService.getCountLike(name, age);
            }
            session = req.getSession();
            session.setAttribute("list", list);
            session.setAttribute("current", current);
            session.setAttribute("total", total);
            session.setAttribute("limit", limit);
            session.setAttribute("name", name);
            session.setAttribute("eage", eage);
            resp.sendRedirect(req.getContextPath() + "/pages/show.jsp");
        }
    }
}
