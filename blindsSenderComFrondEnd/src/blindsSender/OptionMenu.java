package blindsSender;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

import blindsSenderBackEnd.Controller;

@SuppressWarnings("serial")
public class OptionMenu extends JFrame{
	private Controller controller;
	private JComboBox<String> dropDown;
	private JSpinner timeSpinner;
	
	public OptionMenu(final Controller controller) {
		this.controller = controller;
		this.setTitle("Lazy blinds");
		
		
		add(createMainPanel());
		pack();
		addWindowListener(new java.awt.event.WindowAdapter() {
	        @Override
	        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	            controller.close();
	        }
		});
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void UpdateFrame() {
		this.revalidate();
		this.repaint();
	}
	
	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());		
		mainPanel.add(topText(), BorderLayout.PAGE_START);
		mainPanel.add(createCenterPanel(),BorderLayout.CENTER);
		mainPanel.add(new TimedCommandPanel(controller.getTcc(),this),BorderLayout.EAST);
		return mainPanel;
	}
	
	private JPanel createCenterPanel() {
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(createButtonPanel(), BorderLayout.PAGE_START);
		centerPanel.add(createTimerPanel(), BorderLayout.CENTER);
		JButton closeButton = new JButton(close);
		closeButton.setPreferredSize(new Dimension(150,40));
		centerPanel.add(closeButton, BorderLayout.PAGE_END);
		return centerPanel;
	}
	
	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new GridLayout(4,3));
		buttonPanel.add(new JLabel("Left", SwingConstants.CENTER));
		buttonPanel.add(new JLabel("Both", SwingConstants.CENTER));
		buttonPanel.add(new JLabel("Right", SwingConstants.CENTER));
		buttonPanel.add(createButton(lUp,100,40));
		buttonPanel.add(createButton(bUp,100,40));
		buttonPanel.add(createButton(rUp,100,40));
		buttonPanel.add(createButton(lStop,100,40));
		buttonPanel.add(createButton(bStop,100,40));
		buttonPanel.add(createButton(rStop,100,40));
		buttonPanel.add(createButton(lDown,100,40));
		buttonPanel.add(createButton(bDown,100,40));
		buttonPanel.add(createButton(rDown,100,40));
		
		return buttonPanel;
	}
	
	private JPanel createTimerPanel() {
		JPanel timerPanel = new JPanel(new FlowLayout(SwingConstants.CENTER));
		
		String[] sides = {"Both", "Left", "Right"};
		dropDown = new JComboBox<String>(sides);
		dropDown.setEnabled(controller.connection());
		
		timeSpinner = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "yyyy MM-dd HH:mm");
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setValue(new Date());
		timeSpinner.setEnabled(controller.connection());
		
		JPanel smallPanel = new JPanel(new GridLayout(2,1));
		smallPanel.add(createButton(tUp,100,20));
		smallPanel.add(createButton(tDown,100,20));
		
		timerPanel.add(dropDown);
		timerPanel.add(timeSpinner);
		timerPanel.add(smallPanel);
		
		return timerPanel;
	}
	
	private JButton createButton(AbstractAction a, int widht, int height){
		JButton button = new JButton(a);
		button.setPreferredSize(new Dimension(widht,height));
		button.setEnabled(controller.connection());
		return button;
	}
    
    AbstractAction lUp= new AbstractAction("Up") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.lUp();
		}
    };
    
    AbstractAction lStop= new AbstractAction("Stop") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.lStop();
		}
    };
    
    AbstractAction lDown= new AbstractAction("Down") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.lDown();
		}
    };
    
    AbstractAction bUp= new AbstractAction("Up") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.bUp();
		}
    };
    
    AbstractAction bStop= new AbstractAction("Stop") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.bStop();
		}
    };
    
    AbstractAction bDown= new AbstractAction("Down") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.bDown();
		}
    };
    
    AbstractAction rUp= new AbstractAction("Up") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.rUp();
		}
    };
    
    AbstractAction rStop= new AbstractAction("Stop") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.rStop();
		}
    };
    
    AbstractAction rDown= new AbstractAction("Down") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.rDown();
		}
    };
    
    AbstractAction tUp = new AbstractAction("Up") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.tUp(dropDown.getSelectedItem().toString(), (Date) timeSpinner.getValue());
		}
    };
    
    AbstractAction tDown = new AbstractAction("Down") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.tDown(dropDown.getSelectedItem().toString(), (Date) timeSpinner.getValue());
		}
    };
    
    AbstractAction close = new AbstractAction("Close") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.close();
		}
    };
    
    public JLabel topText() {
    	JLabel topText;
    	if (controller.connection()) {
	    	topText = new JLabel("Be in total control", SwingConstants.CENTER);
			topText.setFont(new Font("Verdana", Font.BOLD, 24));
    	} else {
    		topText = new JLabel("No Connection", SwingConstants.CENTER);
			topText.setFont(new Font("Verdana", Font.BOLD, 24));
			topText.setForeground(Color.RED);
    	}
		return topText;
    }
    
}
