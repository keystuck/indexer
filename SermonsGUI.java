//SermonIndexingGUI
//begun 11/28/15

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

class SermonsGUI extends JFrame {
	private JTextArea area = new JTextArea(20, 120);	
	private JFileChooser bookTitle = new JFileChooser(System.getProperty("user.dir"));
	private String currentFile = "untitled";
	private boolean changed = false;

	public SermonsGUI(){
		area.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.CENTER);
		JMenuBar JMB = new JMenuBar();
		setJMenuBar(JMB);

		JMenu file = new JMenu("File");
		JMB.add(file);

		file.add(New);
		file.add(Open);
		file.add(Save);
		file.add(Quit);
		file.addSeparator();

		for (int i = 0; i < 4; i++)
			file.getItem(i).setIcon(null);

		JToolBar tool = new JToolBar();
		add(tool, BorderLayout.NORTH);
		tool.add(New);
		tool.add(Open);
		tool.add(Save);
		tool.addSeparator();

		Save.setEnabled(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		area.addKeyListener(k1);
		setTitle(currentFile);
		setVisible(true);
	}

	private KeyListener k1 = new KeyAdapter() 	{
		public void keyPressed(KeyEvent e){
			changed = true;
			Save.setEnabled(true);
		}
	};

	Action Open = new AbstractAction("Open"){
		public void actionPerformed(ActionEvent e){
			saveOld();
			if (bookTitle.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				readInFile(bookTitle.getSelectedFile().getAbsolutePath());
			}
		}
	};

	Action New = new AbstractAction("New"){
		public void actionPerformed(ActionEvent e){
			saveOld();
			area.setText("");
			currentFile = "unnamed";
			setTitle(currentFile);
			changed = false;
			Save.setEnabled(false);
		}
	};

	Action Save = new AbstractAction("Save"){
		public void actionPerformed(ActionEvent e){
			if (!currentFile.equals("Untitled"))
				saveFile(currentFile);
			else 
				saveFileAs();
		}
	};

	Action Quit = new AbstractAction("Quit"){
		public void actionPerformed(ActionEvent e){
			saveOld();
			System.exit(0);
		}
	};	

	ActionMap m = area.getActionMap();
	
	private void saveFileAs(){
		if (bookTitle.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			saveFile(bookTitle.getSelectedFile().getAbsolutePath());
	}

	private void saveOld(){
		if (changed){
			if (JOptionPane.showConfirmDialog(this, "Would you like to save " + currentFile + "?", "Save", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
				saveFile(currentFile);
		}
	}

	private void readInFile(String fileName){
		try {
			FileReader r = new FileReader(fileName);
			area.read(r, null);
			r.close();
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
		}
		catch(IOException e){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "Editor can't find the file called " + fileName);
		}
	}

	private void saveFile(String fileName){
		try {
			FileWriter w = new FileWriter(fileName);
			area.write(w);
			w.close();
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
			Save.setEnabled(false);
		}
		catch (IOException e){
		}

	}


}