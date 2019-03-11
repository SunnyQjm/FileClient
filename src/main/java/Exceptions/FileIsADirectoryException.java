package Exceptions;

import java.io.IOException;

/**
 * 文件是一个目录异常
 * Created by Sunny on 2017/7/7 0007.
 */
public class FileIsADirectoryException extends IOException{
    private static final long serialVersionUID = 5791125441456382360L;

    public FileIsADirectoryException(String message) {
        super(message);
    }
}
