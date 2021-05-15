package cn.edu.shu.xj.ser.controller.user;

import cn.edu.shu.xj.ser.entity.UserEntity;
import cn.edu.shu.xj.ser.response.Result;
import cn.edu.shu.xj.ser.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/User/common")
public class UserController {

    @Autowired
    IUserService userService;

    @ApiOperation(value = "返回对应分页查询的用户司机信息")
    @GetMapping("/findUserList")
    public Result findUserList(@RequestParam(required = true,defaultValue = "1")Integer current,
                               @RequestParam(required = true,defaultValue = "10")Integer size){
        //对商家进行分页，泛型中注入的就是商家实体类
        List<UserEntity> Total = userService.findUserListTotal();
        Integer Myvalue = (current-1)*size;
        List<UserEntity> userPage = userService.findUserList(Myvalue,size);
        long total = Total.size();
        System.out.println(userPage);
        return Result.ok().data("total",total).data("records",userPage);
    }

    @ApiOperation(value = "获取对应UserId的用户司机信息")
    @GetMapping("/getUserById")
    public Result getUserById(@RequestParam(required = true)Long userId){
        UserEntity userEntity = new UserEntity();
        userEntity = userService.getUserById(userId);
        return Result.ok().data("user",userEntity);
    }

    @ApiOperation(value = "用户司机信息修改")
    @GetMapping("/editUser")
    public Result editUser(@RequestParam(required = true)Long userId,
                           @RequestParam(required = true)String userCity,
                           @RequestParam(required = true)String userAddress){
        UserEntity userEntity = new UserEntity();
        userService.editUser(userId,userCity,userAddress);
        userEntity = userService.getUserById(userId);
        return Result.ok().data("user",userEntity);
    }

    @ApiOperation(value = "用户司机信息删除")
    @GetMapping("/deleteUser")
    public Result deleteUser(@RequestParam(required = false)Long userId){
        userService.deleteUser(userId);
        return Result.ok();
    }

}
