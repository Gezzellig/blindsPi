package blindsSender;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import blindsSenderBackEnd.TimedCommandContainer;

public class TimedCommandPanel extends JPanel implements Observer {
	private static final long serialVersionUID = 6993721233747989121L;
	private TimedCommandContainer tcc;
	private OptionMenu om;

	public TimedCommandPanel(TimedCommandContainer tcc, OptionMenu  om) {
		tcc.addObserver(this);
		this.setPreferredSize(new Dimension(200,200));
		this.tcc = tcc;
		this.om = om;
		update(null,null);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.removeAll();
		for (String s : tcc.getCommands()) {
			this.add(setUpButton(s));
		}
		om.UpdateFrame();
	}
	
	private JButton setUpButton(String s) {
		JButton button = new JButton(createAction(s));
		button.setPreferredSize(new Dimension(175,40));
		return button;
	}

	private AbstractAction createAction(final String s) {
		String[] seperated = s.split(";");
		String[] dateParts = seperated[0].split("-");
		String[] commandParts = seperated[1].split(",");
		return new AbstractAction("<html><center><font size='2'>"+dateParts[2]+"-"+dateParts[1]+"-"+dateParts[0]+"</font><br /><font size='4'>"+dateParts[3]+":"+dateParts[4]+" "+selectSide(commandParts[0])+" "+commandParts[1]+"</font></center></html>") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tcc.remove(s);
			}
		};
	}
	
	private String selectSide(String s) {
		switch(s){
			case "b": return "Both";
			case "l": return "Left";
			case "r": return "Rigth";
		}
		return null;
	}
}
