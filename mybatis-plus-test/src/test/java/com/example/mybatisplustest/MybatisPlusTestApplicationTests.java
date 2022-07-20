package com.example.mybatisplustest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplustest.entity.User;
import com.example.mybatisplustest.mapper.UserMapper;
import com.example.mybatisplustest.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusTestApplicationTests {
	@Autowired
	private UserMapper userMapper;

	//查询全部
	@Test
	public void testSelectList(){
		System.out.println("尝试读取数据库中所有数据");
		List<User> users = userMapper.selectList(null);
		users.forEach(System.out::println);
	}
	//插入
	@Test
	public void testInsert(){
		User user = new User();
		user.setAge(18);
		user.setEmail("my@qq.com");
		user.setName("mary");
		user.setDeleted(0);
		//插入 result是影响的行数
		int result = userMapper.insert(user);
		System.out.println(result);
		//id可以自动写入
		System.out.println(user);
	}
	//根据ID更新
	@Test
	public void testUpdateById(){
		//首先根据ID查询记录
		User user = userMapper.selectById(6L);
		//设置要将其修改的值
		user.setAge(50);
		//将修改方法送上去
		int result = userMapper.updateById(user);
		System.out.println(result);
	}
	//测试selectPage分页
	@Test
	public void testSelectPage() {
		//参数1：当前页 参数2：每页显示的记录数
		Page<User> page = new Page<>(1,3);
		//用到userMapper上 参数2为条件
		userMapper.selectPage(page, null);
		//打印每页
		page.getRecords().forEach(System.out::println);
		System.out.println(page.getCurrent());
		System.out.println(page.getPages());
		System.out.println(page.getSize());
		System.out.println(page.getTotal());
		System.out.println(page.hasNext());
		System.out.println(page.hasPrevious());
	}
	//根据ID删除单条记录
	@Test
	public void testDeleteById(){
		int result = userMapper.deleteById(1L);
		System.out.println(result);
	}
	//根据ID删除多条记录
	@Test
	public void testDeleteBatchIds(){
		int result = userMapper.deleteBatchIds(Arrays.asList(1, 2, 3));
		System.out.println(result);
	}
	//找出年龄大于等于28的
	@Test
	public void testSelectGE(){
		//注意其封装的是实体User
		QueryWrapper<User> UserQueryWrapper = new QueryWrapper<>();
		//列名要打对
		UserQueryWrapper.ge("age",28);
		//执行查询
		List<User> users = userMapper.selectList(UserQueryWrapper);
		System.out.println(users);
	}
	//找出年龄等于20的
	@Test
	public void testSelectNE(){
		QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
		userQueryWrapper.eq("age","20");
		User user = userMapper.selectOne(userQueryWrapper);
		System.out.println(user);
	}
	//找出name中有e的，且email以t开头的用户
	@Test
	public void testSelectLike(){
		QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
		userQueryWrapper.like("name","e").likeRight("email","t");
		List<User> users = userMapper.selectList(userQueryWrapper);
		List<Map<String, Object>> maps = userMapper.selectMaps(userQueryWrapper);
		users.forEach(System.out::println);
	}
	//所有人按年龄升序排列
	@Test
	public void testSelectBy(){
		QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
		userQueryWrapper.orderByAsc("age");
		List<User> users = userMapper.selectList(userQueryWrapper);
		users.forEach(System.out::println);
	}
	//查找年龄=21，名字为Sandy的人
	@Test
	public void testLambdaQuery(){
		LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
		userLambdaQueryWrapper.eq(User::getAge,21);
		userLambdaQueryWrapper.like(User::getName,"Sandy");
		List<User> list = userMapper.selectList(userLambdaQueryWrapper);
		System.out.println(list);
	}
	//注入service后查询所有数据
	@Autowired
	private UserService userService;
	//上面是直接调用mapper，属于dao层的内容，现在是调用service，执行后台的一些操作
	@Test
	public void findAll(){
		//这些方法可以在service中自定义声明
		List<User> list = userService.list();
		for(User u:list){
			System.out.println(u);
		}
	}
}
