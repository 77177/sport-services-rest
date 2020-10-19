package org.application.resources;


import org.application.models.TestObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/test")
public class TestController {

    private final static TestObject testObj = new TestObject();

    @GetMapping()
    public TestObject getTest(){
        return testObj;
    }
}
