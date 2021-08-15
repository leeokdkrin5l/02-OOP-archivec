package com.swagger.demo;

import com.swagger.demo.dto.DemoDto;
import com.swagger.demo.dto.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-13 下午5:20
 */
@RestController
public class IndexController {
    /**
     * test 接口
     * @param page 页数
     * @param size 当前size
     * @return
     */
    @GetMapping("/test/{id}")
    public ResponseEntity<List<DemoDto>> test(@RequestParam int page, int size, @PathVariable("ids") Long id) {
        return ResponseEntity.success();
    }

    /**
     * demo 接口
     * @param demoDto
     * @return
     */
    @PostMapping("/demo")
    public ResponseEntity<String> demo(@RequestBody DemoDto demoDto) {
        return ResponseEntity.success("demo");
    }

    @GetMapping("/map")
    public Map mapTest() {
        return null;
    }
}
