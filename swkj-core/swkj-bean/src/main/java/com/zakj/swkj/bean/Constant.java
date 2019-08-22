package com.zakj.swkj.bean;

/**
 * 用于保存所有固定的字符串常量和action返回的结果字符串，方便做全局修改。
 *
 * @author HuangRZ
 */
public class Constant {

	/**
	 * 产品的“系列列表”静态页面的相对保存路径 - URI
	 */
	public static final String PRODUCT_SERIES_LINE_HTML_OUT_DIR = "/product";

	/**
	 * “产品详情”静态html页面的相对保存路径 - URI
	 */
	public static final String PRODUCT_INFO_HTML_OUT_DIR = "/product/products";

	/**
	 * 系列的“产品列表”静态html页面的相对保存路径 - URI
	 */
	public static final String PRODUCT_LINE_HTML_OUT_DIR = "/product/productLine";


	//存储热销系列的页面
	public static final String HOTPRODUCT_SERIES_HTML_OUT_DIR = "/hotProduct";

	public static final String CONTACT_HTML_OUT_DIR = "/contact";

	/**
	 * 新闻的相关页面的相对保存路径
	 */
	public static final String NEWS_INFO_HTML_OUT_DIR = "/news";

	/**
	 * 用于保存登录验证码到Session的key
	 */
	public static final String CODE_KEY = "code_key";

	/**
	 * 返回登录页面的result
	 */
	public static final String LOGIN = "login";

	/**
	 * 跳转到首页的result
	 */
	public static final String HOME = "home";


	/**
	 * 用于保存登录的用户User到Session的key
	 */
	public static final String USER_KEY = "loginUser";

	/**
	 * 用于指定单张上传图片最大的文件大小
	 */
	public static final long MAX_SIZE = 2*1024*1024;

	/**
	 * 上传图片的后缀名，后缀名必须使用‘，’隔开
	 */
	public static final String IMAGE_EXT = "jpg,jpeg,png,bmp";

	/**
	 * 上传的图片的最大大小
	 */
	public static  final int IMG_MAX_SIZE = 1024*1024;

	/**
	 * 轮播图最大数量
	 */
	public static final int SLIDE_IMG_COUNT_MAX = 10;

	/**
	 * 首页推荐产品的数量
	 */
    public static final int RECOMMEND_PRO_COUNT = 4;

	/**
	 * 跳转到列表展示页的result，需要与struts.xml配置文件的result相对应
	 */
	public static final String LIST = "list";

	/**
	 * 跳转到错误页面的result
	 */
	public static final String ERROR = "error";
}
