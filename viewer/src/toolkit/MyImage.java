package toolkit;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.io.File;

public class MyImage {
//    public static int i = 0;

    /**
     * 获取文件的系统图标。
     * 使用swing的getSystemIcon()方法获取icon，再将icon转换为swt的image。
     *
     * @param display
     * @param file    待获取图标的文件
     * @return 文件的系统图标
     */
    public static Image getSystemIconFromSwing(Display display, File file) {
        // 得到文件图标
        ImageIcon imageIcon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(file);

        if (imageIcon.getImage() instanceof BufferedImage) {
            //TODO 获取的系统图标显示不全,好像本身图片这样
            BufferedImage bufferedImage = (BufferedImage) imageIcon.getImage();
//            // 保存图片的方式
//            try {
//                ImageIO.write(bufferedImage, "png", new File("myImage"+i+".jpg"));
//                i++;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            DirectColorModel colorModel = (DirectColorModel) bufferedImage.getColorModel();
            PaletteData palette = new PaletteData(colorModel.getRedMask(), colorModel.getGreenMask(), colorModel.getBlueMask());
            ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(), colorModel.getPixelSize(), palette);
            //设置每个像素点的颜色与Alpha值
            for (int y = 0; y < data.height; y++) {
                for (int x = 0; x < data.width; x++) {
                    int rgb = bufferedImage.getRGB(x, y);
                    int pixel = palette.getPixel(new RGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF));
                    data.setPixel(x, y, pixel);
                    if (colorModel.hasAlpha()) {
                        data.setAlpha(x, y, (rgb >> 24) & 0xFF);
                    }
                }
            }
            // 生成Image对象
            Image swtImage = new Image(display, data);
            return swtImage;
        } else return null;
    }
}
