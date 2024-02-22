package com.kehua.es.demo.controller;


import com.kehua.es.demo.entity.NeStation;
import com.kehua.es.demo.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StationController {


    @Autowired
    StationService stationService;


    @GetMapping("add")
    public void add(){
        stationService.add();
    }

    @GetMapping("list")
    public List<NeStation> list(){
        return stationService.list();
    }

    @GetMapping("delete/{id}")
    public void delete(@PathVariable Long id){
        stationService.delete();
    }

    @GetMapping("listPage/{pageNum}/{pageSize}")
    public List<NeStation> listPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize){
        return stationService.listPage(pageNum, pageSize);
    }

    @GetMapping("nativeQuery/{pageNum}/{pageSize}")
    public List<NeStation> nativeQuery(@PathVariable Integer pageNum, @PathVariable Integer pageSize){
        return stationService.nativeQuery(pageNum, pageSize);
    }

}
