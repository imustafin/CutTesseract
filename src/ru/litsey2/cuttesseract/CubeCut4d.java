package ru.litsey2.cuttesseract;

import javax.swing.SwingUtilities;

/**
 * A main class, creates a {@link MainFrame}
 * 
 * @author Ilgiz Mustafin
 */
public class CubeCut4d {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new MainFrame("CutTesseract v0.4.2");
			}
		});
		
	}
}
