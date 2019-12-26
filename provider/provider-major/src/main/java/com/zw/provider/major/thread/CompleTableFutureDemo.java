package com.zw.provider.major.thread;

import com.zw.provider.major.mybatis.entity.User;
import com.zw.provider.major.mybatis.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
  * CompletableFuture 测试
  * @date 2019/12/26
  * @author zw
*/
@Service
@RequestMapping("/thread")
public class CompleTableFutureDemo {

//    @Autowired
//    private ThreadPoolExecutor executor;
    @Resource
    private UserService userService;

//    外层的事物回滚，影响不到CompletableFuture.supplyAsync中
    @GetMapping("/CompletableFuture")
    @Transactional(rollbackFor = Exception.class)
    public void executorDemo(@RequestBody User user){
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
            userService.save(user);
            return user.getId();
        });
        CompletableFuture<Object> thenApply = future.thenApply(data -> {
            System.out.println(data);
            int i = 0 / 0;
            return null;
        });
        CompletableFuture.allOf(thenApply, future);
        System.out.println("completable执行完毕！！");
        int h= 1/0;
    }
}
