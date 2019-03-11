import utils.Tool;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Sunny on 2017/7/9 0009.
 */
public class test {
    public void main(String[] args) throws NoSuchAlgorithmException, IOException {
//        FileInputStream fis = new FileInputStream("./src/main/download/RequestBody.java");
//        BufferedInputStream bis = new BufferedInputStream(fis);
//        byte[] buffer = new byte[1024];
//        int len = -1;
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        while ((len = bis.read(buffer)) != -1){
//            md.update(buffer, 0, len);
//        }
//
//        byte[] result = md.digest();
//        String bigInteger = new BigInteger(result).toString();
//        System.out.println(bigInteger);
//        System.out.println(Arrays.toString(result));
//        System.out.println(result.length);

//        FileOutputStream fos = new FileOutputStream("./src/test/test.dat");
//        DataOutputStream dos = new DataOutputStream(fos);
//        byte[] bytes = {3, 2, 4, 5, 6, 8, 3, 2, 123, 43, 1, 123, 43, 24, 52,
//                3, 2, 4, 5, 6, 8, 3, 2, 123, 43, 1, 123, 43, 24, 52,
//                3, 2, 4, 5, 6, 8, 3, 2, 123, 43, 1, 123, 43, 24, 52,
//                3, 2, 4, 5, 6, 8, 3, 2, 123, 43, 1, 123, 43, 24, 52,
//                3, 2, 4, 5, 6, 8, 3, 2, 123, 43, 1, 123, 43, 24, 52,
//                3, 2, 4, 5, 6, 8, 3, 2, 123, 43, 1, 123, 43, 24, 52};
//        dos.writeInt(21);
//        dos.write(bytes);
//        dos.close();
//        fos.close();

        A a = new A();
        B b = (B) a;
    }


    class A{

    }
    class B extends A{

    }
}
