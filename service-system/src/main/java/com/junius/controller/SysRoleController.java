package com.junius.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.junius.Result;
import com.junius.exception.MyException;
import com.junius.model.system.SysRole;
import com.junius.model.vo.SysRoleQueryVo;
import com.junius.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author junius
 * @date 2022/10/25 18:29
 **/
@RestController
@RequestMapping("/admin/system/sysRole")
@Api(tags = "角色管理")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("findAll")
    @ApiOperation("查询所有记录")
    public Result<List> getAll() {
        return Result.ok(sysRoleService.list());
    }

    @PostMapping("remove/{id}")
    @ApiOperation("逻辑删除")
    public Result<Boolean> delete(@PathVariable("id") Long id){
        return Result.ok( sysRoleService.removeById(id));
    }

    @PostMapping("find/{id}")
    @ApiOperation("根据id查询")
    public Result findById(@PathVariable("id") Long id){
        return Result.ok( sysRoleService.getById(id));
    }

    @GetMapping("/queryBy")
//    @ApiOperation("条件分页")
    public Result queryBy(@RequestParam("page") Long page,
                        @RequestParam("limit") Long limit,
                        @RequestParam(value = "roleName",required = false) String roleName){
        Page<SysRole> sysRolePage = new Page<>(page, limit);
        IPage<SysRole> pageModel =  sysRoleService.selectPage(sysRolePage, roleName);
        return Result.ok(pageModel);
    }

    @PostMapping("/query")
    @ApiOperation("条件分页查询")
    public Result query1(@RequestBody SysRoleQueryVo sysRoleQueryVo){
        String roleName = sysRoleQueryVo.getRoleName();
        Long limit = sysRoleQueryVo.getLimit();
        Long page = sysRoleQueryVo.getPage();
        Page<SysRole> sysRolePage = new Page<>(page, limit);
        IPage<SysRole> pageModel =  sysRoleService.selectPage(sysRolePage, roleName);
        return Result.ok(pageModel);
    }

    @PostMapping("/add")
    @ApiOperation("添加角色")
    public Result save(@RequestBody SysRole sysRole){
        boolean save = sysRoleService.save(sysRole);
        if (save){
            return Result.ok(save);
        }else {
            return Result.fail();
        }
    }

    @PostMapping("/update")
    @ApiOperation("更新角色")
    public Result update(@RequestBody SysRole sysRole){
        boolean update = sysRoleService.updateById(sysRole);
        if (update){
            return Result.ok(update);
        }else {
            return Result.fail();
        }
    }

    @PostMapping("batchRemove")
    @ApiOperation("批量删除")
    public Result batchRemove(@RequestBody List<Long> ids){
        boolean b = sysRoleService.removeByIds(ids);
        if (b){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    /**
     * 测试使用
     * @param pageSize
     * @param pageNum
     * @return
     */
    @PutMapping("/findAllByPage")
    @ApiOperation("查询全部并分页")
    public Result findAllByPage(@RequestParam("pageSize") Long pageSize,
                                @RequestParam("pageNum") Long pageNum){
        //创建分页器
        Page<SysRole> pageTool = new Page<>(pageNum, pageSize);
        //分页结果
        IPage<SysRole> res =  sysRoleService.findAllByPageHandler(pageTool);
        return Result.ok(res);
    }

    @PutMapping("/usePlugsDoPage")
    @ApiOperation("使用分页器进行分页")
    public Result usePlugsDoPage(
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNum") int pageNum){
        com.github.pagehelper.Page<Object> objectPage = PageHelper.startPage(pageNum, pageSize).doSelectPage(new ISelect() {
            @Override
            public void doSelect() {
                //查询的结果进行分页
                sysRoleService.list();
            }
        });
        return Result.ok(objectPage.getResult());
    }

    @PostMapping("/testError")
    @ApiOperation("自定义异常测试")
    public Result testError(){
        try {
            int b = 9/0;
            return Result.ok(b);
        }catch (Exception e){
            throw new MyException(444,"自定义异常");
        }

    }

}
