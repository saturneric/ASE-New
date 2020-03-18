package com.codesdream.ase.component.datamanager;

import com.codesdream.ase.component.auth.SHA1Encoder;
import com.codesdream.ase.exception.StringFileConvertException;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.Base64;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

// 将文件处理成可发送的字符串文件对象
@Component
public class StringFileGenerator {

    @Resource
    private SHA1Encoder encoder;

    // 用过读入流创建一个字符串文件
    public Optional<StringFile> generateStringFile(InputStream stream){
        StringFile file = new StringFile();
        // 字符串内容计算
        file.setStrData(generateFile2String(stream));
        if(file.getStrData() == null) return Optional.empty();
        // 相关校验值计算
        file.setSha1Checker(generateSHA1Checker(file.getStrData()));
        file.setSize(file.getStrData().length());
        return Optional.of(file);
    }

    private byte[] readSteamAll(InputStream stream) {
        try {
            byte[] bytes = new byte[stream.available()];

            //检查文件书否完全读取
            if (stream.read(bytes) != bytes.length) return null;
            else return bytes;
        } catch (IOException e){
            return null;
        }
    }

    private String generateFile2String(InputStream stream){
        ByteArrayOutputStream zipDataStream = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(zipDataStream);
            byte[] bytes = readSteamAll(stream);
            if(bytes == null) return null;
            gzipOutputStream.write(bytes);
            gzipOutputStream.close();
            return Base64.getEncoder().encodeToString(zipDataStream.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }

    // 生成字符串文件的校验码
    private String generateSHA1Checker(String str){
        return encoder.encode(str);
    }

    // 检查文件内容是否正确，包括大小与校验码
    public boolean checkStringFile(StringFile file){
        return file.getStrData().length() == file.getSize()
                && encoder.match(file.getStrData(), file.getSha1Checker());
    }

    // 从字符串文件中读取真实的文件数据
    public InputStream readFileString(StringFile file){
        try {
            // 字符串转换为二进制数据
            byte[] bytes = Base64.getDecoder().decode(file.getStrData());
            GZIPInputStream stream = new GZIPInputStream(new ByteArrayInputStream(bytes), bytes.length);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // 数据解压缩
            int readBits = 0;
            byte[] rawBytes = new byte[1024];
            while ((readBits = stream.read(rawBytes)) != -1) {
                outputStream.write(rawBytes, 0, readBits);
            }

            stream.close();
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new StringFileConvertException("Read FileString Failed");
        }
    }
}
