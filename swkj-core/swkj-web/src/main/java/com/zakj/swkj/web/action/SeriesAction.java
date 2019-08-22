package com.zakj.swkj.web.action;

import com.zakj.swkj.bean.Constant;
import com.zakj.swkj.bean.Series;
import com.zakj.swkj.service.IHotProductService;
import com.zakj.swkj.service.ISeriesService;
import com.zakj.swkj.utils.*;
import com.zakj.swkj.web.action.base.BaseAction;
import exception.SeriesException;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/10/23
 * Time: 11:05
 * Description:
 **/
@Controller
@Scope("prototype")
public class SeriesAction extends BaseAction<Series> {

    private final ISeriesService service;

    private File imgFile;

    private String imgFileFileName;

    private String saveDir;

    @Autowired
    public SeriesAction(ISeriesService service, IHotProductService hotProductService) {
        this.service = service;
        this.hotProductService = hotProductService;
    }

    private String del_img_url;

    //删除指定url的图片
    public String delPicture() {
        new File(realPath("/") + del_img_url).delete();
        return NONE;
    }

    //上传图片
    public String uploadPicture() throws IOException {

        try {
            //判断文件后缀名是否正确
            String fileExt = imgFileFileName.substring(imgFileFileName.lastIndexOf(".") + 1).toLowerCase();
            if (!Arrays.asList(Constant.IMAGE_EXT.split(",")).contains(fileExt)) {
                writeJson(KEUtils.error("只能上传图片文件。"));
                return NONE;
            }

            //判断图片的分辨率
            BufferedImage srcImage = ImageIO.read(imgFile);
            int srcImageHeight = srcImage.getHeight(); //获取图片的高度
            int srcImageWidth = srcImage.getWidth();  //获取图片的宽度
            if ((srcImageHeight != 270 || srcImageWidth != 270)) {
                writeJson(KEUtils.error("请选择尺寸为270*270的图片！"));
                return NONE;
            }

            //产生一个唯一的ID作为图片名
            String fileName = IDUtils.getImageName() + "." + fileExt;
            FileUtils.saveImg(realPath("/") + saveDir, imgFile, fileName);

            writeJson(KEUtils.ok(basePath() + saveDir + "/" + fileName));
        } catch (Exception e){
            writeJson(KEUtils.error("上传失败！"));
            Logger.getLogger(this.getClass()).error(e.getMessage());
        }

        return NONE;
    }

    //系列分页查询
    public String pageQuery(){
        try {
            service.pageQuery(pageBean);
            //返回json数据
            writeJson(JsonUtils.parseObject(pageBean, "currentPage", "detachedCriteria", "pageSize"));
        } catch (Exception e){
            Logger.getLogger(this.getClass()).error(e.getMessage());
        }
        return NONE;
    }

    private String ids;

    //批量删除选中的系列
    public String delete() throws IOException {

        try {
            service.deleteBatch(service.deleteSeriesByIds(ids.split(",")), ServletActionContext.getRequest());
            writeJson(ResultUtils.ok(null));
        } catch (Exception e){
            if (e instanceof DataIntegrityViolationException){
                //违反数据完整性异常
                writeJson(ResultUtils.failure("删除失败！无法删除关联了产品的系列！"));
            }
            if (e instanceof SeriesException){
                writeJson(ResultUtils.failure(e.getMessage()));
            }
            Logger.getLogger(this.getClass()).error(e.getMessage());
        }
        return NONE;
    }

    private final IHotProductService hotProductService;

    //添加或更新一个系列
    public String add() throws IOException{
        try {
            service.saveOrUpdate(model, ServletActionContext.getRequest());
        } catch (Exception e){
            if (e instanceof TemplateException){
                this.addActionError("模板生成失败！");
                return Constant.ERROR;
            }
            this.addActionError(e.getMessage());
            Logger.getLogger(this.getClass()).error(e.getMessage());
            return Constant.ERROR;
        }
        return Constant.LIST;
    }

    //查询所有系列数据，返回json
    public String allSeries(){
        try {
            List<Series> list = service.findAllSeries();
            writeJson(JsonUtils.parseArray(list, "products","page_url","hotPage_url","picture_url"));
        } catch (Exception e){
            Logger.getLogger(this.getClass()).error(e.getMessage());
        }

        return NONE;
    }

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }

    public void setSaveDir(String saveDir) {
        this.saveDir = saveDir;
    }

    public void setDel_img_url(String del_img_url) {
        this.del_img_url = del_img_url;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
