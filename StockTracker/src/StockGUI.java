import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.SwingUtilities;

public class StockGUI extends JFrame {
	JPanel stockList;
	JPanel strategyList;
	JPanel stockDisplay;
	
	ExcelTest excelData = new ExcelTest();	
	JScrollPane displayScrollPane;
	JScrollPane stockScrollPane;
	
	//Object[][] data = {{"Google", "12345", "12346", "1", "Hold"}, {"APPL", "23456", "23457", "1", "Hold"}};
	
	StockGUI(){		
		init();		
	}
	
	public void init(){
		
		stockList = new JPanel();
		strategyList = new JPanel();
		stockDisplay = new JPanel();
		
	    
	    
		JTable stockTable = new JTable(excelData.getStockList(), excelData.getColumnNames());
		displayScrollPane = new JScrollPane(stockTable);
		stockScrollPane = new JScrollPane(stockList);
		
		stockDisplay.add(displayScrollPane);
		
		
		/*

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(stockList)
				.addComponent(strategyList)
				.addComponent(stockDisplay));

		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(stockList)
				.addComponent(strategyList)
				.addComponent(stockDisplay));
		*/
		setSize(1000,600);

		
		add(strategyList);
		add(stockList);
		add(stockDisplay);
		stockList.setVisible(true);
		strategyList.setVisible(true);
		stockDisplay.setVisible(true);
		setVisible(true);
	
		//pack();
		
		
	}
	
	public static void main(String[] args) {
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new StockGUI().setVisible(true);
			}
		});
		

	}

}
