package org.newdawn.slick.muffin;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import org.newdawn.slick.util.Log;

/**
 * An implementation of the muffin load/save mechanism based around using the
 * local file system.
 * 
 * @author kappaOne
 */
public class FileMuffin implements Muffin {

	/**
	 * @see org.newdawn.slick.muffin.Muffin#saveFile(java.util.HashMap,
	 *      java.lang.String)
	 */
	public void saveFile(HashMap<Object, Object> scoreMap, String fileName) throws IOException {
		String userHome = System.getProperty("user.home");
		File file = new File(userHome);
		file = new File(file, ".java");
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(file, fileName);
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		// save hashMap
		oos.writeObject(scoreMap);

		oos.close();
	}

	/**
	 * @see org.newdawn.slick.muffin.Muffin#loadFile(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public HashMap<Object, Object> loadFile(String fileName) throws IOException {
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		String userHome = System.getProperty("user.home");

		File file = new File(userHome);
		file = new File(file, ".java");
		file = new File(file, fileName);

		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				
				Object obj = ois.readObject();
				if (obj instanceof HashMap<?, ?>){
					hashMap = (HashMap<Object, Object>) obj;
				}

				ois.close();

			} catch (EOFException e) {
				// End of the file reached, do nothing
			} catch (ClassNotFoundException e) {
				Log.error(e);
				throw new IOException("Failed to pull state from store - class not found");
			}
		}

		return hashMap;
	}
}