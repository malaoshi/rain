import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class CodeRainPanel extends JPanel {
    //12号字的上下左右间距是10
    static final int FONT_SIZE=36;
    //36号字，间距为40
    static final int SPACE=40;
    //休眠时间
    static final int SLEEP_TIME=150;

    private String[][] charset; //随机字符集合
    private int[] pos; //列的起始位置
    private Color[] colors = new Color[10]; //列的渐变颜色
    private List<String> lines;

    public CodeRainPanel(List<String> lines) throws IOException {
        this.lines=lines;
    }
    private void random(String[] charset){
        Random rand = new Random();
        int lineNum=lines.size();

        int length = 0;
        int destPos=0;
        while (true) {
            int index = rand.nextInt(lineNum);
            String line = lines.get(index);

            String[] lineArray = line.split("");
            boolean breakFlag=true;
            if (lineArray.length+destPos >= charset.length) {
                length = charset.length-destPos;
                breakFlag=true;
            } else {
                length = lineArray.length;
                breakFlag=false;
            }

            System.arraycopy(lineArray, 0, charset, destPos, length);
            destPos+=length;

            if(breakFlag){
                break;
            }
        }
    }
    public void init() {
        int width = getWidth();
        int height = getHeight();
        //生成ASCII可见字符集合
        Random rand = new Random();
        charset = new String[width / SPACE][height/SPACE];
        for (int i = 0; i < charset.length; i++) {
            random(charset[i]);
        }

        //随机化列起始位置
        pos = new int[charset.length];
        for (int i = 0; i < pos.length; i++) {
            pos[i] = rand.nextInt(1000);
        }

        //生成从黑色到绿色的渐变颜色
        for (int i = 0; i < colors.length - 1; i++) {
            colors[i] = new Color(0, 255 / colors.length * (i + 1), 0);
        }

        setBackground(Color.BLACK);
    }

    public void start() {
        init();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        repaint(); // 重绘，调用paint()方法
                        Thread.sleep(SLEEP_TIME); //可改变睡眠时间以调节速度
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }
        }).start();

//        addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                System.exit(0);
//            }
//        });
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });
    }


    @Override
    public void paint(Graphics g) {
        //必须设置，否则setBackground(Color.BLACK);设置背景色无效
        super.paint(g);
        //设置字体
        g.setFont(new Font("华文行楷", Font.PLAIN, FONT_SIZE));
        for (int i = 0; i < charset.length; i++) {
            for (int j = 0; j < colors.length; j++) {
                int index = (pos[i] + j) % charset[i].length;
                g.setColor(colors[j]);
                g.drawString(charset[i][index], i * SPACE, (index +1)* SPACE);

            }
            pos[i] = (pos[i] + 1) % charset[i].length;
//            if(pos[i]==0){
//                random(charset[i]);
//            }
        }
    }


}
