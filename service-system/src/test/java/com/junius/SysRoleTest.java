package com.junius;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junius.mapper.SysRoleMapper;
import com.junius.model.system.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * @author junius
 * @date 2022/10/25 17:24
 **/
@SpringBootTest
public class SysRoleTest {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Test
    public void test1(){
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        for (SysRole sysRole : sysRoles){
            System.out.println(sysRole);
        }
    }

    @Test
    public void test2(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("游客");
        sysRole.setRoleCode("YOUKE");
        sysRole.setDescription("游客");
        sysRoleMapper.insert(sysRole);
    }

    @Test
    public void test3(){
        //根据id查询
        SysRole sysRole = sysRoleMapper.selectById("1584842082143555588");
        sysRole.setDescription("系统游客");
        sysRoleMapper.updateById(sysRole);
    }

    @Test
    public void test4(){
        int i = sysRoleMapper.deleteById("1584842082143555588");
    }

    @Test
    //批量删除
    public void test5(){
        List<String> objects = new ArrayList<>();
        objects.add("333");
        objects.add("444");
        sysRoleMapper.deleteBatchIds(objects);
    }

    @Test
    //条件查询
    public void test6(){
        QueryWrapper<SysRole> roleQueryWrapper = new QueryWrapper<>();
        QueryWrapper<SysRole> eq = roleQueryWrapper.eq("role_name", "系统管理员");
        List<SysRole> sysRoles = sysRoleMapper.selectList(eq);
        System.out.println(sysRoles.size()+"=============="+sysRoles);
    }

    @Test
    public void test7(){

    }
}
