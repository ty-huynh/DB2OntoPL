package dh.protege41.db2onto.tab.ui.util.dialog;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dh.protege41.db2onto.tab.ui.util.panel.PanelUtil;

public class DialogUtility {
	
	private static JDialog _dialog;
	private static JComponent _content;
	
	public static JDialog getDialog () {
		return _dialog;
	}

	public static JComponent getContentComponent() {
		return _content;
	}

	public static int showConfirmDialog(Component parent, String title, 
			JComponent content, int messageType,
			int optionType, final JComponent defaultFocusedComponent, boolean modal) {
		
		_content = content;
		JOptionPane optionPane = new JOptionPane(content, messageType, optionType);
		_dialog = createDialog(parent, title, optionPane, defaultFocusedComponent);
		_dialog.setVisible(true);
		_dialog.setModal(modal);
		return getReturnValue(optionPane);
	}

	public static int showConfirmDialog(Component parent, String title, 
			JComponent content, int messageType,
			int optionType, final JComponent defaultFocusedComponent,
			boolean modal, Object[] options, Object defaultOption) {
		
		JOptionPane optionPane = new JOptionPane(content, messageType, optionType, null, options, defaultOption);
		_dialog = createDialog(parent, title, optionPane, defaultFocusedComponent);
		_dialog.setVisible(true);
		_dialog.setModal(modal);
		return getReturnValue(optionPane);
	}

	private static JDialog createDialog(Component parent, String title, JOptionPane optionPane, final JComponent defaultFocusedComponent) {
		JDialog dlg = optionPane.createDialog(parent, title);
		dlg.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				if (defaultFocusedComponent != null) {
				defaultFocusedComponent.requestFocusInWindow();
				}
			}
		});
		dlg.setLocationRelativeTo(parent);
		dlg.setResizable(true);
		dlg.pack();
		dlg.setMaximumSize(new Dimension(800, 600));
		return dlg;
	}
	
	public static void showMessages(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	public static void showInfos(String message, String title) {
		JTextArea ta = new JTextArea(message, 10, 50);
		ta.setEditable(false);
		showConfirmDialog(null, title, (JComponent) PanelUtil.createScroll(ta), JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, true);
	}
	
	public static void showError(String error) {
		JTextArea ta = new JTextArea(error, 10, 50);
		ta.setEditable(false);
		showConfirmDialog(null, "Errors has ocurred", (JComponent) PanelUtil.createScroll(ta), JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, true);
	}
	
	private static int getReturnValue(JOptionPane optionPane) {
		Object value = optionPane.getValue();
		if (value != null && optionPane.getOptions() != null){
			value = Arrays.binarySearch(optionPane.getOptions(), value);
		}
		return (value != null) ? (Integer) value : JOptionPane.CLOSED_OPTION;
	}
}
