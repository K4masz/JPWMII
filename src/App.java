import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

public class App {

    private JButton loadPicture;
    private JLabel pictureContainer;
    private Image image;
    private JPanel mainPanel;
    private final JFileChooser fileChooser;


    public App() {
        init();

        UIManager.put("FileChooser.cancelButtonText", "Anuluj");
        UIManager.put("FileChooser.openButtonText", "Otwórz");
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Grafika JPG", "jpg"));

        JFrame frame = new JFrame("Tomasz Pawlik");
        frame.setSize(200,200);
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500, 500);
        frame.setVisible(true);
    }

    private void init() {

        loadPicture.addActionListener(e -> loadPicture());
        pictureContainer.addComponentListener(recalculateImageSize());
    }

    private void loadPicture() {

        int returnVal = fileChooser.showOpenDialog(fileChooser);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            ImageIcon image = extractImageFromFile(fileChooser.getSelectedFile());
            pictureContainer.setIcon(image);
        }
    }

    private ImageIcon extractImageFromFile(File selectedFile) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        image = toolkit.getImage(selectedFile.toString());
        pictureContainer.setText("");

        return new ImageIcon(image.getScaledInstance(pictureContainer.getWidth(), pictureContainer.getHeight(), Image.SCALE_SMOOTH));
    }


    private ComponentAdapter recalculateImageSize() {
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (image != null)
                    pictureContainer.setIcon(new ImageIcon(image.getScaledInstance(pictureContainer.getWidth(), pictureContainer.getHeight(), Image.SCALE_SMOOTH)));
            }

        };
    }
}