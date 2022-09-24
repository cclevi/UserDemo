package com.chen.UserDemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.chen.UserDemo.common.R;
import com.chen.UserDemo.entity.Employee;
import com.chen.UserDemo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }


    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
//        1.将页面提交的代码进行md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());//DigestUtils是一个加密工具类
//        2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());//实例化一个Employee对象，调用对象的get方法
        Employee emp = employeeService.getOne(queryWrapper);
        // 3、如果没有查询到用户返回失败结果
        if(emp==null){
            return R.error("登录失败");
        }
        //4、密码比对
        if(!emp.getPassword().equals(password)){
            return R.error("密码不正确");
        }
        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果、
        if(emp.getStatus() == 0){
            return R.error("账号已禁用");
        }
        //登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }


    /**
     * 退出系统功能
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){//获取request等等直接传参即可
        //清除Session中保存的当前登录的员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

}
