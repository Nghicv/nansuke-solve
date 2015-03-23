import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import java.awt.Color;
import javax.swing.JTextField;


public class HitoriDisplay extends JFrame {
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					HitoriDisplay frame = new HitoriDisplay();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public HitoriDisplay() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 371);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 114, 26);
		contentPane.add(menuBar);
		
		JMenu File = new JMenu("File");
		menuBar.add(File);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Open");
		File.add(mntmNewMenuItem);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		File.add(mntmSave);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		File.add(mntmClose);
		
		JMenu Edit = new JMenu("Edit");
		menuBar.add(Edit);
		
		JMenu Help = new JMenu("Help");
		menuBar.add(Help);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		Help.add(mntmAbout);
		
		JMenuItem mntmHitoriGame = new JMenuItem("Hitori Game");
		Help.add(mntmHitoriGame);
		
		
		Border border = BorderFactory.createLineBorder(Color.BLUE);
		JPanel panel = new JPanel();
		panel.setBounds(10, 33, 269, 242);
		contentPane.add(panel);
		panel.setBorder(border);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		textField_4 = new JTextField();
		panel.add(textField_4);
		textField_4.setColumns(10);
		
		textField_3 = new JTextField();
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		textField_5 = new JTextField();
		panel.add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		panel.add(textField_6);
		textField_6.setColumns(10);
		
		textField_7 = new JTextField();
		panel.add(textField_7);
		textField_7.setColumns(10);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(291, 33, 129, 242);
		contentPane.add(panel_1);
		
		JButton btnCreate = new JButton("Create");
		panel_1.add(btnCreate);
		
		JButton btnSolver = new JButton("Solver");
		panel_1.add(btnSolver);
		
		JButton btnNewButton = new JButton("Check");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel_1.add(btnNewButton);
	}
}
