package File_Zip_Unzip;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main extends JFrame implements ActionListener {
    // Definition of global values and items that are part of the GUI.

    static public File opened_file, other_file;
    static long past, future;
    static JLabel redLabel, blueLabel, redScore, blueScore;
    static JPanel buttonPanel, titlePanel, scorePanel;
    static JButton ZH, UH, EX;

    public JPanel createContentPane() {
	// We create a bottom JPanel to place everything on.
	JPanel totalGUI = new JPanel();
	totalGUI.setLayout(null);

	titlePanel = new JPanel();
	titlePanel.setLayout(null);
	titlePanel.setLocation(90, 20);
	titlePanel.setSize(170, 70);
	totalGUI.add(titlePanel);

	redLabel = new JLabel("Selected File Size: ");
	redLabel.setLocation(43, 0);
	redLabel.setSize(150, 30);
	redLabel.setHorizontalAlignment(0);
	titlePanel.add(redLabel);

	blueLabel = new JLabel("After zip/unzip the file size: ");
	blueLabel.setLocation(10, 30);
	blueLabel.setSize(170, 30);
	blueLabel.setHorizontalAlignment(0);
	titlePanel.add(blueLabel);

	scorePanel = new JPanel();
	scorePanel.setLayout(null);
	scorePanel.setLocation(270, 20);
	scorePanel.setSize(120, 60);
	totalGUI.add(scorePanel);

	redScore = new JLabel("");
	redScore.setLocation(0, 0);
	redScore.setSize(100, 30);
	redScore.setHorizontalAlignment(0);
	scorePanel.add(redScore);

	blueScore = new JLabel("");
	blueScore.setLocation(0, 30);
	blueScore.setSize(100, 30);
	blueScore.setHorizontalAlignment(0);
	scorePanel.add(blueScore);

	buttonPanel = new JPanel();
	buttonPanel.setLayout(null);
        buttonPanel.setLocation(10, 130);
        buttonPanel.setSize(5200, 150);
	totalGUI.add(buttonPanel);

	ZH = new JButton(" ZIP ");
	ZH.setLocation(130, 0);
	ZH.setSize(120, 30);
	ZH.addActionListener(this);
	buttonPanel.add(ZH);

	UH = new JButton(" UNZIP ");
	UH.setLocation(260, 0);
	UH.setSize(120, 30);
	UH.addActionListener(this);
	buttonPanel.add(UH);

	EX = new JButton("EXIT");
	EX.setLocation(130, 70);
	EX.setSize(250, 30);
	EX.addActionListener(this);
	buttonPanel.add(EX);

	totalGUI.setOpaque(true);
	return totalGUI;
    }

    public void actionPerformed(ActionEvent e) {
        
	if (e.getSource() == ZH) {
            Hzipping.beginHzipping(opened_file.getPath());
            JOptionPane.showMessageDialog(null, "Zipping Finished...",
					"Status", JOptionPane.PLAIN_MESSAGE);
            redScore.setText(opened_file.length() + " Bytes");
            other_file = new File(opened_file.getPath() + ".zip");
            future = other_file.length();
            blueScore.setText(future + " Bytes");
	} else if (e.getSource() == UH) {
            Hunzipping.beginHunzipping(opened_file.getPath());
            JOptionPane.showMessageDialog(null,"UnZipping Finished...", 
                                        "Status",JOptionPane.PLAIN_MESSAGE);
            redScore.setText(opened_file.length() + " Bytes");
            String s = opened_file.getPath();
            s = s.substring(0, s.length() - 6);
            other_file = new File(s);
            future = other_file.length();
            blueScore.setText(future + " Bytes");
	} 
        else if (e.getSource() == EX) {
            System.exit(0);
	}
    }

    private static void createAndShowGUI() {

	// JFrame.setDefaultLookAndFeelDecorated(true);
	JFrame frame = new JFrame("File Compresser (using Huffman Algorithmn)");

	// Create and set up the content pane.
	Main demo = new Main();
	frame.setContentPane(demo.createContentPane());
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setBounds(350, 170, 550, 300);

	frame.setVisible(true);

	JMenu fileMenu = new JMenu("File");

	JMenuBar bar = new JMenuBar();
	frame.setJMenuBar(bar);
	bar.add(fileMenu);

	JMenuItem openItem = new JMenuItem("Open");
	fileMenu.add(openItem);
	openItem.addActionListener(

            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int result = fileChooser.showOpenDialog(null);
			opened_file = fileChooser.getSelectedFile();
			past = opened_file.length();
			redScore.setText(past + " Bytes");
			blueScore.setText("...");
                    }
            });

	JMenuItem exitItem = new JMenuItem("Exit");
	fileMenu.add(exitItem);
	exitItem.addActionListener(

	new ActionListener() {
            public void actionPerformed(ActionEvent event) {
		System.exit(0);
            }
	});

	JMenu helpMenu = new JMenu("Help");
	frame.setJMenuBar(bar);
	bar.add(helpMenu);

	JMenuItem helpItem = new JMenuItem("How To");
	helpMenu.add(helpItem);
	helpItem.addActionListener(

        new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                    JOptionPane.showMessageDialog(null,"Huff man algorithm is used for file compression" +"\n"
                                                    + "To compress a file first open the file then click" + "\n"
                                                    + "ZIP  to zip the file using Huffmans algo.A zipped" + "\n"
                                                    + "file with extension .zip will be created in the same" + "\n"
                                                    + "folder. This is the zipped file. During unzipping just open" + "\n"
                                                    + "this file and click UNZIP  button." + "\n",
                                                    "How To...", JOptionPane.PLAIN_MESSAGE);
            }
        });

	JMenuItem aboutItem = new JMenuItem("About");
	helpMenu.add(aboutItem);

	aboutItem.addActionListener(

        new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                    JOptionPane.showMessageDialog(null,"File Compresser is a software to zip and unzip files" + "\n"
						+ "It is developed on JAVA, as a term SCE project of" + "\n"
						+ " Year - 2, Sem - 4 ,Dept. of CSE,VIIT,Pune." + "\n",
						"About", JOptionPane.PLAIN_MESSAGE);
            }
	});

    }

    public static void main(String[] args) {
	// Schedule a job for the event-dispatching thread:
	// creating and showing this application's GUI.
	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
	});
    }
}