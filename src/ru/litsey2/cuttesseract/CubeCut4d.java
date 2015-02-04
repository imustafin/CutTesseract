package ru.litsey2.cuttesseract;

import javax.swing.SwingUtilities;

/**
 * A main class
 * 
 * @author Ilgiz Mustafin
 * @version 0.3
 */
public class CubeCut4d {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new MainFrame("CutTesseract v0.3-noRotateFix");
			}
		});
		
	}
}
