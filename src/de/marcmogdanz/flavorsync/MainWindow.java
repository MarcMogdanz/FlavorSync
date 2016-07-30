package de.marcmogdanz.flavorsync;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextArea;
import com.alee.utils.FileUtils;
import com.alee.utils.swing.DialogOptions;

public class MainWindow {
	
	private FlavorSync sync;
	private JFrame frame;
	public WebTextArea textArea;
	private WebButton btnNewButton;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				WebLookAndFeel.install();
			}
		});
	}

	public MainWindow() {
		initialize();
		
		sync = new FlavorSync(this);
	}

	private void initialize() {
		frame = new JFrame("FlavorSync v1.0");
		frame.setBounds(100, 100, 400, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textArea = new WebTextArea();
		textArea.setEditable(false);
		textArea.setBounds(99, 209, 172, 257);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		WebScrollPane scroll = new WebScrollPane(textArea);
		scroll.setSize(372, 510);
		scroll.setLocation(10, 52);
		scroll.setPreferredSize(new Dimension(200, 150));
		frame.getContentPane().add(scroll);
		
		WebButton directory = new WebButton("Choose LoL directory...");
		directory.setBounds(10, 11, 271, 30);
		directory.addActionListener(new ActionListener() {
			private WebDirectoryChooser directoryChooser = null;
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				if(directoryChooser == null) {
					directoryChooser = new WebDirectoryChooser(frame.getOwner());
				}
				directoryChooser.setVisible(true);
				if(directoryChooser.getResult() == DialogOptions.OK_OPTION) {
					File file = directoryChooser.getSelectedDirectory();
					directory.setIcon(FileUtils.getFileIcon(file));
					directory.setText(FileUtils.getDisplayFileName(file));
					sync.setLoLPath(file.getAbsolutePath() + "/Config/Champions");
				}
			}
		});
		frame.getContentPane().add(directory);
		
		btnNewButton = new WebButton();
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(sync.getLoLPath() != null) {
					try {
						sync.startDownload();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					WebOptionPane.showMessageDialog(frame.getOwner(), "Choose a directory first.", "Error", WebOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setText("Download");
		btnNewButton.setBounds(291, 11, 90, 30);
		frame.getContentPane().add(btnNewButton);
		
	}
	
}
