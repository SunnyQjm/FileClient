package FileClient;


import Exceptions.EncryptFailError;
import Info.APIInfo;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * 客户端程序，负责对象的创建，以及提供服务
 * Created by Sunny on 2017/7/6 0006.
 */
public class FileClient {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, EncryptFailError {

        while (true){
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if(input.equals("quit"))
                break;
            args = input.split(" ");
            if (args.length != 2)
                System.out.println("Please input right param!!");
            //从配置文件中初始化
            APIInfo.init();
            FileTransOperator fco = new FileTransSupport(APIInfo.ip, APIInfo.port);
            switch (args[0].trim().toLowerCase()) {
                case "upload":
                    fco.upload(args[1].trim());
                    break;
                case "download":
                    fco.download(args[1].trim());
                    break;
                case "remove":
                    fco.remove(args[1].trim());
            }
        }
    }
}
