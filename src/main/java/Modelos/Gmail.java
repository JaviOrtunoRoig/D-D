package Modelos;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Gmail {
	
	// Socket para las comunicaciones
	static SSLSocket socket = null;
	// Streams para el env�o y recepci�n
	static InputStream in = null;
	static OutputStream out = null;
	
	static final String usuario = "elrodricordobes.99@gmail.com";
	static final String clave = "ovdecjaulxolmmqu";
	
	// Esta funci�n se conecta al servidor de SMTP
	private void conectar(){
		try {
			SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
	        // Conectamos el socket al servidor de correo de gmail en el puerto 465
			socket = (SSLSocket) f.createSocket("smtp.gmail.com", 465);

			in = socket.getInputStream();
			out = socket.getOutputStream();
			
		} catch (IOException e) {
			System.exit(-1);
		}
	}
	
	// Esta funci�n cierra el socket
	private void desconectar(){
		try {
			in.close();
			out.close();
			socket.close();

		} catch (IOException e) {
			System.exit(-1);
		}
	}
	
	// Env�o de mensajes
	private void enviar(String mensaje){
		// Mostramos por consola el mensaje a enviar
        try {
			mensaje = mensaje + "\r\n";

			byte[] arrayBytes = mensaje.getBytes();

			out.write(arrayBytes);

        	// Usamos flush para forzar que el envio se haga en este momento
	        out.flush();
	    } catch (IOException e) {
			System.exit(-1);
		}
	}
	
	// Recepci�n de mensajes
	private void recibir(){
        byte [] buffer = new byte[5000];
        try {
			in.read(buffer);

        } catch (IOException e) {
			System.exit(-1);
		}
        // Convertimos el mensaje recibido a String ...
        String recv = new String(buffer,0,buffer.length);
        // ... eliminamos el \r\n final ...
        recv = recv.substring(0,recv.lastIndexOf('\r'));
        // ... y lo mostramos por pantalla

        // TODO: Validar si el c�digo obtenido es correcto (1xx, 2xx, 3xx) o incorrecto (4xx, 5xx)
		boolean incorrecto = false;
		/*if (codigo == -1) {
			System.err.println("Error al leer el mensaje");
		} else if (codigo / 100 == 4 || codigo / 100 == 5) {
			incorrecto = true;
		}*/

        if (false) { //cambiar por incorrecto
			// TODO: Si es incorrecto enviar al servidor un RSET (use el m�todo enviar)
			enviar("RSET");

			// TODO: Env�e luego el comando QUIT
			enviar("QUIT");

			// Desconectamos del servidor
			desconectar();
			System.exit(0);
		}
	}

	public void enviarCorreo(String destinatario, String password) {
		// Flujo de lectura de teclado:
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		
		// Nos conectamos al servidor
		conectar();

		// ESQUEMA DEL PROTOCOLO SMTP
		
		// Recibimos el saludo inicial del servidor
		recibir();
		
		// Enviamos el EHLO y recibimos su respuesta
		enviar("EHLO smtp.google.com");
		recibir();

		enviar("AUTH LOGIN");
		recibir();

		// Enviamos el usuario y la contrase�a codificadas en Base64 y recibimos ambas respuestas
        String user = Base64.getEncoder().encodeToString(usuario.getBytes());
        String pass = Base64.getEncoder().encodeToString(clave.getBytes());
        enviar(user);
        recibir();
        enviar(pass);
        recibir();

		// TODO: enviar origen del mensaje y su recibir su respuesta
		enviar("MAIL FROM: <" + usuario + ">");
		recibir();

		// Ahora los destinos
		enviar("RCPT TO: <" + destinatario + ">");
				recibir();

		// Ahora enviamos el correo: cabeceras + cuerpo
		// TODO: Enviamos el DATA y recibimos la respuesta
		enviar("DATA");
		recibir();

		// Cabeceras:
		// TODO: Enviar la cabecera From: (no hay que recibir respuesta)
		enviar("From: " + usuario);

		// TODO: Enviar las cabeceras To: (no hay que recibir respuesta)
		enviar("To: " + destinatario);
		
		// Leemos el asunto:
		String asunto = "Recuperar Contraseña";

		// TODO: enviar la cabecera Subject: (no hay que recibir respuesta)
		enviar("Subject: " + asunto);

		// Enviamos una l�nea en blanco para separar las cabeceras del cuerpo
		enviar("\n");
	
		// Ahora el cuerpo que son muchas l�neas
		String cuerpo = "Su contraseña es: " + password;
		enviar(cuerpo);

		enviar("\n");
		enviar(".");
		recibir();
		
		enviar("QUIT");
		recibir();
	}
}
