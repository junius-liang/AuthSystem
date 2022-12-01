package com.junius.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junius.MenuHelper;
import com.junius.RouterHelper;
import com.junius.annotation.SysLog;
import com.junius.exception.MyException;
import com.junius.mapper.SysMenuMapper;
import com.junius.mapper.SysRoleMenuMapper;
import com.junius.model.system.SysMenu;
import com.junius.model.system.SysRoleMenu;
import com.junius.model.vo.AssginMenuVo;
import com.junius.model.vo.RouterVo;
import com.junius.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author junius
 * @since 2022-11-07
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    @SysLog(description = "读取菜单列表")
    public List<SysMenu> findNodes() {
        List<SysMenu> allList = baseMapper.selectList(null);
        List<SysMenu> res =  MenuHelper.buildTree(allList);
        return res;
    }

    @Override
    public void removeMenuById1(String id) {
        List<SysMenu> allList = baseMapper.selectList(null);
        boolean flag = true;
        for (SysMenu item : allList) {
            if (item.getParentId()==Long.parseLong(id)){
               throw new MyException(201,"该菜单存在子菜单，无法删除");
            }
        }
        if (flag){
            baseMapper.deleteById(id);
        }
    }

    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //获取所有status为1的权限列表
        List<SysMenu> menuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        //根据角色id获取角色权限
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id",roleId));
        //获取该角色已分配的所有权限id
        List<String> roleMenuIds = new ArrayList<>();
        for (SysRoleMenu roleMenu : roleMenus) {
            roleMenuIds.add(roleMenu.getMenuId());
        }
        //遍历所有权限列表
        for (SysMenu sysMenu : menuList) {
            if(roleMenuIds.contains(sysMenu.getId())){
                //设置该权限已被分配
                sysMenu.setSelect(true);
            }else {
                sysMenu.setSelect(false);
            }
        }
        //将权限列表转换为权限树
        List<SysMenu> sysMenus = MenuHelper.buildTree(menuList);
        return sysMenus;
    }
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //删除已分配的权限
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id",assginMenuVo.getRoleId()));
        //遍历所有已选择的权限id
        for (String menuId : assginMenuVo.getMenuIdList()) {
            if(menuId != null){
                //创建SysRoleMenu对象
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
                //添加新权限
                sysRoleMenuMapper.insert(sysRoleMenu);
            }
        }
    }

    @Override
    public List<RouterVo> getMenuList(String id) {
        List<SysMenu> sysMenuList = new ArrayList<SysMenu>();
        if ("1".equals(id)){
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("status",1);
            wrapper.orderByAsc("sort_value");
            sysMenuList = baseMapper.selectList(wrapper);
        }else {
            sysMenuList = baseMapper.findMenuList(id);
        }
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);
        List<RouterVo> routerVos = RouterHelper.buildRouters(sysMenuTreeList);
        return routerVos;
    }

    @Override
    public List<String> getButtonList(String id) {
        List<SysMenu> sysMenuList;
        if ("1".equals(id)){
           sysMenuList = baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status",1));
        }else {
            sysMenuList = baseMapper.findMenuList(id);
        }
        List<String> res = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            if (sysMenu.getType() == 2){
                String perms = sysMenu.getPerms();
                res.add(perms);
            }
        }
        return res;
    }


}
