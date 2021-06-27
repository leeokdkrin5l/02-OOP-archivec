package com.swagger.demo;

import com.swagger.demo.dto.DemoDto;
import com.swagger.demo.dto.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-07-13 下午5:20
 */
@RestController
public class IndexController {
    @GetMapping("/test")
    public ResponseEntity<List<DemoDto>> test() {
        return ResponseEntity.success();
    }
}
