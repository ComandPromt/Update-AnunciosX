package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import combo_suggestion.ComboBoxSuggestion;
import componente.PopupAlerts;
import javaswingdev.message.MessageDialog;
import roundedButtonsWithImage.ButtonRoundedWithImage;
import textarea.TextArea;
import textarea.TextAreaScroll;
import textfield.TextField;
import utils.Metodos;

@SuppressWarnings("all")

public class Config extends JFrame {

	private JButton buscar;

	private JButton contactos;

	private JButton editar;

	private JButton eliminarContacto;

	public static JList<String> listaWebcamers;

	JPanel panelCasa;

	static TextField precio;

	ComboBoxSuggestion comboBox;

	String sql;

	JLabel jLabel3;

	static JLabel jLabel5;

	static JLabel jLabel6;

	static TextAreaScroll textAreaScroll1;

	static TextAreaScroll textAreaScroll1_1;

	private static ComboBoxSuggestion provincia;

	private static ComboBoxSuggestion localidad;

	private static TextField telefono;

	private static TextField nombre;

	private static int contador;

	static LinkedList<String> listaDatos;

	static DefaultListModel<String> modelo = new DefaultListModel<>();

	private static LinkedList<String> notas = new LinkedList<String>();

	public static LinkedList<String> datosChicas;

	transient ResultSet rs;

	transient Statement s;

	String cnombre;

	String ctipo;

	String cnota;

	PopupAlerts alertas;

	static TextArea titulo;

	static TextArea descripcion;

	LinkedList<String> config = new LinkedList<String>();

	PreparedStatement st;

	private static TextField edad;

	public static void verLocalidades() {

		listaDatos = Metodos.datosIndiceLocalidad(provincia.getSelectedItem().toString());

		if (!listaDatos.isEmpty()) {

			localidad.removeAllItems();

			for (String dato : listaDatos) {

				localidad.addItem(dato);

			}

		}

	}

	public Config() throws IOException, SQLException {

		provincia = new ComboBoxSuggestion();

		provincia.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				verLocalidades();

			}

		});

		localidad = new ComboBoxSuggestion();

		localidad.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				switch (comboBox.getSelectedIndex()) {

				case 1:

					verCaducidadWebcamer();

					break;

				}

			}

		});

		StyleContext context = new StyleContext();

		StyledDocument document = new DefaultStyledDocument(context);

		Style style = context.getStyle(StyleContext.DEFAULT_STYLE);

		StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);

		telefono = new TextField();

		telefono.setHorizontalAlignment(SwingConstants.CENTER);

		context = new StyleContext();

		document = new DefaultStyledDocument(context);

		style = context.getStyle(StyleContext.DEFAULT_STYLE);

		StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);

		nombre = new TextField();

		nombre.setHorizontalAlignment(SwingConstants.CENTER);

		contador = 0;

		initComponents();

		datosChicas = new LinkedList<String>();

		setIconImage(Toolkit.getDefaultToolkit().getImage(Config.class.getResource("/imagenes/file011117.png")));

		setResizable(false);

		setAutoRequestFocus(false);

		this.setSize(new Dimension(1010, 761));

		buscar.setToolTipText("Buscar");

		contactos.setToolTipText("Mostrar todos los contactos");

		editar.setToolTipText("Editar");

		this.setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();

		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Nuevo");

		mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		mnNewMenu.setForeground(Color.BLACK);

		mnNewMenu.setIcon(new ImageIcon(Config.class.getResource("/imagenes/insert.png")));

		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Webcamer");

		mnNewMenu.add(mntmNewMenuItem);

		mntmNewMenuItem.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				try {

					new NuevoRegistro().setVisible(true);

				}

				catch (Exception e1) {

				}

			}

		});

		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		mntmNewMenuItem.setIcon(new ImageIcon(Config.class.getResource("/imagenes/file011115.png")));

		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Caducidades");
		mntmNewMenuItem_2.addMouseListener(new MouseAdapter() {
			@Override

			public void mousePressed(MouseEvent e) {
				new NuevaCaducidad().setVisible(true);
			}

		});

		mntmNewMenuItem_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		mntmNewMenuItem_2.setIcon(new ImageIcon(Config.class.getResource("/imagenes/tiempo.png")));

		mnNewMenu.add(mntmNewMenuItem_2);

		JButton btnNewButton = new JButton();

		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));

		btnNewButton.setText("Actualizar");

		menuBar.add(btnNewButton);

		btnNewButton.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				switch (comboBox.getSelectedIndex()) {

				case 0:

					String webcamer = nombre.getText();

					webcamer = Metodos.eliminarEspacios(webcamer);

					String title = titulo.getText();

					title = Metodos.eliminarEspacios(title);

					String desc = descripcion.getText();

					desc = Metodos.eliminarEspacios(desc);

					if (!webcamer.equals("") && !title.equals("") && !desc.equals("")) {

						try {

							nombre.setText(webcamer);

							titulo.setText(title);

							descripcion.setText(desc);

							sql = "UPDATE CHICAS SET NOMBRE='" + webcamer + "',NOMBRE_SUBIDA='" + title + "',TELEFONO='"
									+ telefono.getText() + "',DESCRIPCION='" + desc + "',LOCALIDAD='"
									+ localidad.getSelectedItem() + "',PROVINCIA='" + provincia.getSelectedItem()
									+ "',EDAD=" + edad.getText() + ",PRECIO=" + precio.getText() + " WHERE NOMBRE='"
									+ listaWebcamers.getSelectedValue() + "'";

							st = Metodos.connect.prepareStatement(sql);

							st.executeUpdate();

							st.close();

							listaWebcamers.removeAll();

							Menu.nombreChica.removeAllItems();

							Metodos.actualizarNombres();

							verChicas();

							alertas.mensaje("El registro se ha actualizado correctamente", 4, 17);

						}

						catch (Exception e1) {

							clean();

						}

					}

					else {

						if (webcamer.equals("") && title.equals("") && desc.equals("")) {

							alertas.mensaje("Por favor, visualize un registro", 2, 16);

						}

						else {

							if (webcamer.equals("") || title.equals("") || desc.equals("")) {

								alertas.mensaje("Por favor, rellena todos los campos", 3, 16);

							}

						}

					}

					break;

				case 1:

					try {

						if (!listaWebcamers.getSelectedValue().isEmpty()) {

							String idPagina = Metodos.saberIdPagina(localidad.getSelectedItem().toString());

							sql = "UPDATE CADUCIDADES SET PAGINA=" + idPagina + ",FECHA_SUBIDA='" + nombre.getText()
									+ "',CADUCIDAD=" + titulo.getText()
									+ " WHERE CHICA=(SELECT ID FROM CHICAS WHERE NOMBRE='"
									+ listaWebcamers.getSelectedValue() + "') AND PAGINA=" + idPagina;

							st = Metodos.connect.prepareStatement(sql);

							st.executeUpdate();

							st.close();

							verCaducidades();

							alertas.mensaje("El registro se ha actualizado correctamente", 4, 17);

						}

					}

					catch (Exception e1) {

					}

					break;

				case 2:

					try {

						sql = "UPDATE CONFIG SET EMAIL='" + nombre.getText() + "',WEB='" + descripcion.getText()
								+ "',TELEFONO='" + telefono.getText() + "',PASS='" + titulo.getText() + "'";

						st = Metodos.connect.prepareStatement(sql);

						st.executeUpdate();

						st.close();

						verConfig();

						alertas.mensaje("El registro se ha actualizado correctamente", 4, 17);

					}

					catch (Exception e1) {

					}

					break;

				}

			}

		});

		btnNewButton.setIcon(new ImageIcon(Config.class.getResource("/imagenes/actualizar.png")));

		btnNewButton.setFocusPainted(false);

		btnNewButton.setContentAreaFilled(false);

		btnNewButton.setBorderPainted(false);

		eliminarContacto = new JButton();
		eliminarContacto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		eliminarContacto.setText("Eliminar");

		menuBar.add(eliminarContacto);

		eliminarContacto.setToolTipText("");

		eliminarContacto.setIcon(new ImageIcon(Config.class.getResource("/imagenes/delete.png")));

		eliminarContacto.setBorderPainted(false);

		eliminarContacto.setContentAreaFilled(false);

		eliminarContacto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		eliminarContacto.setFocusPainted(false);

		eliminarContacto.setRolloverIcon(new ImageIcon(Config.class.getResource("/imagenes/delete_1.png")));

		JButton mntmNewMenuItem_1 = new JButton();
		mntmNewMenuItem_1.setSelectedIcon(new ImageIcon(Config.class.getResource("/imagenes/delete_1.png")));
		mntmNewMenuItem_1.setText("Limpiar");
		mntmNewMenuItem_1.setHorizontalAlignment(SwingConstants.CENTER);

		mntmNewMenuItem_1.setFont(new Font("Tahoma", Font.PLAIN, 16));

		mntmNewMenuItem_1.setIcon(new ImageIcon(Config.class.getResource("/imagenes/delete.png")));

		mntmNewMenuItem_1.setBorderPainted(false);

		mntmNewMenuItem_1.setContentAreaFilled(false);

		mntmNewMenuItem_1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		mntmNewMenuItem_1.setFocusPainted(false);

		mntmNewMenuItem_1.setRolloverIcon(new ImageIcon(Config.class.getResource("/imagenes/delete_1.png")));

		mntmNewMenuItem_1.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				jButton1ActionPerformed(e, true);

			}

		});

		menuBar.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("");

		menuBar.add(mntmNewMenuItem_6);

		JMenuItem mntmNewMenuItem_7 = new JMenuItem("");

		menuBar.add(mntmNewMenuItem_7);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("");

		menuBar.add(mntmNewMenuItem_5);

		eliminarContacto.addMouseListener(new java.awt.event.MouseAdapter() {

			public void mouseClicked(java.awt.event.MouseEvent evt) {

				jButton1ActionPerformed(evt, false);

			}

		});

	}

	protected void jButton1ActionPerformed(MouseEvent evt, boolean tipo) {

		MessageDialog obj;

		int indice = comboBox.getSelectedIndex();

		if (tipo) {

			switch (indice) {

			case 1:

				obj = new MessageDialog(this);

				obj.showMessage("¿Quieres borrar todas las caducidades de " + listaWebcamers.getSelectedValue() + "?",
						"");

				if (obj.getMessageType() == MessageDialog.MessageType.OK) {

					Metodos.eliminarTodasLasCaducidades(listaWebcamers.getSelectedValue());

				}

				break;

			default:

				alertas.mensaje("Solo se usa en caducidades", 3, 26);

				alertas.mensaje("Solo se usa en caducidades", 3, 26);

				break;

			}

		}

		else {

			switch (indice) {

			case 0:

				obj = new MessageDialog(this);

				obj.showMessage("¿Quieres borrar a " + listaWebcamers.getSelectedValue() + "?", "");

				if (obj.getMessageType() == MessageDialog.MessageType.OK) {

					Metodos.eliminarWebcamer(listaWebcamers.getSelectedValue());

				}

				break;

			case 1:

				obj = new MessageDialog(this);

				obj.showMessage("¿Quieres borrar la caducidad de " + listaWebcamers.getSelectedValue() + "?", "");

				if (obj.getMessageType() == MessageDialog.MessageType.OK) {

					Metodos.eliminarCaducidad(listaWebcamers.getSelectedValue(),
							localidad.getSelectedItem().toString());

				}

				break;

			}
		}
	}

	public void vaciarDatos() {

		MessageDialog obj = new MessageDialog(this);

		obj.showMessage("¿Quieres limpiar todos los datos?", "");

		if (obj.getMessageType() == MessageDialog.MessageType.OK) {

			clean();

		}

	}

	public static void clean() {

		nombre.setText("");

		titulo.setText("");

		descripcion.setText("");

		telefono.setText("");

		edad.setText("");

		precio.setText("");

	}

	public Config(String msg) {

		super(msg);

	}

	private void initComponents() throws SQLException, IOException {

		JLabel jLabel1;

		JPanel jPanel3;

		JPanel jPanel4;

		JPanel jPanel5;

		precio = new TextField();

		precio.setFont(new Font("Tahoma", Font.PLAIN, 18));

		comboBox = new ComboBoxSuggestion();

		panelCasa = new JPanel();

		jLabel5 = new JLabel();

		titulo = new TextArea();

		descripcion = new TextArea();

		listaWebcamers = new JList<>();

		listaWebcamers.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {

				if (contador % 2 == 0) {

					try {

						ponerDatosWebcamer();

					}

					catch (Exception e2) {

					}

				}

				contador++;

			}

		});

		listaDatos = new LinkedList<String>();

		listaDatos = Metodos.datosProvincia();

		for (String valor : listaDatos) {

			provincia.addItem(valor);

		}

		listaDatos = Metodos.datosLocalidad();

		for (String valor : listaDatos) {

			localidad.addItem(valor);

		}

		jPanel4 = new JPanel();

		jPanel5 = new JPanel();

		jLabel1 = new JLabel();

		contactos = new JButton();

		contactos.setFont(new Font("Tahoma", Font.PLAIN, 16));

		editar = new JButton();

		editar.setFont(new Font("Tahoma", Font.PLAIN, 16));

		buscar = new JButton();

		buscar.setFont(new Font("Tahoma", Font.PLAIN, 16));

		jPanel3 = new JPanel();

		jPanel3.setBackground(new Color(214, 217, 223));

		TextAreaScroll jScrollPane1;

		jScrollPane1 = new TextAreaScroll();

		listaWebcamers.setFont(new Font("Tahoma", Font.PLAIN, 20));

		listaWebcamers.setFixedCellHeight(40);

		jScrollPane1.setLabelText("");

		jPanel5.setBackground(new java.awt.Color(88, 205, 170));

		jLabel1.setHorizontalAlignment(SwingConstants.LEFT);

		jLabel1.setText("Nombre");

		GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
						.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 481, GroupLayout.PREFERRED_SIZE)));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE));

		GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel4Layout
				.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(jPanel4Layout
								.createSequentialGroup().addComponent(jPanel5, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)));
		setTitle("UpdateAnunciosX Webcamers");
		setBackground(new java.awt.Color(123, 123, 123));

		jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane1.setDoubleBuffered(true);

		listaWebcamers.setBackground(new java.awt.Color(254, 254, 254));

		jScrollPane1.setViewportView(listaWebcamers);

		jLabel5.setHorizontalAlignment(SwingConstants.CENTER);

		panelCasa.setBackground(new Color(214, 217, 223));

		jLabel5.setIcon(new ImageIcon(Config.class.getResource("/imagenes/name.png")));
		jLabel3 = new JLabel();
		jLabel3.setHorizontalAlignment(SwingConstants.CENTER);

		jLabel3.setIcon(new ImageIcon(Config.class.getResource("/imagenes/user.png")));

		nombre.setBackground(new Color(255, 255, 255));
		nombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jLabel6 = new JLabel();
		jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel6.setIcon(new ImageIcon(Config.class.getResource("/imagenes/nota.png")));

		JLabel Jtelefono = new JLabel();
		Jtelefono.setIcon(new ImageIcon(Config.class.getResource("/imagenes/tlf.png")));
		Jtelefono.setHorizontalAlignment(SwingConstants.CENTER);

		telefono.setFont(new Font("Tahoma", Font.PLAIN, 20));

		telefono.setBackground(Color.WHITE);

		telefono.setLabelText("Teléfono");

		provincia.setFont(new Font("Tahoma", Font.PLAIN, 16));

		localidad.setFont(new Font("Tahoma", Font.PLAIN, 16));

		textAreaScroll1 = new TextAreaScroll();

		textAreaScroll1_1 = new TextAreaScroll();

		ButtonRoundedWithImage btnNewButton_1 = new ButtonRoundedWithImage();
		btnNewButton_1.setIcon(new ImageIcon(Config.class.getResource("/imagenes/lupa_+.png")));

		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				aumentarFuente(descripcion);

			}

		});

		ButtonRoundedWithImage btnNewButton_1_1 = new ButtonRoundedWithImage();
		btnNewButton_1_1.setIcon(new ImageIcon(Config.class.getResource("/imagenes/lupa_-.png")));

		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disminuirFuente(descripcion);
			}

		});

		ButtonRoundedWithImage btnNewButton_1_2 = new ButtonRoundedWithImage();
		btnNewButton_1_2.setIcon(new ImageIcon(Config.class.getResource("/imagenes/lupa_+.png")));

		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aumentarFuente(titulo);
			}
		});

		ButtonRoundedWithImage btnNewButton_1_1_1 = new ButtonRoundedWithImage();
		btnNewButton_1_1_1.setIcon(new ImageIcon(Config.class.getResource("/imagenes/lupa_-.png")));

		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disminuirFuente(titulo);
			}

		});

		ButtonRoundedWithImage btnNewButton_1_2_1 = new ButtonRoundedWithImage();
		btnNewButton_1_2_1.setIcon(new ImageIcon(Config.class.getResource("/imagenes/reset_green.png")));

		btnNewButton_1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restablecerFuente(descripcion);
			}
		});

		ButtonRoundedWithImage btnNewButton_2 = new ButtonRoundedWithImage();
		btnNewButton_2.setIcon(new ImageIcon(Config.class.getResource("/imagenes/clean.png")));

		btnNewButton_2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				vaciarDatos();

			}

		});

		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 12));

		edad = new TextField();
		edad.setFont(new Font("Tahoma", Font.PLAIN, 18));
		edad.setLabelText("Edad");

		edad.setColumns(10);

		ButtonRoundedWithImage btnNewButton_1_2_1_1 = new ButtonRoundedWithImage();

		btnNewButton_1_2_1_1.setIcon(new ImageIcon(Config.class.getResource("/imagenes/copy.png")));

		btnNewButton_1_2_1_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Metodos.copiar(titulo.getText(), "", 15);

			}

		});

		ButtonRoundedWithImage btnNewButton_1_2_2_1 = new ButtonRoundedWithImage();

		btnNewButton_1_2_2_1.setIcon(new ImageIcon(Config.class.getResource("/imagenes/copy.png")));

		btnNewButton_1_2_2_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Metodos.copiar(descripcion.getText(), "", 15);

			}

		});

		precio.setLabelText("Precio");

		ButtonRoundedWithImage btnNewButton_1_2_1_1_1 = new ButtonRoundedWithImage();

		btnNewButton_1_2_1_1_1.setIcon(new ImageIcon(Config.class.getResource("/imagenes/copy.png")));

		btnNewButton_1_2_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Metodos.copiar(telefono.getText(), "", 16);
			}
		});

		ButtonRoundedWithImage btnNewButton_1_2_1_1_1_1 = new ButtonRoundedWithImage();

		btnNewButton_1_2_1_1_1_1.setIcon(new ImageIcon(Config.class.getResource("/imagenes/copy.png")));

		btnNewButton_1_2_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Metodos.copiar(edad.getText(), "", 16);
			}
		});

		ButtonRoundedWithImage btnNewButton_1_2_1_1_2 = new ButtonRoundedWithImage();

		btnNewButton_1_2_1_1_2.setIcon(new ImageIcon(Config.class.getResource("/imagenes/copy.png")));

		btnNewButton_1_2_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Metodos.copiar(precio.getText(), "", 16);
			}
		});

		ButtonRoundedWithImage btnNewButton_1_2_1_1_1_2 = new ButtonRoundedWithImage();

		btnNewButton_1_2_1_1_1_2.setIcon(new ImageIcon(Config.class.getResource("/imagenes/copy.png")));

		btnNewButton_1_2_1_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Metodos.copiar(nombre.getText(), "", 16);
			}
		});

		ButtonRoundedWithImage btnNewButton_1_2_1_2 = new ButtonRoundedWithImage();
		btnNewButton_1_2_1_2.setIcon(new ImageIcon(Config.class.getResource("/imagenes/reset_green.png")));

		btnNewButton_1_2_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restablecerFuente(titulo);
			}
		});

		GroupLayout panelCasaLayout = new GroupLayout(panelCasa);
		panelCasaLayout.setHorizontalGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(panelCasaLayout.createSequentialGroup().addContainerGap().addGroup(panelCasaLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(panelCasaLayout.createSequentialGroup()
								.addComponent(provincia, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(localidad, 0, 432, Short.MAX_VALUE))
						.addGroup(
								panelCasaLayout.createSequentialGroup()
										.addGroup(panelCasaLayout.createParallelGroup(Alignment.TRAILING)
												.addGroup(panelCasaLayout.createSequentialGroup()
														.addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, 96,
																Short.MAX_VALUE)
														.addGap(18))
												.addGroup(panelCasaLayout
														.createSequentialGroup()
														.addComponent(Jtelefono, GroupLayout.DEFAULT_SIZE, 110,
																Short.MAX_VALUE)
														.addPreferredGap(ComponentPlacement.RELATED))
												.addGroup(panelCasaLayout.createSequentialGroup()
														.addGroup(panelCasaLayout
																.createParallelGroup(Alignment.TRAILING)
																.addComponent(jLabel6, Alignment.LEADING,
																		GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
																.addComponent(
																		jLabel5, GroupLayout.DEFAULT_SIZE, 110,
																		Short.MAX_VALUE))
														.addPreferredGap(ComponentPlacement.RELATED)))
										.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING).addGroup(
												panelCasaLayout.createSequentialGroup().addGroup(panelCasaLayout
														.createParallelGroup(Alignment.LEADING)
														.addGroup(Alignment.TRAILING, panelCasaLayout
																.createSequentialGroup()
																.addComponent(telefono, GroupLayout.DEFAULT_SIZE, 192,
																		Short.MAX_VALUE)
																.addGap(18)
																.addComponent(btnNewButton_1_2_1_1_1,
																		GroupLayout.PREFERRED_SIZE, 51,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(18)
																.addComponent(precio, GroupLayout.PREFERRED_SIZE, 55,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(18)
																.addComponent(btnNewButton_1_2_1_1_2,
																		GroupLayout.PREFERRED_SIZE, 53,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED))
														.addComponent(nombre, GroupLayout.DEFAULT_SIZE, 405,
																Short.MAX_VALUE))
														.addGap(30))
												.addGroup(panelCasaLayout.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(panelCasaLayout
																.createParallelGroup(Alignment.TRAILING)
																.addComponent(textAreaScroll1_1,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(textAreaScroll1, GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
														.addPreferredGap(ComponentPlacement.UNRELATED)))
										.addGroup(panelCasaLayout
												.createParallelGroup(Alignment.LEADING)
												.addGroup(
														panelCasaLayout.createSequentialGroup().addGap(6).addComponent(
																btnNewButton_1_2_1_1_1_2, GroupLayout.PREFERRED_SIZE,
																53, GroupLayout.PREFERRED_SIZE))
												.addComponent(edad, GroupLayout.PREFERRED_SIZE, 55,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btnNewButton_1_2_1_1, GroupLayout.PREFERRED_SIZE, 53,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btnNewButton_1_2, GroupLayout.PREFERRED_SIZE, 53,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 53,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														btnNewButton_1_2_2_1, GroupLayout.PREFERRED_SIZE, 53,
														GroupLayout.PREFERRED_SIZE))
										.addGap(32)
										.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(btnNewButton_1_2_1_2, GroupLayout.PREFERRED_SIZE, 53,
														Short.MAX_VALUE)
												.addComponent(btnNewButton_1_1, GroupLayout.PREFERRED_SIZE, 53,
														Short.MAX_VALUE)
												.addComponent(btnNewButton_1_2_1, 0, 0, Short.MAX_VALUE)
												.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING, false)
														.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 53,
																Short.MAX_VALUE)
														.addComponent(btnNewButton_1_2_1_1_1_1,
																GroupLayout.PREFERRED_SIZE, 53, Short.MAX_VALUE)
														.addComponent(btnNewButton_1_1_1, GroupLayout.PREFERRED_SIZE,
																53, Short.MAX_VALUE)))))
						.addGap(30)));
		panelCasaLayout.setVerticalGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING).addGroup(panelCasaLayout
				.createSequentialGroup()
				.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING).addGroup(panelCasaLayout
						.createSequentialGroup().addContainerGap()
						.addGroup(panelCasaLayout
								.createParallelGroup(Alignment.BASELINE).addComponent(localidad,
										GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
								.addComponent(provincia, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(nombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel3)
								.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnNewButton_2, Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
										.addComponent(btnNewButton_1_2_1_1_1_2, Alignment.TRAILING,
												GroupLayout.PREFERRED_SIZE, 53, Short.MAX_VALUE)))
						.addGap(76)
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING).addGroup(panelCasaLayout
								.createSequentialGroup().addGap(45)
								.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(textAreaScroll1, GroupLayout.PREFERRED_SIZE, 126,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(panelCasaLayout.createSequentialGroup()
												.addGroup(panelCasaLayout.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(btnNewButton_1_1_1, Alignment.LEADING, 0, 0,
																Short.MAX_VALUE)
														.addComponent(btnNewButton_1_2, Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE, 52, Short.MAX_VALUE))
												.addGap(15)
												.addGroup(panelCasaLayout.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(btnNewButton_1_2_1_2, 0, 0, Short.MAX_VALUE)
														.addComponent(btnNewButton_1_2_1_1, GroupLayout.PREFERRED_SIZE,
																53, Short.MAX_VALUE))))
								.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(panelCasaLayout.createSequentialGroup().addGap(23)
												.addGroup(panelCasaLayout.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(btnNewButton_1_1, 0, 0, Short.MAX_VALUE)
														.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 57,
																Short.MAX_VALUE))
												.addGap(30)
												.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(btnNewButton_1_2_1, GroupLayout.PREFERRED_SIZE,
																55, Short.MAX_VALUE)
														.addComponent(btnNewButton_1_2_2_1, GroupLayout.PREFERRED_SIZE,
																55, Short.MAX_VALUE))
												.addGap(6))
										.addGroup(panelCasaLayout.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(textAreaScroll1_1, GroupLayout.PREFERRED_SIZE, 160,
														GroupLayout.PREFERRED_SIZE))))
								.addGroup(panelCasaLayout.createSequentialGroup().addGap(75).addComponent(jLabel5)
										.addGap(83).addComponent(jLabel6, GroupLayout.PREFERRED_SIZE, 74,
												GroupLayout.PREFERRED_SIZE))))
						.addGroup(panelCasaLayout.createSequentialGroup().addGap(171).addGroup(panelCasaLayout
								.createParallelGroup(Alignment.LEADING).addComponent(Jtelefono)
								.addComponent(precio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(panelCasaLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnNewButton_1_2_1_1_1_1, GroupLayout.PREFERRED_SIZE, 53,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(edad, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
								.addComponent(btnNewButton_1_2_1_1_1, GroupLayout.PREFERRED_SIZE, 53,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(panelCasaLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(telefono, GroupLayout.PREFERRED_SIZE, 55,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnNewButton_1_2_1_1_2, GroupLayout.PREFERRED_SIZE, 55,
												GroupLayout.PREFERRED_SIZE)))))
				.addGap(6)));

		textAreaScroll1_1.setViewportView(descripcion);

		textAreaScroll1.setViewportView(titulo);

		descripcion.setBackground(new Color(255, 255, 255));

		descripcion.setFont(new Font("Monospaced", Font.PLAIN, 18));

		titulo.setFont(new Font("Monospaced", Font.PLAIN, 18));

		panelCasa.setLayout(panelCasaLayout);

		comboBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				switch (comboBox.getSelectedIndex()) {

				case 0:

					verChicas();

					break;

				case 1:

					verCaducidades();

					break;

				case 2:

					verConfig();

					break;

				}

			}

		});

		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();

		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

		comboBox.setRenderer(listRenderer);

		comboBox.addItem("Chicas");

		comboBox.addItem("Caducidades");

		comboBox.addItem("Configuración");

		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel3Layout
				.createSequentialGroup().addGap(28)
				.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 936, GroupLayout.PREFERRED_SIZE)
						.addGroup(jPanel3Layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(panelCasa, GroupLayout.PREFERRED_SIZE, 733, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(jPanel3Layout.createSequentialGroup().addGap(25)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
								.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 558, GroupLayout.PREFERRED_SIZE)
								.addComponent(panelCasa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(35, Short.MAX_VALUE)));
		jPanel3.setLayout(jPanel3Layout);

		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout
						.createSequentialGroup().addComponent(jPanel3, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(42, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, 651, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(77, Short.MAX_VALUE)));
		getContentPane().setLayout(layout);

		pack();
	}

	protected void verCaducidades() {

		try {

			localidad.removeAllItems();

			clean();

			LinkedList<String> lista = Menu.nombresChicas;

			Collections.sort(lista);

			for (String dato : lista) {

				modelo.addElement(dato);

			}

			listaWebcamers.setModel(modelo);

			for (String nombre : Metodos.verPaginas()) {

				localidad.addItem(nombre);

			}

			nombre.setLabelText("Fecha de subida");

			textAreaScroll1.setLabelText("Días para caducar");

			jLabel5.setIcon(new ImageIcon(Config.class.getResource("/imagenes/tiempo.png")));

		}

		catch (Exception e) {

		}

	}

	void verConfig() {

		try {

			modelo.removeAllElements();

			listaWebcamers.setModel(modelo);

			clean();

			nombre.setLabelText("Usuario");

			textAreaScroll1.setLabelText("Contraseña");

			textAreaScroll1_1.setLabelText("Web");

			jLabel5.setIcon(new ImageIcon(Config.class.getResource("/imagenes/pass.png")));

			jLabel6.setIcon(new ImageIcon(Config.class.getResource("/imagenes/url.png")));

			config = Metodos.saberConfig();

			descripcion.setText(config.get(1));

			nombre.setText(config.get(0));

			titulo.setText(config.get(3));

			telefono.setText(config.get(2));

		}

		catch (Exception e) {

		}

	}

	void restablecerFuente(TextArea texto) {

		texto.setFont(new Font("Tahoma", Font.PLAIN, 16));

	}

	void aumentarFuente(TextArea texto) {

		int size = texto.getFont().getSize();

		size++;

		if (size == 100) {

			size = 18;

		}

		texto.setFont(new Font("Tahoma", Font.PLAIN, size));

	}

	void disminuirFuente(TextArea texto) {

		int size = texto.getFont().getSize();

		size--;

		if (size == 11) {

			size = 18;

		}

		texto.setFont(new Font("Tahoma", Font.PLAIN, size));

	}

	public static void verChicas() {

		try {

			clean();

			verLocalidades();

			nombre.setLabelText("Nombre");

			jLabel5.setIcon(new ImageIcon(Config.class.getResource("/imagenes/name.png")));

			jLabel6.setIcon(new ImageIcon(Config.class.getResource("/imagenes/nota.png")));

			textAreaScroll1.setLabelText("Texto de cabecera");

			textAreaScroll1_1.setLabelText("Nota");

			modelo.removeAllElements();

			LinkedList<String> lista = Menu.nombresChicas;

			Collections.sort(lista);

			for (String dato : lista) {

				modelo.addElement(dato);

			}

			listaWebcamers.setModel(modelo);

		}

		catch (Exception e) {

		}

	}

	public void ponerWebcamer(String string) throws IOException {

		listaWebcamers.setSelectedValue(string, true);

		ponerDatosWebcamer();

	}

	public void ponerDatosWebcamer() throws IOException {

		switch (comboBox.getSelectedIndex()) {

		case 0:

			datosChicas = Metodos.saberDatos(listaWebcamers.getSelectedValue());

			provincia.setSelectedItem(datosChicas.get(2));

			localidad.setSelectedItem(datosChicas.get(1));

			nombre.setText(datosChicas.get(0));

			telefono.setText(datosChicas.get(5));

			titulo.setText(datosChicas.get(3));

			descripcion.setText(datosChicas.get(4));

			edad.setText(datosChicas.get(6));

			precio.setText(datosChicas.get(7));

			break;

		case 1:

			verCaducidadWebcamer();

			break;

		}

	}

	public void verCaducidadWebcamer() {

		try {

			config = Metodos.saberCaducidades(listaWebcamers.getSelectedValue(),
					localidad.getSelectedItem().toString());

			if (!config.isEmpty()) {

				clean();

				nombre.setText(config.get(0));

				titulo.setText(config.get(1));

			}

			config = Metodos.saberPaginasCaducidades(listaWebcamers.getSelectedValue());

			String separador = ",";

			descripcion.setText("");

			int contador = 0;

			for (String valor : config) {

				contador++;

				if (config.size() == 1 || contador == config.size()) {

					separador = "";

				}

				descripcion.setText(descripcion.getText() + valor + separador);

			}

		}

		catch (Exception e) {

		}

	}
}
