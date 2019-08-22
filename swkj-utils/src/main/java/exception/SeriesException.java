package exception;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/10/26
 * Time: 10:52
 * Description: 产品系列模块相关的异常，用于封装异常信息，方便抛到action层进行处理。
 **/
public class SeriesException extends Exception {

    public SeriesException(String message) {
        super(message);
    }
}
