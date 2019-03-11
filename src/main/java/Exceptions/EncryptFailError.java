package Exceptions;

/**
 * 加密失败异常
 * Created by Sunny on 2017/7/13 0013.
 */
public class EncryptFailError extends Exception{
    public EncryptFailError(String message) {
        super(message);
    }
}
