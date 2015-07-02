import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * une class pour ajouter un image en arriere fond
 * @author  Nicholas on 27/12/13.
 */
public class ScreenLayers {

    public static void main(String[] args) {
        new ScreenLayers();
    }

    public ScreenLayers() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }

                MapPane mapPane = new MapPane();
                try {
                    //mapPane.addLayer(new ImageLayer(ImageIO.read(new File("Ponie.png")), 360, 10));
                    //mapPane.addLayer(new ImageLayer(ImageIO.read(getClass().getResource( "/resources/background/q02th.jpg" )), 360, 10));

                    //mapPane.addLayer(new ImageLayer(ImageIO.read(new File("Layer01.png")), 0, 0));
                    mapPane.addLayer(new ImageLayer(ImageIO.read(getClass().getResource("/resources/cartes/c1.png")), 360, 10));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(mapPane);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class ImageLayer extends JComponent {

        private Image bg;
        private int xOffset;
        private int yOffset;

        public ImageLayer(Image image, int x, int y) {
            bg = image;
            xOffset = x;
            yOffset = y;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bg != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.drawImage(bg, xOffset, yOffset, this);
                g2d.dispose();
            }
        }

    }

    public class MapPane extends JLayeredPane {

        private BufferedImage bg;

        public MapPane() {
            try {
                //bg = ImageIO.read(new File("PirateMap.jpg"));
                bg = ImageIO.read(getClass().getResource("/resources/background/q02th.jpg"));
            } catch (IOException exp) {
                exp.printStackTrace();
            }
            setLayout(new GridBagLayout());
        }

        public void addLayer(JComponent layer) {

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.BOTH;

            add(layer, gbc);

        }

        @Override
        public Dimension getPreferredSize() {
            return bg == null ? new Dimension(200, 200) : new Dimension(bg.getWidth(), bg.getHeight());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bg != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                int x = (getWidth() - bg.getWidth()) / 2;
                int y = (getHeight() - bg.getHeight()) / 2;
                g2d.drawImage(bg, x, y, this);
                g2d.dispose();
            }
        }
    }
}
