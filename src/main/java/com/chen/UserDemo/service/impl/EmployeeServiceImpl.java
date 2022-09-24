package com.chen.UserDemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.UserDemo.entity.Employee;
import com.chen.UserDemo.mapper.EmployeeMapper;
import com.chen.UserDemo.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
