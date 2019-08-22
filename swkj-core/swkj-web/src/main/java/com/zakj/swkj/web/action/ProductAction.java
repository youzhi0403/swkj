package com.zakj.swkj.web.action;

import com.zakj.swkj.bean.Constant;
import com.zakj.swkj.bean.Product;
import com.zakj.swkj.bean.Series;
import com.zakj.swkj.service.IProductService;
import com.zakj.swkj.utils.*;
import com.zakj.swkj.web.action.base.BaseAction;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/21
 * Time: 17:33
 * Description: 产品模板控制层
 **/
@Controller
@Scope("prototype")
public class ProductAction extends BaseAction<Product> {

    private final IProductService service;

    /*属性驱动，获取上传的图片文件，属性名必须为表单上传时input标签的name属性值！！ */
    /*通过form表单上传，必须要设置这两个属性：enctype="multipart/form-data" method:"post"*/
    private File imgFile;

    /* “文件属性名 + FileName”是固定格式，获取上传文件的文件名 */
    private String imgFileFileName;

    /* "文件属性名 + ContentType"是固定格式，获取上传的文件类型 */
    private String imgFileContentType;

    /*通过配置文件注入参数，配置上传的文件的保存路径*/
    private String saveDir;

    //上传的图片标志位：0 产品图片  1 系列图片
    private int imgFlag;

    @Autowired
    public ProductAction(IProductService service) {
        this.service = service;
    }

    public void setSaveDir(String saveDir) {
        this.saveDir = saveDir;
    }

    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }

    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public void setImgFlag(int imgFlag) {
        this.imgFlag = imgFlag;
    }

    /**
     * 用于接收上传的产品图片，并存储到tomcat
     *
     * @return 不需要跳转页面，返回NONE
     */
    public String uploadPicture() throws IOException {

        try {
            //判断文件后缀名是否正确
            String fileExt = imgFileContentType.substring(imgFileContentType.indexOf("/") + 1).toLowerCase();
            if (!Arrays.asList(Constant.IMAGE_EXT.split(",")).contains(fileExt)) {
                writeJson(KEUtils.error("只能上传图片文件。"));
                return NONE;
            }

            //判断文件大小
//            if (imgFile.length() > Constant.MAX_SIZE) {
//                writeJson(KEUtils.error("上传文件超过2M限制大小。"));
//                return NONE;
//            }

            //判断图片的分辨率
            BufferedImage srcImage = ImageIO.read(imgFile);
            int srcImageHeight = srcImage.getHeight(); //获取图片的高度
            int srcImageWidth = srcImage.getWidth();  //获取图片的宽度
            if ((imgFlag == 0) && (srcImageHeight != 450 || srcImageWidth != 450)) {
                throw new RuntimeException("请选择尺寸为450*450的图片！");
            }

            if ((imgFlag == 1) && (srcImageHeight != 270 || srcImageWidth != 270)) {
                throw new RuntimeException("请选择尺寸为270*270的图片！");
            }

            //产生一个唯一的ID作为图片名
            String fileName = IDUtils.getImageName() + "." + fileExt;
            FileUtils.saveImg(realPath("/") + saveDir, imgFile, fileName);

            writeJson(KEUtils.ok(basePath() + saveDir + "/" + fileName));
        } catch (Exception e) {
            if (e instanceof RuntimeException)
                writeJson(KEUtils.error(e.getMessage()));
            else
                writeJson(KEUtils.error("上传失败！"));
            LoggerUtils.error(this, e.getMessage());
        }
        return NONE;
    }

    /**
     * 接收添加/修改的产品数据
     *
     * @return 不跳转页面，返回NONE
     */
    public String save() {
        try {
            service.saveProduct(model, ServletActionContext.getRequest());
        } catch (Exception e) {
            LoggerUtils.exception(this, e);
        }
        return Constant.LIST;
    }

    /**
     * 获取list列表数据
     *
     * @return 不跳转页面，返回NONE
     */
    public String list() throws IOException {

        HibernateHelper helper = new HibernateHelper();
        helper.setDc(pageBean.getDetachedCriteria());
        helper.addOrder(Order.desc("create_time")); //给离线查询添加一个desc order（逆向查询）

        String product_name = model.getProduct_name();
        String product_number = model.getProduct_number();
        if (product_name != null)
            helper.addLikeConditions("product_name", model.getProduct_name());
        if (product_number != null)
            helper.addLikeConditions("product_number", model.getProduct_number());
        if (model.getSeries() != null) {
            String s_name = model.getSeries().getS_name();
            if (s_name != null)
                helper.createAlias("series", "s")
                        .addLikeConditions("s.s_name", model.getSeries().getS_name());
        }
        pageBean.setDetachedCriteria(helper.getDc());

        //查询数据
        service.findProductList(pageBean);
        //返回json数据
        writeJson(JsonUtils.parseObject(pageBean, "currentPage", "detachedCriteria", "pageSize"));

        return NONE;
    }

    private String delIds;

    public String delete() throws IOException {
        try {
            List<Product> products = JsonUtils.toList(delIds, Product.class);
            service.deleteProducts(products, ServletActionContext.getRequest());
            writeJson(ResultUtils.ok(null));
        } catch (Exception e) {
            if (e instanceof RuntimeException){
                writeJson(ResultUtils.failure(e.getMessage()));
            }
            Logger.getLogger(ProductAction.class).error(e.getMessage());
        }
        return NONE;
    }

    private String del_img_url;

    //删除指定url的图片
    public String delPicture() {
        new File(realPath("/") + del_img_url).delete();
        return NONE;
    }

    public void setDelIds(String delIds) {
        this.delIds = delIds;
    }

    public void setDel_img_url(String del_img_url) {
        this.del_img_url = del_img_url;
    }

}
