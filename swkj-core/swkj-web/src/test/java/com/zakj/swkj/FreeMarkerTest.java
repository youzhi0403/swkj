package com.zakj.swkj;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

public class FreeMarkerTest {

//	@Test
	public void testFreeMarker() throws Exception {
		//1、创建一个模板文件
		//2、创建一个Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		//3、设置模板文件保存的目录
		configuration.setDirectoryForTemplateLoading(new File("H:/IDEA-Projects/swkj/swkj-core/swkj-web/src/main/webapp/WEB-INF/ftl"));
		//4、模板文件的编码格式，一般就是utf-8
		configuration.setDefaultEncoding("utf-8");
		//5、加载一个模板文件，创建一个模板对象。
		Template template = configuration.getTemplate("student.ftl");
		//6、创建一个数据集。可以是pojo也可以是map。推荐使用map
		Map data = new HashMap<>();
		data.put("hello", "hello freemarker!");
		//创建一个pojo对象
		Student student = new Student(1, "小明", 18, "回龙观");
		data.put("student", student);
		//添加一个list
		List<Student> stuList = new ArrayList<>();
		stuList.add(new Student(1, "小明1", 18, "回龙观"));
		stuList.add(new Student(2, "小明2", 19, "回龙观"));
		stuList.add(new Student(3, "小明3", 20, "回龙观"));
		stuList.add(new Student(4, "小明4", 21, "回龙观"));
		stuList.add(new Student(5, "小明5", 22, "回龙观"));
		stuList.add(new Student(6, "小明6", 23, "回龙观"));
		stuList.add(new Student(7, "小明7", 24, "回龙观"));
		stuList.add(new Student(8, "小明8", 25, "回龙观"));
		stuList.add(new Student(9, "小明9", 26, "回龙观"));
		data.put("stuList", stuList);
		//添加日期类型
		data.put("date", new Date());
		//null值的测试
		data.put("val", "123");
		//7、创建一个Writer对象，指定输出文件的路径及文件名。
//		Writer out = new FileWriter(new File("D:/temp/JavaEE32/freemarker/hello.txt"));
		Writer out = new FileWriter(new File("H:/IDEA-Projects/swkj/swkj-core/swkj-web/src/main/webapp/front_page/product_page/student.html"));
		//8、生成静态页面
		template.process(data, out);
		//9、关闭流
		out.close();
	}
}
