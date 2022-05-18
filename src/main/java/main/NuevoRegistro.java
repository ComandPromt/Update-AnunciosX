package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import combo_suggestion.ComboBoxSuggestion;
import componente.PopupAlerts;
import textfield.TextField;
import utils.MaterialButtomRectangle;
import utils.Metodos;

@SuppressWarnings("all")

public class NuevoRegistro extends javax.swing.JFrame implements ActionListener, ChangeListener {

	private TextField nombre;

	JTextArea titulo;

	JTextArea descripcion;

	private TextField telefono;

	private Point mouseClickPoint;

	TextField edad;

	TextField precio;

	LinkedList<String> listaDatos;

	JComboBox provincia;

	PopupAlerts alertas;

	JComboBox localidad;

	protected void guardar() {

		try {

			int telefonoc = Integer.parseInt(Metodos.eliminarEspacios(telefono.getText()));
			int edadc = Integer.parseInt(Metodos.eliminarEspacios(edad.getText()));
			int precioc = Integer.parseInt(Metodos.eliminarEspacios(precio.getText()));
			String name = Metodos.eliminarEspacios(nombre.getText());

			String nota = Metodos.eliminarEspacios(titulo.getText());

			String desc = Metodos.eliminarEspacios(descripcion.getText());

			String sql;

			ResultSet result;

			ArrayList<Integer> lista = new ArrayList<Integer>();

			if (!name.isEmpty()) {

				PreparedStatement st = Metodos.connect
						.prepareStatement("SELECT COUNT(ID) AS RECUENTO FROM CHICAS WHERE NOMBRE='" + name + "'");

				result = st.executeQuery();

				result.next();

				lista.add(Integer.parseInt(result.getString("RECUENTO")));

				if (lista.get(0) == 0) {

					sql = "INSERT INTO CHICAS (NOMBRE,LOCALIDAD,PROVINCIA,NOMBRE_SUBIDA,DESCRIPCION,TELEFONO,EDAD,PRECIO) VALUES(?,?,?,?,?,?,?,?)";

					st = Metodos.connect.prepareStatement(sql);

					st.setString(1, name);

					st.setString(2, localidad.getSelectedItem().toString());

					st.setString(3, provincia.getSelectedItem().toString());

					st.setString(4, nota);

					st.setString(5, desc);

					st.setInt(6, telefonoc);

					st.setInt(7, edadc);

					st.setInt(8, precioc);

					st.executeUpdate();

					st.close();

					vaciarDatos();

					Menu.nombreChica.removeAllItems();

					File dir = new File(Menu.directorioActual + "\\imagenes\\" + name);

					if (!dir.exists()) {

						dir.mkdir();

					}

					Metodos.actualizarNombres();

					alertas.mensaje("Webcamer introducida correctamente", 4, 18);

				}

				else {

					alertas.mensaje("Nombre de Webcamer repetido", 3, 20);

				}

			}

		}

		catch (Exception e) {

		}

	}

	private void vaciarDatos() {

		telefono.setText("");

		nombre.setText("");

		titulo.setText("");

		descripcion.setText("");

	}

	public NuevoRegistro() {

		getContentPane().setBackground(Color.WHITE);

		listaDatos = new LinkedList<String>();

		getContentPane().addKeyListener(new KeyAdapter() {

			@Override

			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					guardar();
				}
			}

		});

		setIconImage(Toolkit.getDefaultToolkit().getImage(NuevoRegistro.class.getResource("/imagenes/insert.png")));

		setTitle("Update Anuncios X -Nuevo Registro");

		initComponents();

		setUndecorated(true);

		setShape(new RoundRectangle2D.Double(0, 0, 847, 600, 50, 50));

		this.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				mouseClickPoint = e.getPoint(); // update the position

			}

		});

		this.addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {

				Point newPoint = e.getLocationOnScreen();

				newPoint.translate(-mouseClickPoint.x, -mouseClickPoint.y); // Moves the point by given values from its
																			// location
				setLocation(newPoint); // set the new location

			}

		});

		this.setVisible(true);
	}

	private void initComponents() {

		edad = new TextField();
		edad.setFont(new Font("Tahoma", Font.PLAIN, 15));

		precio = new TextField();
		precio.setFont(new Font("Tahoma", Font.PLAIN, 15));

		alertas = new PopupAlerts();

		provincia = new ComboBoxSuggestion();

		localidad = new ComboBoxSuggestion();

		descripcion = new JTextArea("", 0, 50);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		setResizable(false);

		MaterialButtomRectangle btnNewButton = new MaterialButtomRectangle();

		btnNewButton.setText("Insertar");

		btnNewButton.setBackground(new java.awt.Color(0, 102, 0));

		btnNewButton.setForeground(new java.awt.Color(255, 255, 255));

		btnNewButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				guardar();
			}

		});

		nombre = new TextField();

		nombre.setBackground(Color.decode("#FFF1FD"));

		nombre.setHorizontalAlignment(SwingConstants.CENTER);

		nombre.addKeyListener(new KeyAdapter() {

			@Override

			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					guardar();
				}
			}

		});

		nombre.setFont(new Font("Tahoma", Font.PLAIN, 15));

		nombre.setLabelText("Nombre");

		nombre.setHorizontalAlignment(SwingConstants.CENTER);

		nombre.setColumns(10);

		JScrollPane scrollPane = new JScrollPane((Component) null);

		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JLabel jLabel3 = new JLabel();

		jLabel3.setIcon(new ImageIcon(NuevoRegistro.class.getResource("/imagenes/user.png")));

		jLabel3.setText("Nombre");

		jLabel3.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblNewLabel = new JLabel("Título");

		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblNewLabel.setIcon(new ImageIcon(NuevoRegistro.class.getResource("/imagenes/name.png")));

		Date myDate = new Date();

		JLabel lblNewLabel_1 = new JLabel("Tlf");

		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));

		lblNewLabel_1.setIcon(new ImageIcon(NuevoRegistro.class.getResource("/imagenes/tlf.png")));

		JLabel lblNewLabel_3_1 = new JLabel("Localidad");

		lblNewLabel_3_1.setIcon(new ImageIcon(NuevoRegistro.class.getResource("/imagenes/city.png")));

		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblNewLabel_3_2 = new JLabel("Provincia");

		lblNewLabel_3_2.setIcon(new ImageIcon(NuevoRegistro.class.getResource("/imagenes/city.png")));

		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		telefono = new TextField();

		telefono.setBackground(Color.decode("#FFF1FD"));

		telefono.setHorizontalAlignment(SwingConstants.CENTER);

		telefono.setFont(new Font("Tahoma", Font.PLAIN, 15));

		telefono.setLabelText("Teléfono");

		JLabel lblNewLabel_2 = new JLabel("Descripción");

		lblNewLabel_2.setIcon(new ImageIcon(NuevoRegistro.class.getResource("/imagenes/nota.png")));

		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JScrollPane scrollPane_1 = new JScrollPane((Component) null);

		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		provincia.setEditable(false);

		localidad.setEditable(false);

		provincia.setFont(new Font("Tahoma", Font.PLAIN, 20));

		localidad.setFont(new Font("Tahoma", Font.PLAIN, 20));

		provincia.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				listaDatos = Metodos.datosIndiceLocalidad(provincia.getSelectedItem().toString());

				if (!listaDatos.isEmpty()) {

					localidad.removeAllItems();

					for (String dato : listaDatos) {

						localidad.addItem(dato);

					}

				}
			}

		});

		try {

			listaDatos = Metodos.datosProvincia();

			for (String valor : listaDatos) {

				provincia.addItem(valor);

			}

			listaDatos = Metodos.datosLocalidad();

			for (String valor : listaDatos) {

				localidad.addItem(valor);

			}

		}

		catch (Exception e) {

		}

		JButton btnNewButton_1 = new JButton("");

		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				dispose();

			}

		});

		btnNewButton_1.setIcon(new ImageIcon(NuevoRegistro.class.getResource("/imagenes/close.png")));

		JButton btnNewButton_1_1 = new JButton("");

		btnNewButton_1_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				setState(JFrame.ICONIFIED);

			}

		});

		btnNewButton_1_1.setIcon(new ImageIcon(NuevoRegistro.class.getResource("/imagenes/min.png")));

		edad.setLabelText("Edad");

		edad.setBackground(Color.decode("#FFF1FD"));

		edad.setHorizontalAlignment(SwingConstants.CENTER);

		precio.setLabelText("Precio");

		precio.setBackground(Color.decode("#FFF1FD"));
		precio.setHorizontalAlignment(SwingConstants.CENTER);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup()
				.addGap(98)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(5)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 125,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 96,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(telefono, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(nombre, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
								.addGap(18)
								.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblNewLabel_3_2, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblNewLabel_3_1, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
								.addGap(18)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(162)
												.addComponent(btnNewButton_1_1, GroupLayout.PREFERRED_SIZE, 32,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
												.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 33,
														GroupLayout.PREFERRED_SIZE))
										.addComponent(localidad, 0, 249, Short.MAX_VALUE)
										.addComponent(provincia, Alignment.TRAILING, 0, 249, Short.MAX_VALUE))
								.addGap(9))
						.addGroup(layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(Alignment.TRAILING)
										.addGroup(layout.createSequentialGroup()
												.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 165,
														GroupLayout.PREFERRED_SIZE)
												.addGap(18).addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 305,
														GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup()
												.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 115,
														GroupLayout.PREFERRED_SIZE)
												.addGap(63).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 305,
														GroupLayout.PREFERRED_SIZE)))
								.addGap(50)
								.addGroup(layout.createParallelGroup(Alignment.TRAILING)
										.addComponent(edad, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
										.addComponent(precio, GroupLayout.PREFERRED_SIZE, 152,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 118,
												GroupLayout.PREFERRED_SIZE))))
				.addGap(51)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addGap(81)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 63,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addComponent(provincia, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addComponent(nombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
						.addGroup(layout.createSequentialGroup().addGap(28)
								.addGroup(layout.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnNewButton_1_1, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE))))
				.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addGap(38)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 63,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(telefono, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
						.addGroup(layout.createSequentialGroup().addGap(51)
								.addComponent(localidad, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
				.addGap(42)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup().addGap(14).addComponent(lblNewLabel,
								GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
						.addComponent(edad, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
				.addGap(3).addComponent(precio, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(20).addComponent(lblNewLabel_2,
												GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
										.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 117,
												GroupLayout.PREFERRED_SIZE)))
						.addGroup(layout.createSequentialGroup().addGap(44).addComponent(btnNewButton,
								GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(22, Short.MAX_VALUE)));

		descripcion.setWrapStyleWord(true);

		descripcion.setLineWrap(true);

		descripcion.setFont(new Font("Monospaced", Font.PLAIN, 16));

		descripcion.setBackground(Color.WHITE);

		scrollPane_1.setViewportView(descripcion);

		titulo = new JTextArea("", 0, 50);

		titulo.setWrapStyleWord(true);

		titulo.setLineWrap(true);

		titulo.setFont(new Font("Monospaced", Font.PLAIN, 16));

		titulo.setBackground(Color.WHITE);

		scrollPane.setViewportView(titulo);

		getContentPane().setLayout(layout);

		setSize(new Dimension(847, 600));

		setLocationRelativeTo(null);

	}

	@Override

	public void stateChanged(ChangeEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
