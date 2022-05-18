
package utils;

import java.awt.Component;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import main.Menu;
import main.Vencimiento;

public class FormatoTabla extends DefaultTableCellRenderer {
	public FormatoTabla() {
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

		return repetido;

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

	int paso = 0;

	public static LinkedList<String> FechasVencimientosRojos = new LinkedList<String>();

	public static LinkedList<String> FechasVencimientosVerdes = new LinkedList<String>();

	public static LinkedList<String> FechasVencimientosNaranjas = new LinkedList<String>();

	public static LinkedList<String> FechasVencimientosAmarillos = new LinkedList<String>();

	int indice = 0;

	String fecha;

	@Override

	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
			int row, int column) {

		if (indice < table.getRowCount()) {

			fecha = table.getValueAt(indice, 2).toString();

			if (FechasVencimientosRojos.contains(fecha)) {

				table.setValueAt("- 15 DÍAS", indice, 3);

			}

			if (FechasVencimientosNaranjas.contains(fecha)) {

				table.setValueAt("+15 DÍAS <--> 1 MES", indice, 3);

			}

			if (FechasVencimientosAmarillos.contains(fecha)) {

				table.setValueAt("+1 MES <--> -2 MESES", indice, 3);

			}

			if (FechasVencimientosVerdes.contains(fecha)) {

				table.setValueAt("¡SUBIR YA!", indice, 3);

			}

			++indice;

		}

		super.getTableCellRendererComponent(table, value, selected, focused, row, column);

		return this;

	}

}