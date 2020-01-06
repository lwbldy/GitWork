package com.lwbldy.gen.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.lwbldy.common.util.PageUtils;
import com.lwbldy.common.util.R;
import com.lwbldy.gen.service.SysGeneratorService;
import com.lwbldy.gen.vo.TableConfVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 代码生成
 */
@Controller
@RequestMapping("/sys/generator")
@Api(tags = "自动生成代码")
public class GenController {

    @Autowired
    private SysGeneratorService sysGeneratorService;

    @GetMapping("/tableList")
    @ApiOperation(value="查询所有表页面")
    public String tableLists(){
        return "/system/generator/tableLists";
    }

    @PostMapping("/tableList")
    @ApiOperation(value="查询所有表")
    @ResponseBody
    public R findAllTables(){
        List<Map<String, Object>> list = sysGeneratorService.queryList(null);
        int total = sysGeneratorService.queryTotal(null);
        return PageUtils.restPage(list,total);
    }


    @GetMapping("/tableInfo/{tableName}")
    @ApiOperation(value="配置表信息自动生成代码页面")
    public String tableInfo(@PathVariable("tableName") String tableName, ModelMap map){
        TableConfVO table = sysGeneratorService.initTableInfo(tableName);
        String tableJSON = JSON.toJSONString(table);
        System.out.println(tableJSON);
        map.put("tableJSON",tableJSON);
        return "/system/generator/tableInfo";
    }

    @PostMapping("/tableInfo")
    @ApiOperation(value="自动生成代码")
    @ResponseBody
    public R initInfo(@RequestBody TableConfVO table)throws IOException{
        String tableJSON = JSON.toJSONString(table);
        System.out.println(tableJSON);
        return sysGeneratorService.generatorCode(table);
    }
}
