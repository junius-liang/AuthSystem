package com.junius.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junius.Result;
import com.junius.mapper.SysUserMapper;
import com.junius.model.system.SysUser;
import com.junius.model.vo.RouterVo;
import com.junius.model.vo.SysUserPageVo;
import com.junius.service.SysMenuService;
import com.junius.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author junius
 * @date 2022/10/27 15:34
 **/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public Result selectPage(SysUserPageVo sysUserPageVo) {
        Page<SysUser> sysUserPage = new Page<>(sysUserPageVo.getPage(), sysUserPageVo.getSize());
        IPage<SysUser> sysUserIPage = baseMapper.selectPage(sysUserPage, sysUserPageVo.getKeyword());
        for (SysUser record : sysUserIPage.getRecords()) {
            Integer status = record.getStatus();
            if (status == 1) {
                record.setShow(true);
            } else {
                record.setShow(false);
            }
        }
        return Result.ok(sysUserIPage);
    }

    @Override
    public int delete(String id) {
        return baseMapper.del(id);
    }

    @Override
    public Result modStateSer(String id, int state) {
        SysUser sysUser = baseMapper.selectById(id);
        sysUser.setStatus(state);
        int i = baseMapper.updateById(sysUser);
        if (i==0){
            return Result.fail("更改用户状态失败");
        }
        return Result.ok("更改用户状态成功");
    }

    @Override
    public Map<String, Object> getUserInfo(String userName) {
        Map<String, Object> map = new HashMap<>(16);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userName);
        SysUser sysUser = baseMapper.selectOne(queryWrapper);
        map.put("name",userName);
        //菜单权限数据
        List<RouterVo> menuIdList =  sysMenuService.getMenuList(sysUser.getId());
        map.put("routers",menuIdList);

        //按钮权限数据
        List<String> buttonList =  sysMenuService.getButtonList(sysUser.getId());
        map.put("buttons",buttonList);

        return map;
    }
}
