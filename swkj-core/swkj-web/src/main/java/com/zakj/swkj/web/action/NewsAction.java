package com.zakj.swkj.web.action;

import com.zakj.swkj.bean.Constant;
import com.zakj.swkj.bean.News;
import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.Product;
import com.zakj.swkj.service.INewsService;
import com.zakj.swkj.utils.*;
import com.zakj.swkj.web.action.base.BaseAction;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/9/25
 * Time: 15:06
 * Description:
 **/
@Controller
@Scope("prototype")
public class NewsAction extends BaseAction<News> {
    private final INewsService service;
    @Autowired
    public NewsAction(INewsService service) {
        this.service = service;
    }

    private String page;
    private String rows;

    public void setPage(String page) {
        this.page = page;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    private String delIds;

    public void setDelIds(String delIds) {
        this.delIds = delIds;
    }

    public String list() {
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        /*Logger.getLogger(this.getClass()).info();*/
        try {
            writeJson(service.newsListExceptDescription(model, pageBean).toString());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(this.getClass()).info(e);
        }

        return NONE;
    }

    public String delete() {
        JSONObject result = new JSONObject();
        int delNums = service.newsDelete(delIds,ServletActionContext.getRequest());
        if (delNums > 0) {
            result.put("success", "true");
            result.put("delNums", delNums);
        } else {
            result.put("errorMsg", "删除失败");
        }
        try {
            writeJson(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

    public String save() throws Exception {
        Logger.getLogger(this.getClass()).info("Id:" + model.getId());
        Logger.getLogger(this.getClass()).info("Title:" + model.getTitle());
        Logger.getLogger(this.getClass()).info("Title_picture_url:" + model.getTitle_picture_url());
        Logger.getLogger(this.getClass()).info("News_author:" + model.getNews_author());
        Logger.getLogger(this.getClass()).info("Page_url:" + model.getPage_url());
        Logger.getLogger(this.getClass()).info("Create_date:" + model.getCreate_date());
        Logger.getLogger(this.getClass()).info("News_description:" + model.getNews_description());
        Logger.getLogger(this.getClass()).info("Origin:" + model.getOrigin());
        Logger.getLogger(this.getClass()).info("Page_view:" + model.getPage_view());

        try {
            //*到这里已经把图片保存到服务器，接下来要做的就是，把路径以及其他信息传给service层，让service层处理接下里啊的业务*//*
            JSONObject result = new JSONObject();
            if (StringUtil.isEmpty(model.getId())) {
                //添加操作
                if (!service.add(model, ServletActionContext.getRequest())) {
                    result.put("errorMsg", "保存失败");
                }
                //更新或生成列表页面
                service.saveOrUpdatePage(ServletActionContext.getRequest());
            } else {
                //更新操作
                if (!service.update(model, ServletActionContext.getRequest())) {
                    result.put("e                                                                                                  rrorMsg", "保存失败");
                }
                //更新或生成列表页面
                service.saveOrUpdatePage(ServletActionContext.getRequest());
            }
            writeJson(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;

    }
    /*属性驱动，获取上传的图片文件，属性名必须为表单上传时input标签的name属性值！！ */
    /*通过form表单上传，必须要设置这两个属性：enctype="multipart/form-data" method:"post"*/
    private File imgFile;

    /* “文件属性名 + FileName”是固定格式，获取上传文件的文件名 */
    private String imgFileFileName;

    /* "文件属性名 + ContentType"是固定格式，获取上传的文件类型 */
    private String imgFileContentType;

    /*通过配置文件注入参数，配置上传的文件的保存路径*/
    private String saveDir;

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }

    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }

    public void setSaveDir(String saveDir) {
        this.saveDir = saveDir;
    }

    public String uploadPicture() throws IOException {

        //判断文件后缀名是否正确
        String fileExt = imgFileContentType.substring(imgFileContentType.indexOf("/") + 1).toLowerCase();
        if (!Arrays.asList(Constant.IMAGE_EXT.split(",")).contains(fileExt)) {
            writeJson(KEUtils.error("只能上传图片文件。"));
            return NONE;
        }

        //判断文件大小
        if (imgFile.length() > Constant.MAX_SIZE) {
            writeJson(KEUtils.error("上传文件超过限制大小。"));
            return NONE;
        }

        byte[] buffer = new byte[1024];
        //读取文件
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(imgFile);

            //产生一个唯一的ID作为图片名
            String fileName = IDUtils.getImageName() + "." + fileExt;
            //保存到服务器的文件
            File newFile = new File(realPath(saveDir) + "/" + fileName);
            //如果出现文件重名，则重新上传（重名几率几乎忽略不计）。
            if (newFile.exists()) {
                writeJson(KEUtils.error("上传失败，请重新上传!"));
                return NONE;
            } else {
                while (!newFile.createNewFile()) {
                    if (!newFile.mkdirs()) {
                        writeJson(KEUtils.error("无法保存图片，请重试或联系后台管理员!"));
                        return NONE;
                    }
                }
            }
            fos = new FileOutputStream(newFile);
            int length;
            while ((length = fis.read(buffer)) > 0) {
                //每次写入length长度的内容
                fos.write(buffer, 0, length);
            }
            fos.flush();

            writeJson(KEUtils.ok(basePath() + saveDir + "/" + fileName));

        } catch (IOException e) {
            e.printStackTrace();
            LoggerUtils.error(this, e.getStackTrace());
        } finally {
            CloseUtils.closeIO(fis, fos);
        }
        return NONE;
    }

    public String getNewsContent() {
        try {
            writeJson(ResultUtils.ok(service.getNewsContentById(model.getId())));
        } catch (Exception e) {
            e.printStackTrace();

        }
        return NONE;
    }

}
