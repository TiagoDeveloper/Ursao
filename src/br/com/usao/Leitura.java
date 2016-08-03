package br.com.usao;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Leitura extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9129231997307213832L;
	
	
	
	private FileReader reader;
	private BufferedReader bReader;
	
	private FileWriter writer;
	private BufferedWriter bWriter;
	
	private JLabel label1 = new JLabel("Entrada:");
	private JLabel label2 = new JLabel("Saida:");
	
	private JTextField entrada = new JTextField(30);
	private JTextField saida = new JTextField(30);
	
	private JButton bEntrada = new JButton("Entrada");
	private JButton bSaida = new JButton("Saída");
	
	private JFileChooser chooser = new JFileChooser();
	
	private JButton gerar = new JButton("Gerar");
	
	private String nomeSaida;
	
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public void janela(){
	
		this.setLayout(new GridBagLayout());
		
		
		this.gbc.insets = new Insets(10, 10 , 10, 10);
		
		this.gbc.gridx = 0;
		this.gbc.gridy = 0;
		this.add(label1,this.gbc);
		
		this.gbc.gridx = 1;
		this.gbc.gridy = 0;
		this.entrada.setEditable(false);		
		this.add(entrada,this.gbc);
		
		this.gbc.gridx = 2;
		this.gbc.gridy = 0;
		this.add(bEntrada,this.gbc);
		bEntrada.addActionListener(this);
		
		
		this.gbc.gridx = 0;
		this.gbc.gridy = 1;
		this.add(label2,this.gbc);
		
		this.gbc.gridx = 1;
		this.gbc.gridy = 1;
		this.saida.setEditable(false);	
		this.add(saida,this.gbc);
		
		this.gbc.gridx = 2;
		this.gbc.gridy = 1;
		this.bSaida.setEnabled(false);
		this.add(bSaida,this.gbc);
		bSaida.addActionListener(this);
		
		this.gbc.gridx = 1;
		this.gbc.gridy = 2;
		this.gerar.setEnabled(false);
		this.add(gerar,this.gbc);
		gerar.addActionListener(this);
		
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
	}
	
	
	public Leitura() throws IOException {
		
		janela();
		
		
	
	
	}
	
	
	public void criar() throws IOException{
		
		this.reader = new FileReader(this.entrada.getText());
		this.writer = new FileWriter(this.saida.getText()+this.nomeSaida+"[COPIA].dat");
		
		
		this.bReader = new BufferedReader(reader);
		this.bWriter = new BufferedWriter(writer);
		
		String linha = null;
		
		
		while((linha=this.bReader.readLine()) != null){
			
			if(linha.contains("cloneof")){
				
				do{
					

					if(linha.equals("</datafile>")){
						break;
					}
				
				}while(!((linha=this.bReader.readLine()).equals("</game>")));
					
					
				
				
				linha=this.bReader.readLine();
			}else{
				
				this.bWriter.append(linha);
				this.bWriter.append("\n");
				this.bWriter.flush();
				
			}
			
			
			
		}
		
		this.bWriter.append("</datafile>");
		this.bWriter.flush();
		
		this.bReader.close();
		this.reader.close();
		this.bWriter.close();
		this.writer.close();
	
	
		
	}
	
	
	
	
	public static void main(String[] args) throws IOException {
		new Leitura();
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == this.gerar){
			
			try {
				criar();
			} catch (IOException e1) {				
				e1.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(this, "Gerado!!!");
			
			
		}else if(e.getSource() == this.bEntrada){
			
			
			escolhaArq(this.entrada);
			
			if(!this.entrada.equals("")){
				
				this.bSaida.setEnabled(true);
			}
			
		}else if(e.getSource() == this.bSaida){
			
			escolhaSaida(this.saida);
			
			
			if(!this.saida.equals("")){
				
				this.gerar.setEnabled(true);
			}
		}
				
		
		
	}
	
	public void escolhaArq(JTextField field){
		
		this.chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		int returnValue = this.chooser.showOpenDialog(this);
		
		if(returnValue == JFileChooser.APPROVE_OPTION){
			
			field.setText(this.chooser.getSelectedFile().getAbsolutePath());
			this.nomeSaida = this.chooser.getSelectedFile().getName();
		}
		
	}
	
	public void escolhaSaida(JTextField field){
		
		
		this.chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int returnValue = this.chooser.showOpenDialog(this);
		
		if(returnValue == JFileChooser.APPROVE_OPTION){
			
			field.setText(this.chooser.getSelectedFile().getAbsolutePath()+"\\");
		}
		
	}
	

}
