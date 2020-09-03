// package com.zw.provider.major.mongo.controller;
//
// import com.zw.provider.major.mongo.entity.Person;
// import com.zw.provider.major.mongo.service.PersonService;
// import com.zw.base.QueryPage;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import javax.annotation.Resource;
//
//
// /**
//  * test
//  *
//  * @author zw
//  * @date 2019/12/18
//  */
// @RestController
// @RequestMapping("/person")
// public class PersonController {
//
//     @Resource
//     private PersonService personService;
//
//
//     @PostMapping("/insert")
//     public Object insert(@RequestBody Person person) {
//         return personService.insert(person);
//     }
//
//     @PostMapping("/list")
//     public Object list(@RequestBody Person person) {
//         return personService.find(person);
//     }
//
//
//     @PostMapping("/page")
//     public Object page(@RequestBody QueryPage<Person> page) {
//         return personService.pagination(page);
//     }
// }
