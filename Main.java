import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static java.util.List<String> read(String file, String charset) throws IOException {
        List list = new ArrayList();
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, charset);
        BufferedReader br = new BufferedReader(isr);
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            list.add(line);
        }
        br.close();
        isr.close();
        fis.close();
        return list;
    }

    public static void showFrame(java.util.List<String> lines){
        //        获取屏幕大小
        Dimension screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screensize.getWidth();
        int height = (int)screensize.getHeight();
//        int width = 640;
//        int height = 480;

        JFrame frame = new JFrame();
        CodeRainPanel panel = null; // 面板对象
        try {
            panel = new CodeRainPanel(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.add(panel); // 将面板添加到JFrame中
        frame.setSize(width,height); // 设置大小
        frame.setResizable(false);
        frame.setUndecorated(true);//不显示标题
//        frame.setAlwaysOnTop(true); // 设置其总在最上
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 默认关闭操作
//        frame.setLocationRelativeTo(null); // 设置窗体初始位置
        frame.setVisible(true); // 尽快调用paint
        panel.start(); // 启动执行
        panel.requestFocus(); // 注意,与上面的这一句不能反
    }
    public static void showErrorFrame(){
        Font font=new Font("宋体",Font.PLAIN,16);
        JFrame frame = new JFrame();
        JLabel label=new JLabel("读取文件失败！",JLabel.CENTER);
        label.setFont(font);
        frame.add(label);

        frame.setSize(300,200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        String file="data.txt";
        if(args!=null && args.length>0){
            file=args[0];
        }
        java.util.List<String> lines=null;
        try {
            lines=read(file,"gbk");
            showFrame(lines);
        } catch (IOException e) {
//            e.printStackTrace();
            showErrorFrame();
        }

    }
}
