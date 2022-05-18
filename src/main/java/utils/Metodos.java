package utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

import main.Config;
import main.Menu;
import main.Vencimiento;

public abstract class Metodos {

	public static Connection connect;

	static ResultSet result;

	static Clipboard clipboard;

	public static String extraerExtension(String nombreArchivo) {

		String extension = "";

		if (nombreArchivo.length() >= 3) {

			extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1, nombreArchivo.length());

			extension = extension.toLowerCase();

			if (extension.endsWith(".ts")) {

				extension = "ts";

			}

		}

		return extension;

	}

	public static boolean esBisiesto(int year) {

		if (year >= 3344) {
			return true;
		}

		else {

			if ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))) {
				return true;
			}

			else {
				return false;
			}
		}

	}

	public static LinkedList<Integer> buscarVencimientosAmarillo(LinkedList<String> lista, String busqueda) {

		LinkedList<Integer> repetido = new LinkedList<Integer>();

		LinkedList<Integer> resultado = new LinkedList<Integer>();

		LinkedList<Integer> meses31 = new LinkedList<Integer>();

		int dia, mes, year;

		meses31.add(1);

		meses31.add(3);

		meses31.add(5);

		meses31.add(7);

		meses31.add(8);

		meses31.add(10);

		meses31.add(12);

		dia = Integer.parseInt(busqueda.substring(0, busqueda.indexOf("/")));

		mes = Integer.parseInt(busqueda.substring(busqueda.indexOf("/") + 1, busqueda.lastIndexOf("/")));

		year = Integer.parseInt(busqueda.substring(busqueda.lastIndexOf("/") + 1, busqueda.length()));

		String ceromes = "";

		String cerodia = "";

		LinkedList<String> fechasAmarillas = new LinkedList<String>();

		if (dia == 31 && meses31.contains(mes) || dia == 30 && !meses31.contains(mes)
				|| dia == 28 && mes == 2 && !esBisiesto(year) || dia == 29 && mes == 2 && esBisiesto(year)) {
			dia = 1;
		}

		else {
			++dia;
		}

		if (mes == 12) {
			mes = 1;
		} else {

			mes++;

		}

		if (mes <= 9) {
			ceromes = "0";
		}

		else {
			ceromes = "";
		}

		if (dia <= 9) {
			cerodia = "0";
		}

		else {
			cerodia = "";
		}

		busqueda = cerodia + dia + "/" + ceromes + mes + "/" + year;

		for (int x = 0; x < 30; x++) {

			if (x > 0) {
				++dia;
			}

			if (dia == 29 && mes == 2 && !esBisiesto(year) ||

					year < 3344 && dia == 30 && mes == 2 && esBisiesto(year) || dia == 31 && !meses31.contains(mes)
					|| dia == 32 && meses31.contains(mes)) {
				dia = 1;
				++mes;
			}

			if (dia == 32 && mes == 12) {
				dia = 1;
				++year;
			}

			if (mes <= 9) {
				ceromes = "0";
			} else {
				ceromes = "";
			}

			if (dia <= 9) {
				cerodia = "0";
			}

			else {
				cerodia = "";
			}

			busqueda = cerodia + dia + "/" + ceromes + mes + "/" + year;

			FormatoTabla.FechasVencimientosAmarillos.add(busqueda);

			fechasAmarillas.add(busqueda);

		}

		for (int i = 0; i < 30; i++) {

			repetido = buscarFechasVencimientos(lista, fechasAmarillas.get(i), 3);

			if (repetido.size() > 0) {

				for (int x = 0; x < repetido.size(); x++) {

					resultado.add(repetido.get(x));
				}

			}

		}

		return resultado;
	}

	public static LinkedList<Integer> buscarVencimientosRojos(LinkedList<String> lista, String busqueda) {

		LinkedList<Integer> repetido = new LinkedList<Integer>();

		LinkedList<Integer> resultado = new LinkedList<Integer>();

		int dia, mes, year;

		dia = Integer.parseInt(busqueda.substring(0, busqueda.indexOf("/")));

		mes = Integer.parseInt(busqueda.substring(busqueda.indexOf("/") + 1, busqueda.lastIndexOf("/")));

		year = Integer.parseInt(busqueda.substring(busqueda.lastIndexOf("/") + 1, busqueda.length()));

		String ceromes = "";

		String cerodia = "";

		LinkedList<String> fechasRojas = new LinkedList<String>();

		if (ultimoDiaMes(dia, mes, year)) {

			dia = 1;

			if (mes == 12) {
				mes = 1;
				++year;
			}

			else {
				++mes;
			}

		}

		else {
			++dia;
		}

		for (int x = 0; x < 15; x++) {

			if (mes <= 9) {
				ceromes = "0";
			}

			else {
				ceromes = "";
			}

			if (dia <= 9) {
				cerodia = "0";
			}

			else {
				cerodia = "";
			}

			busqueda = cerodia + dia + "/" + ceromes + mes + "/" + year;

			FormatoTabla.FechasVencimientosRojos.add(busqueda);

			fechasRojas.add(busqueda);

			if (ultimoDiaMes(dia, mes, year)) {

				dia = 1;

				if (mes == 12) {
					mes = 1;
					++year;
				}

				else {
					++mes;
				}

			}

			else {
				++dia;
			}

		}

		for (int i = 0; i < 15; i++) {

			repetido = buscarFechasVencimientos(lista, fechasRojas.get(i), 1);

			if (repetido.size() > 0) {

				for (int x = 0; x < repetido.size(); x++) {

					resultado.add(repetido.get(x));
				}

			}

		}

		return resultado;

	}

	public static boolean ultimoDiaMes(int dia, int mes, int year) {

		boolean resultado = false;

		if (dia == 30) {

			switch (mes) {

			case 4:
			case 6:
			case 9:
			case 11:
				resultado = true;
				break;

			}

		}

		if (dia == 31) {

			switch (mes) {

			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				resultado = true;
				break;

			}

		}

		if (year < 3344 && (mes == 2 && (dia == 29 && esBisiesto(year)) || (dia == 28 && !esBisiesto(year)

		))) {
			resultado = true;
		}

		return resultado;
	}

	public static LinkedList<Integer> buscarColoresVencimientos(String fecha, LinkedList<String> tipoVencimiento) {

		LinkedList<Integer> todosLosVencimientos = new LinkedList<Integer>();

		LinkedList<Integer> indiceVtos = new LinkedList<Integer>();

		todosLosVencimientos = buscarVencimientosRojos(tipoVencimiento, convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {

			indiceVtos.add(todosLosVencimientos.get(i));
		}

		todosLosVencimientos = buscarVencimientosNaranja(tipoVencimiento, convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {

			indiceVtos.add(todosLosVencimientos.get(i));
		}

		todosLosVencimientos = buscarVencimientosAmarillo(tipoVencimiento, fecha);

		for (int i = 0; i < todosLosVencimientos.size(); i++) {

			indiceVtos.add(todosLosVencimientos.get(i));
		}

		todosLosVencimientos = buscarVencimientosVerdes(tipoVencimiento, Metodos.convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {
			indiceVtos.add(todosLosVencimientos.get(i));
		}

		return indiceVtos;

	}

	public static LinkedList<Integer> buscarVencimientosNaranja(LinkedList<String> lista, String busqueda) {

		LinkedList<Integer> repetido = new LinkedList<Integer>();

		LinkedList<Integer> resultado = new LinkedList<Integer>();

		LinkedList<Integer> meses31 = new LinkedList<Integer>();

		int dia, mes, year;

		meses31.add(1);

		meses31.add(3);

		meses31.add(5);

		meses31.add(7);

		meses31.add(8);

		meses31.add(10);

		meses31.add(12);

		dia = Integer.parseInt(busqueda.substring(0, busqueda.indexOf("/")));

		mes = Integer.parseInt(busqueda.substring(busqueda.indexOf("/") + 1, busqueda.lastIndexOf("/")));

		year = Integer.parseInt(busqueda.substring(busqueda.lastIndexOf("/") + 1, busqueda.length()));

		String ceromes = "";

		String cerodia = "";

		LinkedList<String> fechasNaranjas = new LinkedList<String>();

		if (dia >= 16 && !meses31.contains(mes)) {

			dia -= 15;

			++mes;
		}

		else {

			dia += 16;
		}

		for (int x = 0; x < 15; x++) {

			if (dia == 29 && mes == 2 && !esBisiesto(year) ||

					year < 3344 && dia == 30 && mes == 2 && esBisiesto(year) || dia == 31 && !meses31.contains(mes)
					|| dia == 32 && meses31.contains(mes)) {
				dia = 1;
				++mes;
			}

			if (dia == 32 && mes == 12) {
				dia = 1;
				++year;
			}

			if (mes <= 9) {
				ceromes = "0";
			} else {
				ceromes = "";
			}

			if (dia <= 9) {
				cerodia = "0";
			}

			else {
				cerodia = "";
			}

			busqueda = cerodia + dia + "/" + ceromes + mes + "/" + year;

			FormatoTabla.FechasVencimientosNaranjas.add(busqueda);

			fechasNaranjas.add(busqueda);

			++dia;

		}

		for (int i = 0; i < 15; i++) {

			repetido = buscarFechasVencimientos(lista, fechasNaranjas.get(i), 2);

			if (repetido.size() > 0) {

				for (int x = 0; x < repetido.size(); x++) {

					resultado.add(repetido.get(x));
				}

			}

		}

		return resultado;
	}

	public static LinkedList<Integer> buscarVencimientosVerdes(LinkedList<String> lista, String busqueda) {

		LinkedList<Integer> repetido = new LinkedList<Integer>();

		String cerodia = "";

		int dia, mes, year;

		dia = Integer.parseInt(busqueda.substring(0, busqueda.indexOf("/")));

		mes = Integer.parseInt(busqueda.substring(busqueda.indexOf("/") + 1, busqueda.lastIndexOf("/")));

		year = Integer.parseInt(busqueda.substring(busqueda.lastIndexOf("/") + 1, busqueda.length()));

		boolean mesEspecial = false;

		int vueltasComprobacion = 1;

		boolean cambioFebrero = false;

		String ceromes = "";

		if (dia != 29 && dia != 30 && dia != 31 && mes != 11 && mes != 12) {

			if (mes == 2 && dia == 28) {
				vueltasComprobacion = 3;
			}

			mes += 2;

		}

		else {

			if (mes == 11) {
				mes = 1;
				++year;
				mesEspecial = true;
			}

			if (mes == 12) {

				mes = 2;

				cambioFebrero = true;

				++year;

				if (dia == 29 || dia == 30 || dia == 31) {

					if ((dia == 31 || dia == 30) && year >= 3344) {
						dia = 30;
					}

					else {

						dia = 28;

						if (esBisiesto(year)) {

							dia = 29;

						}

					}
				}

				mesEspecial = true;
			}

			if ((!cambioFebrero && mes == 2 && dia == 29) || (dia == 30 && mes == 6)) {
				vueltasComprobacion = 2;
			}

			if (!mesEspecial) {
				mes += 2;
			}

		}

		for (int i = 0; i < vueltasComprobacion; i++) {

			if (i > 0) {
				++dia;
			}

			if (mes <= 9) {
				ceromes = "0";
			}

			else {
				ceromes = "";
			}

			if (dia <= 9) {
				cerodia = "0";
			}

			else {
				cerodia = "";
			}

			busqueda = cerodia + dia + "/" + ceromes + mes + "/" + year;

			FormatoTabla.FechasVencimientosVerdes.add(busqueda);

			repetido = buscarFechasVencimientos(lista, busqueda, 4);

		}

		return repetido;

	}

	public static String convertirFecha(String cadena, boolean tipo) {

		String fecha = "";

		try {

			String mes = "";

			String year = "";

			int dia;

			String mesCorto = "";

			String diaCorto = "";

			int mesFecha = 0;

			String busquedaMes = "";

			String busquedaDia = "";

			if (!cadena.isEmpty() && !cadena.contains("null") && cadena.length() > 2) {

				if (cadena.indexOf(" ") == -1) {

					busquedaDia = cadena.substring(0, cadena.indexOf("/"));

					busquedaMes = cadena.substring(cadena.indexOf("/") + 1, cadena.lastIndexOf("/"));

					if (busquedaDia.indexOf("0") == 0) {
						dia = Integer.parseInt(busquedaDia.substring(1, busquedaDia.length()));
						diaCorto = "0";
					}

					else {
						dia = Integer.parseInt(busquedaDia);
					}

					if (busquedaMes.contains("0")) {
						mesFecha = Integer.parseInt(busquedaMes.substring(1, busquedaMes.length()));
						mesCorto = "0";
					}

					else {
						mesFecha = Integer.parseInt(busquedaMes);
					}

					year = cadena.substring(cadena.lastIndexOf("/") + 1, cadena.length());

				} else {

					int limiteMes = cadena.indexOf(" ") + 4;

					year = cadena.substring(cadena.lastIndexOf(" ") + 1, cadena.length());

					mes = cadena.substring(cadena.indexOf(" ") + 1, limiteMes);
					cadena = cadena.substring(limiteMes + 1, cadena.length());
					dia = Integer.parseInt(cadena.substring(0, cadena.indexOf(" ")));

					switch (mes) {

					case "Jan":
						mesFecha = 1;
						break;

					case "Feb":
						mesFecha = 2;
						break;

					case "Mar":
						mesFecha = 3;
						break;

					case "Apr":
						mesFecha = 4;
						break;

					case "May":
						mesFecha = 5;
						break;

					case "Jun":
						mesFecha = 6;
						break;

					case "Jul":
						mesFecha = 7;
						break;

					case "Aug":
						mesFecha = 8;
						break;

					case "Sep":
						mesFecha = 9;
						break;

					case "Oct":
						mesFecha = 10;
						break;

					case "Nov":
						mesFecha = 11;
						break;

					case "Dec":
						mesFecha = 12;
						break;

					default:
						break;

					}
				}

				if (mesFecha <= 9) {
					mesCorto = "0";
				}

				if (dia <= 9) {
					diaCorto = "0";
				}

				if (tipo) {
					fecha = mesCorto + mesFecha + "/" + diaCorto + dia + "/" + year;
				} else {
					fecha = diaCorto + dia + "/" + mesCorto + mesFecha + "/" + year;
				}

			}
		} catch (Exception e) {
			fecha = "";
		}

		return fecha;
	}

	public static LinkedList<Integer> buscarFechasVencimientos(LinkedList<String> lista, String busqueda, int color) {

		LinkedList<Integer> repetido = new LinkedList<Integer>();

		Date date = new Date();

		int dia = date.getDay();

		int mes = date.getMonth();

		String cerodia = "";

		String ceromes = "";

		if (dia <= 9) {

			cerodia = "0";

		}

		else {

			cerodia = "";

		}

		if (mes <= 9) {

			ceromes = "0";

		}

		else {

			ceromes = "";

		}

		String hoy = cerodia + dia + "/" + ceromes + mes + "/" + date.getYear();

		int indice = -1;

		indice = lista.indexOf(busqueda);

		String pintura = "";

		switch (color) {

		case 1:
			pintura = "R";
			break;

		case 2:
			pintura = "O";
			break;

		case 3:

			if (hoy.toString().equals(busqueda)) {
				pintura = "V";
			}

			else {
				pintura = "A";
			}
			break;

		case 4:
			pintura = "V";
			break;

		default:
			pintura = "B";
			break;

		}
		try {

			while (indice != -1) {

				Vencimiento.contactosVencimientos.add(Menu.nombresChicas.get(indice));

				Vencimiento.colores.add(pintura);

				Vencimiento.colores.add(pintura);

				Vencimiento.colores.add(pintura);

				Vencimiento.colores.add(pintura);

				repetido.add(indice);

				lista.set(indice, null);

				indice = lista.indexOf(busqueda);

			}
		} catch (Exception e) {

		}
		return repetido;

	}

	public static LinkedList<String> directorio(String ruta, String extension, boolean filtro, boolean carpeta) {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			File f = new File(ruta);

			if (f.exists()) {

				File[] ficheros = f.listFiles();

				String fichero = "";

				String extensionArchivo;

				File folder;

				for (int x = 0; x < ficheros.length; x++) {

					fichero = ficheros[x].getName();

					folder = new File(ruta + fichero);

					extensionArchivo = extraerExtension(fichero);

					if (filtro) {

						if (folder.isFile()) {

							if ((extension.equals("images") && (extensionArchivo.equals("jpg")

									|| extensionArchivo.equals("png")))

									|| extension.equals(".") || extension.equals(extensionArchivo)) {

								if (carpeta) {

									lista.add(ruta + fichero);

								}

								else {

									lista.add(fichero);

								}

							}

						}

					}

					else {

						if (carpeta) {
							lista.add(ruta + fichero);
						}

						else {

							if (folder.isDirectory()) {

								if (!fichero.isEmpty()) {
									lista.add(fichero);
								}

							}

						}

					}

				}

			}

		}

		catch (Exception e) {

		}

		Collections.sort(lista);

		return lista;

	}

	@SuppressWarnings("static-access")
	public static void copiar(String cadena, String texto, int size) {

		if (!texto.isEmpty()) {

			Menu.alertas.mensaje(texto, 2, size);

			Menu.alertas.mensaje(texto, 2, size);

		}

		StringSelection stringSelection = new StringSelection(cadena);

		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		clipboard.setContents(stringSelection, null);

	}

	public static Connection conexionBD() throws IOException {

		try {

			connect = DriverManager.getConnection("jdbc:sqlite:" + new File(".").getCanonicalPath() + "\\webcamx.db");

			if (connect != null) {

			}

		}

		catch (Exception ex) {

		}

		return connect;

	}

	@SuppressWarnings("unchecked")

	public static void actualizarNombres() {

		try {

			Menu.nombresChicas = ponerNombreChicas();

			for (String nombre : Menu.nombresChicas) {

				Menu.nombreChica.addItem(nombre);

			}

			Menu.ponerNombreChica(true);

			if (!Menu.nombresChicas.isEmpty()) {

				Menu.numChicas.setText("/ " + Menu.nombresChicas.size());

				Menu.chica.setMaxValor(Menu.nombresChicas.size());

			}

		}

		catch (Exception e) {

			//

		}

	}

	public static void abrirCarpeta(String ruta) throws IOException {

		if (ruta != null && !ruta.equals("") && !ruta.isEmpty()) {

			try {

				Runtime.getRuntime().exec("cmd /c C:\\Windows\\explorer.exe " + "\"" + ruta + "\"");

			}

			catch (IOException e) {

			}
		}

	}

	public static LinkedList<String> saberConfig() throws IOException {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement("SELECT EMAIL,WEB,TELEFONO,PASS FROM CONFIG");

			result = st.executeQuery();

			result.next();

			lista.add(result.getString("EMAIL"));

			lista.add(result.getString("WEB"));

			lista.add(result.getString("TELEFONO"));

			lista.add(result.getString("PASS"));

		}

		catch (SQLException ex) {

		}

		return lista;

	}

	public static LinkedList<String> saberDatos(String nombre) throws IOException {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement(
					"SELECT NOMBRE,LOCALIDAD,PROVINCIA,NOMBRE_SUBIDA,DESCRIPCION,TELEFONO,EDAD,PRECIO FROM CHICAS WHERE NOMBRE='"
							+ nombre + "'");

			result = st.executeQuery();

			result.next();

			lista.add(result.getString("NOMBRE"));

			lista.add(result.getString("LOCALIDAD"));

			lista.add(result.getString("PROVINCIA"));

			lista.add(result.getString("NOMBRE_SUBIDA"));

			lista.add(result.getString("DESCRIPCION"));

			lista.add(result.getString("TELEFONO"));

			lista.add(result.getString("EDAD"));

			lista.add(result.getString("PRECIO"));

		}

		catch (SQLException ex) {

		}

		return lista;

	}

	public static LinkedList<String> datosLocalidad() throws IOException {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement("SELECT NOMBRE FROM LOCALIDADES");

			result = st.executeQuery();

			while (result.next()) {

				lista.add(result.getString("NOMBRE"));

			}

		} catch (SQLException ex) {

		}

		return lista;

	}

	public static LinkedList<String> datosProvincia() throws IOException {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement("SELECT NOMBRE FROM PROVINCIAS");

			result = st.executeQuery();

			while (result.next()) {

				lista.add(result.getString("NOMBRE"));

			}

		} catch (SQLException ex) {

		}

		return lista;

	}

	public static int numChicas() {

		int resultado = 0;

		try {

			PreparedStatement st = connect.prepareStatement("SELECT COUNT(NOMBRE) AS RECUENTO FROM CHICAS");

			result = st.executeQuery();

			result.next();

			resultado = Integer.parseInt(result.getString("RECUENTO"));

		}

		catch (Exception ex) {

		}

		return resultado;

	}

	public static LinkedList<String> ponerNombreChicas() {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement("SELECT NOMBRE FROM CHICAS ORDER BY NOMBRE");

			result = st.executeQuery();

			while (result.next()) {

				lista.add(result.getString("NOMBRE"));

			}

		}

		catch (Exception ex) {

		}

		return lista;

	}

	public static LinkedList<String> verConfig() {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement("SELECT EMAIL,WEB,TELEFONO,PASS FROM CONFIG");

			result = st.executeQuery();

			result.next();

			lista.add(result.getString("EMAIL"));

			lista.add(result.getString("WEB"));

			lista.add(result.getString("TELEFONO"));

			lista.add(result.getString("PASS"));

		}

		catch (Exception ex) {

		}

		return lista;

	}

	public static LinkedList<String> saberCookies(String pagina) throws IOException {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement(
					"SELECT VALOR_1,VALOR_2 FROM COOKIES WHERE PAGINA=(SELECT ID FROM PAGINAS WHERE NOMBRE='" + pagina
							+ "')");

			result = st.executeQuery();

			while (result.next()) {

				lista.add(result.getString("VALOR_1"));

				lista.add(result.getString("VALOR_2"));

			}

		}

		catch (SQLException ex) {

		}

		return lista;

	}

	public static String eliminarEspacios(String cadena) {

		cadena = cadena.replace("   ", "  ");

		cadena = cadena.replace("  ", " ");

		cadena = cadena.trim();

		return cadena;

	}

	public static LinkedList<String> datosIndiceProvincia(String localidad) {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement(
					"SELECT NOMBRE FROM PROVINCIAS WHERE ID=(SELECT PROVINCIAS FROM LOCALIDADES WHERE NOMBRE='"
							+ localidad + "')");

			result = st.executeQuery();

			result.next();

			lista.add(result.getString("NOMBRE"));

		}

		catch (Exception e) {

		}

		return lista;

	}

	public static LinkedList<String> datosIndiceLocalidad(String provincia) {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement(
					"SELECT NOMBRE FROM LOCALIDADES WHERE PROVINCIA=(SELECT ID FROM PROVINCIAS WHERE NOMBRE='"
							+ provincia + "')");

			result = st.executeQuery();

			while (result.next()) {

				lista.add(result.getString("NOMBRE"));

			}

		}

		catch (Exception e) {

		}

		return lista;

	}

	public static void eliminarWebcamer(Object selectedItem) {

		try {

			PreparedStatement st;

			String sql = "DELETE FROM CHICAS WHERE NOMBRE='" + selectedItem.toString() + "'";

			st = Metodos.connect.prepareStatement(sql);

			st.executeUpdate();

			Menu.nombreChica.removeAllItems();

			actualizarNombres();

			Config.listaWebcamers.removeAll();

			Config.verChicas();

		}

		catch (SQLException e) {

		}

	}

	public static String aumentarDia(Date fecha, int valor) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(fecha);

		int calendarTime = Calendar.DAY_OF_MONTH;

		int temp = calendar.get(calendarTime);

		calendar.set(calendarTime, temp + valor);

		Date newDate = calendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String date = sdf.format(newDate);

		return date;

	}

	public static void convertir(String extension, String salida, String carpeta) {

		LinkedList<String> listaImagenes = directorio(carpeta, extension, true, true);

		int resto = 3;

		if (extension.length() == 4) {

			resto = 5;

		}

		for (int i = 0; i < listaImagenes.size(); i++) {

			File f1 = new File(carpeta + listaImagenes.get(i));

			File f2 = new File(
					carpeta + listaImagenes.get(i).substring(0, listaImagenes.get(i).length() - resto) + "." + salida);

			f1.renameTo(f2);

		}

	}

	public static LinkedList<String> verVencimientosWebcamers() throws ParseException {

		Vencimiento.caducidades = Metodos.verCaducidadesWebcamers();

		Vencimiento.paginas = Metodos.verPaginasVencimientosWebcamers();

		Vencimiento.nombresWebcamersCaducidades = Metodos.verNombresWebcamers();

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect
					.prepareStatement("SELECT FECHA_SUBIDA FROM CADUCIDADES ORDER BY FECHA_SUBIDA");

			result = st.executeQuery();

			while (result.next()) {

				lista.add(result.getString("FECHA_SUBIDA"));

			}

		}

		catch (Exception e) {

		}

		for (int i = 0; i < lista.size(); i++) {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			lista.set(i,
					Metodos.aumentarDia(sdf.parse(lista.get(i)), Integer.parseInt(Vencimiento.caducidades.get(i))));

		}

		return lista;

	}

	public static LinkedList<String> verCaducidadesWebcamers() {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement("SELECT CADUCIDAD FROM CADUCIDADES ORDER BY FECHA_SUBIDA");

			result = st.executeQuery();

			while (result.next()) {

				lista.add(result.getString("CADUCIDAD"));

			}

		}

		catch (Exception e) {

		}

		return lista;

	}

	public static LinkedList<String> verNombresWebcamers() {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement(
					"SELECT NOMBRE FROM CHICAS C JOIN CADUCIDADES CA ON C.ID=CA.CHICA ORDER BY CA.FECHA_SUBIDA");

			result = st.executeQuery();

			while (result.next()) {

				lista.add(result.getString("NOMBRE"));

			}

		}

		catch (Exception e) {

		}

		return lista;

	}

	public static LinkedList<String> verPaginas() {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement("SELECT NOMBRE FROM PAGINAS ORDER BY NOMBRE");

			result = st.executeQuery();

			while (result.next()) {

				lista.add(result.getString("NOMBRE"));

			}

		}

		catch (Exception e) {

		}

		return lista;

	}

	public static LinkedList<String> verPaginasVencimientosWebcamers() {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement(
					"SELECT NOMBRE FROM PAGINAS P JOIN CADUCIDADES C ON P.ID=C.PAGINA ORDER BY C.FECHA_SUBIDA;");

			result = st.executeQuery();

			while (result.next()) {

				lista.add(result.getString("NOMBRE"));

			}

		}

		catch (Exception e) {

		}

		return lista;

	}

	public static String saberIdPagina(String nombre) {

		String resultado = "";

		try {

			PreparedStatement st = connect.prepareStatement("SELECT ID FROM PAGINAS WHERE NOMBRE='" + nombre + "'");

			result = st.executeQuery();

			result.next();

			resultado = result.getString("ID");

		}

		catch (Exception e) {

		}

		return resultado;

	}

	public static String saberIdChica(String nombre) {

		String resultado = "";

		try {

			PreparedStatement st = connect.prepareStatement("SELECT ID FROM CHICAS WHERE NOMBRE='" + nombre + "'");

			result = st.executeQuery();

			result.next();

			resultado = result.getString("ID");

		}

		catch (Exception e) {

		}

		return resultado;

	}

	public static LinkedList<String> saberCaducidades(String nombre, String pagina) {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement(
					"SELECT FECHA_SUBIDA,CADUCIDAD FROM CADUCIDADES WHERE CHICA=(SELECT ID FROM CHICAS WHERE NOMBRE='"
							+ nombre + "') AND PAGINA=(SELECT ID FROM PAGINAS WHERE NOMBRE='" + pagina + "')");

			result = st.executeQuery();

			result.next();

			lista.add(result.getString("FECHA_SUBIDA"));

			lista.add(result.getString("CADUCIDAD"));

		}

		catch (SQLException ex) {

		}

		return lista;

	}

	public static LinkedList<String> saberPaginasCaducidades(String webcamer) {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement(
					"SELECT NOMBRE FROM PAGINAS P JOIN CADUCIDADES C ON P.ID=C.PAGINA WHERE C.CHICA=(SELECT ID FROM CHICAS WHERE NOMBRE='"
							+ webcamer + "') ORDER BY C.FECHA_SUBIDA");

			result = st.executeQuery();

			while (result.next()) {

				lista.add(result.getString("NOMBRE"));
			}

		}

		catch (SQLException ex) {

		}

		return lista;

	}

	public static void saberPaginas() {

		for (String valor : ponerPaginas()) {

			Menu.pagina.addItem(valor);

		}

	}

	private static LinkedList<String> ponerPaginas() {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			PreparedStatement st = connect.prepareStatement("SELECT * FROM PAGINAS ORDER BY NOMBRE");

			result = st.executeQuery();

			while (result.next()) {

				lista.add(result.getString("NOMBRE"));

			}

		}

		catch (Exception ex) {

		}

		return lista;

	}

	public static void eliminarCaducidad(String webcamer, String pagina) {

		try {

			PreparedStatement st;

			String sql = "DELETE FROM CADUCIDADES WHERE CHICA=(SELECT ID FROM CHICAS WHERE NOMBRE='" + webcamer
					+ "') AND PAGINA=(SELECT ID FROM PAGINAS WHERE NOMBRE='" + pagina + "')";

			st = Metodos.connect.prepareStatement(sql);

			st.executeUpdate();

		}

		catch (SQLException e) {

		}

	}

	public static void eliminarTodasLasCaducidades(String webcamer) {

		try {

			PreparedStatement st;

			String sql = "DELETE FROM CADUCIDADES WHERE CHICA=(SELECT ID FROM CHICAS WHERE NOMBRE='" + webcamer + "')";

			st = Metodos.connect.prepareStatement(sql);

			st.executeUpdate();

		}

		catch (SQLException e) {

		}

	}

}
