package com.zakj.swkj.web.action;

import com.zakj.swkj.bean.Constant;
import com.zakj.swkj.bean.SlideImg;
import com.zakj.swkj.service.IFrontService;
import com.zakj.swkj.utils.*;
import com.zakj.swkj.web.action.base.BaseAction;
import freemarker.template.TemplateException;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/10/13
 * Time: 17:16
 * Description:
 **/
@Controller
@Scope("prototype")
public class FrontAction extends BaseAction<SlideImg> {

    //接收图片
    private File imgFile;

    private String imgFileFileName;

    private String saveDir;

    @Autowired
    public FrontAction(IFrontService service) {
        this.service = service;
    }

    //轮播图片上传
    public String uploadPicture() throws IOException {

        try {
            SlideImg slideImg = checkAndSaveImg();
            service.save(slideImg, ServletActionContext.getRequest());
            writeJson(KEUtils.ok(basePath() + slideImg.getImg_url()));
        } catch (TemplateException | RuntimeException e) {
            if (e instanceof RuntimeException)
                writeJson(e.getMessage());
            if (e instanceof TemplateException)
                writeJson(KEUtils.error("创建静态页面失败！"));
            LoggerUtils.error(this, e.getMessage());
        }
        return NONE;
    }

    private final IFrontService service;

    //获取所有轮播图片
    public String pictureList() {
        pageBean.setCurrentPage(1);
        pageBean.setPageSize(Constant.SLIDE_IMG_COUNT_MAX);
        try {
            service.findSlideImgList(pageBean);
            HashMap<String, Object> map = new HashMap<>();
            map.put("total", pageBean.getTotal());
            map.put("rows", pageBean.getRows());
            writeJson(JsonUtils.parseObject(map));
        } catch (Exception e) {
            LoggerUtils.error(this, e.getMessage());
        }
        return NONE;
    }

    private String delIds;

    //删除轮播图片
    public String deleteImgs() throws IOException {
        try {
            List<SlideImg> slideImgs = JsonUtils.toList(delIds, SlideImg.class);
            service.deleteImgsFromDiskAndDB(slideImgs, ServletActionContext.getRequest());
            writeJson(ResultUtils.ok(null));
        } catch (Exception e) {
            if (e instanceof TemplateException)
                writeJson(KEUtils.error("创建静态页面失败！"));
            else
                writeJson(ResultUtils.failure("系统异常！"));
            LoggerUtils.error(this, e.getMessage());
        }
        return NONE;
    }

    //更新轮播图片
    public String updateImg() throws IOException {
        try {
            SlideImg slideImg = checkAndSaveImg();
            slideImg.setId(model.getId());
            slideImg.setImg_index(model.getImg_index());
            //更新数据库并更新静态页面
            service.updateImg(slideImg, ServletActionContext.getRequest());
            //删除被更新图片
            new File(realPath("/") + model.getImg_url()).delete();
            writeJson(KEUtils.ok(slideImg.getImg_url()));
        } catch (Exception e) {
            if (e instanceof RuntimeException)
                writeJson(e.getMessage());
            if (e instanceof TemplateException)
                writeJson(KEUtils.error("创建静态页面失败！"));
            LoggerUtils.error(this, e.getMessage());
        }
        return NONE;
    }

    //获取推荐产品列表
    public String recommendProductList() {
        try {
            HashMap<String, String> recProMap;
            ArrayList<HashMap<String, String>> recProList = new ArrayList<>();
            List<Object[]> list = service.findRecommendProductList();
            for (Object[] objects : list) {
                recProMap = new HashMap<>();
                recProMap.put("id", (String) objects[0]);
                recProMap.put("product_name", (String) objects[1]);
                recProMap.put("picture_url", (String) objects[2]);
                recProMap.put("page_url", (String) objects[3]);
                recProList.add(recProMap);
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("total", recProList.size());
            map.put("rows", recProList);
            writeJson(JsonUtils.parseObject(map));
        } catch (Exception e) {
            LoggerUtils.error(this, e.getMessage());
        }
        return NONE;
    }

    //获取所有产品 - 用于推荐产品选择
    public String proList() {
        try {
            List<Object[]> list = service.findProductIdAndNameList();
            HashMap<String, String> map;
            ArrayList<HashMap<String, String>> maps = new ArrayList<>();
            for (Object[] objects : list) {
                map = new HashMap<>();
                map.put("id", (String) objects[0]);
                map.put("product_name", (String) objects[1]);
                map.put("picture_url", (String) objects[2]);
                maps.add(map);
            }
            writeJson(JsonUtils.parseArray(maps));
        } catch (Exception e) {
            LoggerUtils.error(this, e.getMessage());
        }
        return NONE;
    }

    private String rec_pro_id;
    private String isRec;

    //添加推荐产品
    public String addRecProduct() throws IOException {
        try {
            //查询一下推荐产品的数量
            if (service.getRecProCount(isRec) + 1 > Constant.RECOMMEND_PRO_COUNT) {
                writeJson(ResultUtils.failure("最多只能添加" + Constant.RECOMMEND_PRO_COUNT + "个产品！"));
                return NONE;
            }
            service.addRecProduct(rec_pro_id, isRec, ServletActionContext.getRequest());
            writeJson(ResultUtils.ok(null));
        } catch (Exception e) {
            if (e instanceof TemplateException)
                writeJson(KEUtils.error("创建静态页面失败！"));
            else
                writeJson(ResultUtils.failure("添加失败！"));
            LoggerUtils.error(this, e.getMessage());
        }

        return NONE;
    }

    //删除推荐产品
    public String deleteRecPro() throws IOException {
        try {
            service.deleteRecPro(JsonUtils.toList(delIds, String.class), ServletActionContext.getRequest());
            writeJson(ResultUtils.ok(null));
        } catch (Exception e) {
            if (e instanceof TemplateException)
                writeJson(KEUtils.error("创建静态页面失败！"));
            else
                writeJson(ResultUtils.failure("删除失败！"));
            LoggerUtils.error(this, e.getMessage());
        }
        return NONE;
    }

    private SlideImg checkAndSaveImg() throws IOException {
        //判断轮播图片的数量，避免再添加新图片造成轮播图超出最大限制
        long count = service.getSlideImgCount();
        if (count + 1 > Constant.SLIDE_IMG_COUNT_MAX) {
            throw new RuntimeException(KEUtils.error("轮播图数量超出了10张限制！"));
        }

        //判断图片格式
        String ext = imgFileFileName.substring(imgFileFileName.lastIndexOf(".") + 1);
        if (!Arrays.asList(Constant.IMAGE_EXT.split(",")).contains(ext)) {
            throw new RuntimeException(KEUtils.error("请选择一张图片！"));
        }

        //判断文件大小
        if (imgFile.length() > Constant.IMG_MAX_SIZE) {
            throw new RuntimeException(KEUtils.error("上传文件超过" + (Constant.IMG_MAX_SIZE / 1024) + "K限制大小。"));
        }

        //判断图片的分辨率
        BufferedImage srcImage = ImageIO.read(imgFile);
        int srcImageHeight = srcImage.getHeight(); //获取图片的高度
        int srcImageWidth = srcImage.getWidth();  //获取图片的宽度
        if (srcImageHeight != 536 || srcImageWidth != 1140) {
            throw new RuntimeException(KEUtils.error("请选择尺寸为1140*536的图片！"));
        }

        String fileName = IDUtils.getImageName() + "." + ext;
        //保存图片
        FileUtils.saveImg(realPath(saveDir), imgFile, fileName);
        //拼接图片相对访问路径
        String uri = saveDir + "/" + fileName;

        //封装数据到实体类中
        return new SlideImg(null, uri, "0", String.valueOf(count + 1));
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

    public void setDelIds(String delIds) {
        this.delIds = delIds;
    }

    public void setRec_pro_id(String rec_pro_id) {
        this.rec_pro_id = rec_pro_id;
    }

    public void setIsRec(String isRec) {
        this.isRec = isRec;
    }


}
