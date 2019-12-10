package notepad;

import java.io.*;

public class FileManager {
    private String fileName;
    private boolean isModified = false;
    private String content;

    public FileManager() {
    }

    /**
     * 加载一个文件
     *
     * @param name 文件名
     */
    public void load(String name) {
        final String textString;
        try {
            File file = new File(name);
            FileInputStream stream = new FileInputStream(file.getPath());
            Reader in = new BufferedReader(new InputStreamReader(stream));
            char[] readBuffer = new char[2048];
            StringBuffer buffer = new StringBuffer((int) file.length());
            int n;
            while ((n = in.read(readBuffer)) > 0) {
                buffer.append(readBuffer, 0, n);
            }
            textString = buffer.toString();
            stream.close();
        } catch (FileNotFoundException e) {//如果读文件出错，将错误信息显示到状态栏
            MainWindow.getMainWindow().getStatusLineManager().setMessage("文件未找到:" + fileName);
            return;
        } catch (IOException e) {
            MainWindow.getMainWindow().getStatusLineManager().setMessage("读文件出错:" + fileName);
            return;
        }
        content = textString;  // 将文件的字符保存到content中
        this.fileName = name;
    }

    /**
     * 保存一个文件
     *
     * @param name 文件名
     */
    public void save(String name) {
        final String textString = content;
        try {
            File file = new File(name);
            FileOutputStream stream = new FileOutputStream(file.getPath());
            Writer out = new OutputStreamWriter(stream);
            out.write(textString);
            out.flush();
            stream.close();
        } catch (FileNotFoundException e) {
            MainWindow.getMainWindow().getStatusLineManager().setMessage("文件未找到:" + fileName);
            return;
        } catch (IOException e) {
            MainWindow.getMainWindow().getStatusLineManager().setMessage("保存文件出错:" + fileName);
            return;
        }
    }

    /**
     * @return 返回 content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content 要设置的 content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return 修改过为true，否则false
     */
    public boolean isModified() {
        return isModified;
    }

    /**
     * @param modified 设置Modified
     */
    public void setModified(boolean modified) {
        this.isModified = modified;
    }

    /**
     * @return 返回 fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName 要设置的fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
