package com.example.mybatisplustest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplustest.entity.User;
import com.example.mybatisplustest.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
		User user = userMapper.selectById(1L);
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
}
