import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class StockGUI extends JFrame {
	
	JPanel stockListPanel1;
	JPanel strategyPanel2;
	JPanel stockDisplayPanel3;
	JTable stockTable;
	ExcelTest excelData = new ExcelTest();	
	JScrollPane displayScrollPane;
	JScrollPane stockScrollPane;
	
	
	
	StockGUI(){		
		init();		
	}
	
	public void init(){
		
		stockListPanel1 = new JPanel();
		strategyPanel2 = new JPanel();
		stockDisplayPanel3 = new JPanel();
		    
		//stockTable = new JTable(excelData.getStockList(), excelData.getColumnNames());
		displayScrollPane = new JScrollPane(stockTable);
		stockScrollPane = new JScrollPane(stockListPanel1);
		
		
		
		stockListPanel1.setBorder(BorderFactory.createTitledBorder(null, "Stock List Panel", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));		
		strategyPanel2.setBorder(BorderFactory.createTitledBorder(null, "Strategy Panel", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
		stockDisplayPanel3.setBorder(BorderFactory.createTitledBorder(null, "Stock Display Panel", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)		
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)				
				.addComponent(stockDisplayPanel3, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)	
				.addGroup(layout.createSequentialGroup()
					.addComponent(stockListPanel1, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
						.addComponent(strategyPanel2, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
						
			)));
		
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)	
			.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(stockListPanel1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)	
				.addComponent(strategyPanel2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
			
				.addComponent(stockDisplayPanel3, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
				));
			
		//stockDisplayPanel.setSize(400,200);		
		//strategyPanel.setSize(200,200);
		//stockListPanel.setSize(200,200);
		//setSize(1200,600);
		pack();
		
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
