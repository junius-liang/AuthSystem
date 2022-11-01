package com.junius.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junius.MyJwtUtil;
import com.junius.Result;
import com.junius.model.system.SysUser;
import com.junius.model.vo.SysUserPageVo;
import com.junius.model.vo.SysUserVo;
import com.junius.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author junius
 * @date 2022/10/27 15:42
 **/
@RestController
@Api(tags = "用户功能")
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody SysUserVo sysUserVo){
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().eq("username", sysUserVo.getUserName());
        List<SysUser> list = userService.list(queryWrapper);
        if (list.size()>0){
            return Result.fail().message("用户已经注册");
        }
        SysUser sysUser = new SysUser();
        sysUser.setUsername(sysUserVo.getUserName());
        sysUser.setPassword(sysUserVo.getPwd());
        userService.save(sysUser);
        return Result.ok();
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result login(@RequestBody SysUserVo sysUserVo){
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().eq("username", sysUserVo.getUserName());
        List<SysUser> list = userService.list(queryWrapper);
        if (list.size()<1){
            return Result.fail().message("用户未注册");
        }
        SysUser sysUser = list.get(0);
        if (sysUser.getPassword().equals(sysUserVo.getPwd())){
            String token =  MyJwtUtil.getJwt(sysUser.getUsername());
            return Result.build(token,200,"登录成功");
        }
        return Result.fail().message("密码错误");
    }

    @PostMapping("/queryPageUser")
    @ApiOperation("条件分页查询")
    public Result queryPageUser(@RequestBody SysUserPageVo sysUserPageVo){
        return userService.selectPage(sysUserPageVo);
    }

    @PostMapping("/addUser")
    @ApiOperation("添加用户")
    public Result addUser(@RequestBody SysUser sysUser){
        boolean save = userService.save(sysUser);
        if (save){
            return  Result.ok();
        }
        return Result.fail().message("添加失败");
    }

    @GetMapping("/getUserById/{id}")
    @ApiOperation("根据Id查询用户")
    public Result queryUserById(@PathVariable String id){
        SysUser user = userService.getById(id);
        return Result.ok(user);
    }

    @PostMapping("/updateUser")
    @ApiOperation("修改用户")
    public Result updateUser(@RequestBody SysUser sysUser){
        boolean is_succeed = userService.updateById(sysUser);
        if (is_succeed){
            return  Result.ok();
        }
        return Result.fail().message("添加失败");
    }

    @PostMapping("remove/{id}")
    @ApiOperation("逻辑删除")
    public Result<Boolean> delete(@PathVariable("id") String id) {
        return Result.ok(userService.removeById(id));
    }
}
