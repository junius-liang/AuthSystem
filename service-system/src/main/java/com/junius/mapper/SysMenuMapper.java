package com.junius.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.junius.model.system.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author junius
 * @since 2022-11-07
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> findMenuList(@Param("userId") String userId);
}
