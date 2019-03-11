package FileClient;

import Data.FileInfo;
import Exceptions.EncryptFailError;
import Exceptions.FileIsADirectoryException;
import Info.PropertiesPathInfo;
import Info.TransProtocol;
import base.SocketSupport;
import body.RequestBody;
import utils.GsonUtil;
import utils.Tool;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 客服端与节点服务器通信
 * Created by Sunny on 2017/7/6 0006.
 */
public class FileTransToStorageSupport extends SocketSupport implements FileTransOperator {

    private String uuid;
    private FileInfo fileInfo;

    public FileTransToStorageSupport(String ip, int port, String uuid) throws IOException {
        super(ip, port);
        this.uuid = uuid;
        this.fileInfo = new FileInfo.Builder()
                .uid(uuid)
                .build();
    }

    public FileTransToStorageSupport(String ip, int port, FileInfo fileInfo) throws IOException {
        super(ip, port);
        this.uuid = fileInfo.getUid();
        this.fileInfo = fileInfo;
    }


    @Override
    public FileInfo upload(String path) throws IOException, NoSuchAlgorithmException, EncryptFailError {
        //表示自己要上传文件
        dos.writeInt(TransProtocol.CODE_UPLOAD_FILE);
        //存储节点上保存文件时的名字
        dos.writeUTF(uuid);
        dos.flush();

        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        //缓冲数组
        byte[] buffer = new byte[4 * 1024];
        int len = -1;
        byte[] temp;
        long saveSize = 0;

        //以下变量都用于进度统计以及传输速率统计
        long totalLength = file.length();
        long alreadyTrans = 0;
        int pre_progress = 0;
        int progress = 0;
        long startTime = System.currentTimeMillis();

        //用于本地计算MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        while ((len = bufferedInputStream.read(buffer)) != -1) {
            alreadyTrans += len;

            temp = Tool.compress(Tool.subByteArray(buffer, 0, len));
            //文件分段加密
            temp = Tool.quickEncrypt(temp);
            if (temp == null) {
                throw new EncryptFailError("加密出错");
            }
            //文件段压缩以后加密
            md.update(temp);
            dos.writeInt(temp.length);
            dos.write(temp);
            saveSize += temp.length + 4;        //一个int值四个字节

            if ((progress = (int) Math.floor(alreadyTrans * 1.0 / totalLength * 100)) > pre_progress) {
                pre_progress = progress;
                System.out.println("已上传：" + progress + "%");
            }
        }
        System.out.println("上传速率：" + (totalLength / 1024.0) / (System.currentTimeMillis() - startTime) * 1000 + "KB/s");
        //写出一个值为-1的int值表示文件传输结束
        dos.writeInt(-1);

        //将本地计算的加密以后的md5值传给存储节点，比对以后判断文件传输过程中是否出错
        dos.writeUTF(new BigInteger(md.digest()).toString());
        dos.flush();

        //关闭文件流
        bufferedInputStream.close();
        fileInputStream.close();

        close();

        fileInfo.setSaveSize(saveSize);
        return fileInfo;
    }

    /**
     * 从存储节点下载文件
     *
     * @param uid
     * @throws IOException
     */
    @Override
    public void download(String uid) throws IOException {
        dos.writeInt(TransProtocol.CODE_DOWNLOAD_FILE);
        dos.writeUTF(uid);
        dos.flush();

        FileOutputStream fos = new FileOutputStream(PropertiesPathInfo.downloadPath + fileInfo.getOriginalName());
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        byte buffer[];
        int len = -1;
        byte[] temp;

        while ((len = dis.readInt()) != -1) {
            buffer = new byte[len];
            dis.readFully(buffer);
            temp = Tool.quickDecrypt(buffer);
            temp = Tool.decompress(temp);
            System.out.println("temp");
            assert temp != null;
            bos.write(temp);
        }
        bos.close();
        fos.close();

        close();
    }


    /**
     * 文件的删除操作委托给FileServer了，不用客户端向Node请求删除文件
     * @param uid
     * @throws IOException
     */
    @Override
    public void remove(String uid) throws IOException {
//        dos.writeInt(TransProtocol.CODE_REMOVE_FILE);
//        dos.writeUTF(uid);
//
//        int result = dis.readInt();
//        close();
//        if (result == -1)
//            throw new IOException("存储服务器上没有该文件");
//        else if (result == -2)
//            throw new IOException("删除文件失败");
    }
}
