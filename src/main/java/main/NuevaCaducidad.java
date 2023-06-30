package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.sqlite.SQLiteException;

import com.raven.datechooser.DateChooser;
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;

import combo_suggestion.ComboBoxSuggestion;
import componente.PopupAlerts;
import roundedButtonsWithImage.ButtonRoundedWithImage;
import spinner.Spinner;
import textfield.TextField;
import utils.MaterialButtomRectangle;
import utils.Metodos;

@SuppressWarnings("all")

public class NuevaCaducidad extends javax.swing.JFrame implements ActionListener, ChangeListener {

	private Spinner caducidad;

	private Point mouseClickPoint;

	LinkedList<String> listaDatos;

	JComboBox paginas;

	PopupAlerts alertas;

	JComboBox webcamer;

	private TextField fecha;

	private DateChooser dateChooser;

	protected void guardar() {

		try {

			String sql = "INSERT INTO CADUCIDADES (CHICA,PAGINA,FECHA_SUBIDA,CADUCIDAD) VALUES("
					+ Metodos.saberIdChica(webcamer.getSelectedItem().toString()) + ","
					+ Metodos.saberIdPagina(paginas.getSelectedItem().toString()) + ",'"
					+ fecha.getText().replace("-", "/") + "'," + caducidad.getValor() + ")";

			PreparedStatement st;

			st = Metodos.connect.prepareStatement(sql);

			st.executeUpdate();

			alertas.mensaje("Webcamer introducida correctamente", 4, 18);

		}

		catch (SQLiteException e) {

			alertas.mensaje("Webcamer y pagina ya existe", 3, 18);

		}

		catch (Exception e1) {

		}

	}

	public NuevaCaducidad() {

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

				mouseClickPoint = e.getPoint();

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

		webcamer = new ComboBoxSuggestion();

		fecha = new TextField();

		fecha.setFont(new Font("Tahoma", Font.PLAIN, 15));

		fecha.setBackground(Color.decode("#FFF1FD"));

		fecha.setLabelText("Fecha de subida");

		dateChooser = new com.raven.datechooser.DateChooser(2);

		dateChooser.setTextRefernce(fecha);

		dateChooser.addEventDateChooser(new EventDateChooser() {

			@Override
			public void dateSelected(SelectedAction action, SelectedDate date) {

				if (action.getAction() == SelectedAction.DAY_SELECTED) {

					dateChooser.hidePopup();

				}

			}

		});

		alertas = new PopupAlerts();

		paginas = new ComboBoxSuggestion();

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

			@Override
			public void actionPerformed(ActionEvent e) {

				guardar();
			}

		});

		caducidad = new Spinner();

		caducidad.getEditor().setFont(new Font("Tahoma", Font.PLAIN, 15));

		caducidad.setBackground(Color.decode("#FFF1FD"));

		caducidad.addKeyListener(new KeyAdapter() {

			@Override

			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					guardar();
				}
			}

		});

		caducidad.setLabelText("Caducidad");

		JLabel jLabel3 = new JLabel();

		jLabel3.setIcon(new ImageIcon(NuevoRegistro.class.getResource("/imagenes/user.png")));

		jLabel3.setText("Nombre");

		jLabel3.setFont(new Font("Tahoma", Font.PLAIN, 14));

		Date myDate = new Date();

		JLabel lblNewLabel_1 = new JLabel("Fecha de subida");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);

		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));

		lblNewLabel_1.setIcon(new ImageIcon(NuevaCaducidad.class.getResource("/imagenes/tiempo.png")));

		JLabel lblNewLabel_3_1 = new JLabel("Días para Caducar");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.LEFT);

		lblNewLabel_3_1.setIcon(new ImageIcon(NuevoRegistro.class.getResource("/imagenes/city.png")));

		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblNewLabel_3_2 = new JLabel("Página");
		lblNewLabel_3_2.setHorizontalAlignment(SwingConstants.LEFT);

		lblNewLabel_3_2.setIcon(new ImageIcon(NuevoRegistro.class.getResource("/imagenes/city.png")));

		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		paginas.setEditable(false);

		webcamer.setEditable(false);

		paginas.setFont(new Font("Tahoma", Font.PLAIN, 20));

		webcamer.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JButton btnNewButton_1 = new JButton("");

		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();

			}

		});

		btnNewButton_1.setIcon(new ImageIcon(NuevoRegistro.class.getResource("/imagenes/close.png")));

		JButton btnNewButton_1_1 = new JButton("");

		btnNewButton_1_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setState(JFrame.ICONIFIED);

			}

		});

		btnNewButton_1_1.setIcon(new ImageIcon(NuevoRegistro.class.getResource("/imagenes/min.png")));

		ButtonRoundedWithImage jButton5 = new ButtonRoundedWithImage();
		jButton5.setBackground(Color.WHITE);
		jButton5.setText("Seleccionar fecha");

		jButton5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dateChooser.showPopup();
			}

		});

		for (String nombre : Metodos.ponerNombreChicas()) {

			webcamer.addItem(nombre);

		}

		for (String nombre : Metodos.verPaginas()) {

			paginas.addItem(nombre);

		}

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addGroup(layout.createSequentialGroup().addContainerGap()
								.addComponent(btnNewButton_1_1, GroupLayout.PREFERRED_SIZE, 32,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 33,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
								.addGap(85)
								.addGroup(layout.createParallelGroup(Alignment.TRAILING)
										.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
												.addGroup(layout.createSequentialGroup()
														.addGroup(layout.createParallelGroup(Alignment.LEADING)
																.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 125,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(lblNewLabel_3_2))
														.addGap(64))
												.addComponent(lblNewLabel_3_1, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 197,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(webcamer, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
										.addComponent(paginas, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
										.addComponent(fecha, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
										.addComponent(caducidad, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
								.addGap(30)
								.addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addContainerGap(624, Short.MAX_VALUE)
										.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 159,
												GroupLayout.PREFERRED_SIZE))))
						.addGap(64)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addGap(27)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_1_1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
				.addGap(19)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
						.addComponent(webcamer, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
				.addGap(38)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(paginas, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
				.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addPreferredGap(ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
								.addComponent(fecha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(53))
						.addGroup(layout.createSequentialGroup().addGap(48).addComponent(lblNewLabel_1)
								.addPreferredGap(ComponentPlacement.RELATED)))
				.addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addComponent(caducidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
				.addGap(10).addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
				.addGap(55)));

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
