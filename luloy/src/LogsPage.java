import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

public class LogsPage extends JPanel {

    public LogsPage() {
    	setLayout(new BorderLayout());
    	
    	JPanel itemPanel = new JPanel();
    	
    	itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.PAGE_AXIS));
    	add(itemPanel);
    	
    	Object[][] data = {};
    	String[] columnNames = {
    		"Time",
    		"Log Message"
    	};
    	
    	DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.getTableHeader().setEnabled(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);
    	add(scroll, BorderLayout.CENTER);
    	
    	database.logsDB.forEach(log -> {
    		JPanel logPanel = new JPanel();
    		
    		logPanel.setLayout(new GridLayout(1,0));
    		
    		JLabel timeLabel = new JLabel(log.logtime+"");
    		JLabel msgLabel = new JLabel(log.message);
    		
    		logPanel.add(timeLabel, BorderLayout.WEST);
    		logPanel.add(msgLabel, BorderLayout.CENTER);
    		
    		model.addRow(new Object[]{log.logtime, log.message});
    		
    		//itemPanel.add(logPanel);
    	});
    	
    	
    }
    
    
}