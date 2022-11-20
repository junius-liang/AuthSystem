package com.junius;

import com.junius.model.system.SysMenu;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author junius
 * @date 2022/11/07 21:27
 * 树形结构
 **/
@Slf4j
public class MenuHelper {
    public static List<SysMenu> buildTree(List<SysMenu> list) {
        ArrayList<SysMenu> trees = new ArrayList<>();
        for (SysMenu tree : list) {
            if (tree.getParentId().longValue()==0){
                log.info(tree.getId()+"------------------------");
                trees.add(findChildren(tree,list));
            }
        }
        return trees;
    }

    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        sysMenu.setChildren(new ArrayList<SysMenu>());
        for (SysMenu item : sysMenuList) {
            if (item.getParentId()==Long.parseLong(sysMenu.getId())){
                if (sysMenu.getChildren()==null){
                    sysMenu.setChildren(new ArrayList<SysMenu>());
                }
                sysMenu.getChildren().add(findChildren(item,sysMenuList));
            }
        }
        return sysMenu;
    }
}
