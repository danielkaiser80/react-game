package gui;

import architectureinterface.*;
import framework.Implementation;
import framework.LocalClassLoader;
import framework.MixProperties;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * The <code>MixPanel</code> is a <code>JPanel</code> which contains all
 * information on a component of the mix.
 * 
 * @author Daniel Kaiser
 *
 */
public class MixPanel extends JPanel {

	/**
	 * <code>ActionListener</code> that is called, when a component is chosen in
	 * a file dialog. This method tests whether this component implements the
	 * correct interfaces.
	 * 
	 * @author Daniel Kaiser
	 *
	 */
	private class ChoiceListener implements ActionListener {

		public ChoiceListener() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Java Source Code", "java");
			chooser.setFileFilter(filter);

			chooser.setCurrentDirectory(new File("src/" + mixComponent.getPackageName()));

			int returnVal = chooser.showOpenDialog(upperPanel);
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				String binaryName = chooser.getSelectedFile().getName();
				binaryName = LocalClassLoader.normalizeBinaryClassName(binaryName);

				boolean success = true;

				try {
					Class<?> testClass = LocalClassLoader
							.loadClassFile(
									mixComponent.getPackageName() + "." + binaryName);

					setImplementation(testClass.newInstance());

					if (!(getImplementation() instanceof Implementation)) {
						success = false;
					} else {
						switch (mixComponent) {
						case EXTERNAL_INFORMATIONPORT:
							if (!(implementation instanceof ExternalInformationPortInterface)) {
								JOptionPane.showMessageDialog(null,
										binaryName + " must implement the "
												+ mixComponent.name() + "Interface!",
										"Error", JOptionPane.OK_CANCEL_OPTION);
								success = false;
							}
							break;
						case INPUT_OUTPUT_HANDLER:
							if (!(implementation instanceof InputOutputHandlerInterface)) {
								JOptionPane.showMessageDialog(null,
										binaryName + " must implement the "
												+ mixComponent.name() + "Interface!",
										"Error", JOptionPane.OK_CANCEL_OPTION);
								success = false;
							}
							break;
						case KEY_GENERATOR:
							if (!(implementation instanceof KeyGeneratorInterface)) {
								JOptionPane.showMessageDialog(null,
										binaryName + " must implement the "
												+ mixComponent.name() + "Interface!",
										"Error", JOptionPane.OK_CANCEL_OPTION);
								success = false;
							}
							break;
						case MESSAGE_PROCESSOR:
							if (!(implementation instanceof MessageProcessorInterface)) {
								JOptionPane.showMessageDialog(null,
										binaryName + " must implement the "
												+ mixComponent.name() + "Interface!",
										"Error", JOptionPane.OK_CANCEL_OPTION);
								success = false;
							}
							break;
						case NETWORK_CLOCK:
							if (!(implementation instanceof NetworkClockInterface)) {
								JOptionPane.showMessageDialog(null,
										binaryName + " must implement the "
												+ mixComponent.name() + "Interface!",
										"Error", JOptionPane.OK_CANCEL_OPTION);
								success = false;
							}
							break;
						case OUTPUT_STRATEGY:
							if (!(implementation instanceof OutputStrategyInterface)) {
								JOptionPane.showMessageDialog(null,
										binaryName + " must implement the "
												+ mixComponent.name() + "Interface!",
										"Error", JOptionPane.OK_CANCEL_OPTION);
								success = false;
							}
							break;
						case USER_DATABASE:
							if (!(implementation instanceof UserDatabaseInterface)) {
								JOptionPane.showMessageDialog(null,
										binaryName + " must implement the "
												+ mixComponent.name() + "Interface!",
										"Error", JOptionPane.OK_CANCEL_OPTION);
								success = false;
							}
							break;
						default:
							break;
						}
					}

				} catch (Exception e1) {
					success = false;
					e1.printStackTrace();
				}

				if (success) {

					chosenLabel.setText("Chosen implementation: " + binaryName);

					if (configureButton == null) {
						configureButton = new JButton();
						configureButton.setText("Configure");
						lowerPanel.add(configureButton);
					}

					// load properties to work with later
					if (mixProperties == null)
						mixProperties = new MixProperties();
					else
						mixProperties.clear();

					mixProperties.load(binaryName);

					// set implementation name
					mixComponent.setImplementationName(binaryName);
				}
			}
		}
	}

	private static final long serialVersionUID = 1642260334818260371L;

	MixComponent mixComponent;

	JLabel chosenLabel;
	private JLabel descriptionLabel;
	private JButton chooseButton;
	JButton configureButton;
	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();

	JPanel upperPanel = new JPanel(); // in left panel
	JPanel lowerPanel = new JPanel(); // in left panel
	transient Object implementation; // the instantiated mix component
	MixProperties mixProperties; // properties for every component

	private int isWorking = 0; // 0 for not working, +1 for every component with
								// that this component is working

	/**
	 * for every component of the mix, one <code>MixPanel</code> is added to the
	 * GUI
	 * 
	 * @param mixComponent
	 *            component name from the <code>enum MixComponent</code>.
	 */
	MixPanel(final MixComponent mixComponent) {
		this.mixComponent = mixComponent;
		this.setPreferredSize(new java.awt.Dimension(650, 90));
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		leftPanel.setPreferredSize(new java.awt.Dimension(550, 90));
		this.add(leftPanel, BorderLayout.EAST);

		upperPanel.setPreferredSize(new java.awt.Dimension(550, 45));
		leftPanel.add(upperPanel, BorderLayout.NORTH);

		descriptionLabel = new JLabel();
		upperPanel.add(descriptionLabel);
		descriptionLabel.setText("Choose " + mixComponent.name() + ":");
		chooseButton = new JButton();
		upperPanel.add(chooseButton);
		chooseButton.setText("Locate Class");
		chooseButton.addActionListener(new ChoiceListener());

		lowerPanel.setPreferredSize(new java.awt.Dimension(550, 45));
		leftPanel.add(lowerPanel, BorderLayout.SOUTH);

		chosenLabel = new JLabel();
		lowerPanel.add(chosenLabel);
		chosenLabel.setText("No " + mixComponent.name() + " chosen");

		rightPanel.setPreferredSize(new java.awt.Dimension(90, 90));
		this.add(rightPanel, BorderLayout.WEST);
		rightPanel.setBackground(Color.red);
	}

	/**
	 * getter for the current implementation
	 * 
	 * @return current implementation
	 */
	public Object getImplementation() {
		return implementation;
	}

	/**
	 * set the current implementation
	 * 
	 * @param newImplementation
	 *            the new implementation to be set
	 */
	public void setImplementation(Object newImplementation) {
		this.implementation = newImplementation;
	}

	/**
	 * get a property from the property file of the implementation that belongs
	 * to this panel
	 * 
	 * @param propertyName
	 *            name of the property
	 * @return value of the property
	 */
	public String getProperty(String propertyName) {
		return this.mixProperties.getProperty(propertyName);
	}

	/**
	 * returns the name of the component of the panel
	 * 
	 * @return the name of the component of the panel
	 */
	@Override
	public String getName() {
		return this.mixComponent.name();
	}

	/**
	 * returns the capitalized name of the component of the panel
	 * 
	 * @return the capitalized name of the component of the panel
	 */
	public String getCapitalizedName() {
		return this.mixComponent.capitalizedName();
	}

	/**
	 * returns the name of the implementation class loaded in this panel
	 * 
	 * @return name of the implementation class loaded in this panel
	 */
	public String getImplementationName() {
		return getImplementation().getClass().getName().substring(getName().length() + 1);
	}

	/**
	 * increase the number of working combinations with the implementation of
	 * this panel
	 */
	public void increaseWorkingNumber() {
		isWorking++;
	}

	/**
	 * returns the number of working implementations with this panel
	 * 
	 * @return number of working implementations with this panel
	 */
	public int getIsWorking() {
		return isWorking;
	}

	/**
	 * reset the number of working implementations with this panel
	 */
	public void resetWorkingNumber() {
		isWorking = 0;
	}

	/**
	 * change the color of the right panel ("traffic lights") to a new
	 * <code>color</code>
	 * 
	 * @param color
	 *            the new color for the right panel
	 */
	public void colorPanel(Color color) {
		this.rightPanel.setBackground(color);
	}
}