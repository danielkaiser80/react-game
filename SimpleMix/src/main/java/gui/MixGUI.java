package gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumSet;

/**
 * Graphical Front end and configuration utility for the mix framework
 *
 * @author Daniel Kaiser
 *
 */
public class MixGUI extends JFrame {
	
	/**
	 * <code>ActionListener</code> for loading an existing mix configuration
	 * @author Daniel Kaiser
	 *
	 */
	private class OpenListener implements ActionListener {
		public OpenListener() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}

	/**
	 * <code>ActionListener</code> for saving the mix configuration
	 * @author Daniel Kaiser
	 *
	 */
	private class SaveListener implements ActionListener {
		public SaveListener() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}

	/**
	 * <code>ActionListener</code> for the validation of a combination of
	 * several mix components
	 *
	 * @author Daniel Kaiser
	 *
	 */
	class ValidateListener implements ActionListener {

		/**
		 * This method checks whether a combination of several mix components is
		 * valid.
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			try {

				for (final MixPanel panel : panels) {
					System.out.println(panel.getImplementationName());
				}
			} catch (final NullPointerException ex) {
				JOptionPane.showMessageDialog(null,
						"Please choose an implementation for all of the components first!",
						"Error", JOptionPane.OK_CANCEL_OPTION);
				System.err.println(ex.getMessage());
			} finally {

				// all implementations are loaded and instantiated --> test
				// compatibility
				for (final MixPanel panel : panels) {
					panel.resetWorkingNumber();

					for (final MixComponent checkComponent : MixComponents) {

						// don't check the compatibility with the component
						// itself
						if (!checkComponent.capitalizedName()
								.equals(panel.getCapitalizedName())) {

							final String property = panel.getProperty(
									checkComponent.capitalizedName() + "_WITH_"
											+ panel.getCapitalizedName());

							if (property
									.contains(checkComponent.getImplementationName())) {
								panel.increaseWorkingNumber();

							} else
								System.out.println("The "
										+ checkComponent.capitalizedName() + "_WITH_"
										+ panel.getCapitalizedName() + " is " + property
										+ " and does not contain "
										+ checkComponent.getImplementationName() + "!");
						}
					}

					final int isWorking = panel.getIsWorking();

					switch (isWorking) {
					case 0: // red
						panel.colorPanel(Color.red);
						break;
					case 1:
					case 2:
					case 3:
					case 4:
					case 5: // yellow
						panel.colorPanel(Color.yellow);
						break;
					case 6: // green
						panel.colorPanel(Color.green);
						break;
					default: // error
						System.err.println("An error occured - wrong color selected.");
					}
				}
			}
		}
	}

	private static final long serialVersionUID = 1330463905820511574L;
	static final EnumSet<MixComponent> MixComponents = EnumSet.allOf(MixComponent.class);
	MixPanel[] panels = new MixPanel[MixComponents.size()];
	private final JPanel mainPanel = new JPanel();
	private final JPanel buttonPanel = new JPanel();
	private JButton openButton;
	private JButton validateButton;

	private JButton closeButton;

	/**
	 * Constructor, which set the look and feel, loads the <code>super()</code>
	 * constructor and initializes the GUI
	 */
	public MixGUI() {

		super();

		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.apple.laf.AquaLookAndFeel");
		} catch (final Exception e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			{
				// add the three buttons
				getContentPane().add(buttonPanel, BorderLayout.SOUTH);
				buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

				openButton = new JButton();
				buttonPanel.add(openButton);
				openButton.setText("Open Configuration");
				openButton.addActionListener(new OpenListener());

				// TODO enable button, when finished
				openButton.setEnabled(false);

				validateButton = new JButton();
				buttonPanel.add(validateButton);
				validateButton.setText("Validate Configuration");
				validateButton.addActionListener(new ValidateListener());

				closeButton = new JButton();
				buttonPanel.add(closeButton);
				closeButton.setText("Save Configuration");
				closeButton.addActionListener(new SaveListener());

				// TODO enable button, when finished
				closeButton.setEnabled(false);
			}
			{
				// add the main pane which shows all mix components
				getContentPane().add(mainPanel, BorderLayout.NORTH);
				mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
				mainPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

				int i = 0;

				for (final MixComponent mixComponent : MixComponents) {
					panels[i] = new MixPanel(mixComponent);
					mainPanel.add(panels[i]);
					i++;
				}
			}

			this.setSize(660, 700);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main method to display the Mix GUI
	 *
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			final MixGUI inst = new MixGUI();
			inst.setLocationRelativeTo(null);
			inst.setVisible(true);
		});
	}

}
