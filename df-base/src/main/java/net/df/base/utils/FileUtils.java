package net.df.base.utils;


import net.df.base.exception.DfException;
import java.io.*;

/**
 * 文件工具类
 */
public class FileUtils {

    private static String defalutCharset = "UTF-8";

    /**
     * 将输入流保存进指定文件
     * @param inputStream
     * @param file
     */
    public static void saveFile(InputStream inputStream,String file){
        saveFile(inputStream,new File(file));
    }

    /**
     * 将输入流保存进指定文件
     * @param inputStream
     * @param file
     */
    public static void saveFile(InputStream inputStream, File file){
        FileOutputStream fos = null;
        Exception exception = null;
        try {
            fos = new FileOutputStream(file);
            int length = 0;
            byte[] buf = new byte[1024];
            while ((length = inputStream.read(buf)) != -1) {
                fos.write(buf, 0, length);
            }
            fos.flush();
        }catch (Exception e){
            exception = e;
            throw new DfException("文件读写异常",exception);
        }finally {
            close(fos,exception);
        }
    }

    /**
     * 关闭Reader
     * @param reader
     * @param t
     */
    public static void close(Reader reader, Throwable t){
        if(reader != null){
            try {
                reader.close();
            }catch (Exception e){
                t.addSuppressed(e);
            }
        }
    }

    /**
     * 关闭Reader
     * @param reader
     */
    public static void close(Reader reader){
        close(reader,null);
    }

    /**
     * 关闭输入流
     * @param in
     * @param t
     */
    public static void close(InputStream in, Throwable t){
        if(in != null){
            try {
                in.close();
            }catch (Exception e){
                if(t != null){
                    t.addSuppressed(e);
                }
            }
        }
    }

    /**
     * 关闭输入流
     * @param in
     */
    public static void close(InputStream in){
        close(in,null);
    }

    /**
     * 关闭Writer
     * @param writer
     * @param t
     */
    public static void close(Writer writer, Throwable t){
        if(writer != null){
            try {
                writer.close();
            }catch (Exception e){
                if(t != null){
                    t.addSuppressed(e);
                }
            }
        }
    }

    /**
     * 关闭Writer
     * @param writer
     */
    public static void close(Writer writer){
        close(writer,null);
    }

    /**
     * 关闭输出流
     * @param os
     * @param t
     */
    public static void close(OutputStream os, Throwable t){
        if(os != null){
            try {
                os.close();
            }catch (Exception e){
                if(t != null){
                    t.addSuppressed(e);
                }
            }
        }
    }

    /**
     * 关闭输出流
     * @param os
     */
    public static void close(OutputStream os){
        close(os,null);
    }

    /**
     * 读取文件成字符串
     * @param file
     * @param charset
     * @return
     */
    public static String readFile(File file,String charset){
        if(file == null || !file.exists()){
            throw new DfException("文件不存在");
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            int length = inputStream.available();
            byte[] data = new byte[length];
            inputStream.read(data);
            return new String(data, charset);
        }catch (IOException e){
            throw new DfException("读取文件出错", e);
        }finally {
            close(inputStream);
        }
    }

    /**
     * 读取文件成字符串
     * @param file
     * @return
     */
    public static String readFile(File file){
        return readFile(file, defalutCharset);
    }


    /**
     * 读取文件成字符串
     * @param filePath
     * @param charset
     * @return
     */
    public static String readFile(String filePath, String charset){
        File file = new File(filePath);
        return readFile(file,charset);
    }

    public static String readFile(String filePath) {
        return readFile(filePath,defalutCharset);
    }

    //获取用于替换的文件路径分隔符
    public static String getReplaceSeparator(){
        String separator = File.separator;
        if("\\".equals(File.separator)){
            separator = "\\\\";
        }
        return separator;
    }
}
