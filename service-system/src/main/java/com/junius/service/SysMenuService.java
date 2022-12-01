package com.junius.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.junius.model.system.SysMenu;
import com.junius.model.vo.AssginMenuVo;
import com.junius.model.vo.RouterVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author junius
 * @since 2022-11-07
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findNodes();

    void removeMenuById1(String id);

    List<SysMenu> findSysMenuByRoleId(Long roleId);

    /**
     * 保存角色权限
     * @param assginMenuVo
     */
    void doAssign(AssginMenuVo assginMenuVo);

    List<RouterVo> getMenuList(String id);

    List<String> getButtonList(String id);
}
