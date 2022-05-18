package main;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("all")

public class About extends Thread implements ActionListener, ChangeListener {

	static JLabel lab = new JLabel("Ramón Jesús Gómez Carmona");

	static JLabel email = new JLabel("smr2gocar@gmail.com");

	static boolean andar = true;

	@Override

	public void run() {

		try {

			scrollEffect();
		}

		catch (InterruptedException e) {
			//
		}

	}

	public About() {

		JFrame jf = new JFrame("Scroll Effect");

		jf.addWindowListener(new WindowAdapter() {

			@Override

			public void windowClosing(WindowEvent e) {

				andar = false;

			}

		});

		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		jf.setAlwaysOnTop(true);

		jf.setTitle("Sobre");

		jf.setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/imagenes/about.png")));

		JPanel jp = new JPanel();

		jf.setSize(600, 350);

		jp.setSize(800, 600);

		lab.setIcon(new ImageIcon(About.class.getResource("/imagenes/dev.png")));

		lab.setBounds(184, 251, 340, 48);

		lab.setHorizontalAlignment(SwingConstants.CENTER);

		lab.setFont(new Font("Arial", Font.PLAIN, 20));

		jp.setLayout(null);

		jp.add(lab);

		jf.getContentPane().add(jp);

		email.setHorizontalAlignment(SwingConstants.CENTER);

		email.setIcon(new ImageIcon(About.class.getResource("/imagenes/email.png")));

		email.setFont(new Font("Arial", Font.PLAIN, 20));

		email.setBounds(194, 319, 330, 42);

		jp.add(email);

		JLabel lblNewLabel_1 = new JLabel("Programa creado por");

		lblNewLabel_1.setIcon(new ImageIcon(About.class.getResource("/imagenes/created.png")));

		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);

		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 20));

		lblNewLabel_1.setBounds(184, 12, 340, 53);

		jp.add(lblNewLabel_1);

		jf.setVisible(true);

		jf.setLocationRelativeTo(null);

	}

	public static void scrollEffect() throws InterruptedException {

		int x = -80;

		while (andar) {

			if (x == -196) {

				x = -80;

			}

			x--;

			lab.setBounds(-120, x, 800, 600);

			email.setBounds(170, x + 25, 300, 700);

			Thread.sleep(20);

		}

	}

	public void stateChanged(ChangeEvent arg0) {

	}

	public void actionPerformed(ActionEvent arg0) {

	}

}