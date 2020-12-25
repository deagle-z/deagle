// package com.zw.provider.major.mongo.entity;
//
// import lombok.Data;
// import org.springframework.data.annotation.Id;
// import org.springframework.data.annotation.PersistenceConstructor;
// import org.springframework.data.mongodb.core.mapping.Document;
// import org.springframework.data.mongodb.core.mapping.Field;
//
//
// /**
//   * 测试
//   * @date 2019/12/18
//   * @author zw
// */
// @Document(collection = "person")
// @Data
// public class Person {
//     @Id
//     private String id;
//     @Field("name")
// //    @Indexed
//     private String name;
//     @Field("class_name")
//     private String className;
//     @Field("age")
//     private Integer age;
//     @Field("sex")
//     private Integer sex;
//
//     /**
//       * 与mongo数据库映射
//       * @date 2019/12/18
//       * @author zw
//     */
//     @PersistenceConstructor
//     public Person(String id, String name, String className, Integer age, Integer sex) {
//         this.id = id;
//         this.name = name;
//         this.className = className;
//         this.age = age;
//         this.sex = sex;
//     }
// }
