package dh.protege41.db2onto.tab.ui.util;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComponent;

public class FontUtility {

	public static final int SIZE_TINY = 10;
	public static final int SIZE_SMALL = 12;
	public static final int SIZE_MEDIUM = 14;
	public static final int SIZE_LARGE = 16;
	public static final int SIZE_EXTREME = 20;

	public static JComponent setFontForLabel(JComponent c, int style, int size, Color color) {
		Font newFont = new Font(c.getFont().getFontName(), style, size);
		c.setFont(newFont);
		return setColorForLabel(c, color);
	}
	
	public static JComponent setFontForLabel(JComponent c, int style, int size) {
		return setFontForLabel(c, style, size, Color.BLACK);
	}
	
	public static JComponent setTinyLabel(JComponent c, int style) {
		return setFontForLabel(c, style, SIZE_TINY);
	}
	
	public static JComponent setSmallLabel(JComponent c, int style) {
		return setFontForLabel(c, style, SIZE_SMALL);
	}
	
	public static JComponent setMediumLabel(JComponent c, int style) {
		return setFontForLabel(c, style, SIZE_MEDIUM);
	}
	
	public static JComponent setLargeLabel(JComponent c, int style) {
		return setFontForLabel(c, style, SIZE_LARGE);
	}
	
	public static JComponent setExtremeLabel(JComponent c, int style) {
		return setFontForLabel(c, style, SIZE_EXTREME);
	}
	
	public static JComponent setColorForLabel(JComponent c, Color color) {
		c.setForeground(color);
		return c;
	}
}
