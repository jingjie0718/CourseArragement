package com.lyk.coursearrange.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyk.coursearrange.common.ServerResponse;
import com.lyk.coursearrange.entity.TeachbuildInfo;
import com.lyk.coursearrange.entity.request.TeachbuildAddRequest;
import com.lyk.coursearrange.service.TeachbuildInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 测试完成2020.03.22
 *
 *
 *
 * @author lequal
 * @since 2020-03-20
 */
@RestController
@RequestMapping("/teachbuildinfo")
public class TeachBuildInfoController {

    @Autowired
    private TeachbuildInfoService teachBuildInfoService;

    /**
     * 分页查询所有教学楼
     * @return
     */
    @GetMapping("/list/{page}")
    public ServerResponse queryTeachbuilding(@PathVariable("page") Integer page,
                                             @RequestParam(defaultValue = "10") Integer limit) {
        Page<TeachbuildInfo> pages = new Page<>(page, limit);
        QueryWrapper<TeachbuildInfo> wrapper = new QueryWrapper<TeachbuildInfo>().orderByDesc("update_time");
        IPage<TeachbuildInfo> ipage = teachBuildInfoService.page(pages, wrapper);
        if (ipage != null) {
            return ServerResponse.ofSuccess(ipage);
        }
        return ServerResponse.ofError("查询失败");
    }

    /**
     * 查询所有教学楼
     * @return
     */
    @GetMapping("/list")
    public ServerResponse queryallTeachbuilding() {

        List<TeachbuildInfo> list = teachBuildInfoService.list();
        return ServerResponse.ofSuccess(list);
    }

    /**
     * 根据id删除教室
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ServerResponse deleteTeachbuilding(@PathVariable("id") Integer id) {

        boolean b = teachBuildInfoService.removeById(id);
        if (b) {
            return ServerResponse.ofSuccess("删除教室成功");
        }
        return ServerResponse.ofError("删除失败");
    }

    /**
     * 添加教室
     * @param t
     * @return
     */
    @PostMapping("/add")
    public ServerResponse addTeachbuilding(@RequestBody TeachbuildAddRequest t) {
        TeachbuildInfo t1 = new TeachbuildInfo();
        t1.setTeachBuildNo(t.getTeachBuildNo());
        t1.setTeachBuildName(t.getTeachBuildName());
        t1.setTeachBuildLocation(t.getTeachBuildLocation());
        boolean b = teachBuildInfoService.save(t1);
        if (b) {
            return ServerResponse.ofSuccess("添加成功");
        }
        return ServerResponse.ofError("添加失败");
    }

    /**
     * 根据id查询教室信息
     * @param id
     * @return
     */
    @GetMapping("/select/{id}")
    public ServerResponse queryTeachBuildingById(@PathVariable("id") Integer id) {
        // 返回当前需要更新的对象
        return ServerResponse.ofSuccess(teachBuildInfoService.getById(id));
    }


    /**
     * 更新教学楼
     * @param t
     * @return
     */
    @PostMapping("/modify/{id}")
    public ServerResponse modifyTeacher(@PathVariable("id") Integer id, @RequestBody TeachbuildInfo t) {

        QueryWrapper<TeachbuildInfo> wrapper = new QueryWrapper<TeachbuildInfo>().eq("id", id);
        boolean b = teachBuildInfoService.update(t, wrapper);

        if (b) {
            return ServerResponse.ofSuccess("更新成功");
        }
        return ServerResponse.ofError("更新失败");
    }


}

