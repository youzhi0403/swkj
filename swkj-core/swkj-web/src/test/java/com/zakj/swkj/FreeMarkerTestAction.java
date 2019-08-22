package com.zakj.swkj;

import com.opensymphony.xwork2.ActionSupport;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

@Controller
@Scope("prototype")
public class FreeMarkerTestAction extends ActionSupport{

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public String genHtml() throws Exception {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        //加载模板对象
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
        data.put("val", null);
        //指定文件输出的路径及文件名
        Writer out = new FileWriter(new File("H:/IDEA-Projects/swkj/swkj-core/swkj-web/src/main/webapp/front_page/product_page/student.html"));
        //输出文件
        template.process(data, out);
        //关闭流
        out.close();

        return NONE;
    }

}
