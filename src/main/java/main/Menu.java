package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;

import combo_suggestion.ComboBoxSuggestion;
import componente.PopupAlerts;
import javaswingdev.message.MessageDialog;
import roundedButtonsWithImage.ButtonRoundedWithImage;
import spinner.Spinner;
import spinner.SpinnerUI;
import utils.Metodos;

@SuppressWarnings("all")

public class Menu extends javax.swing.JFrame {

	public static Spinner chica;

	public static String directorioActual;

	public static Spinner numPagina;

	static int valorIndice;

	static LinkedList<String> datosConfig;

	static LinkedList<String> fotos;

	public static LinkedList<String> carpetas;

	static String usuario;

	static FirefoxDriver driver;

	static JavascriptExecutor js;

	static int indiceCombobox;

	static int idPagina;

	static About sobre;

	static int numeroChica;

	public static PopupAlerts alertas;

	public static JLabel numChicas;

	static WebElement p;

	static Select sel;

	public static LinkedList<String> nombresChicas;

	public static LinkedList<String> datosChicas;

	public static LinkedList<String> cookies;

	public static LinkedList<String> datosLogin;

	public static ComboBoxSuggestion nombreChica;

	public static LinkedList<String> vencimientos = new LinkedList<String>();

	public static LinkedList<String> vencimientosDecesos = new LinkedList<String>();

	public static ComboBoxSuggestion pagina;

	static JLabel numPaginas;

	public static LinkedList<String> vencimientosLlamadas = new <String>LinkedList();

	public static void abrirCarpetaWebcamer() {

		try {

			Metodos.abrirCarpeta(directorioActual + "\\imagenes\\" + nombreChica.getSelectedItem());

		}

		catch (IOException e1) {

		}

	}

	public static void ejecutarJs(String comando) {

		js = (JavascriptExecutor) driver;

		js.executeScript(comando);

	}

	public Menu() throws IOException {

		setIconImage(Toolkit.getDefaultToolkit().getImage(Menu.class.getResource("/imagenes/file011117.png")));

		setTitle("UpdateAnunciosX");

		JMenuBar menuBar = new JMenuBar();

		setJMenuBar(menuBar);

		JMenuItem mntmNewMenuItem = new JMenuItem("Sobre");

		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 15));

		mntmNewMenuItem.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/about.png")));

		mntmNewMenuItem.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				sobre = new About();

				sobre.andar = true;

				sobre.start();

			}

		});

		JMenu mnNewMenu = new JMenu("Abrir");

		mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 15));

		mnNewMenu.setForeground(Color.BLACK);

		mnNewMenu.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/folder.png")));

		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Abir imagenes");

		mnNewMenu.add(mntmNewMenuItem_1);

		mntmNewMenuItem_1.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				try {

					Metodos.abrirCarpeta(directorioActual + "\\imagenes");

				}

				catch (IOException e1) {

				}

			}

		});

		mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));

		mntmNewMenuItem_1.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/folder.png")));

		JSeparator separator = new JSeparator();

		mnNewMenu.add(separator);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Carpeta Webcamer");

		mntmNewMenuItem_2.setFont(new Font("Segoe UI", Font.PLAIN, 17));

		mntmNewMenuItem_2.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				abrirCarpetaWebcamer();

			}

		});

		mntmNewMenuItem_2.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/folder.png")));
		mnNewMenu.add(mntmNewMenuItem_2);

		JMenu mnNewMenu_1 = new JMenu("Nuevo");
		mnNewMenu_1.setForeground(Color.BLACK);
		mnNewMenu_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		mnNewMenu_1.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/insert.png")));
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Webcamer");

		mnNewMenu_1.add(mntmNewMenuItem_4);

		mntmNewMenuItem_4.setFont(new Font("Segoe UI", Font.PLAIN, 15));

		mntmNewMenuItem_4.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				try {

					new NuevoRegistro().setVisible(true);

				}

				catch (Exception e1) {

				}

			}

		});

		mntmNewMenuItem_4.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/file011115.png")));

		JSeparator separator_1 = new JSeparator();

		mnNewMenu_1.add(separator_1);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Caducidad");

		mntmNewMenuItem_6.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				new NuevaCaducidad().setVisible(true);

			}

		});

		mntmNewMenuItem_6.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		mntmNewMenuItem_6.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/tiempo.png")));
		mnNewMenu_1.add(mntmNewMenuItem_6);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Config");
		menuBar.add(mntmNewMenuItem_3);

		mntmNewMenuItem_3.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				try {

					Config config = new Config();

					config.ponerWebcamer(nombreChica.getSelectedItem().toString());

					config.setVisible(true);

				}

				catch (Exception e1) {

				}

			}

		});

		mntmNewMenuItem_3.setFont(new Font("Segoe UI", Font.PLAIN, 15));

		mntmNewMenuItem_3.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/config.png")));

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Caducidades");

		mntmNewMenuItem_5.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				try {

					new Vencimiento(1).setVisible(true);

				}

				catch (Exception e1) {

				}

			}

		});

		mntmNewMenuItem_5.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/TXT.png")));
		mntmNewMenuItem_5.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		menuBar.add(mntmNewMenuItem_5);

		menuBar.add(mntmNewMenuItem);

		initComponents();

		numPaginas.setText("/" + pagina.getItemCount());

		this.setVisible(true);

	}

	public static void bot() throws IOException {

		cerrarNavegador(false);

		saberNumeroChica();

		if (numeroChica > 0) {

			datosConfig = Metodos.verConfig();

			usuario = datosConfig.get(0);

			indiceCombobox = pagina.getSelectedIndex();

			idPagina = indiceCombobox;

			idPagina++;

			datosChicas = Metodos.saberDatos(nombreChica.getSelectedItem().toString());

			cookies = Metodos.saberCookies(pagina.getSelectedItem().toString());

			numeroChica--;

			driver = new FirefoxDriver();

			FirefoxOptions firefox_options = new FirefoxOptions();

			firefox_options.setCapability("marionette", true);

			switch (pagina.getSelectedItem().toString()) {

			case "milescorts.es":

				try {

					milescorts(numeroChica);

				}

				catch (Exception e) {

				}

				break;

			case "eroticosvip.com":

				eroticosvip(numeroChica);

				break;

			case "sexoenlacity.es":

				sexoEnCity(numeroChica);

				break;

			case "sexlistclub.com":

				listClub(numeroChica);

				break;

			case "pasion.com":

				pasion(numeroChica);

				break;

			case "trilly.sexy":

				trilly(numeroChica);

				break;

			case "mundosexanuncio.com":

				mundosexanuncio(numeroChica);

				break;

			case "putianuncio.com":

				putianuncio(numeroChica);

				break;

			case "wanuncios.com":

				wanuncios(numeroChica);

				break;

			case "pasionycontactos.com":

				pasionycontactos(numeroChica);

				break;

			case "radarsex.es":

				radarsex(numeroChica);

				break;

			case "iberotico.com":

				iberotico(numeroChica);

				break;

			case "destacamos.com":

				destacamos(numeroChica);

				break;

			case "loquovip.com":

				loquo(numeroChica);

				break;

			case "locanto.es":

				locanto(numeroChica);

				break;

			case "adultguia.com":

				adultguia(numeroChica);

				break;

			case "ardienteplacer.com":

				ardienteplacer(numeroChica);

				break;

			case "mileroticos.com":

				mileroticos(numeroChica);

				break;

			}

		}

		siguienteChica(false);

	}

	private static void mileroticos(int indice) {

		try {

			saberFotos(indice);

			driver.get("https://www.mileroticos.com");

			int y = 0;

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			abrirCarpetaWebcamer();

			driver.get("https://www.mileroticos.com/publicar/");

			p = driver.findElement(By.id("Ad_ad_category_id"));

			sel = new Select(p);

			sel.selectByValue("10");

			p = driver.findElement(By.id("Ad_age"));

			sel = new Select(p);

			sel.selectByValue(datosChicas.get(6));

			driver.findElement(By.id("Ad_show_telephone")).click();

			driver.findElement(By.id("Ad_show_whatsapp")).click();

			driver.findElement(By.id("Ad_conditions")).click();

			driver.findElement(By.id("Ad_url")).sendKeys(datosConfig.get(1));

			driver.findElement(By.id("Ad_title")).sendKeys(datosChicas.get(3));

			Metodos.copiar(datosChicas.get(4), "Pegar en descripción", 18);

			Metodos.copiar(datosChicas.get(4), "Pegar en descripción", 18);

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void ardienteplacer(int indice) {

		try {

			saberFotos(indice);

			driver.get("https://www.ardienteplacer.com");

			int y = 0;

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			driver.get("https://www.ardienteplacer.com/publicar-anuncio");

			ejecutarJs("document.getElementsByName('categorias[id]')[10].click();");

			driver.findElement(By.name("anuncio[titulo]")).sendKeys(datosChicas.get(3));

			driver.findElement(By.name("anuncio[nombre]")).sendKeys(nombreChica.getSelectedItem().toString());

			driver.findElement(By.name("anuncio[descripcion]")).sendKeys(datosChicas.get(4));

			p = driver.findElement(By.name("anuncio[edad]"));

			sel = new Select(p);

			sel.selectByValue(datosChicas.get(6));

			driver.findElement(By.name("anuncio[precio]")).sendKeys(datosChicas.get(7));

			driver.findElement(By.name("anuncio[localidad]")).sendKeys(datosChicas.get(1));

			p = driver.findElement(By.name("provincias[id]"));

			sel = new Select(p);

			sel.selectByVisibleText(datosChicas.get(2));

			p = driver.findElement(By.name("nacionalidads[id]"));

			sel = new Select(p);

			sel.selectByValue("2");

			driver.findElement(By.name("anuncio[telefono1]")).sendKeys(datosChicas.get(5));

			driver.findElement(By.id("permite_whatsapp_tel1")).click();

			driver.findElement(By.id("condiciones_y_lopd")).click();

			alertas.mensaje("Click sobre la caja para subir las fotos", 2, 15);

			alertas.mensaje("Click sobre la caja para subir las fotos", 2, 15);

		}

		catch (Exception e) {

		}

	}

	private static void adultguia(int indice) {

		try {

			Config config = new Config();

			alertas.mensaje("En Config copia y pega los datos de la webcamer", 2, 15);

			alertas.mensaje("En Config copia y pega los datos de la webcamer", 2, 15);

			config.ponerWebcamer(nombreChica.getSelectedItem().toString());

			config.setVisible(true);

			abrirCarpetaWebcamer();

			saberFotos(indice);

			driver.get("https://es.adultguia.com");

			int y = 0;

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			driver.get("https://es.adultguia.com/securitycheck");

			ejecutarJs("document.getElementsByClassName('ue-accept-button-accept')[0].click();");

			driver.findElement(By.id("numPhone")).sendKeys(saberTelefono());

			driver.findElement(By.id("checkbox_policy")).click();

			driver.findElement(By.id("email")).sendKeys(datosConfig.get(0));

		}

		catch (Exception e) {

		}

	}

	private static void locanto(int indice) {

		try {

			abrirCarpetaWebcamer();

			saberFotos(indice);

			driver.get("https://www.locanto.es");

			int y = 0;

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			driver.get("https://www.locanto.es/post/");

			driver.get("https://www.locanto.es/post/P/209/20950/1/");

			driver.findElement(By.id("subject")).sendKeys(datosChicas.get(3));

			driver.findElement(By.id("description")).sendKeys(datosChicas.get(4));

			driver.findElement(By.name("age")).sendKeys(datosChicas.get(6));

			ejecutarJs("document.getElementsByName('url')[1].value=\"" + datosConfig.get(1) + "\";");

			driver.findElement(By.id("geo_search_post")).sendKeys(datosChicas.get(1));

		}

		catch (Exception e) {

		}

	}

	private static void loquo(int indice) {

		try {

			saberFotos(indice);

			driver.get("https://www.loquovip.com");

			int y = 0;

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			driver.get("https://www.loquovip.com/nuevo-anuncio/");

			driver.findElement(By.id("title")).sendKeys(datosChicas.get(3));

			Metodos.copiar(datosChicas.get(4), "Pegar en descripción", 18);

			driver.findElement(By.id("web_page")).sendKeys(datosConfig.get(1));

			driver.findElement(By.id("phone")).sendKeys(saberTelefono());

			ejecutarJs("document.getElementById('whatsapp_phone_on').click();");

			ejecutarJs("document.getElementById('legals').click();");

			ejecutarJs("document.getElementById('send').click();");

			p = driver.findElement(By.id("provincia"));

			sel = new Select(p);

			sel.selectByVisibleText(datosChicas.get(2));

			p = driver.findElement(By.id("localidad"));

			sel = new Select(p);

			sel.selectByVisibleText(datosChicas.get(1));

			p = driver.findElement(By.id("categoria"));

			sel = new Select(p);

			sel.selectByValue("12");

			y = 0;

			if (!fotos.isEmpty()) {

				y = 0;

				for (int i = 1; i <= 4; i++) {

					if (y < fotos.size()) {

						driver.findElement(By.id("foto" + i)).sendKeys(fotos.get(y));

					}

					y++;

				}

			}

		}

		catch (Exception e) {

		}

	}

	private static void destacamos(int indice) {

		try {

			abrirCarpetaWebcamer();

			saberFotos(indice);

			driver.get("http://www.destacamos.com");

			int y = 0;

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			driver.get("https://www.destacamos.com/newad.php?step=1&category=6");

			driver.findElement(By.id("title")).sendKeys(datosChicas.get(3));

			driver.findElement(By.id("description")).sendKeys(datosChicas.get(4));

			driver.findElement(By.id("telefono")).sendKeys(saberTelefono());

			ejecutarJs("document.getElementById('whatsapp').click();");

			driver.findElement(By.id("price")).sendKeys(datosChicas.get(7));

			p = driver.findElement(By.id("edad"));

			sel = new Select(p);

			sel.selectByVisibleText(datosChicas.get(6));

			p = driver.findElement(By.id("localidad"));

			sel = new Select(p);

			sel.selectByVisibleText(datosChicas.get(2));

			ejecutarJs("document.getElementById('idiomasEspañol').click();");

			ejecutarJs("document.getElementById('forma_de_pagoTarjeta').click();");

			ejecutarJs("document.getElementById('forma_de_pagoBizum').click();");

		}

		catch (Exception e) {

		}

	}

	private static void iberotico(int indice) {

		try {

			Config config = new Config();

			config.setVisible(true);

			config.ponerWebcamer(nombreChica.getSelectedItem().toString());

			saberFotos(indice);

			driver.get("https://www.iberotico.com");

			driver.manage().addCookie(new Cookie(cookies.get(0), cookies.get(1)));

			driver.get("https://www.iberotico.com/anuncios/publicar");

			driver.findElement(By.name("titulo")).sendKeys(datosChicas.get(3));

			driver.findElement(By.name("descripcion")).sendKeys(datosChicas.get(4));

			driver.findElement(By.name("telefono")).sendKeys(saberTelefono());

			ejecutarJs("document.getElementById('politica_privacidad').click();");

			driver.findElement(By.name("email")).sendKeys(datosConfig.get(0));

			int y = 0;

			if (!fotos.isEmpty()) {

				y = 0;

				for (int i = 1; i <= 7; i++) {

					if (y < fotos.size()) {

						driver.findElement(By.name("file")).sendKeys(fotos.get(y));

					}

					y++;

				}

			}

		}

		catch (Exception e) {

		}

	}

	private static void radarsex(int indice) {

		try {

			saberFotos(indice);

			int y = 0;

			driver.get("https://www.radarsex.es");

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			driver.get("https://www.radarsex.es/es/publicar-anuncios.html");

			if (!fotos.isEmpty()) {

				y = 0;

				for (int i = 1; i <= 7; i++) {

					if (y < fotos.size()) {

						driver.findElement(By.id("thumbnail-" + y)).sendKeys(fotos.get(y));

					}

					y++;

				}

			}

			ejecutarJs("document.getElementById('policy').click();");

			ejecutarJs("document.getElementsByClassName('form-control')[12].value=\"" + datosChicas.get(4) + "\";");

			driver.findElement(By.name("name")).sendKeys(nombreChica.getSelectedItem().toString());

			driver.findElement(By.name("age")).sendKeys(datosChicas.get(6));

			driver.findElement(By.name("phone1")).sendKeys(saberTelefono());

			ejecutarJs("document.getElementsByName(\"title\")[1].value=\"" + datosChicas.get(3) + "\";");

		}

		catch (Exception e) {

		}

	}

	private static void pasionycontactos(int indice) {

		try {

			saberFotos(indice);

			abrirCarpetaWebcamer();

			int y = 0;

			driver.get("https://pasionycontactos.com");

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			driver.get("https://pasionycontactos.com/panel-de-control/anadir-nuevo-anuncio/");

			driver.findElement(By.id("gform_browse_button_1_41")).sendKeys(fotos.get(0));

			p = driver.findElement(By.id("input_1_23"));

			sel = new Select(p);

			sel.selectByVisibleText("Línea Erótica");

			driver.findElement(By.id("input_1_28")).sendKeys(datosChicas.get(3));

			driver.findElement(By.id("input_1_5")).sendKeys(nombreChica.getSelectedItem().toString());

			ejecutarJs("document.getElementById('input_1_14').value=\"" + datosChicas.get(6) + "\"");

			driver.findElement(By.id("input_1_8")).sendKeys(saberTelefono());

			driver.findElement(By.id("input_1_7")).sendKeys(datosChicas.get(4));

			ejecutarJs("document.getElementById('choice_1_11_1').click();");

		}

		catch (Exception e) {

		}

	}

	private static void wanuncios(int indice) {

		try {

			saberFotos(indice);

			abrirCarpetaWebcamer();

			driver.get("https://www.wanuncios.com/publicar");

			driver.findElement(By.id("titulo")).sendKeys(datosChicas.get(3));

			driver.findElement(By.id("phone")).sendKeys(saberTelefono());

			ejecutarJs("document.getElementById('whatsapp').click()");

			ejecutarJs("document.getElementById('mostrar_email').click()");

			ejecutarJs("document.getElementById('condiciones').click()");

			p = driver.findElement(By.id("provincia"));

			sel = new Select(p);

			sel.selectByVisibleText(datosChicas.get(2));

			p = driver.findElement(By.id("categoria"));

			sel = new Select(p);

			sel.selectByVisibleText("Contactos");

			p = driver.findElement(By.id("subcategoria"));

			sel = new Select(p);

			sel.selectByIndex(1);

			driver.findElement(By.name("email")).sendKeys(datosConfig.get(0));

			driver.findElement(By.id("descripcion_ifr")).sendKeys(datosChicas.get(4));

		}

		catch (Exception e) {

		}

	}

	private static void putianuncio(int indice) {

		try {

			saberFotos(indice);

			driver.get("https://www.putianuncio.com");

			abrirCarpetaWebcamer();

			int y = 0;

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			driver.get("https://www.putianuncio.com/anuncios/publicar");

			driver.findElement(By.id("titulo")).sendKeys(datosChicas.get(3));

			driver.findElement(By.id("descripcion")).sendKeys(datosChicas.get(4));

			driver.findElement(By.id("telefono")).sendKeys(saberTelefono());

			p = driver.findElement(By.id("categoria_form"));

			sel = new Select(p);

			sel.selectByValue("52");

			p = driver.findElement(By.id("provincia_form"));

			sel = new Select(p);

			sel.selectByVisibleText(datosChicas.get(2));

			p = driver.findElement(By.id("localidad"));

			sel = new Select(p);

			sel.selectByVisibleText(datosChicas.get(1));

			ejecutarJs("document.getElementById('politica_privacidad').click()");

		}

		catch (Exception e) {

		}

	}

	private static void mundosexanuncio(int indice) {

		try {

			saberFotos(indice);

			driver.get("https://www.mundosexanuncio.com");

			int y = 0;

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			driver.get("https://www.mundosexanuncio.com/publicar");

			if (!fotos.isEmpty()) {

				abrirCarpetaWebcamer();

				driver.findElement(By.id("image_1")).sendKeys(fotos.get(0));

			}

			driver.findElement(By.id("titol")).sendKeys(datosChicas.get(3));

			Metodos.copiar(datosChicas.get(4), "Pegar en descripción", 18);

			driver.findElement(By.id("telefono")).sendKeys(saberTelefono());

			driver.findElement(By.id("enlace")).sendKeys(datosConfig.get(1));

			driver.findElement(By.id("condiciones")).click();

			p = driver.findElement(By.id("id_categoria"));

			sel = new Select(p);

			sel.selectByValue("8");

			p = driver.findElement(By.id("id_provincia"));

			sel = new Select(p);

			sel.selectByVisibleText(datosChicas.get(2));

			p = driver.findElement(By.id("id_ciudad"));

			sel = new Select(p);

			sel.selectByVisibleText(datosChicas.get(1));

		}

		catch (Exception e) {

		}

	}

	private static void trilly(int indice) {

		try {

			saberFotos(indice);

			driver.get("https://trilly.sexy/");

			int y = 0;

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			Config config = new Config();

			alertas.mensaje("En Config copia y pega los datos de la webcamer", 2, 15);

			alertas.mensaje("En Config copia y pega los datos de la webcamer", 2, 15);

			driver.get("https://trillyjava.com/");

			driver.manage()
					.addCookie(new Cookie("usertrilly", "trilly%C2%A7keepublicidadkee1980@gmail.com%C2%A7Anca1980!"));

			driver.get("https://trilly.sexy/special/accedi.html?lang=es&env=trilly&naz=Espana");

			config.setVisible(true);

			config.ponerWebcamer(nombreChica.getSelectedItem().toString());

		}

		catch (Exception e) {

		}

	}

	private static void pasion(int indice) {

		try {

			saberFotos(indice);

			abrirCarpetaWebcamer();

			driver.get("https://www.pasion.com/publicar-anuncios-gratis/");

			driver.get("https://www.pasion.com/lugar/?c=1210");

			int y = 0;

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			String telefono = saberTelefono();

			driver.get("https://www.pasion.com/textos-del-anuncio/?p=" + datosChicas.get(2).toLowerCase() + "&l="
					+ datosChicas.get(1).toLowerCase() + "&c=1210&demandax=n&x=29&y=11");

			driver.findElement(By.id("titulo")).sendKeys(datosChicas.get(3));

			driver.findElement(By.id("telefono1")).sendKeys(saberTelefono());

			driver.findElement(By.id("edad")).sendKeys(datosChicas.get(6));

			driver.findElement(By.id("precio")).sendKeys(datosChicas.get(7));

			driver.findElement(By.id("texto")).sendKeys(datosChicas.get(4));

			driver.findElement(By.id("acepto_condiciones_uso_y_politica_de_privacidad")).click();

			driver.findElement(By.id("email")).sendKeys(datosConfig.get(0));

			driver.findElement(By.id("repemail")).sendKeys(datosConfig.get(0));

			p = driver.findElement(By.id("duracion"));

			sel = new Select(p);

			sel.selectByValue("60");

		}

		catch (Exception e) {

		}

	}

	private static void listClub(int indice) {

		try {

			saberFotos(indice);

			driver.get("https://es.sexlistclub.com");

			int y = 0;

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			String telefono = saberTelefono();

			String titulo = datosChicas.get(3);

			while (titulo.length() < 40) {

				titulo += " ";

			}

			driver.get("https://es.sexlistclub.com/item/new");

			esperar();

			p = driver.findElement(By.id("regionId"));

			driver.findElement(By.id("titlees_ES")).sendKeys(titulo + " todo tipo de shows webcam");

			driver.findElement(By.id("meta_website")).sendKeys(datosConfig.get(1));

			driver.findElement(By.id("meta_phone")).sendKeys(telefono);

			driver.findElement(By.id("meta_mobile")).sendKeys(telefono);

			driver.findElement(By.id("meta_whatsapp")).sendKeys(telefono);

			driver.findElement(By.id("descriptiones_ES")).sendKeys(datosChicas.get(4));

			driver.findElement(By.id("s_tags")).sendKeys("escorts,putas,prostitutas,bitches,whores,webcam,sexo");

			y = 0;

			for (int i = 1; i <= 4; i++) {

				if (y < fotos.size()) {

					driver.findElement(By.name("qqfile")).sendKeys(fotos.get(y));

				}

				y++;

			}

			WebElement myElement = driver.findElement(By.id("s_birthday"));

			String js = "document.getElementById('s_birthday').value='01/04/2004';";

			((JavascriptExecutor) driver).executeScript(js, myElement);

			sel = new Select(p);

			sel.selectByVisibleText(datosChicas.get(2));

			p = driver.findElement(By.id("s_i_am"));

			sel = new Select(p);

			sel.selectByVisibleText("Mujer");

			p = driver.findElement(By.id("s_looking_for"));

			sel = new Select(p);

			sel.selectByVisibleText("Hombre");

		}

		catch (Exception e) {

		}

	}

	private static void sexoEnCity(int indice) {

		try {

			driver.get("https://sexoenlacity.es/entrar/?logged_out=true");

			int y = 0;

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			driver.get("https://sexoenlacity.es/publicar-anuncio/");

			p = driver.findElement(By.className("acadp-category-listing"));

			sel = new Select(p);

			sel.selectByVisibleText("Chicas");

			driver.findElement(By.id("acadp-title")).sendKeys(datosChicas.get(4));

			driver.findElement(By.name("website")).sendKeys(datosConfig.get(1));

			driver.findElement(By.id("acadp-phone")).sendKeys(saberTelefono());

			driver.findElement(By.id("acadp-address")).sendKeys("Madrid");

			driver.findElement(By.tagName("textarea")).sendKeys(datosChicas.get(4));

		}

		catch (Exception e) {

		}

	}

	public static void eroticosvip(int indice) throws IOException {

		try {

			saberFotos(indice);

			driver.get("https://www.eroticosvip.com/login/");

			int y = 0;

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			driver.get("https://www.eroticosvip.com/nuevo-anuncio/");

			p = driver.findElement(By.id("provincia"));

			sel = new Select(p);

			sel.selectByVisibleText(datosChicas.get(2));

			p = driver.findElement(By.id("localidad"));

			sel = new Select(p);

			sel.selectByVisibleText(datosChicas.get(1));

			p = driver.findElement(By.id("categoria"));

			sel = new Select(p);

			sel.selectByIndex(1);

			driver.findElement(By.id("title")).sendKeys(datosChicas.get(3));

			driver.findElement(By.id("phone")).sendKeys(saberTelefono());

			driver.findElement(By.id("texto_anuncio_ifr")).sendKeys(datosChicas.get(4));

			driver.findElement(By.id("whatsapp_phone")).click();

			driver.findElement(By.id("legals")).click();

			try {

				driver.findElement(By.id("send")).click();

			}

			catch (Exception e) {

			}

			y = 0;

			for (int i = 1; i <= 4; i++) {

				if (y < fotos.size()) {

					driver.findElement(By.id("foto" + i)).sendKeys(fotos.get(y));

				}

				y++;

			}

		}

		catch (Exception e) {

		}

	}

	public static void esperar() throws InterruptedException {

		Thread.sleep(2000);

	}

	public static void milescorts(int indice) throws IOException, InterruptedException {

		try {

			saberFotos(indice);

			int y = 0;

			driver.get("https://www.milescorts.es/clientes/login/");

			for (int i = 0; i < cookies.size(); i += 2) {

				y = i;

				y++;

				driver.manage().addCookie(new Cookie(cookies.get(i), cookies.get(y)));

			}

			driver.get("https://www.milescorts.es/publicar.htm");

			y = 1;

			driver.findElement(By.id("title")).sendKeys(datosChicas.get(3));

			driver.findElement(By.id("name")).sendKeys("ONLINE WEBCAM");

			driver.findElement(By.id("phone")).sendKeys(saberTelefono());

			driver.findElement(By.id("web")).sendKeys(datosConfig.get(1));

			Metodos.copiar(datosChicas.get(4), "Pegar en descripción", 18);

			for (int z = 0; z < fotos.size(); z++) {

				if (z < fotos.size()) {

					driver.findElement(By.id("pic" + y)).sendKeys(fotos.get(z));

				}

				y++;

			}

			y = 0;

			p = driver.findElement(By.id("category"));

			sel = new Select(p);

			sel.selectByIndex(1);

			p = driver.findElement(By.id("provincia"));

			sel = new Select(p);

			sel.selectByVisibleText(datosChicas.get(2));

			p = driver.findElement(By.id("location"));

			sel = new Select(p);

			try {

				sel.selectByVisibleText(datosChicas.get(1));

			} catch (Exception e) {

				try {

					sel.selectByVisibleText(datosChicas.get(1) + " ciudad");

				}

				catch (Exception e1) {

					Thread.sleep(1000);
				}
				try {

					sel.selectByIndex(1);

				}

				catch (Exception e2) {

					sel.selectByIndex(0);
				}

			}

		}

		catch (Exception e) {

			p = driver.findElement(By.id("category"));

			sel = new Select(p);

			sel.selectByIndex(1);

			p = driver.findElement(By.id("provincia"));

			sel = new Select(p);

			sel.selectByVisibleText(datosChicas.get(2));

			p = driver.findElement(By.id("location"));

			sel = new Select(p);

			try {

				sel.selectByVisibleText(datosChicas.get(1));

			} catch (Exception e1) {

				try {

					sel.selectByVisibleText(datosChicas.get(1) + " ciudad");

				}

				catch (Exception e2) {

					Thread.sleep(1000);
				}
				try {

					sel.selectByIndex(1);

				}

				catch (Exception e2) {

					sel.selectByIndex(0);
				}

			}

		}

	}

	public static void siguienteChica(boolean restar) {

		saberNumeroChica();

		if (numeroChica == pagina.getItemCount()) {

			numeroChica = 0;

			chica.setValue(numeroChica);

			nombreChica.setSelectedIndex(0);

			indiceCombobox++;

			if (indiceCombobox >= pagina.getItemCount()) {

				indiceCombobox = 0;

			}

			pagina.setSelectedIndex(indiceCombobox);

		}

		else {

			numeroChica++;

			chica.setValue(numeroChica);

			ponerNombreChica(restar);

		}

	}

	private static String saberTelefono() {

		String telefono;

		telefono = datosChicas.get(5);

		if (telefono.isEmpty()) {

			telefono = datosConfig.get(2);

		}

		return telefono;

	}

	public static void saberFotos(int i) throws IOException {

		String carpeta = directorioActual + "\\imagenes\\" + carpetas.get(i) + "\\";

		Metodos.convertir("jpeg", "jpg", carpeta);

		Metodos.convertir("png", "jpg", carpeta + "\\");

		Metodos.convertir("PNG", "jpg", carpeta);

		Metodos.convertir("JPG", "jpg", carpeta);

		Metodos.convertir("webp", "jpg", carpeta);

		fotos = Metodos.directorio(directorioActual + "\\imagenes\\" + carpetas.get(i) + "\\", "images", true, true);

	}

	public static void cerrarNavegador(boolean mensaje) {
		if (mensaje) {
			alertas.mensaje("Cerrando el navegador", 2, 15);
		}
		try {

			if (driver instanceof FirefoxDriver) {

				driver.quit();

			}

		}

		catch (Exception e) {

		}

	}

	public void ponerEnPaginas() {

		try {

			numPagina.ponerFiltro();

			SpinnerUI.Editor editor = (SpinnerUI.Editor) numPagina.getEditor();

			int valorIndice = Integer.parseInt(editor.getText());

			valorIndice--;

			pagina.setSelectedIndex(valorIndice);

		}

		catch (Exception e1) {

		}

	}

	public void ponerEnComboBox() {

		try {

			chica.ponerFiltro();

			SpinnerUI.Editor editor = (SpinnerUI.Editor) chica.getEditor();

			valorIndice = Integer.parseInt(editor.getText());

			valorIndice--;

			nombreChica.setSelectedIndex(valorIndice);

		}

		catch (Exception e1) {

		}

	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {

		MessageDialog obj = new MessageDialog(this);

		obj.showMessage("¿Quieres borrar a " + nombreChica.getSelectedItem() + "?", "");

		if (obj.getMessageType() == MessageDialog.MessageType.OK) {

			Metodos.eliminarWebcamer(nombreChica.getSelectedItem());

		}

	}

	private void initComponents() throws IOException {

		directorioActual = new File(".").getCanonicalPath();

		System.setProperty("webdriver.gecko.driver", directorioActual + "\\geckodriver.exe");

		pagina = new ComboBoxSuggestion();

		pagina.setFont(new Font("Tahoma", Font.PLAIN, 17));

		Metodos.conexionBD();

		Metodos.saberPaginas();

		numPaginas = new JLabel("");

		alertas = new PopupAlerts();

		Metodos.conexionBD();

		pagina.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				numPagina.setValue(pagina.getSelectedIndex() + 1);

			}

		});

		nombreChica = new ComboBoxSuggestion();

		nombreChica.setFont(new Font("Tahoma", Font.PLAIN, 17));

		nombreChica.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				chica.setValue(nombreChica.getSelectedIndex() + 1);

			}

		});

		datosChicas = new LinkedList<String>();

		numChicas = new JLabel("");

		numChicas.setHorizontalAlignment(SwingConstants.RIGHT);

		numChicas.setFont(new Font("Tahoma", Font.PLAIN, 16));

		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();

		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

		datosConfig = Metodos.verConfig();

		carpetas = Metodos.ponerNombreChicas();

		if (!datosConfig.isEmpty()) {

			usuario = datosConfig.get(0);

		}

		numPagina = new Spinner(true, true, false, 1, pagina.getItemCount(), 1);

		numPagina.setValor(1);

		numPagina.getEditor().setAlignmentX(CENTER_ALIGNMENT);

		numPagina.getEditor().setAlignmentY(CENTER_ALIGNMENT);

		numPagina.getEditor().setFont(new Font("Tahoma", Font.PLAIN, 16));

		numPagina.setLabelText("");

		chica = new Spinner(true, true, false, 1, Metodos.numChicas(), 1);

		chica.getEditor().setFont(new Font("Tahoma", Font.PLAIN, 16));

		chica.setLabelText("");

		chica.getEditor().addKeyListener(new KeyAdapter() {

			@Override

			public void keyReleased(KeyEvent e) {

				try {

					chica.ponerFiltro();

				} catch (Exception e1) {

				}

				ponerEnComboBox();

			}

		});

		chica.getEditor().addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {

				SpinnerUI.Editor editor = (SpinnerUI.Editor) chica.getEditor();

				int notches = e.getWheelRotation();

				int valorIndice = Integer.parseInt(editor.getText());

				if (notches < 0) {

					valorIndice++;

				}

				else {

					valorIndice--;

					if (valorIndice <= 0) {

						valorIndice = 1;

					}

					editor.setText("" + valorIndice);

				}

				if (valorIndice <= nombreChica.getItemCount()) {

					editor.setText("" + valorIndice);

					ponerEnComboBox();

				}

			}

		});

		Metodos.actualizarNombres();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		setResizable(false);

		ButtonRoundedWithImage robot = new ButtonRoundedWithImage();

		robot.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/bot.png")));

		robot.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					bot();

				}

				catch (Exception e1) {

				}

			}

		});

		JLabel lblNewLabel = new JLabel("Webcamers");

		lblNewLabel.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/file011115.png")));

		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));

		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		ButtonRoundedWithImage btnNewButton_1 = new ButtonRoundedWithImage();

		btnNewButton_1.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/stop.png")));

		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				cerrarNavegador(true);

			}

		});

		ButtonRoundedWithImage btnNewButton = new ButtonRoundedWithImage();

		btnNewButton.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/images.png")));

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					Metodos.abrirCarpeta(directorioActual + "\\imagenes\\" + nombreChica.getSelectedItem());

				}

				catch (IOException e1) {

				}

			}

		});

		btnNewButton.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/images.png")));

		numPagina.getEditor().addKeyListener(new KeyAdapter() {

			@Override

			public void keyReleased(KeyEvent e) {

				try {

					numPagina.ponerFiltro();

				} catch (Exception e1) {

				}

				ponerEnPaginas();

			}

		});

		numPagina.getEditor().addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {

				SpinnerUI.Editor editor = (SpinnerUI.Editor) numPagina.getEditor();

				int notches = e.getWheelRotation();

				int valorIndice = Integer.parseInt(editor.getText());

				if (notches < 0) {

					valorIndice++;

				}

				else {

					valorIndice--;

					if (valorIndice <= 0) {

						valorIndice = 1;

					}

					editor.setText("" + valorIndice);

				}

				if (valorIndice <= pagina.getItemCount()) {

					editor.setText("" + valorIndice);

					ponerEnPaginas();

				}

			}

		});

		numPaginas.setHorizontalAlignment(SwingConstants.RIGHT);

		numPaginas.setFont(new Font("Tahoma", Font.PLAIN, 16));

		ButtonRoundedWithImage btnNewButton_2 = new ButtonRoundedWithImage();

		btnNewButton_2.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {

				jButton1ActionPerformed(evt);

			}

		});

		btnNewButton_2.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/Recycle_Bin_Full.png")));

		ButtonRoundedWithImage robot_1 = new ButtonRoundedWithImage();

		robot_1.setIcon(new ImageIcon(Config.class.getResource("/imagenes/upload.png")));

		robot_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					switch (pagina.getSelectedItem().toString()) {

					case "ardienteplacer.com":

						if (!fotos.isEmpty()) {

							int y = 0;

							for (int i = 1; i <= 10; i++) {

								if (y < fotos.size()) {

									driver.findElement(By.id("hidden_" + y)).sendKeys(fotos.get(y));

								}

								y++;

							}

						}

						break;

					default:

						alertas.mensaje("Solo para la página ardienteplacer.com", 3, 18);

						alertas.mensaje("Solo para la página ardienteplacer.com", 3, 18);

						break;

					}

				}

				catch (Exception e1) {

				}

			}

		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup()
						.addGap(24)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(chica, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
								.addComponent(numPagina, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(numPaginas, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addComponent(numChicas, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(pagina, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(nombreChica, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE))
						.addGap(18)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(robot, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 55, Short.MAX_VALUE))
								.addGroup(layout.createSequentialGroup()
										.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 52,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 58, Short.MAX_VALUE))
								.addComponent(robot_1, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED))
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE))
				.addGap(74)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup().addGap(38).addComponent(lblNewLabel).addGap(29)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 59, Short.MAX_VALUE)
								.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 59, Short.MAX_VALUE)
								.addComponent(nombreChica, GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
								.addComponent(numChicas, GroupLayout.DEFAULT_SIZE, 59,
										Short.MAX_VALUE)
								.addComponent(chica, GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
						.addGap(117)
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(Alignment.BASELINE)
										.addComponent(pagina, GroupLayout.PREFERRED_SIZE, 50,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(numPaginas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(
												numPagina, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addContainerGap())
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(Alignment.TRAILING)
												.addComponent(robot, GroupLayout.PREFERRED_SIZE, 51, Short.MAX_VALUE)
												.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 51,
														Short.MAX_VALUE))
										.addGap(29).addComponent(robot_1, GroupLayout.PREFERRED_SIZE, 51,
												GroupLayout.PREFERRED_SIZE)
										.addGap(62)))));

		getContentPane().setLayout(layout);

		setSize(new Dimension(830, 605));

		setLocationRelativeTo(null);

	}

	public static void ponerNombreChica(boolean restar) {

		if (!chica.getValue().toString().isEmpty()) {

			saberNumeroChica();

			int indiceChica = numeroChica;

			if (restar) {

				indiceChica--;

			}

			if (indiceChica < nombreChica.getItemCount()) {

				nombreChica.setSelectedIndex(indiceChica);

			}

		}

	}

	public static void saberNumeroChica() {

		numeroChica = nombreChica.getSelectedIndex();

		if (numeroChica < 0) {

			numeroChica = 1;

		}

		else {

			numeroChica++;

		}

	}

	public void stateChanged(ChangeEvent e) {

	}

	public void actionPerformed(ActionEvent e) {

	}
}
