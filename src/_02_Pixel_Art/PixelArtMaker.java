package _02_Pixel_Art;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;

public class PixelArtMaker implements MouseListener, ActionListener {
	private JFrame window;
	private GridInputPanel gip;
	private GridPanel gp;
	ColorSelectionPanel csp;
	private static final String DATA_FILE = "src/_02_Pixel_Art/saved.dat";
	JButton button;
	JButton loadButton;
	
	public GridPanel load() {
		GridPanel grid = null;
		try (FileInputStream fos = new FileInputStream(new File(DATA_FILE));
				ObjectInputStream oos = new ObjectInputStream(fos)) {
			grid = (GridPanel) oos.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {}
		return grid;
		
	}
	
	private static void save(GridPanel data) {
		try (FileOutputStream fos = new FileOutputStream(new File(DATA_FILE)); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(data);
			System.out.println(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		gip = new GridInputPanel(this);	
		window = new JFrame("Pixel Art");
		window.setLayout(new FlowLayout());
		window.setResizable(false);
		button = new JButton();
		loadButton = new JButton();
		button.addActionListener(this);
		loadButton.addActionListener(this);
		button.setText("Save Image");
		loadButton.setText("Load");
		
		window.add(button);
		window.add(loadButton);
		window.add(gip);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	public void submitGridData(int w, int h, int r, int c) {
		gp = new GridPanel(w, h, r, c);
		if (new File(DATA_FILE).exists()) {
			gp=load();
		}
		csp = new ColorSelectionPanel();
		window.remove(gip);
		window.add(gp);
		window.add(csp);
		gp.repaint();
		gp.addMouseListener(this);
		window.pack();
	}
	
	public static void main(String[] args) {
		new PixelArtMaker().start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gp.setColor(csp.getSelectedColor());
		System.out.println(csp.getSelectedColor());
		gp.clickPixel(e.getX(), e.getY());
		gp.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("hi");
		if ((JButton)e.getSource() == this.loadButton) {
			gp = load();
			System.out.println("load");
		} else if ((JButton)e.getSource() == this.button) {
			System.out.println("save");
			save(gp);
		}
		
		gp.repaint();
	}
}
