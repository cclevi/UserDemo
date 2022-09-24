package com.chen.UserDemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.UserDemo.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
