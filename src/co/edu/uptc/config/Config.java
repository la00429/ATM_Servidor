package co.edu.uptc.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * Clase encargada de cargar mensajes desde un archivo de propiedades y
 * asignarlos a un objeto Message usando reflexión.
 */
public class Config {
	/**
	 * Carga los mensajes desde un archivo de propiedades y los asigna a un objeto
	 * Message utilizando reflexión para acceder a los campos del objeto.
	 */
	public void loadMessages() {
		Message message = new Message();
		try {
			Class<?> classMyObj = message.getClass();
			Field[] fields = classMyObj.getDeclaredFields();
			String[] messagesAll = loadProperties();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				fields[i].set(message, messagesAll[i]);
			}
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IllegalArgumentException e) {
			e.getMessage();
		} catch (IllegalAccessException e) {
			e.getMessage();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Carga las propiedades desde un archivo de configuración y devuelve un array
	 * de strings con los mensajes ordenados.
	 *
	 * @return Array de strings con los mensajes ordenados.
	 * @throws FileNotFoundException Si el archivo de configuración no es
	 *                               encontrado.
	 * @throws IOException           Si ocurre un error durante la lectura del
	 *                               archivo.
	 */
	private String[] loadProperties() throws FileNotFoundException, IOException {
		Properties file = new Properties();
		file.load(new FileReader("config/config.properties"));
		ArrayList<String> fileOrder = keysOrder(file);
		String[] messagesAll = new String[fileOrder.size()];
		for (int i = 0; i < fileOrder.size(); i++) {
			messagesAll[i] = file.getProperty((String) fileOrder.get(i));
		}
		return messagesAll;
	}

	/**
	 * Devuelve una lista ordenada de las claves (keys) del archivo de propiedades.
	 *
	 * @param file El archivo de propiedades del que se extraen las claves.
	 * @return Lista ordenada de las claves del archivo de propiedades.
	 */
	private ArrayList<String> keysOrder(Properties file) {
		Hashtable<Object, Object> messagesOrder = file;
		Object[] valores = messagesOrder.keySet().toArray();
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < valores.length; i++) {
			list.add((String) valores[i]);
		}
		Collections.sort(list);
		return list;
	}

}
