package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import utils.FormatoTabla;
import utils.Metodos;

public class Vencimiento extends javax.swing.JFrame {

	static FormatoTabla ft;

	static JLabel iconoVtoSeguro = new JLabel("");

	private static final long serialVersionUID = 1L;

	private JPanel jPanel1;

	private JScrollPane jScrollPane1;

	static JTable jTable1;

	private JTextField jTextField1;

	BufferedReader br;

	BufferedWriter bw;

	public static DefaultTableModel model;

	DefaultTableModel model2;

	String[][] dataStok;

	String path;

	File file;

	LinkedList<String> vencimientos = new LinkedList<String>();

	public static LinkedList<String> nombresWebcamersCaducidades = new LinkedList<String>();

	public static LinkedList<String> paginas = new LinkedList<String>();

	public static LinkedList<String> caducidades = new LinkedList<String>();

	public static LinkedList<String> contactosVencimientos = new LinkedList<String>();

	public static LinkedList<String> colores = new LinkedList<String>();

	static int tipoSeguro = 0;

	static JLabel lblNewLabel_1 = new JLabel("");

	public Vencimiento(int tipo) throws Exception {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Vencimiento.class.getResource("/imagenes/file011117.png")));

		iconoVtoSeguro.setFont(new Font("Dialog", Font.PLAIN, 16));

		vencimientos.clear();

		tipoSeguro = tipo;

		setAlwaysOnTop(true);

		setTitle("Caducidades");

		setResizable(false);

		initComponents();

		this.setSize(new Dimension(780, 650));

		setLocationRelativeTo(null);

		switch (tipo) {

		case 0:
			iconoVtoSeguro.setText(" Selecciona un seguro para visualizar los vencimientos");

			break;

		case 1:

			vencimientos = Menu.vencimientosDecesos;
			break;
		}

		model = (DefaultTableModel) jTable1.getModel();

		loadData(vencimientos);

		verTabla();

	}

	static void verTabla() {

		ft = new FormatoTabla();

		jTable1.setDefaultRenderer(Object.class, ft);

		jTable1.getTableHeader().setEnabled(false);

	}

	public static LinkedList<Integer> buscarColoresVencimientos(String fecha, LinkedList<String> tipoVencimiento) {

		LinkedList<Integer> todosLosVencimientos = new LinkedList<Integer>();

		LinkedList<Integer> indiceVtos = new LinkedList<Integer>();

		todosLosVencimientos = Metodos.buscarVencimientosRojos(tipoVencimiento, Metodos.convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {

			indiceVtos.add(todosLosVencimientos.get(i));
		}

		todosLosVencimientos = Metodos.buscarVencimientosNaranja(tipoVencimiento, Metodos.convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {

			indiceVtos.add(todosLosVencimientos.get(i));
		}

		todosLosVencimientos = Metodos.buscarVencimientosAmarillo(tipoVencimiento, fecha);

		for (int i = 0; i < todosLosVencimientos.size(); i++) {

			indiceVtos.add(todosLosVencimientos.get(i));
		}

		todosLosVencimientos = Metodos.buscarVencimientosVerdes(tipoVencimiento, Metodos.convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {
			indiceVtos.add(todosLosVencimientos.get(i));
		}

		return indiceVtos;

	}

	public void loadData(LinkedList<String> lista) throws Exception {

		vencimientos = Metodos.verVencimientosWebcamers();

		model.getDataVector().removeAllElements();

		model = (DefaultTableModel) jTable1.getModel();

		try {

			ft = new FormatoTabla();

			for (int i = 0; i < vencimientos.size(); i++) {

				model.addRow(
						new Object[] { nombresWebcamersCaducidades.get(i), paginas.get(i), vencimientos.get(i), "" });

			}
		}

		catch (Exception e) {
			//
		}

	}

	private void initComponents() throws ParseException {

		Date fecha = new Date();

		String hoy = Metodos.convertirFecha(fecha.toString(), false);

		buscarColoresVencimientos(hoy, Metodos.verVencimientosWebcamers());

		jPanel1 = new javax.swing.JPanel();

		jScrollPane1 = new javax.swing.JScrollPane();

		jTable1 = new javax.swing.JTable();

		jTable1.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				try {
					model.getDataVector().removeAllElements();
					model = (DefaultTableModel) jTable1.getModel();
					loadData(vencimientos);
					verTabla();

				} catch (Exception e1) {
					//
				}
			}
		});

		jTextField1 = new javax.swing.JTextField();

		jTextField1.setToolTipText("Webcamer");

		jTextField1.setFont(new Font("Tahoma", Font.BOLD, 16));

		jTextField1.setHorizontalAlignment(SwingConstants.CENTER);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		jPanel1.setBackground(Color.WHITE);

		jTable1.setFont(new Font("Arial", Font.PLAIN, 15));

		jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Nombre", "Página", "Vencimiento", "Estado" }) {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("rawtypes")

			Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class,
					java.lang.String.class };
			boolean[] canEdit = new boolean[] { false, false, false, false, false };

			public Class<?> getColumnClass(int columnIndex) {

				if (columnIndex >= 4) {
					columnIndex = 3;
				}

				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}

		});

		jScrollPane1.setViewportView(jTable1);

		jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				jTextField1KeyReleased(evt);
			}
		});

		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Vencimiento.class.getResource("/imagenes/lupa.png")));
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 16));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout
				.createSequentialGroup().addGap(19)
				.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(jPanel1Layout.createSequentialGroup()
								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(jTextField1))
						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 717, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
				.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
						.addComponent(iconoVtoSeguro, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
				.addGap(200)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
						.addGroup(jPanel1Layout.createSequentialGroup().addGap(18)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addGap(51)
								.addComponent(iconoVtoSeguro, GroupLayout.PREFERRED_SIZE, 61,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(1025, Short.MAX_VALUE))
						.addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout
								.createParallelGroup(Alignment.LEADING)
								.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap(31, Short.MAX_VALUE)
										.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 64,
												GroupLayout.PREFERRED_SIZE)
										.addGap(27))
								.addGroup(jPanel1Layout.createSequentialGroup().addGap(48)
										.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 34,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)))
								.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 458, GroupLayout.PREFERRED_SIZE)
								.addGap(597)));
		iconoVtoSeguro.setHorizontalAlignment(SwingConstants.CENTER);
		iconoVtoSeguro.setIcon(null);
		jPanel1.setLayout(jPanel1Layout);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(jPanel1,
				GroupLayout.DEFAULT_SIZE, 1026, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		getContentPane().setLayout(layout);

		pack();
		setLocationRelativeTo(null);
	}

	private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTextField1KeyReleased

		try {

			String cari = jTextField1.getText();

			TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);

			jTable1.setRowSorter(tr);

			tr.setRowFilter(RowFilter.regexFilter(cari));

			verTabla();

		} catch (Exception e) {

			try {

				jTextField1.setText("");

				loadData(vencimientos);

				verTabla();

			} catch (Exception e1) {

			}
		}

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {

		try {

			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {

				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}

			}

		}

		catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Vencimiento.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}

		catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Vencimiento.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}

		catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Vencimiento.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}

		catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Vencimiento.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					new Vencimiento(0).setVisible(true);
				} catch (Exception e) {
	
				}
			}

		});

	}
}
